package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.PetName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetsListResponse {
	private List<PetName> pets;

	public List<PetName> getPets() {
		return pets;
	}

	public void setPets(List<PetName> pets) {
		this.pets = pets;
	}

	
}
