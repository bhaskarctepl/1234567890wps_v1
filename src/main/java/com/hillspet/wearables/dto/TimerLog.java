package com.hillspet.wearables.dto;

public class TimerLog  {

	private String recordName;
	
	private String category;
	
	private String duration;
	
	private String timerDate;
	
	private String petName;
	
	private int petId;
	
	private int petParentId;
	
	private String petParentName;
	
	private String assetNumber;
	
	private int petTimerLogId;
	
	private Boolean isActive;
	
	

	

	

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public int getPetTimerLogId() {
		return petTimerLogId;
	}

	public void setPetTimerLogId(int petTimerLogId) {
		this.petTimerLogId = petTimerLogId;
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getTimerDate() {
		return timerDate;
	}

	public void setTimerDate(String timerDate) {
		this.timerDate = timerDate;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

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

	public String getAssetNumber() {
		return assetNumber;
	}

	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}
	
	


	
}
