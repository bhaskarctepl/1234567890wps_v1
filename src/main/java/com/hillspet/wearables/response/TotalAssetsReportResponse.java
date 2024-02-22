package com.hillspet.wearables.response;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.TotalAssetsListDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TotalAssetsReportResponse extends BaseResultCollection {
	private List<TotalAssetsListDTO> totalAssetsList;
	@JsonProperty("rows")
	@ApiModelProperty(value = "Get the Total assets based on search criteria")
	public List<TotalAssetsListDTO> getTotalAssetsList() {
		return totalAssetsList;
	}

	public void setTotalAssetsList(List<TotalAssetsListDTO> totalAssetsList) {
		this.totalAssetsList = totalAssetsList;
	}

	
	
}