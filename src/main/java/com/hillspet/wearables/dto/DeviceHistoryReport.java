package com.hillspet.wearables.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceHistoryReport extends BaseDTO {
	
	//BDI.DEVICE_TYPE, BDI.DEVICE_MODEL, BDI.DEVICE_NUMBER, JDA.ASSIGN_DATE, JDA.UN_ASSIGN_DATE, JDA.PET_ID, BMP.STUDY_ID, BPI.PET_NAME, BS.STUDY_NAME, BDI.MODIFIED_DATE
	private String deviceType;
	private String deviceModel;
	private String deviceNumber;
	private String petId;
	private String petName;
	private String studyId;
	private String studyName;	
	private LocalDateTime assignDate;
	private LocalDateTime unAssignDate;
	private String assignmentHistory;
	
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
	public String getPetId() {
		return petId;
	}
	public void setPetId(String petId) {
		this.petId = petId;
	}
	public String getPetName() {
		return petName;
	}
	public void setPetName(String petName) {
		this.petName = petName;
	}
	public String getStudyId() {
		return studyId;
	}
	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}
	public String getStudyName() {
		return studyName;
	}
	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}
	public LocalDateTime getAssignDate() {
		return assignDate;
	}
	public void setAssignDate(LocalDateTime assignDate) {
		this.assignDate = assignDate;
	}
	public LocalDateTime getUnAssignDate() {
		return unAssignDate;
	}
	public void setUnAssignDate(LocalDateTime unAssignDate) {
		this.unAssignDate = unAssignDate;
	}

	public String getAssignmentHistory() {
		return assignmentHistory;
	}

	public void setAssignmentHistory(String assignmentHistory) {
		this.assignmentHistory = assignmentHistory;
	}
}
