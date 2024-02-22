package com.hillspet.wearables.response;

import java.util.List;

import com.hillspet.wearables.dto.DeviceStatus;

import io.swagger.annotations.ApiModelProperty;

public class DeviceStatusResponse {
	private List<DeviceStatus> deviceStatuses;
	@ApiModelProperty(value = "List of details for Device Status")
	public List<DeviceStatus> getDeviceStatuses() {
		return deviceStatuses;
	}

	public void setDeviceStatuses(List<DeviceStatus> deviceStatuses) {
		this.deviceStatuses = deviceStatuses;
	}
}
