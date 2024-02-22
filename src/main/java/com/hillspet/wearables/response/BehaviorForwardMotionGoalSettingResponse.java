package com.hillspet.wearables.response;

public class BehaviorForwardMotionGoalSettingResponse {

	int todayForwardMotion;
	int forwardMotionGoalSetting;
	int todayForwardMotionPercentage;

	public int getTodayForwardMotion() {
		return todayForwardMotion;
	}

	public void setTodayForwardMotion(int todayForwardMotion) {
		this.todayForwardMotion = todayForwardMotion;
	}

	public int getForwardMotionGoalSetting() {
		return forwardMotionGoalSetting;
	}

	public void setForwardMotionGoalSetting(int forwardMotionGoalSetting) {
		this.forwardMotionGoalSetting = forwardMotionGoalSetting;
	}

	public int getTodayForwardMotionPercentage() {
		return todayForwardMotionPercentage;
	}

	public void setTodayForwardMotionPercentage(int todayForwardMotionPercentage) {
		this.todayForwardMotionPercentage = todayForwardMotionPercentage;
	}

}
