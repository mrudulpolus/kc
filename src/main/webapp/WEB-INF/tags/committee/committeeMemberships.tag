<%@ include file="/WEB-INF/jsp/kraTldHeader.jsp"%>

<%@ attribute name="readOnly" description="All fields are displayed as read-only elements." required="true" %>

<c:set var="committeeMembershipAttributes" value="${DataDictionary.CommitteeMembership.attributes}" />

<c:if test="${not empty KualiForm.document.committee.committeeMemberships}">
  <p align="center">
    <c:choose>
        <c:when test="${KualiForm.committeeHelper.showActiveMembersOnly}">
              <html:image property="methodToCall.showAllMembers" 
                          src="${ConfigProperties.kra.externalizable.images.url}buttonsmall_showallmembers.gif" 
                          title="Show All Members" 
                          alt="Show All Members" 
                          styleClass="globalbuttons"/>
        </c:when>
        <c:otherwise>
              <html:image property="methodToCall.showActiveMembersOnly" 
                          src="${ConfigProperties.kra.externalizable.images.url}buttonsmall_showactivemembers.gif" 
                          title="Show Active Members" 
                          alt="Show Active Members" 
                          styleClass="globalbuttons"/>
        </c:otherwise>
    </c:choose>
  </p>
</c:if>

<div id="workarea">
<c:forEach items="${KualiForm.document.committee.committeeMemberships}" var="membership" varStatus="status">
    <c:set var="committeeMembershipProperty" value="document.committeeList[0].committeeMemberships[${status.index}]" />
    <c:set var="committeeMembershipRoleProperty" value="committeeHelper.newCommitteeMembershipRoles[${status.index}]" />
    <c:set var="committeeMembershipExpertiseProperty" value="committeeHelper.newCommitteeMembershipExpertise[${status.index}]" />

    <kul:checkErrors keyMatch="${committeeMembershipProperty}.*,${committeeMembershipRoleProperty}.*,${committeeMembershipExpertiseProperty}.*" />

    <c:if test="${!KualiForm.committeeHelper.showActiveMembersOnly || membership.active || hasErrors || empty membership.membershipTypeCode}">
        
        <c:choose>
            <c:when test="${empty transparent}">
                <c:set var="transparent" value="true" />
            </c:when>
            <c:otherwise>
                <c:set var="transparent" value="false" />
            </c:otherwise>
        </c:choose>
    	
    	<%-- Create Tab Title --%>
        <c:choose>
            <c:when test="${membership.active}">
                <c:set var="tabTitleValue" value="${fn:substring(membership.personName, 0, 22)} (active)" />
            </c:when>
            <c:otherwise>
                <c:set var="tabTitleValue" value="${fn:substring(membership.personName, 0, 22)} (inactive)" />
            </c:otherwise>
        </c:choose>
    
        <%-- Create Tab Description --%>
        <c:choose>
            <c:when test="${empty membership.termStartDate && empty membership.termEndDate}">
                <c:set var="tabDescriptionValue" value=" " />
            </c:when>
            <c:otherwise>
                <c:set var="tabDescriptionValue" value="Term ${membership.formattedTermStartDate} - ${membership.formattedTermEndDate}" />
            </c:otherwise>
        </c:choose>
        
        <%-- Create Delete Checkbox --%>
        <c:choose>
            <c:when test="${!readOnly}">
                <c:set var="deleteProperty" value="${committeeMembershipProperty}.delete" />
            </c:when>
            <c:otherwise>
                <c:set var="deleteProperty" value="" />
            </c:otherwise>    
        </c:choose>
            
        <kul:tab tabTitle="${tabTitleValue}"
                 tabErrorKey="${committeeMembershipProperty}.delete"
                 innerTabErrorKey="${committeeMembershipProperty}.*,${committeeMembershipRoleProperty}.*,${committeeMembershipExpertiseProperty}.*"
                 auditCluster="requiredFieldsAuditErrors" 
                 tabAuditKey="" 
                 useRiceAuditMode="true"
                 tabDescription="${tabDescriptionValue}"
                 leftSideHtmlProperty="${deleteProperty}" 
                 leftSideHtmlAttribute="${committeeMembershipAttributes.delete}" 
                 leftSideHtmlDisabled="${readOnly}" 
                 defaultOpen="false"
                 useCurrentTabIndexAsKey="true" 
                 transparentBackground="${transparent}">
                 <div class="tab-container" align="center">
                     <div id="workarea">
                         <div class="tab-container" align="center" id="G100">
                             <h3>
                                 <span class="subhead-left"><bean:write name="KualiForm" property="${committeeMembershipProperty}.personName" /></span>
                                 <span class="subhead-right"><kul:help businessObjectClassName="org.kuali.kra.bo.Committee" altText="help" /></span>
                             </h3>
                             <kra-committee:committeeMembershipDetailsSection committeeMembership="${committeeMembershipProperty}" memberIndex="${status.index}" parentTabValue="${tabTitleValue}"  readOnly="${readOnly}" />
                             <kra-committee:committeeMembershipContactInformationSection committeeMembership="${committeeMembershipProperty}" memberIndex="${status.index}" parentTabValue="${tabTitleValue}"  readOnly="${readOnly}" />
                             <kra-committee:committeeMembershipRolesSection committeeMembership="${committeeMembershipProperty}" memberIndex="${status.index}" parentTabValue="${tabTitleValue}"  readOnly="${readOnly}" />
                             <kra-committee:committeeMembershipExpertiseSection committeeMembership="${committeeMembershipProperty}" memberIndex="${status.index}" parentTabValue="${tabTitleValue}" readOnly="${readOnly}" />
                         </div>
                     </div>
                 </div>
        </kul:tab>
    </c:if>
 </c:forEach>

<c:if test="${not empty transparent}">
    <kul:panelFooter />
</c:if>

</div>