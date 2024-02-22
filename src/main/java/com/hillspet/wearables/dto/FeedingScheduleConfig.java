package com.hillspet.wearables.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedingScheduleConfig {

	private Integer studyId;
	private Integer phaseId;
	private Integer fromPhaseDay;
	private Integer toPhaseDay;
	private Integer phaseDuration;
	private Integer isDeleted;
	private Integer userId;
	private List<FeedingSchedule> feedingScheduleList;
 
	
	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public Integer getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(Integer phaseId) {
		this.phaseId = phaseId;
	}

	public Integer getFromPhaseDay() {
		return fromPhaseDay;
	}

	public void setFromPhaseDay(Integer fromPhaseDay) {
		this.fromPhaseDay = fromPhaseDay;
	}

	public Integer getToPhaseDay() {
		return toPhaseDay;
	}

	public void setToPhaseDay(Integer toPhaseDay) {
		this.toPhaseDay = toPhaseDay;
	}

	public Integer getPhaseDuration() {
		return phaseDuration;
	}

	public void setPhaseDuration(Integer phaseDuration) {
		this.phaseDuration = phaseDuration;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<FeedingSchedule> getFeedingScheduleList() {
		return feedingScheduleList;
	}

	public void setFeedingScheduleList(List<FeedingSchedule> feedingScheduleList) {
		this.feedingScheduleList = feedingScheduleList;
	}

}
