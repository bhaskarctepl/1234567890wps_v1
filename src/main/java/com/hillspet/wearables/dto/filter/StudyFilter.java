package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class StudyFilter extends BaseFilter {

	@ApiParam(name = "planId", value = "Plan Ids for the study")
	@QueryParam("planId")
	private String planId;

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	

	

}
