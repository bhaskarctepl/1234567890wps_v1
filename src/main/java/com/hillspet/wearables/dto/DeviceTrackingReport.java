package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceTrackingReport extends BaseDTO{
	
	private String deviceType;
	private String deviceModel;
	private String deviceNumber;
	private Boolean currentStatus;
	private String currentPetName;
	private String currentStudyName;
	private String currentLocation;
	private String status;
	private String assetStatus;
	private String assetStatusId;
	
	public String getAssetStatus() {
		return assetStatus;
	}
	public void setAssetStatus(String assetStatus) {
		this.assetStatus = assetStatus;
	}
	public String getAssetStatusId() {
		return assetStatusId;
	}
	public void setAssetStatusId(String assetStatusId) {
		this.assetStatusId = assetStatusId;
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
	public String getCurrentPetName() {
		return currentPetName;
	}
	public void setCurrentPetName(String currentPetName) {
		this.currentPetName = currentPetName;
	}
	public String getCurrentStudyName() {
		return currentStudyName;
	}
	public void setCurrentStudyName(String currentStudyName) {
		this.currentStudyName = currentStudyName;
	}
	public String getCurrentLocation() {
		return currentLocation;
	}
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}
	public String getCurrentBatteryLevel() {
		return currentBatteryLevel;
	}
	public void setCurrentBatteryLevel(String currentBatteryLevel) {
		this.currentBatteryLevel = currentBatteryLevel;
	}
	private String currentBatteryLevel;


}
