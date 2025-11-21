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

	@QueryParam("petName")
	@ApiParam(name = "petName", value = "Search by pet name", required = false)
	private String petName;

	@QueryParam("dietNumber")
	@ApiParam(name = "dietNumber", value = "Search by diet number", required = false)
	private String dietNumber;

	@QueryParam("dietName")
	@ApiParam(name = "dietName", value = "Search by diet name", required = false)
	private String dietName;

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

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getDietNumber() {
		return dietNumber;
	}

	public void setDietNumber(String dietNumber) {
		this.dietNumber = dietNumber;
	}

	public String getDietName() {
		return dietName;
	}

	public void setDietName(String dietName) {
		this.dietName = dietName;
	}

}
