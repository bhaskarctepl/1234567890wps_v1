package com.hillspet.wearables.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetListDTO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer petId;
	private String petName;
	private String petPhoto;
	private String speciesName;
	private String breedName;
	private Integer studyId;
	private String studyName;
	private String sensorDetails;
	private String gender;
	private String weight;
	private Integer petStatusId;
	private String petStatus;
	private String petPhotoUrl;
	private Integer selectStudyId;

	private Integer totalActivePets;
	private Integer totalActiveStudies;

	private Integer duplicatePetsCount;
	private LocalDate dateOfBirth;

	private String petParentName;

	private Integer petStudyId;

	private String petParentEmail;

	private Integer deviceId;

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

	public String getSpeciesName() {
		return speciesName;
	}

	public void setSpeciesName(String speciesName) {
		this.speciesName = speciesName;
	}

	public String getBreedName() {
		return breedName;
	}

	public void setBreedName(String breedName) {
		this.breedName = breedName;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

	public String getSensorDetails() {
		return sensorDetails;
	}

	public void setSensorDetails(String sensorDetails) {
		this.sensorDetails = sensorDetails;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
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

	public String getPetPhotoUrl() {
		return petPhotoUrl;
	}

	public void setPetPhotoUrl(String petPhotoUrl) {
		this.petPhotoUrl = petPhotoUrl;
	}

	public Integer getSelectStudyId() {
		return selectStudyId;
	}

	public void setSelectStudyId(Integer selectStudyId) {
		this.selectStudyId = selectStudyId;
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

	public Integer getDuplicatePetsCount() {
		return duplicatePetsCount;
	}

	public void setDuplicatePetsCount(Integer duplicatePetsCount) {
		this.duplicatePetsCount = duplicatePetsCount;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPetParentName() {
		return petParentName;
	}

	public void setPetParentName(String petParentName) {
		this.petParentName = petParentName;
	}

	public Integer getPetStudyId() {
		return petStudyId;
	}

	public void setPetStudyId(Integer petStudyId) {
		this.petStudyId = petStudyId;
	}

	public String getPetParentEmail() {
		return petParentEmail;
	}

	public void setPetParentEmail(String petParentEmail) {
		this.petParentEmail = petParentEmail;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

}
