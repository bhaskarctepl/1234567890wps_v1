package com.hillspet.wearables.dto;

public class PetForwardMotionGoalSetting {
	private int forwardMotionGoalSetting;
	private String forwardMotionGoalSetText;
	private int todayForwardMotionSofar;
	private String todayFMSofarText;
	private int todayForwardMotionVsGoalSettingPercentage;

	private int tobeAchieved;
	private String tobeAchievedText;
	private int overAchieved;
	private String overAchievedText;

	public int getForwardMotionGoalSetting() {
		return forwardMotionGoalSetting;
	}

	public void setForwardMotionGoalSetting(int forwardMotionGoalSetting) {
		this.forwardMotionGoalSetting = forwardMotionGoalSetting;
	}

	public String getForwardMotionGoalSetText() {
		return forwardMotionGoalSetText;
	}

	public void setForwardMotionGoalSetText(String forwardMotionGoalSetText) {
		this.forwardMotionGoalSetText = forwardMotionGoalSetText;
	}

	public int getTodayForwardMotionSofar() {
		return todayForwardMotionSofar;
	}

	public void setTodayForwardMotionSofar(int todayForwardMotionSofar) {
		this.todayForwardMotionSofar = todayForwardMotionSofar;
	}

	public String getTodayFMSofarText() {
		return todayFMSofarText;
	}

	public void setTodayFMSofarText(String todayFMSofarText) {
		this.todayFMSofarText = todayFMSofarText;
	}

	public int getTodayForwardMotionVsGoalSettingPercentage() {
		return todayForwardMotionVsGoalSettingPercentage;
	}

	public void setTodayForwardMotionVsGoalSettingPercentage(int todayForwardMotionVsGoalSettingPercentage) {
		this.todayForwardMotionVsGoalSettingPercentage = todayForwardMotionVsGoalSettingPercentage;
	}

	public int getTobeAchieved() {
		return tobeAchieved;
	}

	public void setTobeAchieved(int tobeAchieved) {
		this.tobeAchieved = tobeAchieved;
	}

	public String getTobeAchievedText() {
		return tobeAchievedText;
	}

	public void setTobeAchievedText(String tobeAchievedText) {
		this.tobeAchievedText = tobeAchievedText;
	}

	public int getOverAchieved() {
		return overAchieved;
	}

	public void setOverAchieved(int overAchieved) {
		this.overAchieved = overAchieved;
	}

	public String getOverAchievedText() {
		return overAchievedText;
	}

	public void setOverAchievedText(String overAchievedText) {
		this.overAchievedText = overAchievedText;
	}

}