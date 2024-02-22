package com.hillspet.wearables.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetStudyDevice extends BaseDTO {

	private Integer petId;
	private Integer studyId;
	private String studyName;
	private String studyDescription;
	private Integer deviceId;
	private String deviceNumber;
	private String deviceModel;
	private String firmwareVersion;
	private String status;
	private LocalDate assignedOn;
	private String assignedOnDate;
	private String lastSync;
	private String batteryPercentage;
	private LocalDate unAssignedOn;
	private int petStudyDeviceId;
	private String deviceType;
	private int petStudyId;
	private Integer unAssignReasonId;
	private String externalPetInfoId;
	private String externalPetValue;
	private boolean isStudyActive;
	private boolean isExternal;

	private LocalDate studyAssignedOn;
	private LocalDate studyEndDate;
	private String studyAssignedOnDate;
	private String unassignedReason;

	private LocalDate dateOfDeath;
	private Boolean isApproximateDateOfDeath;
	private LocalDate lostToFollowUpDate;
	private Boolean isApproxLostToFollowUpDate;

	private String dataStreamId;
	private Integer streamDeviceSeqNum;

	private LocalDate studyStartDate;
	private LocalDate minStudyDeviceAssignedDate;

	public LocalDate getDateOfDeath() {
		return dateOfDeath;
	}

	public void setDateOfDeath(LocalDate dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}

	public Boolean getIsApproximateDateOfDeath() {
		return isApproximateDateOfDeath;
	}

	public void setIsApproximateDateOfDeath(Boolean isApproximateDateOfDeath) {
		this.isApproximateDateOfDeath = isApproximateDateOfDeath;
	}

	public boolean isExternal() {
		return isExternal;
	}

	public void setExternal(boolean isExternal) {
		this.isExternal = isExternal;
	}

	public String getExternalPetValue() {
		return externalPetValue;
	}

	public void setExternalPetValue(String externalPetValue) {
		this.externalPetValue = externalPetValue;
	}

	public String getBatteryPercentage() {
		return batteryPercentage;
	}

	public void setBatteryPercentage(String batteryPercentage) {
		this.batteryPercentage = batteryPercentage;
	}

	public Integer getUnAssignReasonId() {
		return unAssignReasonId;
	}

	public void setUnAssignReasonId(Integer unAssignReasonId) {
		this.unAssignReasonId = unAssignReasonId;
	}

	public boolean isStudyActive() {
		return isStudyActive;
	}

	public void setStudyActive(boolean isStudyActive) {
		this.isStudyActive = isStudyActive;
	}

	public String getStudyAssignedOnDate() {
		return studyAssignedOnDate;
	}

	public void setStudyAssignedOnDate(String studyAssignedOnDate) {
		this.studyAssignedOnDate = studyAssignedOnDate;
	}

	public LocalDate getStudyAssignedOn() {
		return studyAssignedOn;
	}

	public void setStudyAssignedOn(LocalDate studyAssignedOn) {
		this.studyAssignedOn = studyAssignedOn;
	}

	public String getExternalPetInfoId() {
		return externalPetInfoId;
	}

	public void setExternalPetInfoId(String externalPetInfoId) {
		this.externalPetInfoId = externalPetInfoId;
	}

	public int getPetStudyId() {
		return petStudyId;
	}

	public void setPetStudyId(int petStudyId) {
		this.petStudyId = petStudyId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public int getPetStudyDeviceId() {
		return petStudyDeviceId;
	}

	public void setPetStudyDeviceId(int petStudyDeviceId) {
		this.petStudyDeviceId = petStudyDeviceId;
	}

	public LocalDate getUnAssignedOn() {
		return unAssignedOn;
	}

	public void setUnAssignedOn(LocalDate unAssignedOn) {
		this.unAssignedOn = unAssignedOn;
	}

	public String getAssignedOnDate() {
		return assignedOnDate;
	}

	public void setAssignedOnDate(String assignedOnDate) {
		this.assignedOnDate = assignedOnDate;
	}

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
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

	public String getStudyDescription() {
		return studyDescription;
	}

	public void setStudyDescription(String studyDescription) {
		this.studyDescription = studyDescription;
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

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getFirmwareVersion() {
		return firmwareVersion;
	}

	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getAssignedOn() {
		return assignedOn;
	}

	public void setAssignedOn(LocalDate assignedOn) {
		this.assignedOn = assignedOn;
	}

	public String getLastSync() {
		return lastSync;
	}

	public void setLastSync(String lastSync) {
		this.lastSync = lastSync;
	}

	public String getUnassignedReason() {
		return unassignedReason;
	}

	public void setUnassignedReason(String unassignedReason) {
		this.unassignedReason = unassignedReason;
	}

	public LocalDate getStudyEndDate() {
		return studyEndDate;
	}

	public void setStudyEndDate(LocalDate studyEndDate) {
		this.studyEndDate = studyEndDate;
	}

	public LocalDate getLostToFollowUpDate() {
		return lostToFollowUpDate;
	}

	public void setLostToFollowUpDate(LocalDate lostToFollowUpDate) {
		this.lostToFollowUpDate = lostToFollowUpDate;
	}

	public Boolean getIsApproxLostToFollowUpDate() {
		return isApproxLostToFollowUpDate;
	}

	public void setIsApproxLostToFollowUpDate(Boolean isApproxLostToFollowUpDate) {
		this.isApproxLostToFollowUpDate = isApproxLostToFollowUpDate;
	}

	public String getDataStreamId() {
		return dataStreamId;
	}

	public void setDataStreamId(String dataStreamId) {
		this.dataStreamId = dataStreamId;
	}

	public Integer getStreamDeviceSeqNum() {
		return streamDeviceSeqNum;
	}

	public void setStreamDeviceSeqNum(Integer streamDeviceSeqNum) {
		this.streamDeviceSeqNum = streamDeviceSeqNum;
	}

	public LocalDate getStudyStartDate() {
		return studyStartDate;
	}

	public void setStudyStartDate(LocalDate studyStartDate) {
		this.studyStartDate = studyStartDate;
	}

	public LocalDate getMinStudyDeviceAssignedDate() {
		return minStudyDeviceAssignedDate;
	}

	public void setMinStudyDeviceAssignedDate(LocalDate minStudyDeviceAssignedDate) {
		this.minStudyDeviceAssignedDate = minStudyDeviceAssignedDate;
	}

}
