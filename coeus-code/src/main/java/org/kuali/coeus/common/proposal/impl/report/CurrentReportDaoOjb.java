package org.kuali.coeus.common.proposal.impl.report;

import org.apache.commons.lang3.ObjectUtils;
import org.kuali.coeus.common.framework.version.history.VersionHistory;
import org.kuali.coeus.common.framework.version.history.VersionHistoryService;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.coeus.sys.framework.util.CollectionUtils;
import org.kuali.kra.award.contacts.AwardPerson;
import org.kuali.kra.award.home.Award;
import org.kuali.kra.common.printing.CurrentReportBean;
import org.kuali.rice.kew.api.exception.WorkflowException;

import java.util.*;

/**
 * OJB implementation of CurrentReportDao using OJB Report Query (see http://db.apache.org/ojb/docu/guides/query.html#Report+Queries)
 */
public class CurrentReportDaoOjb extends BaseReportDaoOjb implements CurrentReportDao {
    
    private VersionHistoryService versionHistoryService;

     public List<CurrentReportBean> queryForCurrentSupport(String personId) throws WorkflowException {
        List<CurrentReportBean> data = new ArrayList<CurrentReportBean>();
        for(AwardPerson awardPerson: executeCurrentSupportQuery(personId)) {
            lazyLoadAward(awardPerson);
            CurrentReportBean bean = buildReportBean(awardPerson);
            if(bean != null)  {
                data.add(bean);
            }
        }
        return data;
    }

    private CurrentReportBean buildReportBean(AwardPerson awardPerson) throws WorkflowException {
        Award award = awardPerson.getAward();
        CurrentReportBean bean = null;
        if(shouldDataBeIncluded(award.getAwardDocument())
                && award.isActiveVersion()
                && ObjectUtils.equals(getActiveAwardVersionSequenceNumber(award.getAwardNumber()), award.getSequenceNumber())) {
            bean = new CurrentReportBean(awardPerson);
        }
        return bean;
    }

    @SuppressWarnings("unchecked")
    private Collection<AwardPerson> executeCurrentSupportQuery(String personId) {
        return getBusinessObjectService().findMatching(AwardPerson.class, Collections.singletonMap("personId", personId));
    }

    private void lazyLoadAward(AwardPerson awardPerson) {
        if(awardPerson.getAward() == null) {
            Map searchParms = CollectionUtils.zipMap(new String[]{"awardNumber", "sequenceNumber"},
                    new Object[]{awardPerson.getAwardNumber(), awardPerson.getSequenceNumber()});
            Award award = (Award) getBusinessObjectService().findMatching(Award.class, searchParms).iterator().next();
            awardPerson.setAward(award);
        }
    }
    
    private VersionHistoryService getVersionHistoryService() {
        if (versionHistoryService == null) {
            versionHistoryService = KcServiceLocator.getService(VersionHistoryService.class);
        }
        return versionHistoryService;
    }
    
    private Integer getActiveAwardVersionSequenceNumber(String awardNumber) {
        Integer retval = null;
        VersionHistory versionHistory = getVersionHistoryService().findActiveVersion(Award.class, awardNumber);
        if (versionHistory != null) {
            retval = versionHistory.getSequenceOwnerSequenceNumber();
        }
        return retval;
    }
}