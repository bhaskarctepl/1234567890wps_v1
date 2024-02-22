package com.hillspet.wearables.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StreamDevice extends BaseDTO {

	private String dataStreamId;
	private Integer deviceId;
	private String deviceNumber;
	private String deviceType;
	private String deviceModel;
	private LocalDateTime assignedOn;
	private LocalDateTime unassignedOn;
	private Integer petStudyId;
	private Integer studyId;
	private String studyName;
	private String petName;

	public String getDataStreamId() {
		return dataStreamId;
	}

	public void setDataStreamId(String dataStreamId) {
		this.dataStreamId = dataStreamId;
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

	public LocalDateTime getAssignedOn() {
		return assignedOn;
	}

	public void setAssignedOn(LocalDateTime assignedOn) {
		this.assignedOn = assignedOn;
	}

	public LocalDateTime getUnassignedOn() {
		return unassignedOn;
	}

	public void setUnassignedOn(LocalDateTime unassignedOn) {
		this.unassignedOn = unassignedOn;
	}

	public Integer getPetStudyId() {
		return petStudyId;
	}

	public void setPetStudyId(Integer petStudyId) {
		this.petStudyId = petStudyId;
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

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

}
