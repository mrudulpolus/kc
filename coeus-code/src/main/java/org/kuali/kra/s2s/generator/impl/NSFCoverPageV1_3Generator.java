/*
 * Copyright 2005-2014 The Kuali Foundation.
 * 
 * Licensed under the Educational Community License, Version 1.0 (the "License");
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
package org.kuali.kra.s2s.generator.impl;

import gov.grants.apply.forms.nsfCoverPage13V13.NSFCoverPage13Document;
import gov.grants.apply.forms.nsfCoverPage13V13.NSFCoverPage13Document.NSFCoverPage13;
import gov.grants.apply.forms.nsfCoverPage13V13.NSFCoverPage13Document.NSFCoverPage13.NSFUnitConsideration;
import gov.grants.apply.forms.nsfCoverPage13V13.NSFCoverPage13Document.NSFCoverPage13.OtherInfo;
import gov.grants.apply.forms.nsfCoverPage13V13.NSFCoverPage13Document.NSFCoverPage13.PIInfo;
import gov.grants.apply.system.attachmentsV10.AttachedFileDataType;
import gov.grants.apply.system.attachmentsV10.AttachmentGroupMin1Max100DataType;
import gov.grants.apply.system.globalLibraryV20.YesNoDataType;
import org.apache.xmlbeans.XmlObject;
import org.kuali.coeus.common.api.org.OrganizationContract;
import org.kuali.coeus.common.api.org.OrganizationYnqContract;
import org.kuali.coeus.common.api.question.AnswerContract;
import org.kuali.coeus.common.api.question.AnswerHeaderContract;
import org.kuali.coeus.propdev.api.core.DevelopmentProposalContract;
import org.kuali.coeus.propdev.api.location.ProposalSiteContract;
import org.kuali.coeus.propdev.api.person.ProposalPersonContract;
import org.kuali.coeus.propdev.api.core.ProposalDevelopmentDocumentContract;
import org.kuali.coeus.propdev.api.attachment.NarrativeContract;
import org.kuali.kra.s2s.generator.FormGenerator;
import org.kuali.kra.s2s.generator.S2SQuestionnairing;
import org.kuali.kra.s2s.util.S2SConstants;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * This class is used to generate XML Document object for grants.gov
 * NSFCoverPageV1.2. This form is generated using XMLBean API's generated by
 * compiling NSFCoverPageV1.2 schema.
 * 
 * @author Kuali Research Administration Team (kualidev@oncourse.iu.edu)
 */
@FormGenerator("NSFCoverPageV1_3Generator")
public class NSFCoverPageV1_3Generator extends NSFCoverPageBaseGenerator implements S2SQuestionnairing{

	/**
	 * 
	 * This method returns NSFCoverPage13Document object based on proposal
	 * development document which contains the NSFCoverPage13Document
	 * informations
	 * NSFUnitConsideration,FundingOpportunityNumber,PIInfo,CoPIInfo,OtherInfo,and
	 * SingleCopyDocuments for a particular proposal
	 * 
	 * @return nsfCoverPage13Document {@link XmlObject} of type
	 *         NSFCoverPage13Document.
	 */
	private NSFCoverPage13Document getNSFCoverPage13() {
		NSFCoverPage13Document nsfCoverPage13Document = NSFCoverPage13Document.Factory
				.newInstance();
		NSFCoverPage13 nsfCoverPage13 = NSFCoverPage13.Factory.newInstance();
		nsfCoverPage13.setFormVersion(S2SConstants.FORMVERSION_1_3);
		setFundingOpportunityNumber(nsfCoverPage13);
		if (pdDoc.getDevelopmentProposal().getS2sOpportunity() != null
				&& pdDoc.getDevelopmentProposal().getS2sOpportunity()
						.getClosingDate() != null) {
			nsfCoverPage13.setDueDate(pdDoc
					.getDevelopmentProposal().getS2sOpportunity()
					.getClosingDate());
		}
		nsfCoverPage13.setNSFUnitConsideration(getNSFUnitConsideration());
		setOtherInfo(nsfCoverPage13);
		AttachmentGroupMin1Max100DataType attachmentGroup = AttachmentGroupMin1Max100DataType.Factory
				.newInstance();
		attachmentGroup.setAttachedFileArray(getAttachedFileDataTypes());
		if(attachmentGroup.getAttachedFileArray().length > 0)
			nsfCoverPage13.setSingleCopyDocuments(attachmentGroup);
		
		nsfCoverPage13Document.setNSFCoverPage13(nsfCoverPage13);
		return nsfCoverPage13Document;
	}

