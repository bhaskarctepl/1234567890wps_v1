package com.hillspet.wearables.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contains all the information that needed to Validate Duplicate Pet", value = "ValidateDuplicatePetRequest")
@JsonInclude(value = Include.NON_NULL)
public class ValidateDuplicatePetRequest {

	@ApiModelProperty(value = "petId", required = true, example = "7143")
	private Integer petId;
	
	@ApiModelProperty(value = "petName", required = true, example = "Beaumont")
	private String petName;
	
	@ApiModelProperty(value = "breedId", required = true, example = "2")
	private int breedId;
	
	@ApiModelProperty(value = "speciesId", required = true, example = "1")
	private int speciesId;
	
	private List<PetParentRequest> petParents;
	
	private Integer userId;
	
	private String gender;
	

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getBreedId() {
		return breedId;
	}

	public void setBreedId(int breedId) {
		this.breedId = breedId;
	}

	public int getSpeciesId() {
		return speciesId;
	}

	public void setSpeciesId(int speciesId) {
		this.speciesId = speciesId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public List<PetParentRequest> getPetParents() {
		return petParents;
	}

	public void setPetParents(List<PetParentRequest> petParents) {
		this.petParents = petParents;
	}
	
	
}
