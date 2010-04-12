/*
 * Copyright 2006-2008 The Kuali Foundation
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
package org.kuali.kra.institutionalproposal.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.kra.bo.CustomAttribute;
import org.kuali.kra.bo.CustomAttributeDocument;
import org.kuali.kra.bo.versioning.VersionStatus;
import org.kuali.kra.budget.core.Budget;
import org.kuali.kra.budget.distributionincome.BudgetCostShare;
import org.kuali.kra.budget.distributionincome.BudgetUnrecoveredFandA;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.kra.institutionalproposal.ProposalStatus;
import org.kuali.kra.institutionalproposal.contacts.InstitutionalProposalPerson;
import org.kuali.kra.institutionalproposal.contacts.InstitutionalProposalPersonCreditSplit;
import org.kuali.kra.institutionalproposal.contacts.InstitutionalProposalPersonUnit;
import org.kuali.kra.institutionalproposal.contacts.InstitutionalProposalPersonUnitCreditSplit;
import org.kuali.kra.institutionalproposal.customdata.InstitutionalProposalCustomData;
import org.kuali.kra.institutionalproposal.document.InstitutionalProposalDocument;
import org.kuali.kra.institutionalproposal.exception.InstitutionalProposalCreationException;
import org.kuali.kra.institutionalproposal.home.InstitutionalProposal;
import org.kuali.kra.institutionalproposal.home.InstitutionalProposalCostShare;
import org.kuali.kra.institutionalproposal.home.InstitutionalProposalSpecialReview;
import org.kuali.kra.institutionalproposal.home.InstitutionalProposalUnrecoveredFandA;
import org.kuali.kra.institutionalproposal.service.InstitutionalProposalService;
import org.kuali.kra.institutionalproposal.service.InstitutionalProposalVersioningService;
import org.kuali.kra.proposaldevelopment.bo.DevelopmentProposal;
import org.kuali.kra.proposaldevelopment.bo.PropScienceKeyword;
import org.kuali.kra.proposaldevelopment.bo.ProposalPerson;
import org.kuali.kra.proposaldevelopment.bo.ProposalPersonCreditSplit;
import org.kuali.kra.proposaldevelopment.bo.ProposalPersonUnit;
import org.kuali.kra.proposaldevelopment.bo.ProposalSpecialReview;
import org.kuali.kra.proposaldevelopment.bo.ProposalUnitCreditSplit;
import org.kuali.kra.service.VersionException;
import org.kuali.kra.service.VersioningService;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.DocumentService;
import org.kuali.rice.kns.service.SequenceAccessorService;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.KualiDecimal;
import org.kuali.rice.kns.util.ObjectUtils;
import org.mortbay.log.Log;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class provides the default implementation of the InstitutionalProposalService.
 */
@Transactional
public class InstitutionalProposalServiceImpl implements InstitutionalProposalService {
    
    private static final String KC_SYSTEM_USER = "quickstart";
    private static final String WORKFLOW_EXCEPTION_MESSAGE = "Caught workflow exception creating new Institutional Proposal";
    private static final String VERSION_EXCEPTION_MESSAGE = "Caught version exception creating new Institutional Proposal";
    private static final String BLANKET_APPROVE_MESSAGE = "Autogenerated Institutional Proposal from Development Proposal ";
    private static final String NO_PRIOR_VERSION_MESSAGE = "Tried to version an InstitutionalProposal where no prior version exists.";
    private static final String NEW_DOCUMENT_DESCRIPTION = "Generated by Dev Proposal ";
    private static final String DECIMAL_FORMAT = "00000000";
    
    private BusinessObjectService businessObjectService;
    private DocumentService documentService;
    private VersioningService versioningService;
    private InstitutionalProposalVersioningService institutionalProposalVersioningService;
    private SequenceAccessorService sequenceAccessorService;
    
