package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.SupportMaterialTypeCountDTO;

import io.swagger.annotations.ApiModelProperty;

public class SupportMaterialTypeCountResponse extends BaseResultCollection {
	
	@JsonProperty("rows")
	@ApiModelProperty(value = "Get Support Material list")
	private List<SupportMaterialTypeCountDTO> countInfo;

	public List<SupportMaterialTypeCountDTO> getCountInfo() {
		return countInfo;
	}

	public void setCountInfo(List<SupportMaterialTypeCountDTO> countInfo) {
		this.countInfo = countInfo;
	}

	
	

}
