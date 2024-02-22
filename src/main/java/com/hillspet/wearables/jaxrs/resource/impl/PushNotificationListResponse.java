package com.hillspet.wearables.jaxrs.resource.impl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.PushNotification;
import com.hillspet.wearables.response.BaseResultCollection;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PushNotificationListResponse extends BaseResultCollection {

	private List<PushNotification> pushNotifications;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for push notification based on search criteria")
	public List<PushNotification> getPushNotifications() {
		return pushNotifications;
	}

	public void setPushNotifications(List<PushNotification> pushNotifications) {
		this.pushNotifications = pushNotifications;
	}

}
