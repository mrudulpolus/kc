/*
 * Copyright 2005-2014 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.coeus.common.budget.framework.nonpersonnel;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.kuali.coeus.common.budget.framework.copy.DeepCopyIgnore;
import org.kuali.coeus.sys.api.model.ScaleTwoDecimal;
import org.kuali.coeus.sys.framework.persistence.ScaleTwoDecimalConverter;
import org.kuali.rice.krad.data.jpa.PortableSequenceGenerator;

@Entity
@Table(name = "BUDGET_RATE_AND_BASE")
public class BudgetRateAndBase extends AbstractBudgetRateAndBase {

    private static final long serialVersionUID = -6003003851261499575L;

    @Column(name = "BASE_COST")
    @Convert(converter = ScaleTwoDecimalConverter.class)
    private ScaleTwoDecimal baseCost;

    @DeepCopyIgnore
    @PortableSequenceGenerator(name = "SEQ_BUDGET_RATE_AND_BASE_ID")
    @GeneratedValue(generator = "SEQ_BUDGET_RATE_AND_BASE_ID")
    @Id
    @Column(name = "BUDGET_RATE_AND_BASE_ID")
    private Long budgetRateAndBaseId;

    @Column(name = "BUDGET_DETAILS_CAL_AMTS_ID")
    private Long budgetLineItemCalculatedAmountId;

    @Column(name = "BUDGET_DETAILS_ID")
    private Long budgetLineItemId;

    public ScaleTwoDecimal getBaseCost() {
        return baseCost;
    }

    public void setBaseCost(ScaleTwoDecimal baseCost) {
        this.baseCost = baseCost;
    }

    /**
     * Gets the budgetRateAndBaseId attribute. 
     * @return Returns the budgetRateAndBaseId.
     */
    public Long getBudgetRateAndBaseId() {
        return budgetRateAndBaseId;
    }

    /**
     * Sets the budgetRateAndBaseId attribute value.
     * @param budgetRateAndBaseId The budgetRateAndBaseId to set.
     */
    public void setBudgetRateAndBaseId(Long budgetRateAndBaseId) {
        this.budgetRateAndBaseId = budgetRateAndBaseId;
    }

    /**
     * Gets the budgetLineItemCalculatedAmountId attribute. 
     * @return Returns the budgetLineItemCalculatedAmountId.
     */
    public Long getBudgetLineItemCalculatedAmountId() {
        return budgetLineItemCalculatedAmountId;
    }

    /**
     * Sets the budgetLineItemCalculatedAmountId attribute value.
     * @param budgetLineItemCalculatedAmountId The budgetLineItemCalculatedAmountId to set.
     */
    public void setBudgetLineItemCalculatedAmountId(Long budgetLineItemCalculatedAmountId) {
        this.budgetLineItemCalculatedAmountId = budgetLineItemCalculatedAmountId;
    }

    /**
     * Gets the budgetLineItemId attribute. 
     * @return Returns the budgetLineItemId.
     */
    public Long getBudgetLineItemId() {
        return budgetLineItemId;
    }

    /**
     * Sets the budgetLineItemId attribute value.
     * @param budgetLineItemId The budgetLineItemId to set.
     */
    public void setBudgetLineItemId(Long budgetLineItemId) {
        this.budgetLineItemId = budgetLineItemId;
    }
}
