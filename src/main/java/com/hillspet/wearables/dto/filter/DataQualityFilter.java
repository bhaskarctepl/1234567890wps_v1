package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class DataQualityFilter extends BaseFilter {
	@ApiParam(name = "alertType", value = "Search based on Alerty Type drop down")
	@QueryParam("alertTypeId")
	private String alertTypeId;
	@QueryParam("deviceNumber")
	private String deviceNumber;
	
	private String eventDate;
	private String fromDate;
	private String toDate;
	@QueryParam("startDate")
	private String startDate;
	@QueryParam("endDate")
	private String endDate;
	
	@QueryParam("duration")
	private String duration;
	
	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	@QueryParam("priority")
	private String priority;
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getAlertTypeId() {
		return alertTypeId;
	}

	public void setAlertTypeId(String alertTypeId) {
		this.alertTypeId = alertTypeId;
	}

}
