package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.StudyDietListDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudyDietListResponse extends BaseResultCollection {

	private List<StudyDietListDTO> studyDiets;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for Study deits list based on search criteria")
	public List<StudyDietListDTO> getStudyDiets() {
		return studyDiets;
	}

	public void setStudyDiets(List<StudyDietListDTO> studyDiets) {
		this.studyDiets = studyDiets;
	}
}