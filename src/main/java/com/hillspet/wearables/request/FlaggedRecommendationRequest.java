package com.hillspet.wearables.request;

import javax.ws.rs.QueryParam;

import com.hillspet.wearables.dto.filter.BaseFilter;

import io.swagger.annotations.ApiParam;

public class FlaggedRecommendationRequest extends BaseFilter {

	@QueryParam("startDate")
	@ApiParam(name = "startDate", value = "startDate is report start date")
	// @ApiModelProperty(value = "startDate", required = true)
	private String startDate;

	@QueryParam("endDate")
	@ApiParam(name = "endDate", value = "endDate is report end date")
	// @ApiModelProperty(value = "endDate", required = true)
	private String endDate;

	@QueryParam("petIds")
	@ApiParam(name = "petIds", type = "String", value = "selected pet IDS")
	private String petIds;

	@QueryParam("thresholdIds")
	@ApiParam(name = "thresholdIds", type = "String", value = "selected threshold IDS")
	private String thresholdIds;

	@QueryParam("actionIds")
	@ApiParam(name = "actionIds", type = "String", value = "selected action IDS")
	private String actionIds;

	@QueryParam("studyId")
	@ApiParam(name = "studyId", type = "String", value = "selected studyId")
	private Integer studyId;

	@QueryParam("phaseId")
	@ApiParam(name = "phaseId", type = "String", value = "selected phaseId")
	private Integer phaseId;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPetIds() {
		return petIds;
	}

	public void setPetIds(String petIds) {
		this.petIds = petIds;
	}

	public String getThresholdIds() {
		return thresholdIds;
	}

	public void setThresholdIds(String thresholdIds) {
		this.thresholdIds = thresholdIds;
	}

	public String getActionIds() {
		return actionIds;
	}

	public void setActionIds(String actionIds) {
		this.actionIds = actionIds;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public Integer getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(Integer phaseId) {
		this.phaseId = phaseId;
	}

}
