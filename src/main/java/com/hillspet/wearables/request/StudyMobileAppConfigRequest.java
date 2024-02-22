package com.hillspet.wearables.request;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contains all information that needed to config mobile app modularity", value = "StudyMobileAppConfigRequest")
@JsonInclude(value = Include.NON_NULL)
public class StudyMobileAppConfigRequest {

	@ApiModelProperty(value = "mobileAppConfigs", required = true)
	private List<Integer> mobileAppConfigs;

	@ApiModelProperty(value = "weightUnit", required = false)
	private String weightUnit;

	@ApiModelProperty(value = "entsmScaleStartDate", required = false)
	private LocalDate entsmScaleStartDate;

	@ApiModelProperty(value = "entsmScaleEndDate", required = false)
	private LocalDate entsmScaleEndDate;

	@ApiModelProperty(value = "forwardMotionGoalSetting", required = false)
	private Integer forwardMotionGoalSetting;

	public List<Integer> getMobileAppConfigs() {
		return mobileAppConfigs;
	}

	public void setMobileAppConfigs(List<Integer> mobileAppConfigs) {
		this.mobileAppConfigs = mobileAppConfigs;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public LocalDate getEntsmScaleStartDate() {
		return entsmScaleStartDate;
	}

	public void setEntsmScaleStartDate(LocalDate entsmScaleStartDate) {
		this.entsmScaleStartDate = entsmScaleStartDate;
	}

	public LocalDate getEntsmScaleEndDate() {
		return entsmScaleEndDate;
	}

	public void setEntsmScaleEndDate(LocalDate entsmScaleEndDate) {
		this.entsmScaleEndDate = entsmScaleEndDate;
	}

	public Integer getForwardMotionGoalSetting() {
		return forwardMotionGoalSetting;
	}

	public void setForwardMotionGoalSetting(Integer forwardMotionGoalSetting) {
		this.forwardMotionGoalSetting = forwardMotionGoalSetting;
	}

}
