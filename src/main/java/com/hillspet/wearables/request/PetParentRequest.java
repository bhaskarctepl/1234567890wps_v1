package com.hillspet.wearables.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author sgorle
 *
 */
@ApiModel(description = "Contains all information that needed to create a pet parent", value = "PetParentRequest")
@JsonInclude(value = Include.NON_NULL)
public class PetParentRequest {

	@ApiModelProperty(value = "petParentId", required = false, example = "1")
	private Integer petParentId;

	@ApiModelProperty(value = "petParentName", required = true, example = "John")
	private String petParentName;

	@ApiModelProperty(value = "email", required = true, example = "John@gmail.com")
	private String email;

	@ApiModelProperty(value = "isdCodeId", required = true, example = "1")
	private Integer isdCodeId;

	@ApiModelProperty(value = "phoneNumber", required = true, example = "(846) 546-5468")
	private String phoneNumber;

	@ApiModelProperty(value = "petsAssociated", required = true)
	private List<Integer> petsAssociated;

	@ApiModelProperty(value = "status", required = true, example = "1")
	private Integer status;

	@ApiModelProperty(value = "firstName", required = true)
	private String firstName;

	@ApiModelProperty(value = "lastName", required = true)
	private String lastName;

	@ApiModelProperty(value = "secondaryEmailId", required = false)
	private String secondaryEmail;

	@ApiModelProperty(value = "isNotifySecondaryEmail", required = true)
	private Boolean isNotifySecondaryEmail;

	@ApiModelProperty(value = "userId", required = true)
	private Integer userId;

	@ApiModelProperty(value = "isShipAddrSameAsResdntlAddr", required = true)
	private Integer isShipAddrSameAsResdntlAddr;

	@ApiModelProperty(value = "residentialAddress", required = false, example = "Mr John Smith. 132, My Street, Kingston, New York 12401")
	private AddressRequest residentialAddress;

	@ApiModelProperty(value = "shippingAddress", required = false, example = "Mr John Smith. 132, My Street, Kingston, New York 12401")
	private AddressRequest shippingAddress;

	@ApiModelProperty(value = "isPetWithPetParent", required = true, example = "true")
	private Integer isPetWithPetParent;

	@ApiModelProperty(value = "preferredFoodUnitId", required = true, example = "1")
	private Integer preferredFoodUnitId;

	@ApiModelProperty(value = "preferredWeightUnitId", required = true, example = "1")
	private Integer preferredWeightUnitId;

	@ApiModelProperty(value = "isVipPetParent", required = true, example = "TRUE")
	private Boolean isVipPetParent;

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

	public Integer getIsdCodeId() {
		return isdCodeId;
	}

	public void setIsdCodeId(Integer isdCodeId) {
		this.isdCodeId = isdCodeId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<Integer> getPetsAssociated() {
		return petsAssociated;
	}

	public void setPetsAssociated(List<Integer> petsAssociated) {
		this.petsAssociated = petsAssociated;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getIsShipAddrSameAsResdntlAddr() {
		return isShipAddrSameAsResdntlAddr;
	}

	public void setIsShipAddrSameAsResdntlAddr(Integer isShipAddrSameAsResdntlAddr) {
		this.isShipAddrSameAsResdntlAddr = isShipAddrSameAsResdntlAddr;
	}

	public AddressRequest getResidentialAddress() {
		return residentialAddress;
	}

	public void setResidentialAddress(AddressRequest residentialAddress) {
		this.residentialAddress = residentialAddress;
	}

	public AddressRequest getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(AddressRequest shippingAddress) {
		this.shippingAddress = shippingAddress;
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

	public Boolean getIsVipPetParent() {
		return isVipPetParent;
	}

	public void setIsVipPetParent(Boolean isVipPetParent) {
		this.isVipPetParent = isVipPetParent;
	}

}
