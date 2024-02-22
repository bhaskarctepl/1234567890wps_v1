package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetParentListDTO extends BaseDTO {

	private Integer petParentId;
	private String petParentName;
	private String email;
	private String phoneNumber;
	private String petNames;
	private String studyNames;
	private Integer numberOfPets;
	private boolean isOnStudyPetExist;
	private String petParentFirstName;
	private String petParentLastName;

	private String secondaryEmail;
	private Boolean isNotifySecondaryEmail;

	private Integer isShipAddrSameAsResdntlAddr;

	private String residentialAddressString;
	private String shippingAddressString;

	private Integer preferredFoodUnitId;
	private String preferredFoodUnit;
	private Integer preferredWeightUnitId;
	private String preferredWeightUnit;

	public String getSecondaryEmail() {
		return secondaryEmail;
	}

	public void setSecondaryEmail(String secondaryEmail) {
		this.secondaryEmail = secondaryEmail;
	}

	public Boolean getIsNotifySecondaryEmail() {
		return isNotifySecondaryEmail;
	}

	public void setIsNotifySecondaryEmail(Boolean isNotifySecondaryEmail) {
		this.isNotifySecondaryEmail = isNotifySecondaryEmail;
	}

	public boolean isOnStudyPetExist() {
		return isOnStudyPetExist;
	}

	public void setOnStudyPetExist(boolean isOnStudyPetExist) {
		this.isOnStudyPetExist = isOnStudyPetExist;
	}

	public String getPetParentFirstName() {
		return petParentFirstName;
	}

	public void setPetParentFirstName(String petParentFirstName) {
		this.petParentFirstName = petParentFirstName;
	}

	public String getPetParentLastName() {
		return petParentLastName;
	}

	public void setPetParentLastName(String petParentLastName) {
		this.petParentLastName = petParentLastName;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPetNames() {
		return petNames;
	}

	public void setPetNames(String petNames) {
		this.petNames = petNames;
	}

	public String getStudyNames() {
		return studyNames;
	}

	public void setStudyNames(String studyNames) {
		this.studyNames = studyNames;
	}

	public Integer getNumberOfPets() {
		return numberOfPets;
	}

	public void setNumberOfPets(Integer numberOfPets) {
		this.numberOfPets = numberOfPets;
	}

	public Integer getIsShipAddrSameAsResdntlAddr() {
		return isShipAddrSameAsResdntlAddr;
	}

	public void setIsShipAddrSameAsResdntlAddr(Integer isShipAddrSameAsResdntlAddr) {
		this.isShipAddrSameAsResdntlAddr = isShipAddrSameAsResdntlAddr;
	}

	public String getResidentialAddressString() {
		return residentialAddressString;
	}

	public void setResidentialAddressString(String residentialAddressString) {
		this.residentialAddressString = residentialAddressString;
	}

	public String getShippingAddressString() {
		return shippingAddressString;
	}

	public void setShippingAddressString(String shippingAddressString) {
		this.shippingAddressString = shippingAddressString;
	}

	public Integer getPreferredFoodUnitId() {
		return preferredFoodUnitId;
	}

	public void setPreferredFoodUnitId(Integer preferredFoodUnitId) {
		this.preferredFoodUnitId = preferredFoodUnitId;
	}

	public String getPreferredFoodUnit() {
		return preferredFoodUnit;
	}

	public void setPreferredFoodUnit(String preferredFoodUnit) {
		this.preferredFoodUnit = preferredFoodUnit;
	}

	public Integer getPreferredWeightUnitId() {
		return preferredWeightUnitId;
	}

	public void setPreferredWeightUnitId(Integer preferredWeightUnitId) {
		this.preferredWeightUnitId = preferredWeightUnitId;
	}

	public String getPreferredWeightUnit() {
		return preferredWeightUnit;
	}

	public void setPreferredWeightUnit(String preferredWeightUnit) {
		this.preferredWeightUnit = preferredWeightUnit;
	}

}
