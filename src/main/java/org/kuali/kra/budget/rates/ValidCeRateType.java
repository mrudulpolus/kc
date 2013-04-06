/*
 * Copyright 2005-2013 The Kuali Foundation
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
package org.kuali.kra.budget.rates;

import org.kuali.kra.bo.KraPersistableBusinessObjectBase;
import org.kuali.kra.budget.core.CostElement;
import org.kuali.rice.core.api.mo.common.active.MutableInactivatable;

public class ValidCeRateType extends KraPersistableBusinessObjectBase implements MutableInactivatable {

    private String costElement;

    private String rateClassCode;

    private String rateTypeCode;

    private RateClass rateClass;

    private RateType rateType;

    private CostElement costElementBo;

    private boolean active;

    /**
     * Gets the rateClass attribute. 
     * @return Returns the rateClass.
     */
    public RateClass getRateClass() {
        return rateClass;
    }

    /**
     * Sets the rateClass attribute value.
     * @param rateClass The rateClass to set.
     */
    public void setRateClass(RateClass rateClass) {
        this.rateClass = rateClass;
    }

    public String getCostElement() {
        return costElement;
    }

    public void setCostElement(String costElement) {
        this.costElement = costElement;
    }

    public String getRateClassCode() {
        return rateClassCode;
    }

    public void setRateClassCode(String rateClassCode) {
        this.rateClassCode = rateClassCode;
    }

    public String getRateTypeCode() {
        return rateTypeCode;
    }

    public void setRateTypeCode(String rateTypeCode) {
        this.rateTypeCode = rateTypeCode;
    }

    public RateType getRateType() {
        return rateType;
    }

    public void setRateType(RateType rateType) {
        this.rateType = rateType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRateClassType() {
        return rateClass.getRateClassType();
    }

    public CostElement getCostElementBo() {
        return costElementBo;
    }

    public void setCostElementBo(CostElement costElementBo) {
        this.costElementBo = costElementBo;
    }
}
