package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TotalAssetsbyStatusListDTO extends BaseDTO{

	private Integer deviceCount;
	private Integer statusId;
	private String status;
	private Integer totalDeviceCount;
	public Integer getTotalDeviceCount() {
		return totalDeviceCount;
	}
	public void setTotalDeviceCount(Integer totalDeviceCount) {
		this.totalDeviceCount = totalDeviceCount;
	}
	public Integer getDeviceCount() {
		return deviceCount;
	}
	public void setDeviceCount(Integer deviceCount) {
		this.deviceCount = deviceCount;
	}
	public Integer getStatusId() {
		return statusId;
	}
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	;
}
