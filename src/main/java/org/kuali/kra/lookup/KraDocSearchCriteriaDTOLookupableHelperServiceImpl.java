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
package org.kuali.kra.lookup;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.kra.award.document.AwardDocument;
import org.kuali.rice.kew.bo.lookup.DocSearchCriteriaDTOLookupableHelperServiceImpl;
import org.kuali.rice.kew.docsearch.DocumentSearchResult;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.web.struts.form.LookupForm;
import org.kuali.rice.kns.web.ui.Column;
import org.kuali.rice.kns.web.ui.ResultRow;

public class KraDocSearchCriteriaDTOLookupableHelperServiceImpl extends DocSearchCriteriaDTOLookupableHelperServiceImpl {
    private static final String DOCUMENT_TITLE_FIELD = "documentTitle";

    /**
     * Note: In trying to understand what the super class was doing in its performLookup method, I discovered the method is 300+ lines long, with many
     * commented blocks of code.
     *
     * @param lookupForm
     * @param resultTable
     * @param bounded
     * @return
     */
    @Override
    public Collection performLookup(LookupForm lookupForm, Collection resultTable, boolean bounded) {
        List<DocumentSearchResult> docSearchResults = (List<DocumentSearchResult> ) super.performLookup(lookupForm, resultTable, bounded);
        filterOutPlaceholderDocument(resultTable, docSearchResults);
        generateColumnAnchor(resultTable);
        return docSearchResults;
    }

    /**
     * For some reason, Rice uses sends the search results in two collections. So when filtering out the Placeholder document, we must do so in both
     *
     * @param resultTable
     * @param docSearchResults
     */
    private void filterOutPlaceholderDocument(Collection resultTable, List<DocumentSearchResult> docSearchResults) {
        Iterator resultTableIter = resultTable.iterator();
        for(DocumentSearchResult docSearchResult: docSearchResults) {
            resultTableIter.next();
            if(docSearchResult.getResultContainer(DOCUMENT_TITLE_FIELD).getUserDisplayValue().contains(AwardDocument.PLACEHOLDER_DOC_DESCRIPTION)) {
                resultTableIter.remove();
                docSearchResults.remove(docSearchResult);
                break;
            }
        }
    }

    private void generateColumnAnchor(Collection resultTable) {
        for (ResultRow resultRow : (List<ResultRow>)resultTable) {
            for (Column column : resultRow.getColumns()) {
                if (column.getPropertyName().equals("copyDocument") && column.getColumnAnchor()!= null) {
                    AnchorHtmlData anchor = (AnchorHtmlData)column.getColumnAnchor();
                    String docId = StringUtils.substringBetween(column.getPropertyValue(),"docId=", "&");
                    anchor.setHref(StringUtils.substringBetween(column.getPropertyValue(), "<a href=\"", "docId=")+"docId="+docId);
                    column.setColumnAnchor(anchor);
                }
            }
        }
    }
}
