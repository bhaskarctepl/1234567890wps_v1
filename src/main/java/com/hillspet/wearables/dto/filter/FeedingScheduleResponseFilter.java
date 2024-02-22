package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

/**
 * @author yreddy
 *
 */
public class FeedingScheduleResponseFilter extends BaseFilter {

	
  	private int studyId;
	
  	private int phaseId;
  	
 	private int petId;
	
	

	@ApiParam(name = "startDate", value = "2024-01-18")
	@QueryParam("startDate")
	private String startDate;
	
	@ApiParam(name = "endDate", value = "2024-01-18")
	@QueryParam("endDate")
	private String endDate;
	
	
	

	public int getStudyId() {
		return studyId;
	}

	public void setStudyId(int studyId) {
		this.studyId = studyId;
	}

	public int getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(int phaseId) {
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

	
	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}



}
