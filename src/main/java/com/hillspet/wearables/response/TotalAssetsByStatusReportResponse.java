package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.TotalAssetsbyStatusListDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TotalAssetsByStatusReportResponse extends BaseResultCollection {

	private List<TotalAssetsbyStatusListDTO> totalAssetsbyStatusList;
	@JsonProperty("rows")
	@ApiModelProperty(value = "Get the Total assets by status based on search criteria")
	public List<TotalAssetsbyStatusListDTO> getTotalAssetsbyStatusList() {
		return totalAssetsbyStatusList;
	}

	public void setTotalAssetsbyStatusList(List<TotalAssetsbyStatusListDTO> totalAssetsbyStatusList) {
		this.totalAssetsbyStatusList = totalAssetsbyStatusList;
	}
	
}
