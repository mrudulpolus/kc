/*
 * Copyright 2005-2013 The Kuali Foundation.
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

import gov.grants.apply.forms.nsfCoverPageV11.DegreeTypeDataType;
import gov.grants.apply.forms.nsfCoverPageV11.NSFCoverPageDocument;
import gov.grants.apply.forms.nsfCoverPageV11.NSFCoverPageDocument.NSFCoverPage;
import gov.grants.apply.forms.nsfCoverPageV11.NSFCoverPageDocument.NSFCoverPage.CoPIInfo;
import gov.grants.apply.forms.nsfCoverPageV11.NSFCoverPageDocument.NSFCoverPage.CoPIInfo.CoPI;
import gov.grants.apply.forms.nsfCoverPageV11.NSFCoverPageDocument.NSFCoverPage.NSFUnitConsideration;
import gov.grants.apply.forms.nsfCoverPageV11.NSFCoverPageDocument.NSFCoverPage.OtherInfo;
import gov.grants.apply.forms.nsfCoverPageV11.NSFCoverPageDocument.NSFCoverPage.PIInfo;
import gov.grants.apply.system.attachmentsV10.AttachedFileDataType;
import gov.grants.apply.system.attachmentsV10.AttachmentGroupMin1Max100DataType;
import gov.grants.apply.system.globalLibraryV20.YesNoDataType;
import org.apache.xmlbeans.XmlObject;
import org.kuali.kra.proposaldevelopment.bo.*;
import org.kuali.kra.proposaldevelopment.document.ProposalDevelopmentDocument;
import org.kuali.kra.s2s.util.S2SConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * This class is used to generate XML Document object for grants.gov NSFCoverPageV1.1. This form is generated using XMLBean API's
 * generated by compiling NSFCoverPageV1.1 schema.
 * 
 * @author Kuali Research Administration Team (kualidev@oncourse.iu.edu)
 */
public class NSFCoverPageV1_1Generator extends NSFCoverPageBaseGenerator {


    private static final String QUESTION_ID_ACCOMPLISHMENT_RENEWAL = "5";
    private static final String QUESTION_ID_ISCURRENT_PI = "19";
    private static final int PROGRAM_ANNOUNCEMENT_NUMBER_MAX_LENGTH = 40;
    private static final DegreeTypeDataType.Enum DEFAULT_DEGREE_TYPE = DegreeTypeDataType.UKNW_NO_DEGREE_INFORMATION_SPECIFIED;

    /**
     * 
     * This method returns NSFCoverPageDocument object based on proposal development document which contains the
     * NSFCoverPageDocument informations NSFUnitConsideration,FundingOpportunityNumber,PIInfo,CoPIInfo,OtherInfo,and
     * SingleCopyDocuments for a particular proposal
     * 
     * @return nsfCoverPageDocument {@link XmlObject} of type NSFCoverPageDocument.
     */
    private NSFCoverPageDocument getNSFCoverPage() {

        NSFCoverPageDocument nsfCoverPageDocument = NSFCoverPageDocument.Factory.newInstance();
        NSFCoverPage nsfCoverPage = NSFCoverPage.Factory.newInstance();
        nsfCoverPage.setFormVersion(S2SConstants.FORMVERSION_1_1);
        if (pdDoc.getDevelopmentProposal().getProgramAnnouncementNumber() != null) {
            if (pdDoc.getDevelopmentProposal().getProgramAnnouncementNumber().length() > PROGRAM_ANNOUNCEMENT_NUMBER_MAX_LENGTH) {
                nsfCoverPage.setFundingOpportunityNumber(pdDoc.getDevelopmentProposal().getProgramAnnouncementNumber().substring(0, PROGRAM_ANNOUNCEMENT_NUMBER_MAX_LENGTH));
            }
            else {
                nsfCoverPage.setFundingOpportunityNumber(pdDoc.getDevelopmentProposal().getProgramAnnouncementNumber());
            }
        }
        nsfCoverPage.setNSFUnitConsideration(getNSFUnitConsideration());
        nsfCoverPage.setPIInfo(getPIInfo());
        nsfCoverPage.setCoPIInfo(getCoPI());
        nsfCoverPage.setOtherInfo(getOtherInfo());
        AttachmentGroupMin1Max100DataType attachmentGroup = AttachmentGroupMin1Max100DataType.Factory.newInstance();
        attachmentGroup.setAttachedFileArray(getAttachedFileDataTypes());
        nsfCoverPage.setSingleCopyDocuments(attachmentGroup);
        nsfCoverPageDocument.setNSFCoverPage(nsfCoverPage);
        return nsfCoverPageDocument;
    }

