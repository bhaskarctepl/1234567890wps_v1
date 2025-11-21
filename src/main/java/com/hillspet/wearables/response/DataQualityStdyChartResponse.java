package com.hillspet.wearables.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.DataQualityStudyList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataQualityStdyChartResponse  {

	
	private DataQualityStudyList studyChart;

	
	public DataQualityStudyList getStudyChart() {
		return studyChart;
	}

	public void setStudyChart(DataQualityStudyList studyChart) {
		this.studyChart = studyChart;
	}

	
}
