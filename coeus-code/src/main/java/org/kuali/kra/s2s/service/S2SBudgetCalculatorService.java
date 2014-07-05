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
package org.kuali.kra.s2s.service;

import org.kuali.coeus.common.budget.api.core.BudgetContract;
import org.kuali.coeus.common.budget.api.nonpersonnel.BudgetLineItemContract;
import org.kuali.coeus.common.budget.api.period.BudgetPeriodContract;
import org.kuali.coeus.propdev.api.person.ProposalPersonContract;
import org.kuali.coeus.propdev.api.core.ProposalDevelopmentDocumentContract;
import org.kuali.coeus.sys.api.model.ScaleTwoDecimal;
import org.kuali.coeus.common.budget.framework.core.BudgetDocument;
import org.kuali.kra.s2s.S2SException;
import org.kuali.coeus.common.budget.api.category.BudgetCategoryMapContract;
import org.kuali.kra.s2s.generator.bo.BudgetPeriodInfo;
import org.kuali.kra.s2s.generator.bo.BudgetSummaryInfo;
import org.kuali.kra.s2s.generator.bo.IndirectCostInfo;
import org.kuali.kra.s2s.generator.bo.KeyPersonInfo;

import java.util.List;

/**
 * This class contains the Budget related calculations for a proposal
 * 
 * @author Kuali Research Administration Team (kualidev@oncourse.iu.edu)
 */
public interface S2SBudgetCalculatorService {

    String getParticipantSupportCategoryCode();

    List<? extends BudgetLineItemContract> getMatchingLineItems(List<? extends BudgetLineItemContract> lineItems, List<String> budgetCategoryType);

    /**
     * 
     * This method returns a list of {@link BudgetCategoryMapContract} based on the input. The list returned will not contain the categories
     * that the codes passed as a list of {@link String} and also will not contain those that match the types passed as list of
     * {@link String}. In case 2 empty lists are passed as parameters, the method will return entire list without applying any
     * filters.
     * 
     * @param filterTargetCategoryCodes Category Codes that must be filtered
     * @param filterCategoryTypes Category types that must be filtered
     * @return a List of BudgetCategoryMap.
     */
    public List<? extends BudgetCategoryMapContract> getBudgetCategoryMapList(List<String> filterTargetCategoryCodes, List<String> filterCategoryTypes);

    /**
     * 
     * This method does the budget related calculations for a given {@link ProposalDevelopmentDocumentContract} and returns them in
     * {@link BudgetSummaryInfo}
     * 
     * @param pdDoc ProposalDevelopmentDocumentContract.
     * @return BudgetSummaryInfo corresponding to the ProposalDevelopmentDocumentContract object.
     * @throws S2SException
     */
    public BudgetSummaryInfo getBudgetInfo(ProposalDevelopmentDocumentContract pdDoc, List<BudgetPeriodInfo> budgetperiodList) throws S2SException;

    /**
     * 
     * This method gets the list of {@link BudgetPeriodInfo} for the latest {@link BudgetDocument} of the given
     * {@link ProposalDevelopmentDocumentContract}
     * 
     * @param pdDoc ProposalDevelopmentDocumentContract
     * @return a List of BudgetPeriodInfo corresponding to the ProposalDevelopmentDocumentContract object.
     * @throws S2SException
     */
    public List<BudgetPeriodInfo> getBudgetPeriods(ProposalDevelopmentDocumentContract pdDoc) throws S2SException;

    /**
     * 
     * This method determines whether a {@link org.kuali.coeus.propdev.api.person.ProposalPersonContract} is a Non MIT person
     * 
     * @param proposalPerson ProposalPerson.
     * @return boolean true if Non MIT Person false otherwise.
     */
    public boolean isPersonNonMITPerson(ProposalPersonContract proposalPerson);
    /**
     * 
     * This method computes the indirect costs for a given {@link org.kuali.coeus.common.budget.api.period.BudgetPeriodContract}
     * 
     * @param budgetPeriod
     *            given BudgetPeriod.
     * @return IndirectCostInfo for the corresponding BudgetPeriod object.
     */
    public IndirectCostInfo getIndirectCosts(BudgetContract budget,BudgetPeriodContract budgetPeriod);

    public ScaleTwoDecimal getBaseSalaryByPeriod(Long budgetId, int budgetPeriod, KeyPersonInfo keyPerson );

}
