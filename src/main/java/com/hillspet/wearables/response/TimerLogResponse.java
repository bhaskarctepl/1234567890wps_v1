package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.TimerLog;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class TimerLogResponse extends BaseResultCollection {
	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for Timer based on search criteria")
	private List<TimerLog> timerLogList;

	public List<TimerLog> getTimerLogList() {
		return timerLogList;
	}

	public void setTimerLogList(List<TimerLog> timerLogList) {
		this.timerLogList = timerLogList;
	}
	
	
}
