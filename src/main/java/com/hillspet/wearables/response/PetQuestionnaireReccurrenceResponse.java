package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.PetQuestionnaireReccurrence;

import io.swagger.annotations.ApiModelProperty;

public class PetQuestionnaireReccurrenceResponse extends BaseResultCollection {

	private List<PetQuestionnaireReccurrence> petQuestionnaireReccurrences;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for Questionnaires reccurrence for Pets based on search criteria")
	public List<PetQuestionnaireReccurrence> getPetQuestionnaireReccurrences() {
		return petQuestionnaireReccurrences;
	}

	public void setPetQuestionnaireReccurrences(List<PetQuestionnaireReccurrence> petQuestionnaireReccurrences) {
		this.petQuestionnaireReccurrences = petQuestionnaireReccurrences;
	}

}
