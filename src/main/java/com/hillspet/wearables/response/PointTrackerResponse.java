package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.PointTracker;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PointTrackerResponse extends BaseResultCollection {

	private List<PointTracker> pointTrackerList;
	private PointTracker pointTracker;
	private List<PointTracker> campaigns;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for Point Trackers")

	public List<PointTracker> getPointTrackerList() {
		return pointTrackerList;
	}

	public void setPointTrackerList(List<PointTracker> pointTrackerList) {
		this.pointTrackerList = pointTrackerList;
	}

	public PointTracker getPointTracker() {
		return pointTracker;
	}

	public void setPointTracker(PointTracker pointTracker) {
		this.pointTracker = pointTracker;
	}

	public List<PointTracker> getCampaigns() {
		return campaigns;
	}

	public void setCampaigns(List<PointTracker> campaigns) {
		this.campaigns = campaigns;
	}

}
