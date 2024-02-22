package com.hillspet.wearables.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceInfo extends BaseDTO {

	private Integer deviceId;
	private String deviceNumber;
	private String deviceType;
	private String deviceModel;
	private String mfgSerialNumber;
	private String mfgFirmware;
	private String mfgMacAddr;
	private String wifiMacAddr;
	private LocalDateTime addDate;
	private Boolean isGyroScopeDataEnabled;
	private String firmwareVersionNumber;
	private Integer deviceLocationId;
	private String location;
	private String trackingNumber;
	private Integer statusId;
	private String status;
	private String deviceLocationOthers;
	private String otherAssetType;
	private String otherAssetModel;

	private Integer studyId;
	private String studyName;

	public String getOtherAssetType() {
		return otherAssetType;
	}

	public void setOtherAssetType(String otherAssetType) {
		this.otherAssetType = otherAssetType;
	}

	public String getOtherAssetModel() {
		return otherAssetModel;
	}

	public void setOtherAssetModel(String otherAssetModel) {
		this.otherAssetModel = otherAssetModel;
	}

	public String getDeviceLocationOthers() {
		return deviceLocationOthers;
	}

	public void setDeviceLocationOthers(String deviceLocationOthers) {
		this.deviceLocationOthers = deviceLocationOthers;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
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

	public String getMfgSerialNumber() {
		return mfgSerialNumber;
	}

	public void setMfgSerialNumber(String mfgSerialNumber) {
		this.mfgSerialNumber = mfgSerialNumber;
	}

	public String getMfgFirmware() {
		return mfgFirmware;
	}

	public void setMfgFirmware(String mfgFirmware) {
		this.mfgFirmware = mfgFirmware;
	}

	public String getMfgMacAddr() {
		return mfgMacAddr;
	}

	public void setMfgMacAddr(String mfgMacAddr) {
		this.mfgMacAddr = mfgMacAddr;
	}

	public String getWifiMacAddr() {
		return wifiMacAddr;
	}

	public void setWifiMacAddr(String wifiMacAddr) {
		this.wifiMacAddr = wifiMacAddr;
	}

	public LocalDateTime getAddDate() {
		return addDate;
	}

	public void setAddDate(LocalDateTime addDate) {
		this.addDate = addDate;
	}

	public Boolean getIsGyroScopeDataEnabled() {
		return isGyroScopeDataEnabled;
	}

	public void setIsGyroScopeDataEnabled(Boolean isGyroScopeDataEnabled) {
		this.isGyroScopeDataEnabled = isGyroScopeDataEnabled;
	}

	public String getFirmwareVersionNumber() {
		return firmwareVersionNumber;
	}

	public void setFirmwareVersionNumber(String firmwareVersionNumber) {
		this.firmwareVersionNumber = firmwareVersionNumber;
	}

	public Integer getDeviceLocationId() {
		return deviceLocationId;
	}

	public void setDeviceLocationId(Integer deviceLocationId) {
		this.deviceLocationId = deviceLocationId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
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

}
