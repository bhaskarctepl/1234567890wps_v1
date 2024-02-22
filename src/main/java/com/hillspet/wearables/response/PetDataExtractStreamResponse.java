package com.hillspet.wearables.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.PetDataExtractStreamDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetDataExtractStreamResponse {

	private PetDataExtractStreamDTO petDataExtractStreamDTO;

	@ApiModelProperty(value = "Get petDataExtractStream details of particular id")
	public PetDataExtractStreamDTO getPetDataExtractStreamDTO() {
		return petDataExtractStreamDTO;
	}

	public void setPetDataExtractStreamDTO(PetDataExtractStreamDTO petDataExtractStreamDTO) {
		this.petDataExtractStreamDTO = petDataExtractStreamDTO;
	}

}
