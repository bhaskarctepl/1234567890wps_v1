package com.hillspet.wearables.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataQualityDeviceSummary {
	
	private int totalCount;
	private List<String> chartLabels;
	private List<Integer> chartData;
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public List<String> getChartLabels() {
		return chartLabels;
	}
	public void setChartLabels(List<String> chartLabels) {
		this.chartLabels = chartLabels;
	}
	public List<Integer> getChartData() {
		return chartData;
	}
	public void setChartData(List<Integer> chartData) {
		this.chartData = chartData;
	}

	
	
}



