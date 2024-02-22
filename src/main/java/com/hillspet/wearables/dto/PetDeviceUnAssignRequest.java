package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetDeviceUnAssignRequest {

	private Integer petStudyDeviceId;
	private Integer petStudyId;
	private Integer deviceId;
	private Integer reasonId;
	private String unAssignedOn;
	private String replacedDeviceId;

	private String dateOfDeath;
	private Boolean isApproximateDateOfDeath;
	private String lostToFollowUpDate;
	private Boolean isApproxLostToFollowUpDate;
	
	private String deviceNumber;
	private Integer studyId;
	

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public Integer getPetStudyDeviceId() {
		return petStudyDeviceId;
	}

	public void setPetStudyDeviceId(Integer petStudyDeviceId) {
		this.petStudyDeviceId = petStudyDeviceId;
	}

	public Integer getPetStudyId() {
		return petStudyId;
	}

	public void setPetStudyId(Integer petStudyId) {
		this.petStudyId = petStudyId;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getReasonId() {
		return reasonId;
	}

	public void setReasonId(Integer reasonId) {
		this.reasonId = reasonId;
	}

	public String getUnAssignedOn() {
		return unAssignedOn;
	}

	public void setUnAssignedOn(String unAssignedOn) {
		this.unAssignedOn = unAssignedOn;
	}

	public String getReplacedDeviceId() {
		return replacedDeviceId;
	}

	public void setReplacedDeviceId(String replacedDeviceId) {
		this.replacedDeviceId = replacedDeviceId;
	}

	public String getDateOfDeath() {
		return dateOfDeath;
	}

	public void setDateOfDeath(String dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}

	public Boolean getIsApproximateDateOfDeath() {
		return isApproximateDateOfDeath;
	}

	public void setIsApproximateDateOfDeath(Boolean isApproximateDateOfDeath) {
		this.isApproximateDateOfDeath = isApproximateDateOfDeath;
	}

	public String getLostToFollowUpDate() {
		return lostToFollowUpDate;
	}

	public void setLostToFollowUpDate(String lostToFollowUpDate) {
		this.lostToFollowUpDate = lostToFollowUpDate;
	}

	public Boolean getIsApproxLostToFollowUpDate() {
		return isApproxLostToFollowUpDate;
	}

	public void setIsApproxLostToFollowUpDate(Boolean isApproxLostToFollowUpDate) {
		this.isApproxLostToFollowUpDate = isApproxLostToFollowUpDate;
	}

}
