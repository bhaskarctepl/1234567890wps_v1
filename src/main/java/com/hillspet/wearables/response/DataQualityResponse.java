package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.DataQuality;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataQualityResponse extends BaseResultCollection {

	private List<DataQuality> alerts;
	

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for all alerts info that matches the search criteria")
	public List<DataQuality> getAlerts() {
		return alerts;
	}

	public void setAlerts(List<DataQuality> alerts) {
		this.alerts = alerts;
	}
}
