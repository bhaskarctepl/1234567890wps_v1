package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceResponse {

	private List<String> deviceModels;
	private List<String> deviceTypes;

	@ApiModelProperty(value = "List of details for all device types")
	public List<String> getDeviceTypes() {
		return deviceTypes;
	}

	public void setDeviceTypes(List<String> deviceTypes) {
		this.deviceTypes = deviceTypes;
	}

	@ApiModelProperty(value = "List of details for all device models")
	public List<String> getDeviceModels() {
		return deviceModels;
	}

	public void setDeviceModels(List<String> deviceModels) {
		this.deviceModels = deviceModels;
	}

}
