package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.DuplicatePetConfigDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DuplicatePetConfigResponse extends BaseResultCollection {
	
	private List<DuplicatePetConfigDTO> petList;
	
	@JsonProperty("rows")
	@ApiModelProperty(value = "List of duplicate config pets list.")
	public List<DuplicatePetConfigDTO> getPetList() {
		return petList;
	}

	public void setPetList(List<DuplicatePetConfigDTO> petList) {
		this.petList = petList;
	}

	
	
}
