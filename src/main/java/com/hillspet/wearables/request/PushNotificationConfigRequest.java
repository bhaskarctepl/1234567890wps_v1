package com.hillspet.wearables.request;

import java.util.List;

import javax.ws.rs.PathParam;

import io.swagger.annotations.ApiModelProperty;

public class PushNotificationConfigRequest {

	@ApiModelProperty(value = "studyId", required = true)
	@PathParam("studyId")
	private Integer studyId;

	@ApiModelProperty(value = "phaseId", required = true)
	@PathParam("phaseId")
	private Integer phaseId;

	@ApiModelProperty(value = "pushNotificationsAssociated", required = false)
	private List<PushNotificationAssociated> pushNotificationsAssociated;

	private Integer userId;

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

	public List<PushNotificationAssociated> getPushNotificationsAssociated() {
		return pushNotificationsAssociated;
	}

	public void setPushNotificationsAssociated(List<PushNotificationAssociated> pushNotificationsAssociated) {
		this.pushNotificationsAssociated = pushNotificationsAssociated;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
