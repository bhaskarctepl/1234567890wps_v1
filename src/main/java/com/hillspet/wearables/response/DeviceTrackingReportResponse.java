package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.DeviceTrackingReport;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceTrackingReportResponse extends BaseResultCollection {
	
	private List<DeviceTrackingReport> deviceTrackingReportList;
	
	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for device details list search criteria")
	
	public List<DeviceTrackingReport> getDeviceTrackingReport() {
		return deviceTrackingReportList;
	}

	public void setDeviceTrackingReport(List<DeviceTrackingReport> deviceTrackingReportList) {
		this.deviceTrackingReportList = deviceTrackingReportList;
	}

	
}
