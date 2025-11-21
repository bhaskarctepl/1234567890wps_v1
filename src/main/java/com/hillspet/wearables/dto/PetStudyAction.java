package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetStudyAction {

	private Integer petStudyActionId;
	private String petStudyActionName;
	private String colourCode;

	public Integer getPetStudyActionId() {
		return petStudyActionId;
	}

	public void setPetStudyActionId(Integer petStudyActionId) {
		this.petStudyActionId = petStudyActionId;
	}

	public String getPetStudyActionName() {
		return petStudyActionName;
	}

	public void setPetStudyActionName(String petStudyActionName) {
		this.petStudyActionName = petStudyActionName;
	}

	public String getColourCode() {
		return colourCode;
	}

	public void setColourCode(String colourCode) {
		this.colourCode = colourCode;
	}

}
