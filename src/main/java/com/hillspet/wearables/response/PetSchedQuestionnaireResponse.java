package com.hillspet.wearables.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hillspet.wearables.dto.PetSchedQuestionnaire;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetSchedQuestionnaireResponse {

	private PetSchedQuestionnaire petSchedQuestionnaire;

	public PetSchedQuestionnaire getPetSchedQuestionnaire() {
		return petSchedQuestionnaire;
	}

	public void setPetSchedQuestionnaire(PetSchedQuestionnaire petSchedQuestionnaire) {
		this.petSchedQuestionnaire = petSchedQuestionnaire;
	}

}
