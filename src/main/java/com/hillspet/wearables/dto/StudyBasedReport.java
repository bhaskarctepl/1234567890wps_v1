package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudyBasedReport extends BaseDTO{

	private String deviceType;
	private String deviceModel;
	private String deviceNumber;
	private Boolean currentStatus;
	private String petName;
	private String studyName;
	private String currentBatteryLevel;
	private String status;
	private Boolean assetIsActive;
	private String assetStatus;
	private String assetStatusId;
	
	public String getAssetStatusId() {
		return assetStatusId;
	}
	public void setAssetStatusId(String assetStatusId) {
		this.assetStatusId = assetStatusId;
	}
	public String getAssetStatus() {
		return assetStatus;
	}
	public void setAssetStatus(String assetStatus) {
		this.assetStatus = assetStatus;
	}
	public Boolean getAssetIsActive() {
		return assetIsActive;
	}
	public void setAssetIsActive(Boolean assetIsActive) {
		this.assetIsActive = assetIsActive;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceModel() {
		return deviceModel;
	}
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	public String getDeviceNumber() {
		return deviceNumber;
	}
	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}
	public Boolean getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(Boolean currentStatus) {
		this.currentStatus = currentStatus;
	}
	public String getPetName() {
		return petName;
	}
	public void setPetName(String petName) {
		this.petName = petName;
	}
	public String getStudyName() {
		return studyName;
	}
	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}
	public String getCurrentBatteryLevel() {
		return currentBatteryLevel;
	}
	public void setCurrentBatteryLevel(String currentBatteryLevel) {
		this.currentBatteryLevel = currentBatteryLevel;
	}
}
