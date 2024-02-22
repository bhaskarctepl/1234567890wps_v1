package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.StudyNotification;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyNotificationResponse extends BaseResultCollection {
	private List<StudyNotification> studyNotifications;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for Study Notification list based on search criteria")
	public List<StudyNotification> getStudyNotifications() {
		return studyNotifications;
	}

	public void setStudyNotifications(List<StudyNotification> studyNotifications) {
		this.studyNotifications = studyNotifications;
	}
}
