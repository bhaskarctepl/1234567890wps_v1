package com.hillspet.wearables.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

/**
 * @author vvodyaram
 *
 */
@ApiModel(description = "Contains all information that needed to create Pet", value = "PetRequest")
@JsonInclude(value = Include.NON_NULL)
public class PetDTO extends BaseDTO {

	private Integer petId;
	private String petName;
	private String photoName;
	private Integer breedId;
	private String breedName;
	private String gender;
	private Double weight;
	private String weightUnit;
	private LocalDate dateOfBirth;
	private Boolean isDobUnknown;
	private Boolean isMixed;
	private Boolean isNeutered;
	private Boolean isDeceased;
	private Boolean isActive;
	private Boolean isExternalPet;
	private Integer speciesId;
	private String speciesName;
	private String selectStudyId;

	private Integer questionnaireAttempted;
	private Integer studyId;
	private String studyName;
	private LocalDate startDate;
	private LocalDate endDate;

	private List<PetParentDTO> petParents;
	private List<PetDevice> petDevices;
	private List<PetStudyDevice> petStudyDevices;
	private Integer petStatusId;
	private String petStatus;
	private Integer userId;
	private String petImage;
	private String petPhotoUrl;
	private List<PetNote> petNotes;
	private String feedingPreferences;

	private LocalDate dateOfDeath;
	private Boolean isApproximateDateOfDeath;

	private LocalDate lostToFollowUpDate;
	private Boolean isApproxLostToFollowUpDate;

	private Integer isPetWithPetParent;

	private Address petAddress;
	private String petAddressString;

	private Double legLength;
	private Integer legLengthUnitId;
	private String legLengthUnit;
	private Long legLengthHistoryCount;
	private String breedSize;

	private String weightString;
	private String ibwString;
	private String recommendedFoodQuantity;

	private List<ExternalPetIdInfo> extPetIds;

	public LocalDate getDateOfDeath() {
		return dateOfDeath;
	}

	public void setDateOfDeath(LocalDate dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}

	public Boolean getIsApproximateDateOfDeath() {
		return isApproximateDateOfDeath;
	}

	public void setIsApproximateDateOfDeath(Boolean isApproximateDateOfDeath) {
		this.isApproximateDateOfDeath = isApproximateDateOfDeath;
	}

	public String getSelectStudyId() {
		return selectStudyId;
	}

	public void setSelectStudyId(String selectStudyId) {
		this.selectStudyId = selectStudyId;
	}

	public String getSpeciesName() {
		return speciesName;
	}

	public void setSpeciesName(String speciesName) {
		this.speciesName = speciesName;
	}

	public Integer getSpeciesId() {
		return speciesId;
	}

	public void setSpeciesId(Integer speciesId) {
		this.speciesId = speciesId;
	}

	public Boolean getIsExternalPet() {
		return isExternalPet;
	}

	public void setIsExternalPet(Boolean isExternalPet) {
		this.isExternalPet = isExternalPet;
	}

	public List<PetNote> getPetNotes() {
		return petNotes;
	}

	public void setPetNotes(List<PetNote> petNotes) {
		this.petNotes = petNotes;
	}

	public String getPetPhotoUrl() {
		return petPhotoUrl;
	}

	public void setPetPhotoUrl(String petPhotoUrl) {
		this.petPhotoUrl = petPhotoUrl;
	}

	public String getPetImage() {
		return petImage;
	}

