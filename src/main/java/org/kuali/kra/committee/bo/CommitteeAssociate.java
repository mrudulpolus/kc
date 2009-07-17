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
package org.kuali.kra.committee.bo;

import java.util.LinkedHashMap;

import javax.persistence.Column;

import org.kuali.kra.SequenceAssociate;
import org.kuali.kra.bo.KraPersistableBusinessObjectBase;

/**
 * 
 * This class is to maintain repetitive coeus legacy code, committeeNumber & sequenceNumber, for Committee Bos.
 */
public abstract class CommitteeAssociate extends KraPersistableBusinessObjectBase implements SequenceAssociate<Committee> {

    private static final long serialVersionUID = -6350020738083606018L;

    @Column(name = "COMMITTEE_ID_FK")
    private Long committeeIdFk;
    
    private Committee sequenceOwner;

    public Long getCommitteeIdFk() {
        return committeeIdFk;
    }

    public void setCommitteeIdFk(Long committeeIdFk) {
        this.committeeIdFk = committeeIdFk;
    }

    public abstract boolean equals(Object obj);
    
    public abstract int hashCode();
    
    @Override
    protected LinkedHashMap<String, Object> toStringMapper() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("committeeIdFk", this.getCommitteeIdFk());
        return map;
    }

    public Committee getSequenceOwner() {
        return this.sequenceOwner;
    }

    public void setSequenceOwner(Committee newOwner) {
        this.sequenceOwner = newOwner;
    }

    public Integer getSequenceNumber() {
        return this.sequenceOwner.getSequenceNumber();
    }

    public abstract void resetPersistenceState();

}
