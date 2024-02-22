package com.hillspet.wearables.dto;

public class DeviceFirmwareDetails {
	long deviceFirmwareId;
	long deviceId;
	String deviceNumber;
	String deviceModel;
	String version;

	public long getDeviceFirmwareId() {
		return deviceFirmwareId;
	}

	public void setDeviceFirmwareId(long deviceFirmwareId) {
		this.deviceFirmwareId = deviceFirmwareId;
	}

	public long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