    /**
     * Creates a new pending Institutional Proposal based on given development proposal and budget.
     * 
     * @param developmentProposal DevelopmentProposal
     * @param budget Budget
     * @return String The new proposal number
     * @see org.kuali.kra.institutionalproposal.service.InstitutionalProposalService#createInstitutionalProposal(DevelopmentProposal, Budget)
     */
    public String createInstitutionalProposal(DevelopmentProposal developmentProposal, Budget budget) {
        
        GlobalVariables.getUserSession().setBackdoorUser(KC_SYSTEM_USER);
        
        try {
            InstitutionalProposal institutionalProposal = new InstitutionalProposal();
            
            // Set proposal number on new Institutional Proposal so that it will be propagated to all created child BO's before initial save.
            Long nextProposalNumber = sequenceAccessorService.getNextAvailableSequenceNumber(Constants.INSTITUTIONAL_PROPSAL_PROPSAL_NUMBER_SEQUENCE);
            DecimalFormat formatter = new DecimalFormat(DECIMAL_FORMAT);
            String nextProposalNumberAsString = formatter.format(nextProposalNumber);
            institutionalProposal.setProposalNumber(nextProposalNumberAsString);
            
            InstitutionalProposalDocument institutionalProposalDocument = mergeProposals(institutionalProposal, developmentProposal, budget);
            documentService.blanketApproveDocument(institutionalProposalDocument, 
                    BLANKET_APPROVE_MESSAGE + developmentProposal.getProposalNumber(), 
                    new ArrayList<Object>());
            return institutionalProposalDocument.getInstitutionalProposal().getProposalNumber();
        } catch (WorkflowException ex) {
            throw new InstitutionalProposalCreationException(WORKFLOW_EXCEPTION_MESSAGE, ex);
        } finally {
            resetUserSession();
        }
    }
    
    /**
     * Creates a new active version of the Institutional Proposal corresponding to the given proposal number, 
     * with data copied from the given development proposal and budget.
     * 
     * @param proposalNumber String
     * @param developmentProposal DevelopmentProposal
     * @param budget Budget
     * @return String The new version number
     * @see org.kuali.kra.institutionalproposal.service.InstitutionalProposalService#createInstitutionalProposalVersion(String, DevelopmentProposal, Budget)
     */
    public String createInstitutionalProposalVersion(String proposalNumber, DevelopmentProposal developmentProposal, Budget budget) {
        
        GlobalVariables.getUserSession().setBackdoorUser(KC_SYSTEM_USER);
        
        try {
            InstitutionalProposalDocument newInstitutionalProposalDocument = versionProposal(proposalNumber, developmentProposal, budget);
            documentService.blanketApproveDocument(newInstitutionalProposalDocument, 
                    BLANKET_APPROVE_MESSAGE + developmentProposal.getProposalNumber(), 
                    new ArrayList<Object>());
            institutionalProposalVersioningService.updateInstitutionalProposalVersionStatus(
                    newInstitutionalProposalDocument.getInstitutionalProposal(), VersionStatus.ACTIVE);
            return newInstitutionalProposalDocument.getInstitutionalProposal().getSequenceNumber().toString();
        } catch (WorkflowException we) {
            throw new InstitutionalProposalCreationException(WORKFLOW_EXCEPTION_MESSAGE, we);
        } catch (VersionException ve) {
            throw new InstitutionalProposalCreationException(VERSION_EXCEPTION_MESSAGE, ve);
        } finally {
            resetUserSession();
        }
    }
    
    /**
     * Return the PENDING version of an Institutional Proposal, if one exists.
     * Note, PENDING here refers to the Version Status, NOT the Proposal Status of the Institutional Proposal.
     * 
     * This is just a pass-through to InstitutionalProposalVersioningService, but we needed this method to be part of 
     * the module API.
     * 
     * @param proposalNumber String
     * @return InstitutionalProposal, or null if a PENDING version is not found.
     * @see org.kuali.kra.bo.versioning.VersionStatus
     */
    public InstitutionalProposal getPendingInstitutionalProposalVersion(String proposalNumber) {
        return institutionalProposalVersioningService.getPendingInstitutionalProposalVersion(proposalNumber);
    }
    
