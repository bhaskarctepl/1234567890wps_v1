package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.DevicesbyStudyReport;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DevicesbyStudyResponse extends BaseResultCollection {
	private List<DevicesbyStudyReport> devicesbyStudyReportList;
	
	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for device details list search criteria")
	
	public List<DevicesbyStudyReport> getDevicesbyStudyReportList() {
		return devicesbyStudyReportList;
	}

	public void setDevicesbyStudyReportList(List<DevicesbyStudyReport> devicesbyStudyReportList) {
		this.devicesbyStudyReportList = devicesbyStudyReportList;
	}
}
