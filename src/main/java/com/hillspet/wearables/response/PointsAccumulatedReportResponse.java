package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.PointsAccumulatedReport;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PointsAccumulatedReportResponse extends BaseResultCollection {

	private List<PointsAccumulatedReport> pointsAccumulatedReports;

	@JsonProperty("rows")
	@ApiModelProperty(value = "Point Tracker Report Details")
	public List<PointsAccumulatedReport> getPointsAccumulatedReports() {
		return pointsAccumulatedReports;
	}

	public void setPointsAccumulatedReports(List<PointsAccumulatedReport> pointsAccumulatedReports) {
		this.pointsAccumulatedReports = pointsAccumulatedReports;
	}

}
