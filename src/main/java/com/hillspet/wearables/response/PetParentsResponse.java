package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.PetParentDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetParentsResponse {
	private List<PetParentDTO> petParents;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for Pet Parent list based on search criteria")
	public List<PetParentDTO> getPetParents() {
		return petParents;
	}

	public void setPetParents(List<PetParentDTO> petParents) {
		this.petParents = petParents;
	}

}
