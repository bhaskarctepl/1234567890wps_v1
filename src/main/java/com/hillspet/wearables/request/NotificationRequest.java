package com.hillspet.wearables.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contains all information that needed to send notification", value = "NotificationRequest")
@JsonInclude(value = Include.NON_NULL)
public class NotificationRequest {

	@ApiModelProperty(value = "petQuestionnaireScheduleId", required = true)
	private Integer petQuestionnaireScheduleId;

	@ApiModelProperty(value = "petId", required = true)
	private Integer petId;

	@ApiModelProperty(value = "notificationText", required = true)
	private String notificationText;

	private Integer userId;

	public Integer getPetQuestionnaireScheduleId() {
		return petQuestionnaireScheduleId;
	}

	public void setPetQuestionnaireScheduleId(Integer petQuestionnaireScheduleId) {
		this.petQuestionnaireScheduleId = petQuestionnaireScheduleId;
	}

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}

	public String getNotificationText() {
		return notificationText;
	}

	public void setNotificationText(String notificationText) {
		this.notificationText = notificationText;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
