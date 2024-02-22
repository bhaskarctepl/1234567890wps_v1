package com.hillspet.wearables.response;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudyMobileAppConfigResponse {

	private List<Integer> mobileAppConfigs;
	private String weightUnit;
	private LocalDate entsmScaleStartDate;
	private LocalDate entsmScaleEndDate;
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
