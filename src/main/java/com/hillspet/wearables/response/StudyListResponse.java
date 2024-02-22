package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.StudyListDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudyListResponse extends BaseResultCollection {

	private List<StudyListDTO> studies;
	private List<StudyListDTO> studyList;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for Study list based on search criteria")
	public List<StudyListDTO> getStudies() {
		return studies;
	}

	public void setStudies(List<StudyListDTO> studies) {
		this.studies = studies;
	}

	@ApiModelProperty(value = "List of details for the Study list")
	public List<StudyListDTO> getStudyList() {
		return studyList;
	}

	public void setStudyList(List<StudyListDTO> studyList) {
		this.studyList = studyList;
	}
}
