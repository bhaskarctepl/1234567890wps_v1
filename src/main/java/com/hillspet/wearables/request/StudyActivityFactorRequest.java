package com.hillspet.wearables.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contains all information that needed to config activity factor", value = "StudyActivityFactorRequest")
@JsonInclude(value = Include.NON_NULL)
public class StudyActivityFactorRequest {

	@ApiModelProperty(value = "activityFactorConfig", required = false)
	private ActivityFactorConfig activityFactorConfig;

	public ActivityFactorConfig getActivityFactorConfig() {
		return activityFactorConfig;
	}

	public void setActivityFactorConfig(ActivityFactorConfig activityFactorConfig) {
		this.activityFactorConfig = activityFactorConfig;
	}
}
