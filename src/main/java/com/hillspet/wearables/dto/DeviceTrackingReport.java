package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceTrackingReport extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	private String petParentName;
	private String currentBatteryLevel;

	private String isWhiteListed;
	private String boxNumber;
	private String macAddress;
	private String wifiSSId;

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

	public String getPetParentName() {
		return petParentName;
	}

	public void setPetParentName(String petParentName) {
		this.petParentName = petParentName;
	}

	public String getIsWhiteListed() {
		return isWhiteListed;
	}

	public void setIsWhiteListed(String isWhiteListed) {
		this.isWhiteListed = isWhiteListed;
	}

	public String getWifiSSId() {
		return wifiSSId;
	}

	public void setWifiSSId(String wifiSSId) {
		this.wifiSSId = wifiSSId;
	}

	public String getBoxNumber() {
		return boxNumber;
	}

	public void setBoxNumber(String boxNumber) {
		this.boxNumber = boxNumber;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
