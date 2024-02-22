package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.AnalyticalReport;

import io.swagger.annotations.ApiModelProperty;

public class AnalyticalReportResponse extends BaseResultCollection{
	
	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for Plans based on search criteria")
	private List<AnalyticalReport> analyticalReportList;
	private String signedUrl;

	public List<AnalyticalReport> getAnalyticalReportList() {
		return analyticalReportList;
	}

	public void setAnalyticalReportList(List<AnalyticalReport> analyticalReportList) {
		this.analyticalReportList = analyticalReportList;
	}	
	
	public String getSignedUrl() {
		return signedUrl;
	}
	
	public void setSignedUrl(String signedUrl) {
		this.signedUrl = signedUrl;
	}
	
}
