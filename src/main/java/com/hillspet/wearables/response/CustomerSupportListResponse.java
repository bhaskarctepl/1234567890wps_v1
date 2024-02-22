package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.SupportListDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerSupportListResponse extends BaseResultCollection {
	private List<SupportListDTO> supportList;

	@JsonProperty("rows")
	@ApiModelProperty(value = "Get the Support tickets based on search criteria")
	public List<SupportListDTO> getSupportList() {
		return supportList;
	}

	public void setSupportList(List<SupportListDTO> supportList) {
		this.supportList = supportList;
	}
}
