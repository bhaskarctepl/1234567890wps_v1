package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.PointTrackerCampaignActivities;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PointTrackerCampaignActivitiesResponse extends BaseResultCollection {

	private List<PointTrackerCampaignActivities> pointTrackerCampaignActivitiesList;
	
	
	private PointTrackerCampaignActivities pointTrackerCampaignActivities;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for Point Trackers Activities")
	public List<PointTrackerCampaignActivities> getPointTrackerCampaignActivitiesList() {
		return pointTrackerCampaignActivitiesList;
	}


	public void setPointTrackerCampaignActivitiesList(
			List<PointTrackerCampaignActivities> pointTrackerCampaignActivitiesList) {
		this.pointTrackerCampaignActivitiesList = pointTrackerCampaignActivitiesList;
	}


	public PointTrackerCampaignActivities getPointTrackerCampaignActivities() {
		return pointTrackerCampaignActivities;
	}


	public void setPointTrackerCampaignActivities(PointTrackerCampaignActivities pointTrackerCampaignActivities) {
		this.pointTrackerCampaignActivities = pointTrackerCampaignActivities;
	}
	
	
}
