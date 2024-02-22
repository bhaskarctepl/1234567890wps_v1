package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class PetFilter extends BaseFilter {

	@QueryParam("studyId")
	@ApiParam(name = "studyId", type = "integer", value = "Search based on Study drop down")
	private String studyId;

	@QueryParam("petId")
	@ApiParam(name = "petId", type = "integer", value = "Search Pet Id", required = false)
	private int petId;

	@QueryParam("gender")
	@ApiParam(name = "gender", value = "Search pet by gender", required = false)
	private String gender;

	@QueryParam("speciesId")
	@ApiParam(name = "speciesId", type = "integer", value = "Search pet by speciesId", required = false)
	private int speciesId;

	@QueryParam("breedId")
	@ApiParam(name = "breedId", value = "Search pet by breedIds", required = false)
	private String breedId;

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getSpeciesId() {
		return speciesId;
	}

	public void setSpeciesId(int speciesId) {
		this.speciesId = speciesId;
	}

	public String getBreedId() {
		return breedId;
	}

	public void setBreedId(String breedId) {
		this.breedId = breedId;
	}

}
