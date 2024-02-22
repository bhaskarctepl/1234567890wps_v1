package com.hillspet.wearables.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DevicesbyStudyReport extends BaseDTO {

	private String studyName;
	private int devicesbyStudy;
	private int totalDevices;
	private List<AssetStatusCountByStudy> statusNameCount;

	public List<AssetStatusCountByStudy> getStatusNameCount() {
		return statusNameCount;
	}

	public void setStatusNameCount(List<AssetStatusCountByStudy> statusNameCount) {
		this.statusNameCount = statusNameCount;
	}

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

	public int getDevicesbyStudy() {
		return devicesbyStudy;
	}

	public void setDevicesbyStudy(int devicesbyStudy) {
		this.devicesbyStudy = devicesbyStudy;
	}

	public int getTotalDevices() {
		return totalDevices;
	}

	public void setTotalDevices(int totalDevices) {
		this.totalDevices = totalDevices;
	}

}
