package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataQuality extends BaseDTO {

	private String alertName;
	private String deviceName;
	private String deviceNumber;
	private String studyName;
	private String alertDate;
	private String firmware;
	private int alertTypeId;
	private int studyId;
	private String fromDate;
	private String toDate;
	private String startDate;
	private String endDate;
	private String deviceId;
	private String aletActionId;
	private String aletActionName;
	private String alertDetails;
	private String deviceModel;
	private String boxNumber;

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getAletActionId() {
		return aletActionId;
	}

	public String getAlertDetails() {
		return alertDetails;
	}

	public void setAlertDetails(String alertDetails) {
		this.alertDetails = alertDetails;
	}

	public void setAletActionId(String aletActionId) {
		this.aletActionId = aletActionId;
	}

	public String getAletActionName() {
		return aletActionName;
	}

	public void setAletActionName(String aletActionName) {
		this.aletActionName = aletActionName;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public int getStudyId() {
		return studyId;
	}

	public void setStudyId(int studyId) {
		this.studyId = studyId;
	}

	public int getAlertTypeId() {
		return alertTypeId;
	}

	public void setAlertTypeId(int alertTypeId) {
		this.alertTypeId = alertTypeId;
	}

	public String getFirmware() {
		return firmware;
	}

	public void setFirmware(String firmware) {
		this.firmware = firmware;
	}

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

	public String getAlertDate() {
		return alertDate;
	}

	public void setAlertDate(String alertDate) {
		this.alertDate = alertDate;
	}

	public String getRepeatedCount() {
		return repeatedCount;
	}

	public void setRepeatedCount(String repeatedCount) {
		this.repeatedCount = repeatedCount;
	}

	public String getAlertPriority() {
		return alertPriority;
	}

	public void setAlertPriority(String alertPriority) {
		this.alertPriority = alertPriority;
	}

	private String repeatedCount;
	private String alertPriority;

	public String getAlertName() {
		return alertName;
	}

	public void setAlertName(String alertName) {
		this.alertName = alertName;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getBoxNumber() {
		return boxNumber;
	}

	public void setBoxNumber(String boxNumber) {
		this.boxNumber = boxNumber;
	}

}
