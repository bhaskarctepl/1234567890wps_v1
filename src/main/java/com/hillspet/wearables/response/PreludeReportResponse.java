package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.PreludeReport;

import io.swagger.annotations.ApiModelProperty;

public class PreludeReportResponse extends BaseResultCollection{

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for prelude report")
	private List<PreludeReport> preludeReportList;

	public List<PreludeReport> getPreludeReportList() {
		return preludeReportList;
	}

	public void setPreludeReportList(List<PreludeReport> preludeReportList) {
		this.preludeReportList = preludeReportList;
	}
}
