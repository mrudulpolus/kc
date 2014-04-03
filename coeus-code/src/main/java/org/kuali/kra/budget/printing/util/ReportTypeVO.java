package org.kuali.kra.budget.printing.util;

import org.kuali.coeus.sys.api.model.ScaleTwoDecimal;

import java.sql.Date;

public class ReportTypeVO {

	private String rateClassDesc;
	private String rateTypeDesc;
	private Date startDate;
	private Date endDate;
	private ScaleTwoDecimal appliedRate;
	private ScaleTwoDecimal salaryRequested;
	private ScaleTwoDecimal calculatedCost;
	private Boolean onOffCampusFlag;
	private String costElementDesc;
	private String budgetCategoryDesc;
	private ScaleTwoDecimal costSharingAmount;
	private String personName;
	private ScaleTwoDecimal percentEffort;
	private ScaleTwoDecimal percentCharged;
	private String budgetCategoryCode;
	private Integer investigatorFlag;
	private ScaleTwoDecimal vacationRate;
	private ScaleTwoDecimal employeeBenefitRate;
	private ScaleTwoDecimal fringe;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public ScaleTwoDecimal getAppliedRate() {
		return appliedRate;
	}

	public void setAppliedRate(ScaleTwoDecimal appliedRate) {
		this.appliedRate = appliedRate;
	}

	public ScaleTwoDecimal getSalaryRequested() {
		return salaryRequested;
	}

	public void setSalaryRequested(ScaleTwoDecimal salaryRequested) {
		this.salaryRequested = salaryRequested;
	}

	public ScaleTwoDecimal getCalculatedCost() {
		return calculatedCost;
	}

	public void setCalculatedCost(ScaleTwoDecimal calculatedCost) {
		this.calculatedCost = calculatedCost;
	}

	public Boolean getOnOffCampusFlag() {
		return onOffCampusFlag;
	}

	public void setOnOffCampusFlag(Boolean onOffCampusFlag) {
		this.onOffCampusFlag = onOffCampusFlag;
	}

	public String getRateClassDesc() {
		return rateClassDesc;
	}

	public void setRateClassDesc(String rateClassDesc) {
		this.rateClassDesc = rateClassDesc;
	}

	public String getRateTypeDesc() {
		return rateTypeDesc;
	}

	public void setRateTypeDesc(String rateTypeDesc) {
		this.rateTypeDesc = rateTypeDesc;
	}

	public String getCostElementDesc() {
		return costElementDesc;
	}

	public void setCostElementDesc(String costElementDesc) {
		this.costElementDesc = costElementDesc;
	}

	public String getBudgetCategoryDesc() {
		return budgetCategoryDesc;
	}

	public void setBudgetCategoryDesc(String budgetCategoryDesc) {
		this.budgetCategoryDesc = budgetCategoryDesc;
	}

	public ScaleTwoDecimal getCostSharingAmount() {
		return costSharingAmount;
	}

	public void setCostSharingAmount(ScaleTwoDecimal costSharingAmount) {
		this.costSharingAmount = costSharingAmount;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public ScaleTwoDecimal getPercentEffort() {
		return percentEffort;
	}

	public void setPercentEffort(ScaleTwoDecimal percentEffort) {
		this.percentEffort = percentEffort;
	}

	public ScaleTwoDecimal getPercentCharged() {
		return percentCharged;
	}

	public void setPercentCharged(ScaleTwoDecimal percentCharged) {
		this.percentCharged = percentCharged;
	}

	public String getBudgetCategoryCode() {
		return budgetCategoryCode;
	}

	public void setBudgetCategoryCode(String budgetCategoryCode) {
		this.budgetCategoryCode = budgetCategoryCode;
	}

	public Integer getInvestigatorFlag() {
		return investigatorFlag;
	}

	public void setInvestigatorFlag(Integer investigatorFlag) {
		this.investigatorFlag = investigatorFlag;
	}

	public ScaleTwoDecimal getFringe() {
		return fringe;
	}

	public void setFringe(ScaleTwoDecimal fringe) {
		this.fringe = fringe;
	}

	public ScaleTwoDecimal getVacationRate() {
		return vacationRate;
	}

	public void setVacationRate(ScaleTwoDecimal vacationRate) {
		this.vacationRate = vacationRate;
	}

	public ScaleTwoDecimal getEmployeeBenefitRate() {
		return employeeBenefitRate;
	}

	public void setEmployeeBenefitRate(ScaleTwoDecimal employeeBenefitRate) {
		this.employeeBenefitRate = employeeBenefitRate;
	}
}
