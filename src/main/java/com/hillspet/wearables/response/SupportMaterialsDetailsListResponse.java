package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.SupportMaterialDetailsDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SupportMaterialsDetailsListResponse extends BaseResultCollection {
	
	@JsonProperty("rows")
	@ApiModelProperty(value = "Get Support Material list")
	private List<SupportMaterialDetailsDTO> supportMateriaDetailList;

	public List<SupportMaterialDetailsDTO> getSupportMateriaDetailList() {
		return supportMateriaDetailList;
	}

	public void setSupportMateriaDetailList(List<SupportMaterialDetailsDTO> supportMateriaDetailList) {
		this.supportMateriaDetailList = supportMateriaDetailList;
	}

 
	 
	
}
