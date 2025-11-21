package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataQualityStackedAlertCnt {

	private String alertTypeId;
	private String alertCount;
	private String alertType;
	private String colorCode;
	
	
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	public String getAlertTypeId() {
		return alertTypeId;
	}
	public void setAlertTypeId(String alertTypeId) {
		this.alertTypeId = alertTypeId;
	}
	
	public String getAlertType() {
		return alertType;
	}
	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}
	public String getAlertCount() {
		return alertCount;
	}
	public void setAlertCount(String alertCount) {
		this.alertCount = alertCount;
	}
	

}
