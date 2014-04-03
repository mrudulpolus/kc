/*
 * Copyright 2005-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the License);
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

import org.kuali.coeus.sys.framework.model.KcPersistableBusinessObjectBase;
import org.kuali.rice.krad.data.jpa.converters.BooleanYNConverter;

import javax.persistence.*;

@Entity
@Table(name = "RATE_CLASS_TYPE")
public class RateClassType extends KcPersistableBusinessObjectBase {

    @Id
    @Column(name = "RATE_CLASS_TYPE")
    private String rateClassType;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "SORT_ID")
    private Integer sortId;

    @Column(name = "PREFIX_ACTIVITY_TYPE")
    @Convert(converter = BooleanYNConverter.class)
    private Boolean prefixActivityType;

    public String getRateClassType() {
        return rateClassType;
    }

    public void setRateClassType(String rateClassType) {
        this.rateClassType = rateClassType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public final Boolean getPrefixActivityType() {
        return prefixActivityType;
    }

    public final void setPrefixActivityType(Boolean prefixActivityType) {
        this.prefixActivityType = prefixActivityType;
    }
}
