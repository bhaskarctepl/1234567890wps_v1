package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.PointTrackerReport;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PointTrackerReportResponse extends BaseResultCollection {

	private List<PointTrackerReport> pointTrackerReportList;

	@JsonProperty("rows")
	@ApiModelProperty(value = "Point Tracker Report Details")
	public List<PointTrackerReport> getPointTrackerReportList() {
		return pointTrackerReportList;
	}

	public void setPointTrackerReportList(List<PointTrackerReport> pointTrackerReportList) {
		this.pointTrackerReportList = pointTrackerReportList;
	}

}
