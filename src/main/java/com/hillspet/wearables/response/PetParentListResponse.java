package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.PetParentListDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetParentListResponse extends BaseResultCollection {
	private List<PetParentListDTO> petParentList;

	private List<PetParentListDTO> petParents;

	public List<PetParentListDTO> getPetParentList() {
		return petParentList;
	}

	public void setPetParentList(List<PetParentListDTO> petParentList) {
		this.petParentList = petParentList;
	}

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for Pet Parent list based on search criteria")
	public List<PetParentListDTO> getPetParents() {
		return petParents;
	}

	public void setPetParents(List<PetParentListDTO> petParents) {
		this.petParents = petParents;
	}

}
