package com.hillspet.wearables.response;

import java.util.List;

import com.hillspet.wearables.dto.DeviceLocation;

import io.swagger.annotations.ApiModelProperty;

public class DeviceLocationResponse {
	private List<DeviceLocation> deviceLocations;

	@ApiModelProperty(value = "List of details for Device locations")
	public List<DeviceLocation> getDeviceLocations() {
		return deviceLocations;
	}

	public void setDeviceLocations(List<DeviceLocation> deviceLocations) {
		this.deviceLocations = deviceLocations;
	}

}
