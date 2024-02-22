package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class ImageScaleFilter extends BaseFilter {

	@ApiParam(name = "classificationId", value = "Search based on classification drop down")
	@QueryParam("classificationId")
	private String classificationId;

	@ApiParam(name = "scoringTypeId", value = "Search based on scoring Type drop down")
	@QueryParam("scoringTypeId")
	private String scoringTypeId;

	private int studyId;

	private int phaseId;

	@ApiParam(name = "startDate", value = "2024-01-18")
	@QueryParam("startDate")
	private String startDate;

	@ApiParam(name = "endDate", value = "2024-01-18")
	@QueryParam("endDate")
	private String endDate;

	public ImageScaleFilter() {

	}

	public String getClassificationId() {
		return classificationId;
	}

	public void setClassificationId(String classificationId) {
		this.classificationId = classificationId;
	}

	public String getScoringTypeId() {
		return scoringTypeId;
	}

	public void setScoringTypeId(String scoringTypeId) {
		this.scoringTypeId = scoringTypeId;
	}

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

}
