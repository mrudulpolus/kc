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

import gov.grants.apply.forms.nsfDeviationAuthorizationV10.NSFDeviationAuthorizationDocument;
import gov.grants.apply.forms.nsfDeviationAuthorizationV10.NSFDeviationAuthorizationDocument.NSFDeviationAuthorization;
import org.apache.xmlbeans.XmlObject;
import org.kuali.coeus.propdev.impl.core.ProposalDevelopmentDocument;
import org.kuali.kra.s2s.util.S2SConstants;

/**
 * 
 * This class is used to generate XML Document object for grants.gov NSFDeviationAuthorizationV1.0. This form is generated using
 * XMLBean API's generated by compiling NSFDeviationAuthorizationV1.0 schema.
 * 
 * @author Kuali Research Administration Team (kualidev@oncourse.iu.edu)
 */
public class NSFDeviationAuthorizationV1_0Generator extends NSFDeviationAuthorizationBaseGenerator {


    /**
     * 
     * This method returns NSFDeviationAuthorizationDocument object based on proposal development document which contains the
     * NSFDeviationAuthorizationDocument information NSFDeviationAuthorization for a particular proposal
     * 
     * @return authorizationDocument {@link XmlObject} of type NSFDeviationAuthorizationDocument.
     */
    private NSFDeviationAuthorizationDocument getNSFDeviationAuthorization() {
        NSFDeviationAuthorizationDocument authorizationDocument = NSFDeviationAuthorizationDocument.Factory.newInstance();
        NSFDeviationAuthorization nsfAuthorization = NSFDeviationAuthorization.Factory.newInstance();
        nsfAuthorization.setFormVersion(S2SConstants.FORMVERSION_1_0);
        String devAuth = getAbstractText(DEVIATION_AUTHORIZATION);
        if (devAuth != null) {
            nsfAuthorization.setDeviationAuthorization(devAuth);
        }
        authorizationDocument.setNSFDeviationAuthorization(nsfAuthorization);
        return authorizationDocument;
    }


    /**
     * This method creates {@link XmlObject} of type {@link NSFDeviationAuthorizationDocument} by populating data from the given
     * {@link ProposalDevelopmentDocument}
     * 
     * @param proposalDevelopmentDocument for which the {@link XmlObject} needs to be created
     * @return {@link XmlObject} which is generated using the given {@link ProposalDevelopmentDocument}
     * @see org.kuali.kra.s2s.generator.S2SFormGenerator#getFormObject(ProposalDevelopmentDocument)
     */
    public XmlObject getFormObject(ProposalDevelopmentDocument proposalDevelopmentDocument) {
        this.pdDoc = proposalDevelopmentDocument;
        return getNSFDeviationAuthorization();
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
        NSFDeviationAuthorization nsfAuthorization = (NSFDeviationAuthorization) xmlObject;
        NSFDeviationAuthorizationDocument authorizationDocument = NSFDeviationAuthorizationDocument.Factory.newInstance();
        authorizationDocument.setNSFDeviationAuthorization(nsfAuthorization);
        return authorizationDocument;
    }
}
