/*
 * Copyright 2005-2013 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kra.protocol.protocol.funding.impl;

import org.kuali.kra.bo.SpecialReviewApprovalType;
import org.kuali.kra.common.specialreview.service.impl.SpecialReviewServiceImpl;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.kra.infrastructure.KraServiceLocator;
import org.kuali.kra.infrastructure.RoleConstants;
import org.kuali.kra.proposaldevelopment.ProposalDevelopmentUtils;
import org.kuali.kra.proposaldevelopment.bo.DevelopmentProposal;
import org.kuali.kra.proposaldevelopment.bo.ProposalPerson;
import org.kuali.kra.proposaldevelopment.document.ProposalDevelopmentDocument;
import org.kuali.kra.proposaldevelopment.service.KeyPersonnelService;
import org.kuali.kra.proposaldevelopment.service.ProposalDevelopmentService;
import org.kuali.kra.proposaldevelopment.service.impl.KeyPersonnelServiceImpl;
import org.kuali.kra.proposaldevelopment.specialreview.ProposalSpecialReview;
import org.kuali.kra.protocol.ProtocolBase;
import org.kuali.kra.protocol.ProtocolDocumentBase;
import org.kuali.kra.protocol.ProtocolFormBase;
import org.kuali.kra.protocol.protocol.ProtocolHelperBase;
import org.kuali.kra.protocol.protocol.funding.ProtocolFundingSourceBase;
import org.kuali.kra.protocol.protocol.funding.ProtocolProposalDevelopmentDocumentService;
import org.kuali.kra.service.KraAuthorizationService;
import org.kuali.kra.service.PersonEditableService;
import org.kuali.rice.krad.bo.DocumentHeader;
import org.kuali.rice.krad.service.DocumentService;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.GlobalVariables;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

/**
 * 
 * This service creates Proposal Development Document from ProtocolBase for users authorized to create proposal. This created
 * proposal is then added to ProtocolBase Funding sources. 
 */
public abstract class ProtocolProposalDevelopmentDocumentServiceImplBase implements ProtocolProposalDevelopmentDocumentService {

    @Override
    public ProposalDevelopmentDocument createProposalDevelopmentDocument(ProtocolFormBase protocolForm) throws Exception {
        ProposalDevelopmentDocument proposalDevelopmentDocument = null;
        ProtocolBase protocol = protocolForm.getProtocolDocument().getProtocol();
        if(isAuthorizedCreateProposal(protocolForm.getProtocolHelper()))
        {
            DocumentService docService = KRADServiceLocatorWeb.getDocumentService();       
            proposalDevelopmentDocument = (ProposalDevelopmentDocument) docService.getNewDocument(ProposalDevelopmentDocument.class);
            ProposalDevelopmentService proposalDevelopmentService = KraServiceLocator.getService(ProposalDevelopmentService.class);
            populateDocumentOverview(protocol, proposalDevelopmentDocument);
            populateRequiredFields(protocol, proposalDevelopmentDocument);
            proposalDevelopmentService.initializeUnitOrganizationLocation(proposalDevelopmentDocument);       
            proposalDevelopmentService.initializeProposalSiteNumbers(proposalDevelopmentDocument);
            populateProposalPerson_Investigator(protocol, proposalDevelopmentDocument);
            populateProposalSpecialReview(protocol, proposalDevelopmentDocument);

            docService.saveDocument(proposalDevelopmentDocument);
            initializeAuthorization(proposalDevelopmentDocument);        
        }
        return proposalDevelopmentDocument;
    }
   

    protected void populateDocumentOverview(ProtocolBase protocol, ProposalDevelopmentDocument proposalDocument)
    {
        ProtocolDocumentBase protocolDocument = protocol.getProtocolDocument();
        DocumentHeader proposalDocumentHeader = proposalDocument.getDocumentHeader();
        DocumentHeader protocolDocumentHeader = protocolDocument.getDocumentHeader();
      
        proposalDocumentHeader.setDocumentDescription("PD - " + protocolDocumentHeader.getDocumentDescription());
        proposalDocumentHeader.setExplanation("Document created from ProtocolBase - "+protocolDocument.getDocumentNumber());
        proposalDocumentHeader.setOrganizationDocumentNumber(protocolDocumentHeader.getOrganizationDocumentNumber());

    }


    protected void populateRequiredFields(ProtocolBase protocol, ProposalDevelopmentDocument proposalDocument)
    throws Exception
    {
        DevelopmentProposal developmentProposal = proposalDocument.getDevelopmentProposal();

        developmentProposal.setTitle(protocol.getTitle());
        developmentProposal.setOwnedByUnit(protocol.getLeadUnit().getUnit());
        developmentProposal.setOwnedByUnitNumber(protocol.getLeadUnitNumber());
        developmentProposal.setRequestedStartDateInitial(new Date(System.currentTimeMillis()));

        String projectEndDateParameter = getProjectEndDateNumberOfYearsHook();
        int numberOfYears = Integer.parseInt(projectEndDateParameter);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, numberOfYears);
        calendar.add(Calendar.DATE, -1);
        Date projectEndDate = new Date(calendar.getTimeInMillis());
        developmentProposal.setRequestedEndDateInitial(projectEndDate);

