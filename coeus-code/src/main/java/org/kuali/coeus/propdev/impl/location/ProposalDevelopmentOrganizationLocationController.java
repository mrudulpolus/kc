package org.kuali.coeus.propdev.impl.location;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.kuali.coeus.common.framework.rolodex.Rolodex;
import org.kuali.coeus.propdev.impl.core.ProposalDevelopmentControllerBase;
import org.kuali.coeus.propdev.impl.core.ProposalDevelopmentDocumentForm;
import org.kuali.coeus.propdev.impl.location.ProposalSite;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.rice.kns.lookup.LookupableHelperService;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ProposalDevelopmentOrganizationLocationController extends ProposalDevelopmentControllerBase{
	
	
	public LookupableHelperService rolodexLookupableHelperService;	
	// deprecated method. need to extend LookupableImpl
	
	
	 @RequestMapping(value = "/proposalDevelopment", params="methodToCall=performOrganizationalSearch")
	   public ModelAndView performOrganizationalSearch(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
	           HttpServletRequest request, HttpServletResponse response) throws Exception {
		 	ProposalDevelopmentDocumentForm pdForm = (ProposalDevelopmentDocumentForm) form;
		 	pdForm.getAddOrganizationHelper().getResults().clear();
		 	getRolodexLookupableHelperService().setBusinessObjectClass(Rolodex.class);
		 	List<Rolodex> results = (List<Rolodex>) getRolodexLookupableHelperService().getSearchResults(pdForm.getAddOrganizationHelper().getLookupFieldValues());		 	
		 	pdForm.getAddOrganizationHelper().setResults(results);
	     return getTransactionalDocumentControllerService().refresh(form, result, request, response);
		
	   }

	public LookupableHelperService getRolodexLookupableHelperService() {		
		if (rolodexLookupableHelperService == null) {
			rolodexLookupableHelperService = KcServiceLocator.getService("rolodexLookupableHelperService");
        }       
		return rolodexLookupableHelperService;
	}

	public void setRolodexLookupableHelperService(
			LookupableHelperService rolodexLookupableHelperService) {
		rolodexLookupableHelperService = rolodexLookupableHelperService;
	}
	
	@RequestMapping(value = "/proposalDevelopment", params="methodToCall=addNewOrganization")
	   public ModelAndView addNewOrganization(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
	           HttpServletRequest request, HttpServletResponse response) throws Exception {	
			ProposalDevelopmentDocumentForm pdForm = (ProposalDevelopmentDocumentForm) form;
			pdForm.getAddOrganizationHelper().getNewOrganizationFieldValues();
		 	
		 	ProposalSite performanceSite = new ProposalSite();		 	
		 	Rolodex rolodex= new Rolodex();		 	
		 	rolodex.setOrganization(pdForm.getAddOrganizationHelper().getNewOrganizationFieldValues().get("organization"));
		 	rolodex.setAddressLine1(pdForm.getAddOrganizationHelper().getNewOrganizationFieldValues().get("addressLine1"));
		 	rolodex.setAddressLine2(pdForm.getAddOrganizationHelper().getNewOrganizationFieldValues().get("addressLine2"));
		 	rolodex.setCity(pdForm.getAddOrganizationHelper().getNewOrganizationFieldValues().get("city"));
		 	rolodex.setState(pdForm.getAddOrganizationHelper().getNewOrganizationFieldValues().get("state"));
		 	rolodex.setPostalCode(pdForm.getAddOrganizationHelper().getNewOrganizationFieldValues().get("postalCode")); 	
		 	
		 	performanceSite.setRolodex(rolodex);
		 	performanceSite.setLocationTypeCode(ProposalSite.PROPOSAL_SITE_PERFORMANCE_SITE);
		 	pdForm.getDevelopmentProposal().addPerformanceSite(performanceSite);
			return null;
	}	
	
	@RequestMapping(value = "/proposalDevelopment", params="methodToCall=addDistrict")
	   public ModelAndView addDistrict(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
	           HttpServletRequest request, HttpServletResponse response) throws Exception {	
			ProposalDevelopmentDocumentForm pdForm = (ProposalDevelopmentDocumentForm) form;			
			return null;
	}
	
	
	@RequestMapping(value = "/proposalDevelopment", params="methodToCall=selectLine")
	   public ModelAndView selectLine(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
	           HttpServletRequest request, HttpServletResponse response) throws Exception {	
		 final String selectedCollectionPath = form.getActionParamaterValue(UifParameters.SELECTED_COLLECTION_PATH);
	        final String selectedCollectionId = form.getActionParamaterValue(UifParameters.SELECTED_COLLECTION_ID);

	        if (StringUtils.isBlank(selectedCollectionPath)) {
	            throw new RuntimeException("Selected collection was not set for delete line action, cannot delete line");
	        }

	        String selectedLine = form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);	        
	        ProposalDevelopmentDocumentForm pdForm = (ProposalDevelopmentDocumentForm) form;	        
	        ProposalSite performanceSite = new ProposalSite();
	        performanceSite.setLocationTypeCode(ProposalSite.PROPOSAL_SITE_PERFORMANCE_SITE);	        
	        performanceSite.setRolodex(pdForm.getAddOrganizationHelper().getResults().get(Integer.parseInt(selectedLine)));
	        
	        pdForm.getDevelopmentProposal().addPerformanceSite(performanceSite);
	     
	      /*  final int selectedLineIndex;
	        if (StringUtils.isNotBlank(selectedLine)) {
	            selectedLineIndex = Integer.parseInt(selectedLine);
	        } else {
	            selectedLineIndex = -1;
	        }

	        if (selectedLineIndex == -1) {
	            throw new RuntimeException("Selected line index was not set for delete line action, cannot delete line");
	        }
	       */

	        return null;
	}
	

	
	
	/**
     * Called by the delete line action for a model collection. Method
     * determines which collection the action was selected for and the line
     * index that should be removed, then invokes the view helper service to
     * process the action
     *//*
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=selectLine")
    public ModelAndView selectLine(@ModelAttribute("KualiForm") final UifFormBase uifForm, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {

        final String selectedCollectionPath = uifForm.getActionParamaterValue(UifParameters.SELECTED_COLLECTION_PATH);
        final String selectedCollectionId = uifForm.getActionParamaterValue(UifParameters.SELECTED_COLLECTION_ID);

        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("Selected collection was not set for delete line action, cannot delete line");
        }

        String selectedLine = uifForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        final int selectedLineIndex;
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        } else {
            selectedLineIndex = -1;
        }

        if (selectedLineIndex == -1) {
            throw new RuntimeException("Selected line index was not set for delete line action, cannot delete line");
        }

        ViewLifecycle.encapsulateLifecycle(uifForm.getView(), uifForm, uifForm.getViewPostMetadata(), null, request,
                response, new Runnable() {
            @Override
            public void run() {
                ViewLifecycle.getHelper().processCollectionDeleteLine(uifForm, selectedCollectionId,
                        selectedCollectionPath, selectedLineIndex);
            }
        });

        return getUIFModelAndView(uifForm);
    }*/
	
}
