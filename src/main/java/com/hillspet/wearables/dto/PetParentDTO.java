package com.hillspet.wearables.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetParentDTO {

	private Integer petParentId;
	private String petParentName;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private Integer status;
	private String secondaryEmail;
	private Boolean isNotifySecondaryEmail;
	private List<PetsAssociatedDTO> petsAssociated;
	private Integer isShipAddrSameAsResdntlAddr;
	private Address residentialAddress;
	private Address shippingAddress;
	private String residentialAddressString;
	private String shippingAddressString;
	private Integer isPetWithPetParent;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<PetsAssociatedDTO> getPetsAssociated() {
		return petsAssociated;
	}

	public void setPetsAssociated(List<PetsAssociatedDTO> petsAssociated) {
		this.petsAssociated = petsAssociated;
	}

	public Integer getIsShipAddrSameAsResdntlAddr() {
		return isShipAddrSameAsResdntlAddr;
	}

	public void setIsShipAddrSameAsResdntlAddr(Integer isShipAddrSameAsResdntlAddr) {
		this.isShipAddrSameAsResdntlAddr = isShipAddrSameAsResdntlAddr;
	}

	public Address getResidentialAddress() {
		return residentialAddress;
	}

	public void setResidentialAddress(Address residentialAddress) {
		this.residentialAddress = residentialAddress;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
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

	public Integer getIsPetWithPetParent() {
		return isPetWithPetParent;
	}

	public void setIsPetWithPetParent(Integer isPetWithPetParent) {
		this.isPetWithPetParent = isPetWithPetParent;
	}

	public Integer getPreferredFoodUnitId() {
		return preferredFoodUnitId;
	}

	public void setPreferredFoodUnitId(Integer preferredFoodUnitId) {
		this.preferredFoodUnitId = preferredFoodUnitId;
	}

	public Integer getPreferredWeightUnitId() {
		return preferredWeightUnitId;
	}

	public void setPreferredWeightUnitId(Integer preferredWeightUnitId) {
		this.preferredWeightUnitId = preferredWeightUnitId;
	}

	public String getPreferredFoodUnit() {
		return preferredFoodUnit;
	}

	public void setPreferredFoodUnit(String preferredFoodUnit) {
		this.preferredFoodUnit = preferredFoodUnit;
	}

	public String getPreferredWeightUnit() {
		return preferredWeightUnit;
	}

	public void setPreferredWeightUnit(String preferredWeightUnit) {
		this.preferredWeightUnit = preferredWeightUnit;
	}

}
