package com.hillspet.wearables.dto;

public class SensorName {
	private int sensorId;
	private String sensorName;
	private int sensorStatusId;
	private String sensorStatusName;
	private int sensorLocationId;
	private String sensorLocationName;

	private String mfgSerialNumber;
	private String mfgMacAddr;
	private String wifiMacAddr;

	public int getSensorId() {
		return sensorId;
	}

	public void setSensorId(int sensorId) {
		this.sensorId = sensorId;
	}

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	public int getSensorStatusId() {
		return sensorStatusId;
	}

	public void setSensorStatusId(int sensorStatusId) {
		this.sensorStatusId = sensorStatusId;
	}

	public String getSensorStatusName() {
		return sensorStatusName;
	}

	public void setSensorStatusName(String sensorStatusName) {
		this.sensorStatusName = sensorStatusName;
	}

	public int getSensorLocationId() {
		return sensorLocationId;
	}

	public void setSensorLocationId(int sensorLocationId) {
		this.sensorLocationId = sensorLocationId;
	}

	public String getSensorLocationName() {
		return sensorLocationName;
	}

	public void setSensorLocationName(String sensorLocationName) {
		this.sensorLocationName = sensorLocationName;
	}

	public String getMfgSerialNumber() {
		return mfgSerialNumber;
	}

	public void setMfgSerialNumber(String mfgSerialNumber) {
		this.mfgSerialNumber = mfgSerialNumber;
	}

	public String getMfgMacAddr() {
		return mfgMacAddr;
	}

	public void setMfgMacAddr(String mfgMacAddr) {
		this.mfgMacAddr = mfgMacAddr;
	}

	public String getWifiMacAddr() {
		return wifiMacAddr;
	}

	public void setWifiMacAddr(String wifiMacAddr) {
		this.wifiMacAddr = wifiMacAddr;
	}

	@Override
	public String toString() {
		return "SensorName [sensorId=" + sensorId + ", sensorName=" + sensorName + ", sensorStatusId=" + sensorStatusId
				+ ", sensorStatusName=" + sensorStatusName + ", sensorLocationId=" + sensorLocationId
				+ ", sensorLocationName=" + sensorLocationName + ", mfgSerialNumber=" + mfgSerialNumber
				+ ", mfgMacAddr=" + mfgMacAddr + ", wifiMacAddr=" + wifiMacAddr + "]";
	}
}
