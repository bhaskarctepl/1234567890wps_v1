package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.PreDefinedInstruction;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PreDefinedInstructionsResponse {

	private List<PreDefinedInstruction> preDefinedInstructions;

	public List<PreDefinedInstruction> getPreDefinedInstructions() {
		return preDefinedInstructions;
	}

	public void setPreDefinedInstructions(List<PreDefinedInstruction> preDefinedInstructions) {
		this.preDefinedInstructions = preDefinedInstructions;
	}

}
