package com.hillspet.wearables.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.DataQualityStackedList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataQualityStackedResponse  {

	
	private DataQualityStackedList studyChart;

	public DataQualityStackedList getStudyChart() {
		return studyChart;
	}

	public void setStudyChart(DataQualityStackedList studyChart) {
		this.studyChart = studyChart;
	}

	
	
	
}
