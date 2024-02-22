package com.hillspet.wearables.dto.filter;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class IntakeFilter extends BaseFilter {

	@DefaultValue("0")
	@QueryParam("studyId")
	@ApiParam(name = "studyId", type = "integer", value = "study id. Default value is 0")
	private Integer studyId;

	@DefaultValue("0")
	@QueryParam("phaseId")
	@ApiParam(name = "phaseId", type = "integer", value = "phase id. Default value is 0")
	private Integer phaseId;

	@DefaultValue("0")
	@QueryParam("petId")
	@ApiParam(name = "petId", type = "String", value = "pet id. Default value is 0")
	private String petId;

	@ApiParam(name = "startDate", value = "Start Date is the first date component value of date range")
	@QueryParam("startDate")
	private String startDate;

	@ApiParam(name = "endDate", value = "End Date is the second date component value of date range")
	@QueryParam("endDate")
	private String endDate;

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

	public String getPetId() {
		return petId;
	}

	public void setPetId(String petId) {
		this.petId = petId;
	}

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
}
