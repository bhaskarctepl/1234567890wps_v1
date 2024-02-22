package com.hillspet.wearables.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contains all information that needed to create customer", value = "UserRequest")
@JsonInclude(value = Include.NON_NULL)
public class PointTrackerMetricRequest {

	@ApiModelProperty(value = "metricId", required = true)
	private Integer metricId;

	@ApiModelProperty(value = "points", required = true)
	private Integer points;

	private String startDate;
	private String endDate;

	public Integer getMetricId() {
		return metricId;
	}

	public void setMetricId(Integer metricId) {
		this.metricId = metricId;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

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

	@Override
	public String toString() {
		return metricId + "#" + points + "#" + startDate + "#" + endDate;
	}

}
