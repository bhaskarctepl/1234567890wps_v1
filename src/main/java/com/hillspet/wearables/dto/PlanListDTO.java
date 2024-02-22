package com.hillspet.wearables.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlanListDTO extends BaseDTO {
	private Integer planId;
	private String planName;
	private String studyId;
	private String studyName;
	private String planShortName;
	private String planDescription;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private Integer billingType;
	private Integer isDelete;
	private String createDate;
	private String associationDate;
	private String[] studyNames;
	private String[] associationDates;
	private String[] StudyIds;
	private String isActiv;
	private Integer totalCount;
	private List studyAssociatedObject;

	public String[] getStudyIds() {
		return StudyIds;
	}

	public void setStudyIds(String[] studyIds) {
		StudyIds = studyIds;
	}

	public List getStudyAssociatedObject() {
		return studyAssociatedObject;
	}

	public void setStudy_Dates(List studyAssociatedList) {
		this.studyAssociatedObject = studyAssociatedList;
	}

	public String[] getAssociationDates() {
		return associationDates;
	}

	public void setAssociationDates(String[] associationDates) {
		this.associationDates = associationDates;
	}

	public String[] getStudyNames() {
		return studyNames;
	}

	public void setStudyNames(String[] studyNames) {
		this.studyNames = studyNames;
	}

	public String getAssociationDate() {
		return associationDate;
	}

	public void setAssociationDate(String associationDate) {
		this.associationDate = associationDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public String getIsActiv() {
		return isActiv;
	}

	public void setIsActiv(String isActiv) {
		this.isActiv = isActiv;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public String getPlanShortName() {
		return planShortName;
	}

	public void setPlanShortName(String planShortName) {
		this.planShortName = planShortName;
	}

	public String getPlanDescription() {
		return planDescription;
	}

	public void setPlanDescription(String planDescription) {
		this.planDescription = planDescription;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public Integer getBillingType() {
		return billingType;
	}

	public void setBillingType(Integer billingType) {
		this.billingType = billingType;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

}