    /**
     * Designate one or more Institutional Proposals as Funded by an Award.
     * This will create a new Final version of the Institutional Proposal.
     * 
     * @param proposalNumbers The proposals to update.
     * @return List<InstitutionalProposal> The new Funded versions.
     */
    public List<InstitutionalProposal> updateFundedProposals(Set<String> proposalNumbers) {

        GlobalVariables.getUserSession().setBackdoorUser(KC_SYSTEM_USER);
        List<InstitutionalProposal> updatedProposals = new ArrayList<InstitutionalProposal>();
        
        try {
            for (String proposalNumber : proposalNumbers) {
                InstitutionalProposal activeVersion = getInstitutionalProposal(proposalNumber);
                
                if (activeVersion != null) {
                    
                    InstitutionalProposal newVersion = versioningService.createNewVersion(activeVersion);
                    newVersion.setStatusCode(ProposalStatus.FUNDED);
                    
                    InstitutionalProposalDocument institutionalProposalDocument = 
                        (InstitutionalProposalDocument) documentService.getNewDocument(InstitutionalProposalDocument.class);
                    
                    institutionalProposalDocument.getDocumentHeader().setDocumentDescription(
                            activeVersion.getInstitutionalProposalDocument().getDocumentHeader().getDocumentDescription());
                    
                    institutionalProposalDocument.setInstitutionalProposal(newVersion);
                    
                    documentService.blanketApproveDocument(institutionalProposalDocument, 
                            "Update Proposal Status to Funded", new ArrayList<Object>());
                    
                    updatedProposals.add(newVersion);
                    
                } else {
                    Log.warn("Could not designate proposal " + proposalNumber + " as Funded: no Active version found.");
                }
            }
            
            return updatedProposals;
            
        } catch (WorkflowException we) {
            throw new InstitutionalProposalCreationException(WORKFLOW_EXCEPTION_MESSAGE, we);
        } catch (VersionException ve) {
            throw new InstitutionalProposalCreationException(VERSION_EXCEPTION_MESSAGE, ve);
        } finally {
            resetUserSession();
        }
        
    }
    
    /* Local helper methods */
    
    /**
     * Queries the persistence layer to find the InstitutionalProposal record for the given proposalNumber.
     * 
     * @param proposalNumber String
     * @return InstitutionalProposal
     */
    @SuppressWarnings("unchecked")
    protected InstitutionalProposal getInstitutionalProposal(String proposalNumber) {
        Map<String, String> criteria = new HashMap<String, String>();
        criteria.put(InstitutionalProposal.PROPOSAL_NUMBER_PROPERTY_STRING, proposalNumber);
        criteria.put(InstitutionalProposal.PROPOSAL_SEQUENCE_STATUS_PROPERTY_STRING, VersionStatus.ACTIVE.toString());
        Collection results = businessObjectService.findMatching(InstitutionalProposal.class, criteria);
        if (results.isEmpty()) {
            return null;
        }
        
        return (InstitutionalProposal) results.toArray()[0];
    }
    
    private InstitutionalProposalDocument versionProposal(String proposalNumber, DevelopmentProposal developmentProposal, Budget budget)
        throws VersionException, WorkflowException {
        
        InstitutionalProposal currentVersion = getInstitutionalProposal(proposalNumber);
        ObjectUtils.materializeObjects(currentVersion.getInstitutionalProposalScienceKeywords());
        if (currentVersion == null) {
            throw new RuntimeException(NO_PRIOR_VERSION_MESSAGE);
        }
        InstitutionalProposal newVersion = versioningService.createNewVersion(currentVersion);
        InstitutionalProposalDocument newInstitutionalProposalDocument = mergeProposals(newVersion, developmentProposal, budget);
        return newInstitutionalProposalDocument;
    }
    
    private InstitutionalProposalDocument mergeProposals(InstitutionalProposal institutionalProposal, DevelopmentProposal developmentProposal, Budget budget)
        throws WorkflowException {
        
        InstitutionalProposalDocument institutionalProposalDocument = 
            (InstitutionalProposalDocument) documentService.getNewDocument(InstitutionalProposalDocument.class);
        
        institutionalProposalDocument.getDocumentHeader().setDocumentDescription(
                NEW_DOCUMENT_DESCRIPTION + developmentProposal.getProposalNumber());
        
        institutionalProposalDocument.setInstitutionalProposal(institutionalProposal);

        doBaseFieldsDataFeed(institutionalProposal, developmentProposal);
        doCustomAttributeDataFeed(institutionalProposalDocument, developmentProposal);
        
        institutionalProposal.getProjectPersons().clear();
        for (ProposalPerson pdPerson : developmentProposal.getProposalPersons()) {
            institutionalProposal.add(generateInstitutionalProposalPerson(pdPerson));
        }
        
        institutionalProposal.getSpecialReviews().clear();
        for (ProposalSpecialReview dpSpecialReview : developmentProposal.getPropSpecialReviews()) {
            institutionalProposal.addSpecialReview(generateIpSpecialReview(dpSpecialReview));
        }
        
        institutionalProposal.getInstitutionalProposalScienceKeywords().clear();
        for (PropScienceKeyword dpKeyword : developmentProposal.getPropScienceKeywords()) {
            institutionalProposal.addKeyword(dpKeyword.getScienceKeyword());
        }
        
        if (budget != null) {
            doBudgetDataFeed(institutionalProposal, budget);
        }
        
        return institutionalProposalDocument;
    }
    
