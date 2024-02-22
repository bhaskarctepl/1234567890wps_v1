package com.hillspet.wearables.request;

import io.swagger.annotations.ApiModelProperty;

public class ActivityFactorConfig {

	@ApiModelProperty(value = "googleSheetUrl", required = true)
	private String googleSheetUrl;

	@ApiModelProperty(value = "startDate", required = true)
	private String startDate;

	@ApiModelProperty(value = "endDate", required = false)
	private String endDate;

	@ApiModelProperty(value = "hasPrelude", required = true)
	private Integer hasPrelude;

	public String getGoogleSheetUrl() {
		return googleSheetUrl;
	}

	public void setGoogleSheetUrl(String googleSheetUrl) {
		this.googleSheetUrl = googleSheetUrl;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getHasPrelude() {
		return hasPrelude;
	}

	public void setHasPrelude(Integer hasPrelude) {
		this.hasPrelude = hasPrelude;
	}

}
