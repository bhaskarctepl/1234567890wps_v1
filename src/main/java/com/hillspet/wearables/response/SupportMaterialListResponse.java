package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.SupportMaterialDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SupportMaterialListResponse extends BaseResultCollection {

	@JsonProperty("rows")
	@ApiModelProperty(value = "Get Support Material list")
	private List<SupportMaterialDTO> supportMaterialList;

	public List<SupportMaterialDTO> getSupportMaterialList() {
		return supportMaterialList;
	}

	public void setSupportMaterialList(List<SupportMaterialDTO> supportMaterialList) {
		this.supportMaterialList = supportMaterialList;
	}
	
	
}
