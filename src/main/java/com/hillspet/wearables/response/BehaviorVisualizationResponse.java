package com.hillspet.wearables.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hillspet.wearables.dto.PetForwardMotionGoalSetting;
import com.hillspet.wearables.dto.PetForwardMotionInfo;
import com.hillspet.wearables.dto.PetSleepInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BehaviorVisualizationResponse {
	private int petId;
	private PetForwardMotionInfo forwardMotionInfo = new PetForwardMotionInfo();
	private PetSleepInfo sleepInfo = new PetSleepInfo();
	private PetForwardMotionGoalSetting fmGoalSetting = new PetForwardMotionGoalSetting();

	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	public PetForwardMotionInfo getForwardMotionInfo() {
		return forwardMotionInfo;
	}

	public void setForwardMotionInfo(PetForwardMotionInfo forwardMotionInfo) {
		this.forwardMotionInfo = forwardMotionInfo;
	}

	public PetSleepInfo getSleepInfo() {
		return sleepInfo;
	}

	public void setSleepInfo(PetSleepInfo sleepInfo) {
		this.sleepInfo = sleepInfo;
	}

	public PetForwardMotionGoalSetting getFmGoalSetting() {
		return fmGoalSetting;
	}

	public void setFmGoalSetting(PetForwardMotionGoalSetting fmGoalSetting) {
		this.fmGoalSetting = fmGoalSetting;
	}

}
