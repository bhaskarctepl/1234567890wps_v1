package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.PetDataExtractConfigListDTO;

import io.swagger.annotations.ApiModelProperty;

public class PetDataExtractConfigListResponse extends BaseResultCollection{
	
	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for petDataExtractConfigList on search criteria")
	private List<PetDataExtractConfigListDTO> petDataExtractConfigList;
	   
	public List<PetDataExtractConfigListDTO> getPetDataExtractConfigList() {
		return petDataExtractConfigList;
	}

	public void setPetDataExtractConfigList(List<PetDataExtractConfigListDTO> petDataExtractConfigList) {
		this.petDataExtractConfigList = petDataExtractConfigList;
	}

}
