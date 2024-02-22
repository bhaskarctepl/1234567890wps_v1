package com.hillspet.wearables.dto;

import java.util.List;
import java.util.Map;

public class CustomerSupportDetails {

	private Integer ticketId;
	private String ticketTitle;
	private String ticketCreatedDate;
	private String createdBy;
	private String assignedTo;
	private String priority;
	private String status;
	private String petName;
	private String petParentName;
	private String contactMethod;
	private String petParentAddress;
	private String studyName;
	private String issueName;
	private String rootCauseName;
	private String sensorName;
	private String mfgSerialNumber;
	private String mfgMacAddr;
	private String wifiMacAddr;

	private String sensorLocationName;
	private String inventoryStatusName;
	private String agentSystemActionInitial;
	private String agentSystemActionSecondary;
	private String agentSystemActionTertiary;
	private String defectiveSensorAction;
	private String notes;
	private List<Map<String, String>> uploadedFiles;
	private String studyStartDate;
	private String studyEndDate;
	private Boolean studyStatus;

	public String getStudyStartDate() {
		return studyStartDate;
	}

	public void setStudyStartDate(String studyStartDate) {
		this.studyStartDate = studyStartDate;
	}

	public String getStudyEndDate() {
		return studyEndDate;
	}

	public void setStudyEndDate(String studyEndDate) {
		this.studyEndDate = studyEndDate;
	}

	public Boolean getStudyStatus() {
		return studyStatus;
	}

	public void setStudyStatus(Boolean studyStatus) {
		this.studyStatus = studyStatus;
	}

	public Integer getTicketId() {
		return ticketId;
	}

	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}

	public String getTicketTitle() {
		return ticketTitle;
	}

	public void setTicketTitle(String ticketTitle) {
		this.ticketTitle = ticketTitle;
	}

	public String getTicketCreatedDate() {
		return ticketCreatedDate;
	}

	public void setTicketCreatedDate(String ticketCreatedDate) {
		this.ticketCreatedDate = ticketCreatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getPetParentName() {
		return petParentName;
	}

	public void setPetParentName(String petParentName) {
		this.petParentName = petParentName;
	}

	public String getContactMethod() {
		return contactMethod;
	}

	public void setContactMethod(String contactMethod) {
		this.contactMethod = contactMethod;
	}

	public String getPetParentAddress() {
		return petParentAddress;
	}

	public void setPetParentAddress(String petParentAddress) {
		this.petParentAddress = petParentAddress;
	}

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

	public String getIssueName() {
		return issueName;
	}

	public void setIssueName(String issueName) {
		this.issueName = issueName;
	}

	public String getRootCauseName() {
		return rootCauseName;
	}

	public void setRootCauseName(String rootCauseName) {
		this.rootCauseName = rootCauseName;
	}

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	public String getMfgSerialNumber() {
		return mfgSerialNumber;
	}

	public void setMfgSerialNumber(String mfgSerialNumber) {
		this.mfgSerialNumber = mfgSerialNumber;
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

	public String getSensorLocationName() {
		return sensorLocationName;
	}

	public void setSensorLocationName(String sensorLocationName) {
		this.sensorLocationName = sensorLocationName;
	}

	public String getInventoryStatusName() {
		return inventoryStatusName;
	}

	public void setInventoryStatusName(String inventoryStatusName) {
		this.inventoryStatusName = inventoryStatusName;
	}

	public String getAgentSystemActionInitial() {
		return agentSystemActionInitial;
	}

	public void setAgentSystemActionInitial(String agentSystemActionInitial) {
		this.agentSystemActionInitial = agentSystemActionInitial;
	}

	public String getAgentSystemActionSecondary() {
		return agentSystemActionSecondary;
	}

	public void setAgentSystemActionSecondary(String agentSystemActionSecondary) {
		this.agentSystemActionSecondary = agentSystemActionSecondary;
	}

	public String getAgentSystemActionTertiary() {
		return agentSystemActionTertiary;
	}

	public void setAgentSystemActionTertiary(String agentSystemActionTertiary) {
		this.agentSystemActionTertiary = agentSystemActionTertiary;
	}

	public String getDefectiveSensorAction() {
		return defectiveSensorAction;
	}

	public void setDefectiveSensorAction(String defectiveSensorAction) {
		this.defectiveSensorAction = defectiveSensorAction;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<Map<String, String>> getUploadedFiles() {
		return uploadedFiles;
	}

	public void setUploadedFiles(List<Map<String, String>> uploadedFiles) {
		this.uploadedFiles = uploadedFiles;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("CustomerSupportDetails{");
		sb.append("ticketId=").append(ticketId);
		sb.append(", ticketTitle='").append(ticketTitle).append('\'');
		sb.append(", ticketCreatedDate='").append(ticketCreatedDate).append('\'');
		sb.append(", assignedTo='").append(assignedTo).append('\'');
		sb.append(", priority='").append(priority).append('\'');
		sb.append(", status='").append(status).append('\'');
		sb.append(", petName='").append(petName).append('\'');
		sb.append(", petParentName='").append(petParentName).append('\'');
		sb.append(", contactMethod='").append(contactMethod).append('\'');
		sb.append(", petParentAddress='").append(petParentAddress).append('\'');
		sb.append(", studyName='").append(studyName).append('\'');
		sb.append(", issueName='").append(issueName).append('\'');
		sb.append(", rootCauseName='").append(rootCauseName).append('\'');
		sb.append(", sensorName='").append(sensorName).append('\'');
		sb.append(", sensorLocationName='").append(sensorLocationName).append('\'');
		sb.append(", inventoryStatusName='").append(inventoryStatusName).append('\'');
		sb.append(", agentSystemActionInitial='").append(agentSystemActionInitial).append('\'');
		sb.append(", agentSystemActionSecondary='").append(agentSystemActionSecondary).append('\'');
		sb.append(", agentSystemActionTertiary='").append(agentSystemActionTertiary).append('\'');
		sb.append(", defectiveSensorAction='").append(defectiveSensorAction).append('\'');
		sb.append(", notes='").append(notes).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
