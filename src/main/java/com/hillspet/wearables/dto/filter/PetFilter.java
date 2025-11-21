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

	@QueryParam("petName")
	@ApiParam(name = "petName", value = "Search pet by pet name", required = false)
	private String petName;

	@QueryParam("petParentName")
	@ApiParam(name = "petParentName", value = "Search pet by Pet Parent Name", required = false)
	private String petParentName;

	@QueryParam("studyName")
	@ApiParam(name = "studyName", value = "Search pet by Study Name", required = false)
	private String studyName;

	@QueryParam("pushNotificationStatus")
	@ApiParam(name = "pushNotificationStatus", type = "integer", value = "Search based on Notification drop down Enabled = 1 and Disabled = 0")
	private String pushNotificationStatus;

	@QueryParam("emailNotificationStatus")
	@ApiParam(name = "emailNotificationStatus", type = "integer", value = "Search based on Notification drop down Enabled = 1 and Disabled = 0")
	private String emailNotificationStatus;

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

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
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

	public String getPushNotificationStatus() {
		return pushNotificationStatus;
	}

	public void setPushNotificationStatus(String pushNotificationStatus) {
		this.pushNotificationStatus = pushNotificationStatus;
	}

	public String getEmailNotificationStatus() {
		return emailNotificationStatus;
	}

	public void setEmailNotificationStatus(String emailNotificationStatus) {
		this.emailNotificationStatus = emailNotificationStatus;
	}

}
