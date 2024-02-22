package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class StudyPlanFilter extends BaseFilter {
	@ApiParam(name = "studyId", value = "study id")
	@QueryParam("studyId")
	private Integer studyId;

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

}
