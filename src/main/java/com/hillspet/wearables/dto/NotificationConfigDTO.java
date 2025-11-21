package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contains individual notification configuration details")
public class NotificationConfigDTO {

	@ApiModelProperty(value = "ID of the notification", required = false, example = "1")
	@JsonProperty("notificationId")
	private Integer notificationId;

	@ApiModelProperty(value = "Time of the notification", required = false, example = "10:00:00")
	@JsonProperty("notificationTime")
	private String notificationTime;

	@ApiModelProperty(value = "Indicates if the notification is enabled (1 for enabled, 0 for disabled)", required = false, example = "1")
	@JsonProperty("isNotificationEnabled")
	private Integer isNotificationEnabled;

	
	public Integer getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}

	public String getNotificationTime() {
		return notificationTime;
	}

	public void setNotificationTime(String notificationTime) {
		this.notificationTime = notificationTime;
	}

	public Integer getIsNotificationEnabled() {
		return isNotificationEnabled;
	}

	public void setIsNotificationEnabled(Integer isNotificationEnabled) {
		this.isNotificationEnabled = isNotificationEnabled;
	}
}
