package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.PetSchedQuestionnaire;

import io.swagger.annotations.ApiModelProperty;

public class PetSchedQuestionnaireListResponse extends BaseResultCollection {

	private List<PetSchedQuestionnaire> petSchedQuestionnaires;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for Questionnaires Schedules for Pets based on search criteria")
	public List<PetSchedQuestionnaire> getPetSchedQuestionnaires() {
		return petSchedQuestionnaires;
	}

	public void setPetSchedQuestionnaires(List<PetSchedQuestionnaire> petSchedQuestionnaires) {
		this.petSchedQuestionnaires = petSchedQuestionnaires;
	}

}