    /**
     * 
     * This method returns PIInfo informations such as DegreeType,DegreeYear,CurrentPI status, for the PI.
     * 
     * @return PIInfo object containing principal investigator Degree details.
     */
    private PIInfo getPIInfo() {
        PIInfo pInfo = PIInfo.Factory.newInstance();
        ProposalPerson PI = s2sUtilService.getPrincipalInvestigator(pdDoc);
        if (PI != null) {
            for (ProposalPersonDegree personDegree : PI.getProposalPersonDegrees()) {
                DegreeTypeDataType.Enum degreeType = DEFAULT_DEGREE_TYPE;
                if (personDegree.getDegreeType() != null && personDegree.getDegreeType().getDegreeCode() != null) {
                    StringBuilder degreeTypeDetail = new StringBuilder();
                    degreeTypeDetail.append(personDegree.getDegreeType().getDegreeCode());
                    degreeTypeDetail.append(": ");
                    degreeTypeDetail.append(personDegree.getDegreeType().getDescription());
                    degreeType = DegreeTypeDataType.Enum.forString(degreeTypeDetail.toString());
                    if(degreeType==null){
                        //Some degrees in nthe database are not available DegreeType. Therefor this extra check.
                        degreeType=DEFAULT_DEGREE_TYPE;
                    }
                }
                else {
                    degreeType = DEFAULT_DEGREE_TYPE;
                }
                pInfo.setDegreeType(degreeType);
                if (personDegree.getGraduationYear() != null) {
                    pInfo.setDegreeYear(getYearAsCalendar(personDegree.getGraduationYear()));
                }
            }
            pInfo.setIsCurrentPI(getYNQAnswer(QUESTION_ID_ISCURRENT_PI));
        }
        return pInfo;
    }

    /**
     * 
     * This method returns CoPIInfo informations such as Name,DegreeType,DegreeYear for the CoPI.
     * 
     * @return CoPIInfo object containing Co-principal investigator Degree details.
     */
    private CoPIInfo getCoPI() {

        CoPIInfo coPIInfo = CoPIInfo.Factory.newInstance();
        int count = 0;
        ProposalPerson coInvestigator = null;
        for (ProposalPerson proposalPerson : pdDoc.getDevelopmentProposal().getProposalPersons()) {
            if (proposalPerson.getProposalPersonRoleId() != null
                    && proposalPerson.getProposalPersonRoleId().equals(PI_C0_INVESTIGATOR)) {
                count++;
            }
        }
        CoPI[] coPIArray = new CoPI[count];
        count = 0;

        for (ProposalPerson proposalPerson : pdDoc.getDevelopmentProposal().getProposalPersons()) {
            if (proposalPerson.getProposalPersonRoleId() != null
                    && proposalPerson.getProposalPersonRoleId().equals(PI_C0_INVESTIGATOR)) {
                coInvestigator = proposalPerson;
                CoPI coPI = CoPI.Factory.newInstance();
                coPI.setName(globLibV20Generator.getHumanNameDataType(coInvestigator));
                for (ProposalPersonDegree personDegree : coInvestigator.getProposalPersonDegrees()) {
                    DegreeTypeDataType.Enum degreeType = DEFAULT_DEGREE_TYPE;
                    if (personDegree!=null && personDegree.getDegreeType() != null && personDegree.getDegreeType().getDegreeCode() != null) {
                        StringBuilder degreeTypeDetail = new StringBuilder();
                        degreeTypeDetail.append(personDegree.getDegreeType().getDegreeCode());
                        degreeTypeDetail.append(": ");
                        degreeTypeDetail.append(personDegree.getDegreeType().getDescription());
                        degreeType = DegreeTypeDataType.Enum.forString(degreeTypeDetail.toString());
                        if(degreeType==null){
                            //Some degrees in the database are not available DegreeType. Therefor this extra check.
                            degreeType=DEFAULT_DEGREE_TYPE;
                        }
                    }
                    coPI.setDegreeType(degreeType);
                    if (personDegree.getGraduationYear() != null) {
                        coPI.setDegreeYear(getYearAsCalendar(personDegree.getGraduationYear()));
                    }
                }
                coPIArray[count] = coPI;
                count++;
            }
        }
        coPIInfo.setCoPIArray(coPIArray);
        return coPIInfo;
    }

