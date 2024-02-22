package com.hillspet.wearables.request;

import io.swagger.annotations.ApiModelProperty;

public class StudyNotificationStatus {

	@ApiModelProperty(value = "studyId", required = true, example = "1")
	private Integer studyId;

	@ApiModelProperty(value = "status", required = true, example = "1")
	private Integer status;

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
