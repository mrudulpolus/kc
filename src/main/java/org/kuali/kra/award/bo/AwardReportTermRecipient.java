/*
 * Copyright 2006-2008 The Kuali Foundation
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

package org.kuali.kra.award.bo;

import java.util.LinkedHashMap;

import org.kuali.kra.bo.KraPersistableBusinessObjectBase;
import org.kuali.kra.bo.Rolodex;

/**
 * 
 * This class represents the AwardReportTermRecipient business object and is mapped
 * to AWARD_REP_TERMS_RECNT table
 */
public class AwardReportTermRecipient extends KraPersistableBusinessObjectBase {
    
    private Long awardReportTermRecipientId;
    private Integer awardReportTermId;
    private String contactTypeCode; 
    private Integer rolodexId;
    private Integer numberOfCopies; 
    
    private ContactType contactType;
    private Rolodex rolodex; 
    private AwardReportTerm awardReportTerm; 
    
    /**
     * 
     * Constructs a AwardReportTerm.java.
     */
    public AwardReportTermRecipient() { 

    } 
    
    /**
     * 
     * @return
     */
    public Integer getAwardReportTermId() {
        return awardReportTermId;
    }

    /**
     * 
     * @param awardReportTermId
     */
    public void setAwardReportTermId(Integer awardReportTermId) {
        this.awardReportTermId = awardReportTermId;
    }

    /**
     *
     * @return
     */
    public String getContactTypeCode() {
        return contactTypeCode;
    }

    /**
     *
     * @param contactTypeCode
     */
    public void setContactTypeCode(String contactTypeCode) {
        this.contactTypeCode = contactTypeCode;
    }

    /**
     *
     * @return
     */
    public Integer getRolodexId() {
        return rolodexId;
    }

    /**
     *
     * @param rolodexId
     */
    public void setRolodexId(Integer rolodexId) {
        this.rolodexId = rolodexId;
    }

    /**
     *
     * @return
     */
    public Integer getNumberOfCopies() {
        return numberOfCopies;
    }

    /**
     *
     * @param numberOfCopies
     */
    public void setNumberOfCopies(Integer numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    /**
     *
     * @return
     */
    public ContactType getContactType() {
        return contactType;
    }

    /**
     *
     * @param contactType
     */
    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    /**
     *
     * @return
     */
    public Rolodex getRolodex() {
        return rolodex;
    }

    /**
     *
     * @param rolodex
     */
    public void setRolodex(Rolodex rolodex) {
        this.rolodex = rolodex;
    }
    
    /**
     * 
     * @see org.kuali.core.bo.BusinessObjectBase#toStringMapper()
     */
    @Override 
    protected LinkedHashMap<String, Object> toStringMapper() {
        LinkedHashMap<String, Object> hashMap = new LinkedHashMap<String, Object>();
        hashMap.put("awardReportTermRecipientId", getAwardReportTermRecipientId());
        hashMap.put("awardReportTermId", getAwardReportTermId());        
        hashMap.put("contactTypeCode", getContactTypeCode());
        hashMap.put("rolodexId", getRolodexId());
        hashMap.put("numberOfCopies", getNumberOfCopies());
        return hashMap;
    }

    public Long getAwardReportTermRecipientId() {
        return awardReportTermRecipientId;
    }

    public void setAwardReportTermRecipientId(Long awardReportTermRecipientId) {
        this.awardReportTermRecipientId = awardReportTermRecipientId;
    }

    public AwardReportTerm getAwardReportTerm() {
        return awardReportTerm;
    }

    public void setAwardReportTerm(AwardReportTerm awardReportTerm) {
        this.awardReportTerm = awardReportTerm;
    }
    
}