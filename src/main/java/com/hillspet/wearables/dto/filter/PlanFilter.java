package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class PlanFilter extends BaseFilter {

	@ApiParam(name = "studyId", value = "Search based on study drop down-Single or Multiple study ids")
	@QueryParam("studyId")
	private String studyId;

	public PlanFilter() {

	}

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	

	
}