    /**
     * 
     * This method returns Investigator status,DisclosureLobbyingActivities,ExploratoryResearch,HistoricPlaces,
     * HighResolutionGraphics and AccomplishmentRenewal information for the OtherInfo type.
     * 
     * @return OtherInfo object containing other informations about the principal investigator.
     */
    private OtherInfo getOtherInfo() {
        OtherInfo otherInfo = OtherInfo.Factory.newInstance();
        YesNoDataType.Enum yesNoDataType = getYNQAnswer(QUESTION_ID_BEGIN_INVESTIGATOR);
        if (yesNoDataType != null) {
            otherInfo.setIsBeginInvestigator(yesNoDataType);
        }
        yesNoDataType = getLobbyingAnswer();
        if (yesNoDataType != null) {
            otherInfo.setIsDisclosureLobbyingActivities(yesNoDataType);
        }
        yesNoDataType = getYNQAnswer(QUESTION_ID_EXPLORATORY_RESEARCH);
        if (yesNoDataType != null) {
            otherInfo.setIsExploratoryResearch(yesNoDataType);
        }
        yesNoDataType = getYNQAnswer(QUESTION_ID_HISTORIC_PLACES);
        if (yesNoDataType != null) {
            otherInfo.setIsHistoricPlaces(yesNoDataType);
        }

        String proposalTypeCode = pdDoc.getDevelopmentProposal().getProposalTypeCode();
        if (proposalTypeCode != null) {
            otherInfo.setIsAccomplishmentRenewal(proposalTypeCode.equals(QUESTION_ID_ACCOMPLISHMENT_RENEWAL) ? YesNoDataType.Y_YES
                    : YesNoDataType.N_NO);
        }
        yesNoDataType = getYNQAnswer(QUESTION_ID_RESOLUTION_GRAPHICS);
        if (yesNoDataType != null) {
            otherInfo.setIsHighResolutionGraphics(yesNoDataType);
        }
        return otherInfo;
    }

    /**
     * 
     * This method YesNo data type YNQ answers based on the ProposalYnq QuestionId
     * 
     * @param questionId Proposal Ynq question id
     * @return answer (YesNoDataType.Enum) corresponding to the question id.
     */
    private YesNoDataType.Enum getYNQAnswer(String questionId) {

        YesNoDataType.Enum answer = null;
        for (ProposalYnq proposalYnq : pdDoc.getDevelopmentProposal().getProposalYnqs()) {
            if (proposalYnq.getQuestionId() != null && proposalYnq.getQuestionId().equals(questionId)) {
                if (proposalYnq.getAnswer() != null) {
                    answer = (proposalYnq.getAnswer().equals(S2SConstants.PROPOSAL_YNQ_ANSWER_Y) ? YesNoDataType.Y_YES
                            : YesNoDataType.N_NO);
                }
            }
        }
        return answer;
    }

