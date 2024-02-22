package com.hillspet.wearables.dto;

public class PetForwardMotionInfo {
	private int walking;
	private String walkingText;
	private int running;
	private String runningText;
	private int todayForwardMotion;
	private String todayForwardMotionText;

	private int lastWeekForwardMotionAverage;
	private String lastWeekFMAvgText;

	private int todayVsLastWeekFMAvgPercentage;

	private int previousDayForwardMotion;
	private String previousDayFMText;

	private int prevDayFMVsLastWeekFMAvgPercentage;
	private int todayForwardMotionSofar;
	private String todayForwardMotionSofarText;

	private int todayFMSofarVsLastWeekFMAvgPercentage;
	private int prevDayForwardMotionAtThisTime;
	private int todayFMSofarVsPrevDayFMAtThisTime;

	public int getWalking() {
		return walking;
	}

	public void setWalking(int walking) {
		this.walking = walking;
	}

	public String getWalkingText() {
		return walkingText;
	}

	public void setWalkingText(String walkingText) {
		this.walkingText = walkingText;
	}

	public int getRunning() {
		return running;
	}

	public void setRunning(int running) {
		this.running = running;
	}

	public String getRunningText() {
		return runningText;
	}

	public void setRunningText(String runningText) {
		this.runningText = runningText;
	}

	public int getTodayForwardMotion() {
		return todayForwardMotion;
	}

	public void setTodayForwardMotion(int todayForwardMotion) {
		this.todayForwardMotion = todayForwardMotion;
	}

	public String getTodayForwardMotionText() {
		return todayForwardMotionText;
	}

	public void setTodayForwardMotionText(String todayForwardMotionText) {
		this.todayForwardMotionText = todayForwardMotionText;
	}

	public int getLastWeekForwardMotionAverage() {
		return lastWeekForwardMotionAverage;
	}

	public void setLastWeekForwardMotionAverage(int lastWeekForwardMotionAverage) {
		this.lastWeekForwardMotionAverage = lastWeekForwardMotionAverage;
	}

	public String getLastWeekFMAvgText() {
		return lastWeekFMAvgText;
	}

	public void setLastWeekFMAvgText(String lastWeekFMAvgText) {
		this.lastWeekFMAvgText = lastWeekFMAvgText;
	}

	public int getTodayVsLastWeekFMAvgPercentage() {
		return todayVsLastWeekFMAvgPercentage;
	}

	public void setTodayVsLastWeekFMAvgPercentage(int todayVsLastWeekFMAvgPercentage) {
		this.todayVsLastWeekFMAvgPercentage = todayVsLastWeekFMAvgPercentage;
	}

	public int getPreviousDayForwardMotion() {
		return previousDayForwardMotion;
	}

	public void setPreviousDayForwardMotion(int previousDayForwardMotion) {
		this.previousDayForwardMotion = previousDayForwardMotion;
	}

	public String getPreviousDayFMText() {
		return previousDayFMText;
	}

	public void setPreviousDayFMText(String previousDayFMText) {
		this.previousDayFMText = previousDayFMText;
	}

	public int getPrevDayFMVsLastWeekFMAvgPercentage() {
		return prevDayFMVsLastWeekFMAvgPercentage;
	}

	public void setPrevDayFMVsLastWeekFMAvgPercentage(int prevDayFMVsLastWeekFMAvgPercentage) {
		this.prevDayFMVsLastWeekFMAvgPercentage = prevDayFMVsLastWeekFMAvgPercentage;
	}

	public int getTodayForwardMotionSofar() {
		return todayForwardMotionSofar;
	}

	public void setTodayForwardMotionSofar(int todayForwardMotionSofar) {
		this.todayForwardMotionSofar = todayForwardMotionSofar;
	}

	public String getTodayForwardMotionSofarText() {
		return todayForwardMotionSofarText;
	}

	public void setTodayForwardMotionSofarText(String todayForwardMotionSofarText) {
		this.todayForwardMotionSofarText = todayForwardMotionSofarText;
	}

	public int getTodayFMSofarVsLastWeekFMAvgPercentage() {
		return todayFMSofarVsLastWeekFMAvgPercentage;
	}

	public void setTodayFMSofarVsLastWeekFMAvgPercentage(int todayFMSofarVsLastWeekFMAvgPercentage) {
		this.todayFMSofarVsLastWeekFMAvgPercentage = todayFMSofarVsLastWeekFMAvgPercentage;
	}

	public int getPrevDayForwardMotionAtThisTime() {
		return prevDayForwardMotionAtThisTime;
	}

	public void setPrevDayForwardMotionAtThisTime(int prevDayForwardMotionAtThisTime) {
		this.prevDayForwardMotionAtThisTime = prevDayForwardMotionAtThisTime;
	}

	public int getTodayFMSofarVsPrevDayFMAtThisTime() {
		return todayFMSofarVsPrevDayFMAtThisTime;
	}

	public void setTodayFMSofarVsPrevDayFMAtThisTime(int todayFMSofarVsPrevDayFMAtThisTime) {
		this.todayFMSofarVsPrevDayFMAtThisTime = todayFMSofarVsPrevDayFMAtThisTime;
	}

}