package com.hillspet.wearables.request;

import io.swagger.annotations.ApiModelProperty;

public class PushNotificationAssociated {

	@ApiModelProperty(value = "studyPushNotificationConfigId", required = true)
	private Integer studyPushNotificationConfigId;

	private Integer studyId;

	private Integer phaseId;

	@ApiModelProperty(value = "notificationId", required = true)
	private Integer notificationId;

	@ApiModelProperty(value = "notificationName", required = false)
	private String notificationName;

	@ApiModelProperty(value = "startDate", required = true)
	private String startDate;

	@ApiModelProperty(value = "endDate", required = true)
	private String endDate;

	@ApiModelProperty(value = "displayTime", required = false)
	private String displayTime;

	@ApiModelProperty(value = "frequencyId", required = false)
	private Integer frequencyId;

	@ApiModelProperty(value = "frequency", required = false)
	private String frequency;

	@Override
	public String toString() {
		return notificationId + "#" + startDate + "#" + endDate + "#" + displayTime + "#" + frequency;
	}

	public Integer getStudyPushNotificationConfigId() {
		return studyPushNotificationConfigId;
	}

	public void setStudyPushNotificationConfigId(Integer studyPushNotificationConfigId) {
		this.studyPushNotificationConfigId = studyPushNotificationConfigId;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public Integer getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(Integer phaseId) {
		this.phaseId = phaseId;
	}

	public Integer getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}

	public String getNotificationName() {
		return notificationName;
	}

	public void setNotificationName(String notificationName) {
		this.notificationName = notificationName;
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

	public String getDisplayTime() {
		return displayTime;
	}

	public void setDisplayTime(String displayTime) {
		this.displayTime = displayTime;
	}

	public Integer getFrequencyId() {
		return frequencyId;
	}

	public void setFrequencyId(Integer frequencyId) {
		this.frequencyId = frequencyId;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
}
