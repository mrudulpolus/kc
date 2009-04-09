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
package org.kuali.kra.award.rules;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.kuali.core.UserSession;
import org.kuali.core.service.DocumentService;
import org.kuali.core.util.ErrorMap;
import org.kuali.core.util.ErrorMessage;
import org.kuali.core.util.GlobalVariables;
import org.kuali.core.util.TypedArrayList;
import org.kuali.kra.KraTestBase;
import org.kuali.kra.award.document.AwardDocument;
import org.kuali.kra.irb.bo.Protocol;
import org.kuali.kra.irb.bo.ProtocolUnit;
import org.kuali.kra.irb.document.ProtocolDocument;
import org.kuali.kra.irb.personnel.ProtocolPerson;
import org.kuali.rice.KNSServiceLocator;

import edu.iu.uis.eden.exception.WorkflowException;

/**
 * Base class for Award business rule tests.
 * 
 */
public abstract class AwardRuleTestBase extends KraTestBase {

    protected DocumentService documentService = null;
    protected static final String DEFAULT_DOCUMENT_DESCRIPTION = "Award Document";
    protected static final String AWARD_STATUS_STR = "100"; //test of option "Pending/In Progress";
    protected static final String AWARD_TYPE_CODE_STR = "1";//test of option "Standard";
    protected static final String AWARD_TITLE_STR = "New protocol test";
    protected static final String PRINCIPAL_INVESTIGATOR_ID = "000000001";
    protected static final String PRINCIPAL_INVESTIGATOR_NAME = "Terry Durkin";
    protected static final String PRINCIPAL_INVESTIGATOR_UNIT = "BL-BL";
    protected static final String PRINCIPAL_INVESTIGATOR_ROLE = "PI";
    protected static final String REFERENCE_PERSON_ROLE = "protocolPersonRole";
    protected static final String REFERENCE_UNIT = "unit";
    

    @Before
    public void setUp() throws Exception {
        super.setUp();
        GlobalVariables.setUserSession(new UserSession("quickstart"));
        GlobalVariables.setErrorMap(new ErrorMap());
        GlobalVariables.setAuditErrorMap(new HashMap());
        documentService = KNSServiceLocator.getDocumentService();
    }

    @After
    public void tearDown() throws Exception {
        GlobalVariables.setUserSession(null);
        GlobalVariables.setErrorMap(null);
        GlobalVariables.setAuditErrorMap(null);
        documentService = null;
        super.tearDown();
    }
    
    /**
     * Get a new Protocol Document.
     * 
     * @return a new Protocol Document.
     * @throws WorkflowException
     */
    protected AwardDocument getNewAwardDocument() throws WorkflowException {
        return (AwardDocument) documentService.getNewDocument("AwardDocument");
    }

    /**
     * This method is to set required fields for Protocol document
     * @param document
     */
    protected void setProtocolRequiredFields(ProtocolDocument document) {
        Protocol protocol = document.getProtocol();
        document.getDocumentHeader().setDocumentDescription(DEFAULT_DOCUMENT_DESCRIPTION);
        protocol.setProtocolTypeCode(AWARD_TYPE_CODE_STR);
        protocol.setTitle(AWARD_TITLE_STR);
        
        ProtocolPerson protocolPerson = getProtocolPerson(PRINCIPAL_INVESTIGATOR_ID, PRINCIPAL_INVESTIGATOR_NAME, PRINCIPAL_INVESTIGATOR_ROLE);
        
        ProtocolUnit protocolUnit = new ProtocolUnit();
        protocolUnit.setUnitNumber(PRINCIPAL_INVESTIGATOR_UNIT);
        protocolUnit.setLeadUnitFlag(true);
        protocolUnit.setProtocolNumber("0");
        protocolUnit.setSequenceNumber(0);
        protocolUnit.refreshReferenceObject(REFERENCE_UNIT);

        protocol.setLeadUnitForValidation(protocolUnit);
        protocolPerson.getProtocolUnits().add(protocolUnit);
        
        protocol.getProtocolPersons().add(protocolPerson);
        protocol.setLeadUnitNumber(PRINCIPAL_INVESTIGATOR_UNIT);
        protocol.setPrincipalInvestigatorId(PRINCIPAL_INVESTIGATOR_ID);
    }
    
    /**
     * This method is to get protocol person details
     * @param personId
     * @param personName
     * @param personRole
     * @return
     */
    protected ProtocolPerson getProtocolPerson(String personId, String personName, String personRole) {
        ProtocolPerson protocolPerson = new ProtocolPerson();
        protocolPerson.setPersonId(personId);
        protocolPerson.setPersonName(personName);
        protocolPerson.setProtocolPersonRoleId(personRole);
        protocolPerson.setPreviousPersonRoleId(personRole);
        protocolPerson.setProtocolNumber("0");
        protocolPerson.setSequenceNumber(0);
        protocolPerson.refreshReferenceObject(REFERENCE_PERSON_ROLE);
        return protocolPerson;
    }
    
    /**
     * This method is to get protocol person with role PI
     * @return
     */
    protected ProtocolPerson getPrincipalInvestigator() {
        return getProtocolPerson(PRINCIPAL_INVESTIGATOR_ID, PRINCIPAL_INVESTIGATOR_NAME, PRINCIPAL_INVESTIGATOR_ROLE);
    }

    /**
     * Assert an error.  The Error Map should have one error with the given
     * property key and error key.
     * @param propertyKey
     * @param errorKey
     */
    protected void assertError(String propertyKey, String errorKey) {
        TypedArrayList errors = GlobalVariables.getErrorMap().getMessages(propertyKey);
        assertNotNull(errors);
        assertTrue(errors.size() == 1);
        
        ErrorMessage message = (ErrorMessage) errors.get(0);
        assertNotNull(message);
        assertEquals(message.getErrorKey(), errorKey);
    }
}
