package com.hillspet.wearables.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.DataQualityDeviceSummary;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataQualityDeviceSummaryResponse  {

	
	private DataQualityDeviceSummary deviceChart;

	public DataQualityDeviceSummary getDeviceChart() {
		return deviceChart;
	}

	public void setDeviceChart(DataQualityDeviceSummary deviceChart) {
		this.deviceChart = deviceChart;
	}

	


	
}
