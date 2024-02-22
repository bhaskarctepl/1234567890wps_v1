package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class PetObservationMediaFilter extends BaseFilter {

	@ApiParam(name = "startDate", value = "Start Date is the first date component value of date range")
	@QueryParam("startDate")
	private String startDate;

	@ApiParam(name = "endDate", value = "End Date is the second date component value of date range")
	@QueryParam("endDate")
	private String endDate;

	@ApiParam(name = "status", value = "Status key holds status value.")
	@QueryParam("status")
	private String status;

	@ApiParam(name = "study", value = "Study key holds Study value.")
	@QueryParam("study")
	private String study;

	@ApiParam(name = "petId", value = "Pet Id key holds Pet Id.")
	@QueryParam("petId")
	private String petId;

	@ApiParam(name = "studyId", value = "Study Id key holds Study Id.")
	@QueryParam("studyId")
	private String studyId;

	@ApiParam(name = "phaseId", value = "Study Id key holds Study Id.")
	@QueryParam("phaseId")
	private String phaseId;

	public PetObservationMediaFilter() {

	}

	public PetObservationMediaFilter(String startDate, String endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStudy() {
		return study;
	}

	public void setStudy(String study) {
		this.study = study;
	}

	public String getPetId() {
		return petId;
	}

	public void setPetId(String petId) {
		this.petId = petId;
	}

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

}