    private void doBaseFieldsDataFeed(InstitutionalProposal institutionalProposal, DevelopmentProposal developmentProposal) {
        institutionalProposal.setProposalTypeCode(Integer.parseInt(developmentProposal.getProposalTypeCode()));
        institutionalProposal.setActivityTypeCode(developmentProposal.getActivityTypeCode());
        institutionalProposal.setStatusCode(getDefaultStatusCode());
        institutionalProposal.setSponsorCode(developmentProposal.getSponsorCode());
        institutionalProposal.setTitle(developmentProposal.getTitle());
        institutionalProposal.setSubcontractFlag(developmentProposal.getSubcontracts());
        institutionalProposal.setRequestedStartDateTotal(developmentProposal.getRequestedStartDateInitial());
        institutionalProposal.setRequestedEndDateTotal(developmentProposal.getRequestedEndDateInitial());
        institutionalProposal.setDeadlineDate(developmentProposal.getDeadlineDate());
        institutionalProposal.setNoticeOfOpportunityCode(developmentProposal.getNoticeOfOpportunityCode());
        institutionalProposal.setNumberOfCopies(developmentProposal.getNumberOfCopies());
        institutionalProposal.setDeadlineType(developmentProposal.getDeadlineType());
        institutionalProposal.setMailBy(developmentProposal.getMailBy());
        institutionalProposal.setMailType(developmentProposal.getMailType());
        institutionalProposal.setMailAccountNumber(developmentProposal.getMailAccountNumber());
        institutionalProposal.setMailDescription(developmentProposal.getMailDescription());
        institutionalProposal.setPrimeSponsorCode(developmentProposal.getPrimeSponsorCode());
        institutionalProposal.setCurrentAwardNumber(developmentProposal.getCurrentAwardNumber());
        institutionalProposal.setCfdaNumber(developmentProposal.getCfdaNumber());
        institutionalProposal.setNewDescription(developmentProposal.getNewDescription());
        institutionalProposal.setNoticeOfOpportunityCode(developmentProposal.getNoticeOfOpportunityCode());
        institutionalProposal.setNsfCode(developmentProposal.getNsfCode());
        institutionalProposal.setSponsorProposalNumber(developmentProposal.getSponsorProposalNumber());
        institutionalProposal.setOpportunity(developmentProposal.getProgramAnnouncementNumber());
        institutionalProposal.setCfdaNumber(developmentProposal.getCfdaNumber());
        institutionalProposal.setLeadUnitNumber(developmentProposal.getUnitNumber());
        institutionalProposal.setDefaultInitialContractAdmin();
        if (developmentProposal.getRolodex() != null) {
            institutionalProposal.setRolodexId(developmentProposal.getRolodex().getRolodexId());
        }
    }
    