	private void setFundingOpportunityNumber(NSFCoverPage13 nsfCoverPage13) {
		if (pdDoc.getDevelopmentProposal().getProgramAnnouncementNumber() != null) {
			if (pdDoc.getDevelopmentProposal().getProgramAnnouncementNumber()
					.length() > PROGRAM_ANNOUNCEMENT_NUMBER_MAX_LENGTH) {
				nsfCoverPage13.setFundingOpportunityNumber(pdDoc
						.getDevelopmentProposal()
						.getProgramAnnouncementNumber().substring(0,
								PROGRAM_ANNOUNCEMENT_NUMBER_MAX_LENGTH));
			} else {
				nsfCoverPage13.setFundingOpportunityNumber(pdDoc
						.getDevelopmentProposal()
						.getProgramAnnouncementNumber());
			}
		}
	}

	/**
	 * 
	 * This method returns Investigator
	 * status,DisclosureLobbyingActivities,ExploratoryResearch,HistoricPlaces,
	 * HighResolutionGraphics and AccomplishmentRenewal information for the
	 * OtherInfo type.
	 */
	private void setOtherInfo(NSFCoverPage13 nsfCoverPage13) {
		OtherInfo otherInfo = OtherInfo.Factory.newInstance();
		PIInfo pInfo = PIInfo.Factory.newInstance();
		for (AnswerContract questionnaireAnswer : getPropDevQuestionAnswerService().getQuestionnaireAnswers(pdDoc.getDevelopmentProposal().getProposalNumber(),getNamespace(),getFormName())) {
			String answer = questionnaireAnswer.getAnswer();
			int questionId = questionnaireAnswer.getQuestionNumber();
			
			if (answer != null) {
				switch (questionId) {
				case QUESTION_CURRENT_PI:
					pInfo
							.setIsCurrentPI(answer
									.equals(S2SConstants.PROPOSAL_YNQ_ANSWER_Y) ? YesNoDataType.Y_YES
									: YesNoDataType.N_NO);
					break;
				case QUESTION_BEGIN_INVESTIGATOR:
					otherInfo
							.setIsBeginInvestigator(answer
									.equals(S2SConstants.PROPOSAL_YNQ_ANSWER_Y) ? YesNoDataType.Y_YES
									: YesNoDataType.N_NO);
					break;
				case QUESTION_EARLY_CONCEPT_GRANT:
					otherInfo
							.setIsEarlyConceptGrant(answer
									.equals(S2SConstants.PROPOSAL_YNQ_ANSWER_Y) ? YesNoDataType.Y_YES
									: YesNoDataType.N_NO);
					break;
				case QUESTION_RAPIDRESPONSE_GRANT:
					otherInfo
							.setIsRapidResponseGrant(answer
									.equals(S2SConstants.PROPOSAL_YNQ_ANSWER_Y) ? YesNoDataType.Y_YES
									: YesNoDataType.N_NO);
					break;
				case QUESTION_ACCOMPLISHMENT_RENEWAL:
					otherInfo
							.setIsAccomplishmentRenewal(answer
									.equals(S2SConstants.PROPOSAL_YNQ_ANSWER_Y) ? YesNoDataType.Y_YES
									: YesNoDataType.N_NO);
					break;
				case QUESTION_RESOLUTION_GRAPHICS:
					otherInfo
							.setIsHighResolutionGraphics(answer
									.equals(S2SConstants.PROPOSAL_YNQ_ANSWER_Y) ? YesNoDataType.Y_YES
									: YesNoDataType.N_NO);
					break;
				default:
					break;
				}
			}
		}
		nsfCoverPage13.setPIInfo(pInfo);
		otherInfo.setIsDisclosureLobbyingActivities(getLobbyingAnswer());
		nsfCoverPage13.setOtherInfo(otherInfo);
	}

	/**
	 * 
	 * This method YesNo data type Lobbying answers based on the ProposalYnq
	 * QuestionId
	 * 
	 * @return answer (YesNoDataType.Enum) corresponding to Ynq question id.
	 */
	private YesNoDataType.Enum getLobbyingAnswer() {
		YesNoDataType.Enum answer = YesNoDataType.N_NO;
		
		for (ProposalPersonContract proposalPerson : pdDoc.getDevelopmentProposal()
				.getProposalPersons()) {
		  	if (proposalPerson.getProposalPersonRoleId() != null
					&& proposalPerson.getProposalPersonRoleId().equals(
							PRINCIPAL_INVESTIGATOR)
					|| PI_C0_INVESTIGATOR.equals(proposalPerson.getProposalPersonRoleId())) {
		  	List<? extends AnswerHeaderContract> headers=getPropDevQuestionAnswerService().getQuestionnaireAnswerHeaders(pdDoc.getDevelopmentProposal().getProposalNumber());
                AnswerHeaderContract answerHeader=headers.get(0);
		  	List <? extends AnswerContract> certificationAnswers=answerHeader.getAnswers();
		  	
		  	for(AnswerContract certificatonAnswer : certificationAnswers){
		  	    if (certificatonAnswer != null
		  	            && PROPOSAL_YNQ_LOBBYING_ACTIVITIES
		  	            .equals(certificatonAnswer.getQuestionId())
		  	            && S2SConstants.PROPOSAL_YNQ_ANSWER_Y
		  	            .equals(certificatonAnswer.getAnswer())) {
		  	        return YesNoDataType.Y_YES;
		  	    }
		  	    
		  	}
  
			}
		}
		OrganizationContract organization = getOrganizationFromDevelopmentProposal(pdDoc.getDevelopmentProposal());
		if (organization != null) {
            for (OrganizationYnqContract organizationYnq : organization.getOrganizationYnqs()) {
                if (organizationYnq.getQuestionId().equals(LOBBYING_QUESTION_ID)) {
                    if(getAnswerFromOrganizationYnq(organizationYnq)){
                        return YesNoDataType.Y_YES;
                    }
                }
            }
        }

		return answer;
	}

