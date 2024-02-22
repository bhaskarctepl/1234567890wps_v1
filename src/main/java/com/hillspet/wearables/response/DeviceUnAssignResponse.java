package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.DeviceUnAssignReason;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceUnAssignResponse {
	private List<DeviceUnAssignReason> unAssignReason;

	public List<DeviceUnAssignReason> getUnAssignReason() {
		return unAssignReason;
	}

	public void setUnAssignReason(List<DeviceUnAssignReason> unAssignReason) {
		this.unAssignReason = unAssignReason;
	}
	

}