        String activityTypeCode = ProposalDevelopmentUtils.getProposalDevelopmentDocumentParameter(ProposalDevelopmentUtils.ACTIVITY_TYPE_CODE_RESEARCH_PARM);
        String proposalTypeCode = ProposalDevelopmentUtils.getProposalDevelopmentDocumentParameter(ProposalDevelopmentUtils.PROPOSAL_TYPE_CODE_NEW_PARM);

        developmentProposal.setActivityTypeCode(activityTypeCode);
        developmentProposal.setProposalTypeCode(proposalTypeCode);
                
        // find sponsor from funding source
        List<ProtocolFundingSourceBase> protocolFundingSources = protocol.getProtocolFundingSources();
        ProtocolFundingSourceBase sponsorProtocolFundingSource = null; 
        for(ProtocolFundingSourceBase protocolFundingSource : protocolFundingSources)
        {
            if ( protocolFundingSource.isSponsorFunding() )
            {
                sponsorProtocolFundingSource = protocolFundingSource;
                break;
            }
        }
        if(sponsorProtocolFundingSource != null)
        {
            developmentProposal.setSponsorCode(sponsorProtocolFundingSource.getFundingSourceNumber());
        }

    }

    /**
     * Initialize the Authorizations for a new proposal.  The initiator/creator
     * is assigned the Aggregator role.
     * @param doc the proposal development document
     */
    protected void initializeAuthorization(ProposalDevelopmentDocument document) {
        String userId = GlobalVariables.getUserSession().getPrincipalId();
        KraAuthorizationService kraAuthService = KraServiceLocator.getService(KraAuthorizationService.class);
        kraAuthService.addRole(userId, RoleConstants.AGGREGATOR, document);
    }


    protected void populateProposalPerson_Investigator(ProtocolBase protocol, ProposalDevelopmentDocument proposalDocument) {
        ProposalPerson proposalPerson = new ProposalPerson();

        proposalPerson.setPersonId(protocol.getPrincipalInvestigatorId());
        PersonEditableService personEditableService = KraServiceLocator.getService(PersonEditableService.class);
        personEditableService.populateContactFieldsFromPersonId(proposalPerson);

        proposalPerson.setProposalPersonRoleId(Constants.PRINCIPAL_INVESTIGATOR_ROLE);
        
        proposalPerson.setDevelopmentProposal(proposalDocument.getDevelopmentProposal());
        proposalPerson.setProposalNumber(proposalDocument.getDevelopmentProposal().getProposalNumber());
        proposalPerson.setProposalPersonNumber(new Integer(1));

        proposalPerson.setOptInUnitStatus("Y");
        proposalPerson.setOptInCertificationStatus("Y");
        proposalDocument.getDevelopmentProposal().getProposalPersons().add(proposalPerson);

        KeyPersonnelService keyPersonnelService = (KeyPersonnelServiceImpl) KraServiceLocator.getService(KeyPersonnelService.class);
        keyPersonnelService.populateProposalPerson(proposalPerson, proposalDocument);
        keyPersonnelService.assignLeadUnit(proposalPerson, proposalDocument.getDevelopmentProposal().getOwnedByUnitNumber());
    
    }


    protected void populateProposalSpecialReview(ProtocolBase protocol, ProposalDevelopmentDocument proposalDocument)
    {
    if (protocol != null) {
        Integer specialReviewNumber = proposalDocument.getDocumentNextValue(Constants.SPECIAL_REVIEW_NUMBER);
        
        ProposalSpecialReview specialReview = new ProposalSpecialReview();
        specialReview.setSpecialReviewNumber(specialReviewNumber);
        specialReview.setSpecialReviewTypeCode(getSpecialReviewTypeHook());
        specialReview.setApprovalTypeCode(SpecialReviewApprovalType.PENDING);
        specialReview.setProtocolNumber(protocol.getProtocolNumber());
        specialReview.setProposalNumber(proposalDocument.getDevelopmentProposal().getProposalNumber());
        
        specialReview.setProtocolStatus(protocol.getProtocolStatus().getDescription());
        specialReview.setComments(SpecialReviewServiceImpl.NEW_SPECIAL_REVIEW_COMMENT);
        proposalDocument.getDevelopmentProposal().getPropSpecialReviews().add(specialReview);
        }
    }


    protected boolean isAuthorizedCreateProposal(ProtocolHelperBase protocolHelper) {
        // TODO Auto-generated method stub
        boolean canCreateProposal = protocolHelper.isCanCreateProposalDevelopment();
        return canCreateProposal;
    }


    protected abstract String getProjectEndDateNumberOfYearsHook();
    protected abstract String getSpecialReviewTypeHook();

}
