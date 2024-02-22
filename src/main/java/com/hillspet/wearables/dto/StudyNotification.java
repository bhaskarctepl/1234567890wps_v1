package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudyNotification extends BaseDTO {
	private Integer studyId;
	private String studyName;
	private Boolean isNotificationEnable;
	private Boolean isActive;
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	public Boolean getIsNotificationEnable() {
		return isNotificationEnable;
	}

	public void setIsNotificationEnable(Boolean isNotificationEnable) {
		this.isNotificationEnable = isNotificationEnable;
	}

}
