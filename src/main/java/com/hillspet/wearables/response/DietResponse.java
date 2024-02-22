package com.hillspet.wearables.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.DietDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DietResponse {

	private DietDTO dietDto;

	@JsonProperty("diet")
	@ApiModelProperty(value = "List of details of Diet details")
	public DietDTO getDietDto() {
		return dietDto;
	}

	public void setDietDto(DietDTO dietDto) {
		this.dietDto = dietDto;
	}

}
