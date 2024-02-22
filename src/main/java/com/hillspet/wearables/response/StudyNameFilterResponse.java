package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.StudyNameFilter;
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyNameFilterResponse {
	List<StudyNameFilter> studyNameList;

	public List<StudyNameFilter> getStudyNameList() {
		return studyNameList;
	}

	public void setStudyNameList(List<StudyNameFilter> studyNameList) {
		this.studyNameList = studyNameList;
	}
}
