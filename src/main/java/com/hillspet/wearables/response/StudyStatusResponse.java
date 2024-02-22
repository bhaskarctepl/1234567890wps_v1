package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hillspet.wearables.dto.StudyStatus;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudyStatusResponse {

	private List<StudyStatus> studyStatuses;

	public List<StudyStatus> getStudyStatuses() {
		return studyStatuses;
	}

	public void setStudyStatuses(List<StudyStatus> studyStatuses) {
		this.studyStatuses = studyStatuses;
	}

}
