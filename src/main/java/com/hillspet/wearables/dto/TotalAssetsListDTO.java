package com.hillspet.wearables.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TotalAssetsListDTO extends BaseDTO{
	private Integer deviceCount;
	private Integer deviceTotalCount;
	
	private Integer deviceId;
	private String deviceType;
	
	private List ModelAssociatedObject;
	
	
	
	public List getModelAssociatedObject() {
		return ModelAssociatedObject;
	}
	public void setModelAssociatedObject(List modelAssociatedObject) {
		ModelAssociatedObject = modelAssociatedObject;
	}
	public Integer getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public Integer getDeviceCount() {
		return deviceCount;
	}
	public void setDeviceCount(Integer deviceCount) {
		this.deviceCount = deviceCount;
	}
	public Integer getDeviceTotalCount() {
		return deviceTotalCount;
	}
	public void setDeviceTotalCount(Integer deviceTotalCount) {
		this.deviceTotalCount = deviceTotalCount;
	}

}
