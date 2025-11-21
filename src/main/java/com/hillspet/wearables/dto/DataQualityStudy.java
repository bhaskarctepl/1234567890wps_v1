package com.hillspet.wearables.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataQualityStudy {
	
	private String studyName;
	private List<String> chartLabels;
	private List<DataQualityStudyChart> chartData;
	private String totalCount;
	private String currentCount;
	private String chartMidTitle;
	
	public String getStudyName() {
		return studyName;
	}
	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}
	public List<String> getChartLabels() {
		return chartLabels;
	}
	public void setChartLabels(List<String> chartLabels) {
		this.chartLabels = chartLabels;
	}
	public List<DataQualityStudyChart> getChartData() {
		return chartData;
	}
	public void setChartData(List<DataQualityStudyChart> chartData) {
		this.chartData = chartData;
	}
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public String getCurrentCount() {
		return currentCount;
	}
	public void setCurrentCount(String currentCount) {
		this.currentCount = currentCount;
	}
	public String getChartMidTitle() {
		return chartMidTitle;
	}
	public void setChartMidTitle(String chartMidTitle) {
		this.chartMidTitle = chartMidTitle;
	}
	
	
}



