package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.PetDataExtractConfigListDTO;
import com.hillspet.wearables.dto.PetListDTO;

import io.swagger.annotations.ApiModelProperty;

public class PetDataExtractListResponse extends BaseResultCollection {

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for Pets on search criteria")
	private List<PetListDTO> petsList;

	private List<PetDataExtractConfigListDTO> petDataExtractConfigList;

	public List<PetListDTO> getPetsList() {
		return petsList;
	}

	public void setPetsList(List<PetListDTO> petsList) {
		this.petsList = petsList;

	}

	public List<PetDataExtractConfigListDTO> getPetDataExtractConfigList() {
		return petDataExtractConfigList;
	}

	public void setPetDataExtractConfigList(List<PetDataExtractConfigListDTO> petDataExtractConfigList) {
		this.petDataExtractConfigList = petDataExtractConfigList;
	}

}
