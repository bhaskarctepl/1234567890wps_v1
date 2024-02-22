package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudyStatus {

	private Integer studyStatusId;

	private String studyStatus;

	public Integer getStudyStatusId() {
		return studyStatusId;
	}

	public void setStudyStatusId(Integer studyStatusId) {
		this.studyStatusId = studyStatusId;
	}

	public String getStudyStatus() {
		return studyStatus;
	}

	public void setStudyStatus(String studyStatus) {
		this.studyStatus = studyStatus;
	}

}