    private void doCustomAttributeDataFeed(InstitutionalProposalDocument institutionalProposalDocument, DevelopmentProposal developmentProposal) throws WorkflowException {
        Map<String, CustomAttributeDocument> dpCustomAttributes = developmentProposal.getProposalDocument().getCustomAttributeDocuments();
        Map<String, CustomAttributeDocument> ipCustomAttributes = institutionalProposalDocument.getCustomAttributeDocuments();
        List<InstitutionalProposalCustomData> ipCustomDataList = institutionalProposalDocument.getInstitutionalProposal().getInstitutionalProposalCustomDataList();
        InstitutionalProposalCustomData ipCustomData;
        CustomAttributeDocument dpCustomAttributeDocument;
        for (String key : dpCustomAttributes.keySet()) {
            if (ipCustomAttributes.containsKey(key)) {
                dpCustomAttributeDocument = dpCustomAttributes.get(key);
                ipCustomAttributes.put(key, dpCustomAttributeDocument);
                ipCustomData = new InstitutionalProposalCustomData();
                ipCustomData.setCustomAttribute(new CustomAttribute());
                ipCustomData.getCustomAttribute().setId(dpCustomAttributeDocument.getCustomAttributeId());
                ipCustomData.setCustomAttributeId((long) dpCustomAttributeDocument.getCustomAttributeId());
                ipCustomData.setInstitutionalProposal(institutionalProposalDocument.getInstitutionalProposal());
                ipCustomData.setValue(dpCustomAttributeDocument.getCustomAttribute().getValue());
                ipCustomDataList.add(ipCustomData);
            }
        }
    }

    
    private InstitutionalProposalPerson generateInstitutionalProposalPerson(ProposalPerson pdPerson) {
        InstitutionalProposalPerson ipPerson = new InstitutionalProposalPerson();
        if (ObjectUtils.isNotNull(pdPerson.getPersonId())) {
            ipPerson.setPersonId(pdPerson.getPersonId());
        }
        if (ObjectUtils.isNotNull(pdPerson.getRolodexId())) {
            ipPerson.setRolodexId(pdPerson.getRolodexId());
        }
        ipPerson.setAutoIncrementSet(pdPerson.isAutoIncrementSet());
        ipPerson.setContactRoleCode(pdPerson.getRole().getRoleCode());
        for (ProposalPersonCreditSplit pdPersonCreditSplit : pdPerson.getCreditSplits()) {
            InstitutionalProposalPersonCreditSplit ipPersonCreditSplit = new InstitutionalProposalPersonCreditSplit();
            ipPersonCreditSplit.setAutoIncrementSet(pdPersonCreditSplit.isAutoIncrementSet());
            ipPersonCreditSplit.setCredit(pdPersonCreditSplit.getCredit());
            ipPersonCreditSplit.setInvCreditTypeCode(pdPersonCreditSplit.getInvCreditTypeCode());
            ipPersonCreditSplit.setNewCollectionRecord(pdPersonCreditSplit.isNewCollectionRecord());
            ipPerson.add(ipPersonCreditSplit);
        }
        ipPerson.setEmailAddress(pdPerson.getEmailAddress());
        ipPerson.setFaculty(pdPerson.getFacultyFlag());
        ipPerson.setFullName(pdPerson.getFullName());
        ipPerson.setKeyPersonRole(pdPerson.getProjectRole());
        ipPerson.setNewCollectionRecord(pdPerson.isNewCollectionRecord());
        //ipPerson.setPerson(pdPerson.getPerson());
        ipPerson.setPhoneNumber(pdPerson.getPhoneNumber());
        ipPerson.setRoleCode(pdPerson.getRole().getRoleCode());
        ipPerson.setTotalEffort(pdPerson.getPercentageEffort());
        for (ProposalPersonUnit pdPersonUnit : pdPerson.getUnits()) {
            InstitutionalProposalPersonUnit ipPersonUnit = new InstitutionalProposalPersonUnit();
            ipPersonUnit.setAutoIncrementSet(pdPersonUnit.isAutoIncrementSet());
            ipPersonUnit.setLeadUnit(pdPersonUnit.isLeadUnit());
            ipPersonUnit.setNewCollectionRecord(pdPersonUnit.isNewCollectionRecord());
            ipPersonUnit.setUnitNumber(pdPersonUnit.getUnitNumber());
            for (ProposalUnitCreditSplit pdPersonCreditSplit : pdPersonUnit.getCreditSplits()) {
                InstitutionalProposalPersonUnitCreditSplit ipPersonUnitCreditSplit = new InstitutionalProposalPersonUnitCreditSplit();
                ipPersonUnitCreditSplit.setAutoIncrementSet(pdPersonCreditSplit.isAutoIncrementSet());
                ipPersonUnitCreditSplit.setCredit(pdPersonCreditSplit.getCredit());
                ipPersonUnitCreditSplit.setInvCreditTypeCode(pdPersonCreditSplit.getInvCreditTypeCode());
                ipPersonUnitCreditSplit.setNewCollectionRecord(pdPersonCreditSplit.isNewCollectionRecord());
                ipPersonUnit.add(ipPersonUnitCreditSplit);
            }
            ipPerson.add(ipPersonUnit);
        }
        
        return ipPerson;
    }
    
    private InstitutionalProposalSpecialReview generateIpSpecialReview(ProposalSpecialReview dpSpecialReview) {
        InstitutionalProposalSpecialReview ipSpecialReview = new InstitutionalProposalSpecialReview();
        ipSpecialReview.setApplicationDate(dpSpecialReview.getApplicationDate());
        ipSpecialReview.setApprovalDate(dpSpecialReview.getApprovalDate());
        ipSpecialReview.setApprovalTypeCode(dpSpecialReview.getApprovalTypeCode());
        ipSpecialReview.setComments(dpSpecialReview.getComments());
        ipSpecialReview.setExpirationDate(dpSpecialReview.getExpirationDate());
        ipSpecialReview.setProtocolNumber(dpSpecialReview.getProtocolNumber());
        ipSpecialReview.setSpecialReview(dpSpecialReview.getSpecialReview());
        ipSpecialReview.setSpecialReviewApprovalType(dpSpecialReview.getSpecialReviewApprovalType());
        ipSpecialReview.setSpecialReviewCode(dpSpecialReview.getSpecialReviewCode());
        ipSpecialReview.setSpecialReviewNumber(dpSpecialReview.getSpecialReviewNumber());
        ipSpecialReview.setValidSpecialReviewApproval(dpSpecialReview.getValidSpecialReviewApproval());
        for (String dpExempt : dpSpecialReview.getExemptNumbers()) {
            ipSpecialReview.addSpecialReviewExemption(dpExempt);
        }
        return ipSpecialReview;
    }
    
