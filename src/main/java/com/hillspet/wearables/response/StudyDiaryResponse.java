package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.StudyDiary;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudyDiaryResponse extends BaseResultCollection {

	private List<StudyDiary> studyDairyList;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of feeding schedules for a study")
	public List<StudyDiary> getStudyDairyList() {
		return studyDairyList;
	}

	public void setStudyDairyList(List<StudyDiary> studyDairyList) {
		this.studyDairyList = studyDairyList;
	}

	
	
}
