package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.PetStatus;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetStatusResponse {

	private List<PetStatus> petStatuses;

	@ApiModelProperty(value = "List of details for Pet Statuses")
	public List<PetStatus> getPetStatuses() {
		return petStatuses;
	}

	public void setPetStatuses(List<PetStatus> petStatuses) {
		this.petStatuses = petStatuses;
	}

}