    /*
     * This method return true if question is answered otherwise false .
     */
    protected boolean getAnswerFromOrganizationYnq(OrganizationYnqContract organizationYnq) {
        return organizationYnq.getAnswer().equals(ANSWER_INDICATOR_VALUE);
    }

	/*
     * This method will get the Organization from the Development proposal.
     */
	    private OrganizationContract getOrganizationFromDevelopmentProposal(DevelopmentProposalContract developmentProposal) {
	        OrganizationContract organization = null;
	        ProposalSiteContract proposalSite = developmentProposal.getApplicantOrganization();
	        if (proposalSite != null) {
	            organization = proposalSite.getOrganization();
	        }
	        return organization;
	    }
   
	/**
	 * 
	 * This method returns DivisionCode and ProgramCode information for the
	 * NSFUnitConsideration type.
	 * 
	 * @return NSFUnitConsideration object containing unit consideration
	 *         informations like Division Code and Program code.
	 */
	private NSFUnitConsideration getNSFUnitConsideration() {
		NSFUnitConsideration nsfConsideration = NSFUnitConsideration.Factory
				.newInstance();
		nsfConsideration.setDivisionCode(pdDoc.getDevelopmentProposal()
				.getAgencyDivisionCode());
		nsfConsideration.setProgramCode(pdDoc.getDevelopmentProposal()
				.getAgencyProgramCode());
		return nsfConsideration;
	}

	/**
	 * 
	 * This method returns attachment type for the form and it can be of type
	 * Personal Data or Proprietary Information.
	 * 
	 * @return AttachedFileDataType[] array of attachments based on the
	 *         narrative type code.
	 */
	private AttachedFileDataType[] getAttachedFileDataTypes() {
		List<AttachedFileDataType> attachedFileDataTypeList = new ArrayList<>();
		for (NarrativeContract narrative : pdDoc.getDevelopmentProposal()
				.getNarratives()) {
			if (narrative.getNarrativeType().getCode() != null) {
				int narrativeTypeCode = Integer.parseInt(narrative.getNarrativeType().getCode());
				if (narrativeTypeCode == PERSONAL_DATA
						|| narrativeTypeCode == PROPRIETARY_INFORMATION 
						|| narrativeTypeCode == SINGLE_COPY_DOCUMENT) {
                    AttachedFileDataType attachedFileDataType = getAttachedFileType(narrative);
					if(attachedFileDataType != null){
						attachedFileDataTypeList.add(attachedFileDataType);
					}
				}
			}
		}
		return attachedFileDataTypeList
				.toArray(new AttachedFileDataType[attachedFileDataTypeList
						.size()]);
	}

	/**
	 * This method creates {@link XmlObject} of type
	 * {@link NSFCoverPage13Document} by populating data from the given
	 * {@link ProposalDevelopmentDocumentContract}
	 * 
	 * @param ProposalDevelopmentDocumentContract
	 *            for which the {@link XmlObject} needs to be created
	 * @return {@link XmlObject} which is generated using the given
	 *         {@link ProposalDevelopmentDocumentContract}
	 * @see org.kuali.kra.s2s.generator.S2SFormGenerator#getFormObject(ProposalDevelopmentDocumentContract)
	 */
	public XmlObject getFormObject(
			ProposalDevelopmentDocumentContract ProposalDevelopmentDocumentContract) {
		this.pdDoc = ProposalDevelopmentDocumentContract;
		return getNSFCoverPage13();
	}

    public String getFormName() {
        return "NSF_CoverPage_1_3-V1.3";
    }

    public String getNamespace() {
        return "http://apply.grants.gov/forms/NSF_CoverPage_1_3-V1.3";
    }
}
