package com.hillspet.wearables.response;

import java.util.List;

import com.hillspet.wearables.dto.Algorithm;

import io.swagger.annotations.ApiModelProperty;

public class AlgorithmResponse {
	private List<Algorithm> Algorithms;

	@ApiModelProperty(value = "List of details for Algorithms details")
	public List<Algorithm> getAlgorithms() {
		return Algorithms;
	}

	public void setAlgorithms(List<Algorithm> algorithms) {
		Algorithms = algorithms;
	}
}
