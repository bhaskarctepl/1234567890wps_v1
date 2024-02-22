package com.hillspet.wearables.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetsAssociatedDTO {
	private Integer petId;
	private String petName;
	private String petPhoto;
	private String breedName;
	private String weight;
	private String gender;
	private LocalDate dob;
	private Boolean isDobUnknown;
	private Boolean isNeutered;
	private Integer petStatusId;
	private String petStatus;
	private Boolean isDeceased;
	private String deviceNumber;
	private String deviceType;
	private String deviceModel;
	private String petPhotoUrl;

	private int petStudyId;

	public int getPetStudyId() {
		return petStudyId;
	}

	public void setPetStudyId(int petStudyId) {
		this.petStudyId = petStudyId;
	}

	public Boolean getIsDeceased() {
		return isDeceased;
	}

	public String getPetPhotoUrl() {
		return petPhotoUrl;
	}

	public void setPetPhotoUrl(String petPhotoUrl) {
		this.petPhotoUrl = petPhotoUrl;
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

	public String getPetPhoto() {
		return petPhoto;
	}

	public void setPetPhoto(String petPhoto) {
		this.petPhoto = petPhoto;
	}

	public String getBreedName() {
		return breedName;
	}

	public void setBreedName(String breedName) {
		this.breedName = breedName;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public Boolean getIsNeutered() {
		return isNeutered;
	}

	public void setIsNeutered(Boolean isNeutered) {
		this.isNeutered = isNeutered;
	}

	public void setIsDeceased(Boolean isDeceased) {
		this.isDeceased = isDeceased;
	}

	public Boolean getIsDobUnknown() {
		return isDobUnknown;
	}

	public void setIsDobUnknown(Boolean isDobUnknown) {
		this.isDobUnknown = isDobUnknown;
	}

	public Integer getPetStatusId() {
		return petStatusId;
	}

	public void setPetStatusId(Integer petStatusId) {
		this.petStatusId = petStatusId;
	}

	public String getPetStatus() {
		return petStatus;
	}

	public void setPetStatus(String petStatus) {
		this.petStatus = petStatus;
	}

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
}
