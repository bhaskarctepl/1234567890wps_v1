package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BehaviorHistory {

	int petId;
	String activityDay;
	Integer walking;
	String walkingText;
	Integer running;
	String runningText;
	Integer forwardMotion;
	String forwardMotionText;

	Integer nightSleep;
	String nightSleepText;
	Integer daySleep;
	String daySleepText;
	Integer totalSleep;
	String totalSleepText;

	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	public String getActivityDay() {
		return activityDay;
	}

	public void setActivityDay(String activityDay) {
		this.activityDay = activityDay;
	}

	public Integer getWalking() {
		return walking;
	}

	public void setWalking(Integer walking) {
		this.walking = walking;
	}

	public String getWalkingText() {
		return walkingText;
	}

	public void setWalkingText(String walkingText) {
		this.walkingText = walkingText;
	}

	public Integer getRunning() {
		return running;
	}

	public void setRunning(Integer running) {
		this.running = running;
	}

	public String getRunningText() {
		return runningText;
	}

	public void setRunningText(String runningText) {
		this.runningText = runningText;
	}

	public Integer getForwardMotion() {
		return forwardMotion;
	}

	public void setForwardMotion(Integer forwardMotion) {
		this.forwardMotion = forwardMotion;
	}

	public String getForwardMotionText() {
		return forwardMotionText;
	}

	public void setForwardMotionText(String forwardMotionText) {
		this.forwardMotionText = forwardMotionText;
	}

	public Integer getNightSleep() {
		return nightSleep;
	}

	public void setNightSleep(Integer nightSleep) {
		this.nightSleep = nightSleep;
	}

	public String getNightSleepText() {
		return nightSleepText;
	}

	public void setNightSleepText(String nightSleepText) {
		this.nightSleepText = nightSleepText;
	}

	public Integer getDaySleep() {
		return daySleep;
	}

	public void setDaySleep(Integer daySleep) {
		this.daySleep = daySleep;
	}

	public String getDaySleepText() {
		return daySleepText;
	}

	public void setDaySleepText(String daySleepText) {
		this.daySleepText = daySleepText;
	}

	public Integer getTotalSleep() {
		return totalSleep;
	}

	public void setTotalSleep(Integer totalSleep) {
		this.totalSleep = totalSleep;
	}

	public String getTotalSleepText() {
		return totalSleepText;
	}

	public void setTotalSleepText(String totalSleepText) {
		this.totalSleepText = totalSleepText;
	}

}
