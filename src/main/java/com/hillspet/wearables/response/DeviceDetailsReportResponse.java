package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.DeviceDetailsReport;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceDetailsReportResponse extends BaseResultCollection {

	private List<DeviceDetailsReport> deviceDetailsReportList;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for device details list search criteria")
	public List<DeviceDetailsReport> getDeviceDetailsReport() {
		return deviceDetailsReportList;
	}

	public void setDeviceDetailsReport(List<DeviceDetailsReport> deviceDetailsReportList) {
		this.deviceDetailsReportList = deviceDetailsReportList;
	}

}

