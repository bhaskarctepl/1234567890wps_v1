package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.PetIBWHistoryDTO;
import com.hillspet.wearables.dto.PetWeightChartDTO;
import com.hillspet.wearables.dto.PetWeightHistoryDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetWeightHistoryResponse extends BaseResultCollection {
	private List<PetWeightHistoryDTO> weightList;
	private List<PetIBWHistoryDTO> ibwList;
	private List<PetWeightChartDTO> weightChartList;

	@ApiModelProperty(value = "Get Pet Weight details of particular pet id.")
	public List<PetWeightHistoryDTO> getWeightList() {
		return weightList;
	}

	public void setWeightList(List<PetWeightHistoryDTO> weightList) {
		this.weightList = weightList;
	}

	@ApiModelProperty(value = "Get Pet IBW Weight details of particular pet id.")
	public List<PetIBWHistoryDTO> getIbwList() {
		return ibwList;
	}

	public void setIbwList(List<PetIBWHistoryDTO> ibwList) {
		this.ibwList = ibwList;
	}

	public List<PetWeightChartDTO> getWeightChartList() {
		return weightChartList;
	}

	public void setWeightChartList(List<PetWeightChartDTO> weightChartList) {
		this.weightChartList = weightChartList;
	}

}
