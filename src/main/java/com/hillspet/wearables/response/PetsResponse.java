package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.PetListDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetsResponse extends BaseResultCollection {
	private List<PetListDTO> petsList;
	private List<PetListDTO> pets;

	private Integer totalActivePets;
	private Integer totalActiveStudies;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for Pets based on search criteria")
	public List<PetListDTO> getPetsList() {
		return petsList;
	}

	public void setPetsList(List<PetListDTO> petsList) {
		this.petsList = petsList;
	}

	public Integer getTotalActivePets() {
		return totalActivePets;
	}

	public void setTotalActivePets(Integer totalActivePets) {
		this.totalActivePets = totalActivePets;
	}

	public Integer getTotalActiveStudies() {
		return totalActiveStudies;
	}

	public void setTotalActiveStudies(Integer totalActiveStudies) {
		this.totalActiveStudies = totalActiveStudies;
	}

	public List<PetListDTO> getPets() {
		return pets;
	}

	public void setPets(List<PetListDTO> pets) {
		this.pets = pets;
	}

}
