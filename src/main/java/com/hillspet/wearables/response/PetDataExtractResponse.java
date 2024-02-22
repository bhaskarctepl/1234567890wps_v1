package com.hillspet.wearables.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.PetDataExtractStreamsDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetDataExtractResponse {

	private PetDataExtractStreamsDTO petDataExtractStreamsDTO;

	@ApiModelProperty(value = "Get petDataExtractStream details of particular id")
	public PetDataExtractStreamsDTO getPetDataExtractStreamsDTO() {
		return petDataExtractStreamsDTO;
	}

	public void setPetDataExtractStreamsDTO(PetDataExtractStreamsDTO petDataExtractStreamsDTO) {
		this.petDataExtractStreamsDTO = petDataExtractStreamsDTO;
	}

}
