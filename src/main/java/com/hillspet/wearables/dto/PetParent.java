package com.hillspet.wearables.dto;

public class PetParent {
	private int petParentId;
	private String petParentName;
	private String petParentAddress;
	private String email;
	private String fcmToken;
	private Boolean isVipPetParent;

	public int getPetParentId() {
		return petParentId;
	}

	public void setPetParentId(int petParentId) {
		this.petParentId = petParentId;
	}

	public String getPetParentName() {
		return petParentName;
	}

	public void setPetParentName(String petParentName) {
		this.petParentName = petParentName;
	}

	public String getPetParentAddress() {
		return petParentAddress;
	}

	public void setPetParentAddress(String petParentAddress) {
		this.petParentAddress = petParentAddress;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFcmToken() {
		return fcmToken;
	}

	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}

	public Boolean getIsVipPetParent() {
		return isVipPetParent;
	}

	public void setIsVipPetParent(Boolean isVipPetParent) {
		this.isVipPetParent = isVipPetParent;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("PetParentName{");
		sb.append("petParentId=").append(petParentId);
		sb.append(", petParentName='").append(petParentName).append('\'');
		sb.append(", petParentAddress='").append(petParentAddress).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
