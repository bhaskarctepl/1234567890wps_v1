package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.PetBreed;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetBreedResponse {

	private List<PetBreed> breeds;

	@ApiModelProperty(value = "List of details for Pet Breeds")
	public List<PetBreed> getBreeds() {
		return breeds;
	}

	public void setBreeds(List<PetBreed> breeds) {
		this.breeds = breeds;
	}

}
