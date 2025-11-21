package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationConfig {

	private Integer notificationConfigId;
	private String notificationConfigName;
	
	
	public Integer getIsMandatory() {
		return isMandatory;
	}
	
	public void setIsMandatory(Integer isMandatory) {
		this.isMandatory = isMandatory;
	}
	private Integer isMandatory;
	
	
	public Integer getNotificationConfigId() {
		return notificationConfigId;
	}
	public void setNotificationConfigId(Integer notificationConfigId) {
		this.notificationConfigId = notificationConfigId;
	}
	public String getNotificationConfigName() {
		return notificationConfigName;
	}
	public void setNotificationConfigName(String notificationConfigName) {
		this.notificationConfigName = notificationConfigName;
	}

}