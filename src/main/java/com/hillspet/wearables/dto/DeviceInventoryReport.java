package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceInventoryReport extends BaseDTO {

	private String deviceType;
	private String deviceModel;
	private String totalDevices;
	private String availableDevices;
	private String malfunctionedDevices;
	private String inUseDevices;

	public String getInUseDevices() {
		return inUseDevices;
	}

	public void setInUseDevices(String inUseDevices) {
		this.inUseDevices = inUseDevices;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getTotalDevices() {
		return totalDevices;
	}

	public void setTotalDevices(String totalDevices) {
		this.totalDevices = totalDevices;
	}

	public String getAvailableDevices() {
		return availableDevices;
	}

	public void setAvailableDevices(String availableDevices) {
		this.availableDevices = availableDevices;
	}

	public String getMalfunctionedDevices() {
		return malfunctionedDevices;
	}

	public void setMalfunctionedDevices(String malfunctionedDevices) {
		this.malfunctionedDevices = malfunctionedDevices;
	}

}
