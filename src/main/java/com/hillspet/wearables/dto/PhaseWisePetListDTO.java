package com.hillspet.wearables.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhaseWisePetListDTO extends BaseDTO {

	private Integer petId;
	private String petName;
	private Integer breedId;
	private String breedName;
	private String gender;
	private String weight;
	private String weightUnit;
	private Boolean isActive;
	private Integer speciesId;
	private String speciesName;
	private Integer studyId;
	private String studyName;
	private Integer actionId;
	private String startDate;
	private String endDate;
	private String removedDate;
	private Integer treatmentGroupId;
	private String treatmentGroupName;
	private Integer PhaseId;
	private Integer PhaseName;
	private String petParentName;
	private String petPhoto;
	private String petPhotoUrl;
	private int petStudyId;
	private int fromPhaseDay;
	private LocalDate afEligibilityDate;

	public int getPetStudyId() {
		return petStudyId;
	}

	public void setPetStudyId(int petStudyId) {
		this.petStudyId = petStudyId;
	}

	public String getPetParentName() {
		return petParentName;
	}

	public void setPetParentName(String petParentName) {
		this.petParentName = petParentName;
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

	public Integer getBreedId() {
		return breedId;
	}

	public void setBreedId(Integer breedId) {
		this.breedId = breedId;
	}

	public String getBreedName() {
		return breedName;
	}

	public void setBreedName(String breedName) {
		this.breedName = breedName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getSpeciesId() {
		return speciesId;
	}

	public void setSpeciesId(Integer speciesId) {
		this.speciesId = speciesId;
	}

	public String getSpeciesName() {
		return speciesName;
	}

	public void setSpeciesName(String speciesName) {
		this.speciesName = speciesName;
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

	public Integer getActionId() {
		return actionId;
	}

	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getRemovedDate() {
		return removedDate;
	}

	public void setRemovedDate(String removedDate) {
		this.removedDate = removedDate;
	}

	public Integer getTreatmentGroupId() {
		return treatmentGroupId;
	}

	public void setTreatmentGroupId(Integer treatmentGroupId) {
		this.treatmentGroupId = treatmentGroupId;
	}

	public String getTreatmentGroupName() {
		return treatmentGroupName;
	}

	public void setTreatmentGroupName(String treatmentGroupName) {
		this.treatmentGroupName = treatmentGroupName;
	}

	public Integer getPhaseId() {
		return PhaseId;
	}

	public void setPhaseId(Integer phaseId) {
		PhaseId = phaseId;
	}

	public Integer getPhaseName() {
		return PhaseName;
	}

	public void setPhaseName(Integer phaseName) {
		PhaseName = phaseName;
	}

	public String getPetPhoto() {
		return petPhoto;
	}

	public void setPetPhoto(String petPhoto) {
		this.petPhoto = petPhoto;
	}

	public String getPetPhotoUrl() {
		return petPhotoUrl;
	}

	public void setPetPhotoUrl(String petPhotoUrl) {
		this.petPhotoUrl = petPhotoUrl;
	}

	public int getFromPhaseDay() {
		return fromPhaseDay;
	}

	public void setFromPhaseDay(int fromPhaseDay) {
		this.fromPhaseDay = fromPhaseDay;
	}

	public LocalDate getAfEligibilityDate() {
		return afEligibilityDate;
	}

	public void setAfEligibilityDate(LocalDate afEligibilityDate) {
		this.afEligibilityDate = afEligibilityDate;
	}

}
