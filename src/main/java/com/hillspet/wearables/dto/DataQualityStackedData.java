package com.hillspet.wearables.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataQualityStackedData {
	
	private String alertDate;
	private List<DataQualityStackedAlertCnt> alertValue;
	
	public List<DataQualityStackedAlertCnt> getAlertValue() {
		return alertValue;
	}
	public void setAlertValue(List<DataQualityStackedAlertCnt> alertValue) {
		this.alertValue = alertValue;
	}
	public String getAlertDate() {
		return alertDate;
	}
	public void setAlertDate(String alertDate) {
		this.alertDate = alertDate;
	}
	
	
	
	
}



