package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class PetNotificationDTO {

	@ApiModelProperty(value = "Pet Id of the pet", required = true, example = "2356")
	@JsonProperty("petId")
	private Integer petId;

	@ApiModelProperty(value = "Indicates if the push notification is enabled (1 for enabled, 0 for disabled)", required = true, example = "1")
	@JsonProperty("isPushNotificationEnabled")
	private Integer isPushNotificationEnabled;

	@ApiModelProperty(value = "Indicates if the email notification is enabled (1 for enabled, 0 for disabled)", required = true, example = "1")
	@JsonProperty("isEmailNotificationEnabled")
	private Integer isEmailNotificationEnabled;

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}

	public Integer getIsPushNotificationEnabled() {
		return isPushNotificationEnabled;
	}

	public void setIsPushNotificationEnabled(Integer isPushNotificationEnabled) {
		this.isPushNotificationEnabled = isPushNotificationEnabled;
	}

	public Integer getIsEmailNotificationEnabled() {
		return isEmailNotificationEnabled;
	}

	public void setIsEmailNotificationEnabled(Integer isEmailNotificationEnabled) {
		this.isEmailNotificationEnabled = isEmailNotificationEnabled;
	}

}
