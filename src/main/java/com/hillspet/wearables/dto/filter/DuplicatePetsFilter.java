package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

public class DuplicatePetsFilter extends BaseFilter {

	@QueryParam("petName")
	@ApiParam(name = "petName", type = "String", value = "Search based on pet drop down", required = true)
	private String petName;

	@QueryParam("dateOfBirth")
	@ApiParam(name = "dateOfBirth", type = "LocalDate", value = "Search based on pet date of birth", required = false)
	private String dateOfBirth;

	@QueryParam("gender")
	@ApiModelProperty(name = "Gender", value = "Search based on pet gender", required = false)
	private String gender;

	@QueryParam("breedId")
	@ApiModelProperty(name = "breedId", value = "Search based on pet breed", required = false)
	private String breedId;

	@QueryParam("primaryPetId")
	@ApiModelProperty(name = "primaryPetId", value = "Exclude primary pet from duplicate pet", required = false)
	private Integer primaryPetId;

	@QueryParam("petParentName")
	@ApiParam(name = "petParentName", value = "Search pet by Pet Parent Name", required = false)
	private String petParentName;

	@QueryParam("studyName")
	@ApiParam(name = "studyName", value = "Search pet by study name", required = false)
	private String studyName;

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBreedId() {
		return breedId;
	}

	public void setBreedId(String breedId) {
		this.breedId = breedId;
	}

	public Integer getPrimaryPetId() {
		return primaryPetId;
	}

	public void setPrimaryPetId(Integer primaryPetId) {
		this.primaryPetId = primaryPetId;
	}

	public String getPetParentName() {
		return petParentName;
	}

	public void setPetParentName(String petParentName) {
		this.petParentName = petParentName;
	}

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

}
