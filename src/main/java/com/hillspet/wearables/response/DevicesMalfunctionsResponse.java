package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.DevicesMalfunctionsReport;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DevicesMalfunctionsResponse extends BaseResultCollection {

private List<DevicesMalfunctionsReport> devicesMalfunctionsReportList;
@JsonProperty("rows")
@ApiModelProperty(value = "List of details for device details list search criteria")
public List<DevicesMalfunctionsReport> getDevicesMalfunctionsReportList() {
	return devicesMalfunctionsReportList;
}

public void setDevicesMalfunctionsReportList(List<DevicesMalfunctionsReport> devicesMalfunctionsReportList) {
	this.devicesMalfunctionsReportList = devicesMalfunctionsReportList;
}
	
	
}
