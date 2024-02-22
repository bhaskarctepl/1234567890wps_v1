package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudyLocation {

	private Integer studyLocationId;
	private String studyLocation;

	public Integer getStudyLocationId() {
		return studyLocationId;
	}

	public void setStudyLocationId(Integer studyLocationId) {
		this.studyLocationId = studyLocationId;
	}

	public String getStudyLocation() {
		return studyLocation;
	}

	public void setStudyLocation(String studyLocation) {
		this.studyLocation = studyLocation;
	}

}
