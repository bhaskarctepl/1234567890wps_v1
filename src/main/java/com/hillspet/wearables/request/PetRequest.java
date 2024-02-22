package com.hillspet.wearables.request;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hillspet.wearables.dto.ExternalPetIdInfo;
import com.hillspet.wearables.dto.PetDevice;
import com.hillspet.wearables.dto.PetDeviceUnAssignRequest;
import com.hillspet.wearables.dto.PetStudyDevice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author vvodyaram
 *
 */
@ApiModel(description = "Contains all the information that needed to create Pet", value = "PetRequest")
@JsonInclude(value = Include.NON_NULL)
public class PetRequest {

	public PetRequest() {
	}

	private Integer petId;

	@ApiModelProperty(value = "petName", required = true, example = "Beaumont")
	private String petName;

	@ApiModelProperty(value = "breedId", required = true)
	private Integer breedId;

	@ApiModelProperty(value = "Gender", required = true, example = "Male")
	private String gender;

	private Double weight;

	@ApiModelProperty(value = "weightUnit", required = true, example = "LBS/KGS")
	private String weightUnit;

	private LocalDate dateOfBirth;
	private Boolean isDobUnknown;

	private Boolean isNeutered;

	private Boolean isActive;

	private List<PetDevice> petDevices;

	private List<PetStudyDevice> petStudyDevices;

	private List<PetParentRequest> petParents;

	private Integer petStatusId;

	private Integer userId;

	private String petImage;

	private String removedPetParents;

	private String removedStudyIds;

	private List<PetDeviceUnAssignRequest> petUnAssignAssets;

	private Boolean confirmOffStudy;

	private LocalDate dateOfDeath;

	private Boolean isApproximateDateOfDeath;

	private LocalDate lostToFollowUpDate;

	private Boolean isApproxLostToFollowUpDate;

	private Integer isPetWithPetParent;

	private AddressRequest petAddress;

	private Double legLength;

	private Integer legLengthUnitId;
	
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

	public Boolean getConfirmOffStudy() {
		return confirmOffStudy;
	}

	public void setConfirmOffStudy(Boolean confirmOffStudy) {
		this.confirmOffStudy = confirmOffStudy;
	}

	public List<PetDeviceUnAssignRequest> getPetUnAssignAssets() {
		return petUnAssignAssets;
	}

	public void setPetUnAssignAssets(List<PetDeviceUnAssignRequest> petUnAssignAssets) {
		this.petUnAssignAssets = petUnAssignAssets;
	}

	public String getRemovedStudyIds() {
		return removedStudyIds;
	}

	public void setRemovedStudyIds(String removedStudyIds) {
		this.removedStudyIds = removedStudyIds;
	}

	public String getRemovedPetParents() {
		return removedPetParents;
	}

	public void setRemovedPetParents(String removedPetParents) {
		this.removedPetParents = removedPetParents;
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

	public Integer getBreedId() {
		return breedId;
	}

	public void setBreedId(Integer breedId) {
		this.breedId = breedId;
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

	public Boolean getIsDobUnknown() {
		return isDobUnknown;
	}

	public void setIsDobUnknown(Boolean isDobUnknown) {
		this.isDobUnknown = isDobUnknown;
	}

	public Boolean getIsNeutered() {
		return isNeutered;
	}

	public void setIsNeutered(Boolean isNeutered) {
		this.isNeutered = isNeutered;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	public List<PetParentRequest> getPetParents() {
		return petParents;
	}

	public void setPetParents(List<PetParentRequest> petParents) {
		this.petParents = petParents;
	}

	public Integer getPetStatusId() {
		return petStatusId;
	}

	public void setPetStatusId(Integer petStatusId) {
		this.petStatusId = petStatusId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public AddressRequest getPetAddress() {
		return petAddress;
	}

	public void setPetAddress(AddressRequest petAddress) {
		this.petAddress = petAddress;
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
	
	public List<ExternalPetIdInfo> getExtPetIds() {
		return extPetIds;
	}

	public void setExtPetIds(List<ExternalPetIdInfo> extPetIds) {
		this.extPetIds = extPetIds;
	}

}
