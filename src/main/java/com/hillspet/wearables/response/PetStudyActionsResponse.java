package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.PetStudyAction;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetStudyActionsResponse {

	private List<PetStudyAction> PetStudyActions;

	public List<PetStudyAction> getPetStudyActions() {
		return PetStudyActions;
	}

	public void setPetStudyActions(List<PetStudyAction> petStudyActions) {
		PetStudyActions = petStudyActions;
	}

}
