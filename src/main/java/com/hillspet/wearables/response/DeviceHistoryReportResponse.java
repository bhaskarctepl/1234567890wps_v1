package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.DeviceHistoryReport;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceHistoryReportResponse extends BaseResultCollection {

	private List<DeviceHistoryReport> deviceHistoryReportList;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for device history report list search criteria")
	public List<DeviceHistoryReport> getDeviceHistoryReport() {
		return deviceHistoryReportList;
	}

	public void setDeviceHistoryReport(List<DeviceHistoryReport> deviceHistoryReportList) {
		this.deviceHistoryReportList = deviceHistoryReportList;
	}

}
