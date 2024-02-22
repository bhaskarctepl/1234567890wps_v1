package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrackerActivityStatusNotes extends BaseDTO {

	private Integer trackerPetPointsId;
	private String  rejectNotes;
	private Integer behaviorId;
	private Integer statusId;
	
	public Integer getTrackerPetPointsId() {
		return trackerPetPointsId;
	}
	public void setTrackerPetPointsId(Integer trackerPetPointsId) {
		this.trackerPetPointsId = trackerPetPointsId;
	}
	public String getRejectNotes() {
		return rejectNotes;
	}
	public void setRejectNotes(String rejectNotes) {
		this.rejectNotes = rejectNotes;
	}
	public Integer getBehaviorId() {
		return behaviorId;
	}
	public void setBehaviorId(Integer behaviorId) {
		this.behaviorId = behaviorId;
	}
	public Integer getStatusId() {
		return statusId;
	}
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	

}
