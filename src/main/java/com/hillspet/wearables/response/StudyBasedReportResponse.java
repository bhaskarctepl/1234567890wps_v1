package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.StudyBasedReport;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyBasedReportResponse extends BaseResultCollection {
	
	private List<StudyBasedReport> studyBasedReportList;
	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for device details list search criteria")
	
	public List<StudyBasedReport> getStudyBasedReportList() {
		return studyBasedReportList;
	}

	public void setStudyBasedReportList(List<StudyBasedReport> studyBasedReportList) {
		this.studyBasedReportList = studyBasedReportList;
	}
	
	
	
	

	
}