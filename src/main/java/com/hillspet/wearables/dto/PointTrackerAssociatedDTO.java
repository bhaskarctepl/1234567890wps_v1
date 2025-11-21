package com.hillspet.wearables.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PointTrackerAssociatedDTO {

	private Integer id;
	private String activityName;
	private String points;
	private String eligibleForPointsAccumulation;
	private LocalDate startDate;
	private LocalDate endDate;
	private String studyConfigId;

	
	
	public String getStudyConfigId() {
		return studyConfigId;
	}

	public void setStudyConfigId(String studyConfigId) {
		this.studyConfigId = studyConfigId;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getEligibleForPointsAccumulation() {
		return eligibleForPointsAccumulation;
	}

	public void setEligibleForPointsAccumulation(String eligibleForPointsAccumulation) {
		this.eligibleForPointsAccumulation = eligibleForPointsAccumulation;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

}
