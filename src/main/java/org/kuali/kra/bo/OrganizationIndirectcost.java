package org.kuali.kra.bo;

import java.sql.Date;
import java.util.LinkedHashMap;

import org.kuali.kra.budget.RateDecimal;

public class OrganizationIndirectcost extends KraPersistableBusinessObjectBase {

	private Integer idcNumber;
	private String organizationId;
	private RateDecimal applicableIndirectcostRate;
	private Date endDate;
	private String idcComment;
	private Integer idcRateTypeCode;
	private Date requestedDate;
	private Date startDate;
    private Organization organization;

	public OrganizationIndirectcost(){
		super();
	}

	public Integer getIdcNumber() {
		return idcNumber;
	}

	public void setIdcNumber(Integer idcNumber) {
		this.idcNumber = idcNumber;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public RateDecimal getApplicableIndirectcostRate() {
		return applicableIndirectcostRate;
	}

	public void setApplicableIndirectcostRate(RateDecimal applicableIndirectcostRate) {
		this.applicableIndirectcostRate = applicableIndirectcostRate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getIdcComment() {
		return idcComment;
	}

	public void setIdcComment(String idcComment) {
		this.idcComment = idcComment;
	}

	public Integer getIdcRateTypeCode() {
		return idcRateTypeCode;
	}

	public void setIdcRateTypeCode(Integer idcRateTypeCode) {
		this.idcRateTypeCode = idcRateTypeCode;
	}

	public Date getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	@Override 
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap hashMap = new LinkedHashMap();
		hashMap.put("idcNumber", getIdcNumber());
		hashMap.put("organizationId", getOrganizationId());
		hashMap.put("applicableIndirectcostRate", getApplicableIndirectcostRate());
		hashMap.put("endDate", getEndDate());
		hashMap.put("idcComment", getIdcComment());
		hashMap.put("idcRateTypeCode", getIdcRateTypeCode());
		hashMap.put("requestedDate", getRequestedDate());
		hashMap.put("startDate", getStartDate());
		return hashMap;
	}

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
