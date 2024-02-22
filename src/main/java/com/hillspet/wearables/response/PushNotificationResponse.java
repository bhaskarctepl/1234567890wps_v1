package com.hillspet.wearables.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.PushNotification;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PushNotificationResponse extends BaseResultCollection {

	private PushNotification pushNotification;

	public void setPushNotification(PushNotification pushNotification) {
		this.pushNotification = pushNotification;
	}

	@JsonProperty("rows")
	@ApiModelProperty(value = "Get push notification of particular id")
	public PushNotification getPushNotification() {
		return pushNotification;
	}
}
