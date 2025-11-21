/**
 * 
 */
package com.hillspet.wearables.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author radepu
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BulkAssetUploadDeviceInfo {

	private Integer stagingId;
	private String deviceNumber;
	private String deviceType;
	private String deviceModel;
	private String mfgSerialNumber;
	private String mfgFirmware;
	private String mfgMacAddr;
	private String wifiMacAddr;

	private Integer deviceLocationId;
	private String deviceLocationName;
	private String location;
	private String trackingNumber;
	private String studyName;
	private Integer studyId;

	private int createdUser;
	private LocalDateTime createdDate;

	private String exceptionMsg;
	private String attachmentName;

	private String wifiSSID;

	private String boxNumber;

	public String getBoxNumber() {
		return boxNumber;
	}

	public void setBoxNumber(String boxNumber) {
		this.boxNumber = boxNumber;
	}

	public String getWifiSSID() {
		return wifiSSID;
	}

	public void setWifiSSID(String wifiSSID) {
		this.wifiSSID = wifiSSID;
	}

	public Integer getStagingId() {
		return stagingId;
	}

	public void setStagingId(Integer stagingId) {
		this.stagingId = stagingId;
	}

	public String getDeviceLocationName() {
		return deviceLocationName;
	}

	public void setDeviceLocationName(String deviceLocationName) {
		this.deviceLocationName = deviceLocationName;
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

	public int getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(int createdUser) {
		this.createdUser = createdUser;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

}
