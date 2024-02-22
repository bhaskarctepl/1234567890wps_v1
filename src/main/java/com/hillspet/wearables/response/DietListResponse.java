package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.DietDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DietListResponse extends BaseResultCollection {

	private List<DietDTO> dietList;
	private List<DietDTO> diets;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for Diet list based on search criteria")
	public List<DietDTO> getDietList() {
		return dietList;
	}

	public void setDietList(List<DietDTO> dietList) {
		this.dietList = dietList;
	}

	public List<DietDTO> getDiets() {
		return diets;
	}

	public void setDiets(List<DietDTO> diets) {
		this.diets = diets;
	}
}