    private void doBudgetDataFeed(InstitutionalProposal institutionalProposal, Budget budget) {
     // Base fields from Budget
        institutionalProposal.setRequestedStartDateInitial(budget.getBudgetPeriods().get(0).getStartDate());
        institutionalProposal.setRequestedEndDateInitial(budget.getBudgetPeriods().get(0).getEndDate());
        institutionalProposal.setTotalDirectCostInitial(new KualiDecimal(budget.getBudgetPeriod(0).getTotalDirectCost().bigDecimalValue()));
        institutionalProposal.setTotalIndirectCostInitial(new KualiDecimal(budget.getBudgetPeriod(0).getTotalIndirectCost().bigDecimalValue()));
        institutionalProposal.setTotalDirectCostTotal(new KualiDecimal(budget.getTotalDirectCost().bigDecimalValue()));
        institutionalProposal.setTotalIndirectCostTotal(new KualiDecimal(budget.getTotalIndirectCost().bigDecimalValue()));
        
        // Cost Shares (from Budget)
        institutionalProposal.getInstitutionalProposalCostShares().clear();
        for (BudgetCostShare budgetCostShare : budget.getBudgetCostShares()) {
            InstitutionalProposalCostShare ipCostShare = new InstitutionalProposalCostShare();
            ipCostShare.setCostShareTypeCode(getDefaultCostShareTypeCode());
            ipCostShare.setAmount(new KualiDecimal(budgetCostShare.getShareAmount().bigDecimalValue()));
            ipCostShare.setCostSharePercentage(new KualiDecimal(budgetCostShare.getSharePercentage().bigDecimalValue()));
            ipCostShare.setFiscalYear(budgetCostShare.getFiscalYear().toString());
            ipCostShare.setSourceAccount(budgetCostShare.getSourceAccount());
            institutionalProposal.add(ipCostShare);
        }
        
        // Unrecovered F and As (from Budget)
        institutionalProposal.getInstitutionalProposalUnrecoveredFandAs().clear();
        for (BudgetUnrecoveredFandA budgetUfa : budget.getBudgetUnrecoveredFandAs()) {
            InstitutionalProposalUnrecoveredFandA ipUfa = new InstitutionalProposalUnrecoveredFandA();
            ipUfa.setApplicableIndirectcostRate(new KualiDecimal(budgetUfa.getApplicableRate().bigDecimalValue()));
            ipUfa.setFiscalYear(budgetUfa.getFiscalYear().toString());
            ipUfa.setOnCampusFlag("Y".equals(budgetUfa.getOnCampusFlag()) ? true : false);
            ipUfa.setSourceAccount(budgetUfa.getSourceAccount());
            ipUfa.setIndirectcostRateTypeCode(Integer.parseInt(budget.getOhRateClassCode()));
            ipUfa.setUnderrecoveryOfIndirectcost(new KualiDecimal(budgetUfa.getAmount().bigDecimalValue()));
            institutionalProposal.add(ipUfa);
        }
    }
    
    private void resetUserSession() {
        GlobalVariables.getUserSession().clearBackdoorUser();
    }
    
    private Integer getDefaultStatusCode() {
        return 1;
    }
    
    private Integer getDefaultCostShareTypeCode() {
        return 1;
    }
    
    /* Service injection getters and setters */
    
    public void setBusinessObjectService(BusinessObjectService businessObjectService) {
        this.businessObjectService = businessObjectService;
    }
    
    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    public void setVersioningService(VersioningService versioningService) {
        this.versioningService = versioningService;
    }

    public void setInstitutionalProposalVersioningService(InstitutionalProposalVersioningService institutionalProposalVersioningService) {
        this.institutionalProposalVersioningService = institutionalProposalVersioningService;
    }
    
    /**
     * Set the Sequence Accessor Service.
     * @param sequenceAccessorService the Sequence Accessor Service
     */
    public void setSequenceAccessorService(SequenceAccessorService sequenceAccessorService) {
        this.sequenceAccessorService = sequenceAccessorService;
    }
    
}
