package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class FeedingScheduleFilter extends BaseFilter {

	@ApiParam(name = "fromPhaseDay", value = "fromPhaseDay ")
	@QueryParam("fromPhaseDay")
	private String fromPhaseDay;

	@ApiParam(name = "toPhaseDay", value = "toPhaseDay ")
	@QueryParam("toPhaseDay")
	private String toPhaseDay;

	public String getFromPhaseDay() {
		return fromPhaseDay;
	}

	public void setFromPhaseDay(String fromPhaseDay) {
		this.fromPhaseDay = fromPhaseDay;
	}

	public String getToPhaseDay() {
		return toPhaseDay;
	}

	public void setToPhaseDay(String toPhaseDay) {
		this.toPhaseDay = toPhaseDay;
	}
	
	
}
