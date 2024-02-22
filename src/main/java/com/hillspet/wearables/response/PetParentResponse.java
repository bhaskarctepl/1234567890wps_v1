package com.hillspet.wearables.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.PetParentDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetParentResponse {
	
	private PetParentDTO petParentDto;
	
	@JsonProperty("petParent")
	@ApiModelProperty(value = "List of details of Pet Parent details")
	public PetParentDTO getPetParentDto() {
		return petParentDto;
	}

	public void setPetParentDto(PetParentDTO petParentDto) {
		this.petParentDto = petParentDto;
	}
}