    /**
     * 
     * This method YesNo data type Lobbying answers based on the ProposalYnq QuestionId
     * 
     * @return answer (YesNoDataType.Enum) corresponding to Ynq question id.
     */
    private YesNoDataType.Enum getLobbyingAnswer() {

        YesNoDataType.Enum answer = YesNoDataType.N_NO;
        for (ProposalPerson proposalPerson : pdDoc.getDevelopmentProposal().getProposalPersons()) {
            if (proposalPerson.getProposalPersonRoleId() != null
                    && proposalPerson.getProposalPersonRoleId().equals(PRINCIPAL_INVESTIGATOR)
                    || proposalPerson.getProposalPersonRoleId().equals(PI_C0_INVESTIGATOR)) {
                for (ProposalPersonYnq personYnq : proposalPerson.getProposalPersonYnqs()) {
                    if (personYnq != null) {
                        if (personYnq.getQuestionId() != null && personYnq.getQuestionId().equals(PROPOSAL_YNQ_LOBBYING_ACTIVITIES)) {
                            if (personYnq.getAnswer() != null && personYnq.getAnswer().equals(S2SConstants.PROPOSAL_YNQ_ANSWER_Y)) {
                                return YesNoDataType.Y_YES;
                            }
                        }
                    }
                }
            }
        }
        return answer;
    }

    /**
     * 
     * This method returns DivisionCode and ProgramCode information for the NSFUnitConsideration type.
     * 
     * @return NSFUnitConsideration object containing unit consideration informations like Division Code and Program code.
     */
    private NSFUnitConsideration getNSFUnitConsideration() {

        NSFUnitConsideration nsfConsideration = NSFUnitConsideration.Factory.newInstance();
        nsfConsideration.setDivisionCode(pdDoc.getDevelopmentProposal().getAgencyDivisionCode());
        nsfConsideration.setProgramCode(pdDoc.getDevelopmentProposal().getAgencyProgramCode());
        return nsfConsideration;
    }

    /**
     * 
     * This method returns attachment type for the form and it can be of type Personal Data or Proprietary Information.
     * 
     * @return AttachedFileDataType[] array of attachments based on the narrative type code.
     */
	private AttachedFileDataType[] getAttachedFileDataTypes() {
		List<AttachedFileDataType> attachedFileDataTypeList = new ArrayList<AttachedFileDataType>();
		AttachedFileDataType attachedFileDataType = null;
		for (Narrative narrative : pdDoc.getDevelopmentProposal()
				.getNarratives()) {
			if (narrative.getNarrativeTypeCode() != null) {
				int narrativeTypeCode = Integer.parseInt(narrative
						.getNarrativeTypeCode());
				if (narrativeTypeCode == PERSONAL_DATA
						|| narrativeTypeCode == PROPRIETARY_INFORMATION 
						|| narrativeTypeCode == SINGLE_COPY_DOCUMENT) {
					attachedFileDataType = getAttachedFileType(narrative);
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
     * This method creates {@link XmlObject} of type {@link NSFCoverPageDocument} by populating data from the given
     * {@link ProposalDevelopmentDocument}
     * 
     * @param proposalDevelopmentDocument for which the {@link XmlObject} needs to be created
     * @return {@link XmlObject} which is generated using the given {@link ProposalDevelopmentDocument}
     * @see org.kuali.kra.s2s.generator.S2SFormGenerator#getFormObject(ProposalDevelopmentDocument)
     */
    public XmlObject getFormObject(ProposalDevelopmentDocument proposalDevelopmentDocument) {
        this.pdDoc = proposalDevelopmentDocument;
        return getNSFCoverPage();
    }

    /**
     * This method typecasts the given {@link XmlObject} to the required generator type and returns back the document of that
     * generator type.
     * 
     * @param xmlObject which needs to be converted to the document type of the required generator
     * @return {@link XmlObject} document of the required generator type
     * @see org.kuali.kra.s2s.generator.S2SFormGenerator#getFormObject(XmlObject)
     */
    public XmlObject getFormObject(XmlObject xmlObject) {
        NSFCoverPage nsfCoverPage = (NSFCoverPage) xmlObject;
        NSFCoverPageDocument nsfCoverPageDocument = NSFCoverPageDocument.Factory.newInstance();
        nsfCoverPageDocument.setNSFCoverPage(nsfCoverPage);
        return nsfCoverPageDocument;
    }
}
