package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class PointTrackFilter {
	
	
	@QueryParam("studyId")
	@ApiParam(name = "studyId", type = "String",  required = true)
	private String studyId;
	
	@QueryParam("phaseId")
	@ApiParam(name = "phaseId", type = "String", required = true)
	private String phaseId;
	
	@QueryParam("startDate")
	@ApiParam(name = "startDate", type = "String", required = false)
	private String startDate;
	
	@QueryParam("endDate")
	@ApiParam(name = "endDate", type = "String",  required = false)
	private String endDate;

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	public String getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(String phaseId) {
		this.phaseId = phaseId;
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
