package com.hillspet.wearables.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contains all information that needed to add notes to a study", value = "StudyNotesRequest")
@JsonInclude(value = Include.NON_NULL)
public class StudyNotesRequest {

	@ApiModelProperty(value = "studyNotesId", required = false)
	private Integer studyNotesId;

	@ApiModelProperty(value = "notes", required = true)
	private String notes;

	public Integer getStudyNotesId() {
		return studyNotesId;
	}

	public void setStudyNotesId(Integer studyNotesId) {
		this.studyNotesId = studyNotesId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
