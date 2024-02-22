package com.hillspet.wearables.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hillspet.wearables.dto.StudyDiet;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contains all information that needed to create study", value = "StudyDietRequest")
@JsonInclude(value = Include.NON_NULL)
public class StudyDietRequest {

	@ApiModelProperty(value = "studyDietList", required = false)
	private List<StudyDiet> studyDietList;

	public List<StudyDiet> getStudyDietList() {
		return studyDietList;
	}

	public void setStudyDietList(List<StudyDiet> studyDietList) {
		this.studyDietList = studyDietList;
	}

}
