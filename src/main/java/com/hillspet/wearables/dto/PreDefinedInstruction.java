package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PreDefinedInstruction {

	private Integer preDefinedInstructionId;
	private String preDefinedInstruction;

	public Integer getPreDefinedInstructionId() {
		return preDefinedInstructionId;
	}

	public void setPreDefinedInstructionId(Integer preDefinedInstructionId) {
		this.preDefinedInstructionId = preDefinedInstructionId;
	}

	public String getPreDefinedInstruction() {
		return preDefinedInstruction;
	}

	public void setPreDefinedInstruction(String preDefinedInstruction) {
		this.preDefinedInstruction = preDefinedInstruction;
	}

}
