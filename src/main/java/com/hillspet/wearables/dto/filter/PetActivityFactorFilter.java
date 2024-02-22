package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class PetActivityFactorFilter extends BaseFilter {

	@QueryParam("petIds")
	@ApiParam(name = "petIds", type = "integer", value = "Search based on pet id")
	private String petIds;

	@QueryParam("studyIds")
	@ApiParam(name = "studyIds", type = "integer", value = "Search based on study id")
	private String studyIds;

	@ApiParam(name = "startDate", value = "Start Date is the first date component value of date range")
	@QueryParam("startDate")
	private String startDate;

	@ApiParam(name = "endDate", value = "End Date is the second date component value of date range")
	@QueryParam("endDate")
	private String endDate;
	public String getPetIds() {
		return petIds;
	}
	public void setPetIds(String petIds) {
		this.petIds = petIds;
	}
	public String getStudyIds() {
		return studyIds;
	}

	public void setStudyIds(String studyIds) {
		this.studyIds = studyIds;
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
