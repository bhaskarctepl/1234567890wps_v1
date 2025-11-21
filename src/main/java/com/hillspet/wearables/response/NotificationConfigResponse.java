package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.NotificationConfig;


@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationConfigResponse {

	private List<NotificationConfig> notificationConfigs;

	public List<NotificationConfig> getNotificationConfigs() {
		return notificationConfigs;
	}

	public void setNotificationConfigs(List<NotificationConfig> notificationConfigs) {
		this.notificationConfigs = notificationConfigs;
	}


}