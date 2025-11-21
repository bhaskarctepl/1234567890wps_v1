package com.hillspet.wearables.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DuplicatePetConfigDTO {

	private Integer primaryPetId;
	private String petName;
	private String dataStreamId;
	private String duplicatePets;
	private String petStitchGroupId;
	private String petParentNames;
	private Boolean isPetVip;
	private String gender;
	private String breedName;
	private LocalDate dateOfBirth;

	public String getPetStitchGroupId() {
		return petStitchGroupId;
	}

	public void setPetStitchGroupId(String petStitchGroupId) {
		this.petStitchGroupId = petStitchGroupId;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public Integer getPrimaryPetId() {
		return primaryPetId;
	}

	public void setPrimaryPetId(Integer primaryPetId) {
		this.primaryPetId = primaryPetId;
	}

	public String getDataStreamId() {
		return dataStreamId;
	}

	public void setDataStreamId(String dataStreamId) {
		this.dataStreamId = dataStreamId;
	}

	public String getDuplicatePets() {
		return duplicatePets;
	}

	public void setDuplicatePets(String duplicatePets) {
		this.duplicatePets = duplicatePets;
	}

	public String getPetParentNames() {
		return petParentNames;
	}

	public void setPetParentNames(String petParentNames) {
		this.petParentNames = petParentNames;
	}

	public Boolean getIsPetVip() {
		return isPetVip;
	}

	public void setIsPetVip(Boolean isPetVip) {
		this.isPetVip = isPetVip;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBreedName() {
		return breedName;
	}

	public void setBreedName(String breedName) {
		this.breedName = breedName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	

}
