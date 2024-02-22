package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.PetDataStreamDTO;
import com.hillspet.wearables.dto.PetListDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DuplicatePetResponse extends BaseResultCollection {

	private List<PetListDTO> primaryPetsList;

	private List<PetListDTO> primaryPets;
	private List<PetListDTO> duplicatePets;
	
	private List<PetDataStreamDTO> petDataStream;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for Primary Pets mapped with duplicate Pets based on search criteria")
	public List<PetListDTO> getPrimaryPetsList() {
		return primaryPetsList;
	}

	
	public List<PetDataStreamDTO> getPetDataStream() {
		return petDataStream;
	}


	public void setPetDataStream(List<PetDataStreamDTO> petDataStream) {
		this.petDataStream = petDataStream;
	}


	public void setPrimaryPetsList(List<PetListDTO> primaryPetsList) {
		this.primaryPetsList = primaryPetsList;
	}

	public List<PetListDTO> getPrimaryPets() {
		return primaryPets;
	}

	public void setPrimaryPets(List<PetListDTO> primaryPets) {
		this.primaryPets = primaryPets;
	}

	public List<PetListDTO> getDuplicatePets() {
		return duplicatePets;
	}

	public void setDuplicatePets(List<PetListDTO> duplicatePets) {
		this.duplicatePets = duplicatePets;
	}

}
