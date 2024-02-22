package com.hillspet.wearables.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author sgorle
 *
 */
@ApiModel(description = "Contains all information that needed to update study notification status", value = "StudyNotificationRequest")
@JsonInclude(value = Include.NON_NULL)
public class StudyNotificationRequest {

	@ApiModelProperty(value = "plansSubscribed", required = true)
	private List<StudyNotificationStatus> studyNotificationStatusList;

	public List<StudyNotificationStatus> getStudyNotificationStatusList() {
		return studyNotificationStatusList;
	}

	public void setStudyNotificationStatusList(List<StudyNotificationStatus> studyNotificationStatusList) {
		this.studyNotificationStatusList = studyNotificationStatusList;
	}

}
