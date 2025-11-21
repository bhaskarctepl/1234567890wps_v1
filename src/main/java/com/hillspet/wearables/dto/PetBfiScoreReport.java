package com.hillspet.wearables.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetBfiScoreReport extends BaseDTO {

	private Integer petId;
	private Integer petBfiImageSetId;
	private Integer petParentId;
	private String petParentName;
	private String petName;
	private String breedName;
	private String photoName;
	private String gender;
	private String petAge;
	private Boolean isVipPetParent;

	private LocalDateTime capturedOn;
	private String score;
	private List<ScoreInfo> scoreInfo;

	private List<PetBfiImage> petBfiImages;

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}

	public Integer getPetBfiImageSetId() {
		return petBfiImageSetId;
	}

	public void setPetBfiImageSetId(Integer petBfiImageSetId) {
		this.petBfiImageSetId = petBfiImageSetId;
	}

	public Integer getPetParentId() {
		return petParentId;
	}

	public void setPetParentId(Integer petParentId) {
		this.petParentId = petParentId;
	}

	public String getPetParentName() {
		return petParentName;
	}

	public void setPetParentName(String petParentName) {
		this.petParentName = petParentName;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getBreedName() {
		return breedName;
	}

	public void setBreedName(String breedName) {
		this.breedName = breedName;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPetAge() {
		return petAge;
	}

	public void setPetAge(String petAge) {
		this.petAge = petAge;
	}

	public LocalDateTime getCapturedOn() {
		return capturedOn;
	}

	public void setCapturedOn(LocalDateTime capturedOn) {
		this.capturedOn = capturedOn;
	}

	public List<ScoreInfo> getScoreInfo() {
		return scoreInfo;
	}

	public void setScoreInfo(List<ScoreInfo> scoreInfo) {
		this.scoreInfo = scoreInfo;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public List<PetBfiImage> getPetBfiImages() {
		return petBfiImages;
	}

	public void setPetBfiImages(List<PetBfiImage> petBfiImages) {
		this.petBfiImages = petBfiImages;
	}

	public Boolean getIsVipPetParent() {
		return isVipPetParent;
	}

	public void setIsVipPetParent(Boolean isVipPetParent) {
		this.isVipPetParent = isVipPetParent;
	}
}
