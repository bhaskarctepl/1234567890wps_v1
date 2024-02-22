package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudyDiet {
	
	@ApiModelProperty(value = "studyDietId", required = false)
	private Integer studyDietId;

	@ApiModelProperty(value = "dietId", required = true)
	private Integer dietId;

	public Integer getStudyDietId() {
		return studyDietId;
	}

	public void setStudyDietId(Integer studyDietId) {
		this.studyDietId = studyDietId;
	}

	public Integer getDietId() {
		return dietId;
	}

	public void setDietId(Integer dietId) {
		this.dietId = dietId;
	}

}
