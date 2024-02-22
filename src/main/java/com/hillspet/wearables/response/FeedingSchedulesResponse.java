package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.FeedingSchedule;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedingSchedulesResponse  extends BaseResultCollection {

	private List<FeedingSchedule> feedingScheduleList;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of feeding schedules for a study")
	public List<FeedingSchedule> getFeedingScheduleList() {
		return feedingScheduleList;
	}

	public void setFeedingScheduleList(List<FeedingSchedule> feedingScheduleList) {
		this.feedingScheduleList = feedingScheduleList;
	}

}