	public void setPetImage(String petImage) {
		this.petImage = petImage;
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

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
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

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Boolean getIsMixed() {
		return isMixed;
	}

	public void setIsMixed(Boolean isMixed) {
		this.isMixed = isMixed;
	}

	public Boolean getIsNeutered() {
		return isNeutered;
	}

	public void setIsNeutered(Boolean isNeutered) {
		this.isNeutered = isNeutered;
	}

	public Boolean getIsDeceased() {
		return isDeceased;
	}

	public void setIsDeceased(Boolean isDeceased) {
		this.isDeceased = isDeceased;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getQuestionnaireAttempted() {
		return questionnaireAttempted;
	}

	public void setQuestionnaireAttempted(Integer questionnaireAttempted) {
		this.questionnaireAttempted = questionnaireAttempted;
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

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public List<PetDevice> getPetDevices() {
		return petDevices;
	}

	public void setPetDevices(List<PetDevice> petDevices) {
		this.petDevices = petDevices;
	}

	public List<PetStudyDevice> getPetStudyDevices() {
		return petStudyDevices;
	}

	public void setPetStudyDevices(List<PetStudyDevice> petStudyDevices) {
		this.petStudyDevices = petStudyDevices;
	}

	public List<PetParentDTO> getPetParents() {
		return petParents;
	}

	public void setPetParents(List<PetParentDTO> petParents) {
		this.petParents = petParents;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFeedingPreferences() {
		return feedingPreferences;
	}

	public void setFeedingPreferences(String feedingPreferences) {
		this.feedingPreferences = feedingPreferences;
	}

	public Boolean getIsDobUnknown() {
		return isDobUnknown;
	}

	public void setIsDobUnknown(Boolean isDobUnknown) {
		this.isDobUnknown = isDobUnknown;
	}

	public LocalDate getLostToFollowUpDate() {
		return lostToFollowUpDate;
	}

	public void setLostToFollowUpDate(LocalDate lostToFollowUpDate) {
		this.lostToFollowUpDate = lostToFollowUpDate;
	}

	public Boolean getIsApproxLostToFollowUpDate() {
		return isApproxLostToFollowUpDate;
	}

	public void setIsApproxLostToFollowUpDate(Boolean isApproxLostToFollowUpDate) {
		this.isApproxLostToFollowUpDate = isApproxLostToFollowUpDate;
	}

	public Integer getIsPetWithPetParent() {
		return isPetWithPetParent;
	}

	public void setIsPetWithPetParent(Integer isPetWithPetParent) {
		this.isPetWithPetParent = isPetWithPetParent;
	}

	public Address getPetAddress() {
		return petAddress;
	}

	public void setPetAddress(Address petAddress) {
		this.petAddress = petAddress;
	}

	public String getPetAddressString() {
		return petAddressString;
	}

	public void setPetAddressString(String petAddressString) {
		this.petAddressString = petAddressString;
	}

	public Double getLegLength() {
		return legLength;
	}

	public void setLegLength(Double legLength) {
		this.legLength = legLength;
	}

	public Integer getLegLengthUnitId() {
		return legLengthUnitId;
	}

	public void setLegLengthUnitId(Integer legLengthUnitId) {
		this.legLengthUnitId = legLengthUnitId;
	}

	public String getLegLengthUnit() {
		return legLengthUnit;
	}

	public void setLegLengthUnit(String legLengthUnit) {
		this.legLengthUnit = legLengthUnit;
	}

	public Long getLegLengthHistoryCount() {
		return legLengthHistoryCount;
	}

	public void setLegLengthHistoryCount(Long legLengthHistoryCount) {
		this.legLengthHistoryCount = legLengthHistoryCount;
	}

	public String getBreedSize() {
		return breedSize;
	}

	public void setBreedSize(String breedSize) {
		this.breedSize = breedSize;
	}

	public List<ExternalPetIdInfo> getExtPetIds() {
		return extPetIds;
	}

	public void setExtPetIds(List<ExternalPetIdInfo> extPetIds) {
		this.extPetIds = extPetIds;
	}

	public String getWeightString() {
		return weightString;
	}

	public void setWeightString(String weightString) {
		this.weightString = weightString;
	}

	public String getIbwString() {
		return ibwString;
	}

	public void setIbwString(String ibwString) {
		this.ibwString = ibwString;
	}

	public String getRecommendedFoodQuantity() {
		return recommendedFoodQuantity;
	}

	public void setRecommendedFoodQuantity(String recommendedFoodQuantity) {
		this.recommendedFoodQuantity = recommendedFoodQuantity;
	}

}
