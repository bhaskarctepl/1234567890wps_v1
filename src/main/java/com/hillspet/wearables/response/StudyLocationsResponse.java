package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hillspet.wearables.dto.StudyLocation;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudyLocationsResponse {

	private List<StudyLocation> studyLocations;

	public List<StudyLocation> getStudyLocations() {
		return studyLocations;
	}

	public void setStudyLocations(List<StudyLocation> studyLocations) {
		this.studyLocations = studyLocations;
	}

}
