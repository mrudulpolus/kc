/*
 * Copyright 2005-2010 The Kuali Foundation
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
package org.kuali.kra.test.infrastructure;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runner.notification.RunListener;
import org.kuali.kra.test.infrastructure.lifecycle.KcUnitTestSeleniumLifecycle;

import com.thoughtworks.selenium.Selenium;

@RunWith(KcSeleniumTestRunner.class)
public class KcSeleniumTestBase extends KcUnitTestBase {
    
    private static KcUnitTestSeleniumLifecycle LIFECYCLE = new KcUnitTestSeleniumLifecycle();
    private static RunListener RUN_LISTENER = new KcUnitTestRunListener(LIFECYCLE);
    
    protected static Selenium selenium;
    
    @BeforeClass
    public static final void seleniumBeforeClass() {
        if (!LIFECYCLE.isPerSuiteStarted()) {
            LIFECYCLE.startPerSuite();
            selenium = LIFECYCLE.getSelenium();
        }
        LIFECYCLE.startPerClass();
    }
    
    @AfterClass
    public static final void seleniumAfterClass() {
        LIFECYCLE.stopPerClass();
    }
    
    @Before
    public void seleniumBeforeTest() throws Exception {
        LIFECYCLE.startPerTest(transactional);
    }
    
    @After
    public void seleniumAfterTest() throws Exception {
        LIFECYCLE.stopPerTest();
    }
    
    /**
     * This method returns the <code>RunListener</code> needed to ensure the KC persistent lifecycles shut down properly
     * @return the RunListener responsible for shutting down all KC persistent lifecycles
     */
    public static RunListener getRunListener() {
        return RUN_LISTENER;
    }

}