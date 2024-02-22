package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.Occurance;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OccuranceResponse {

	private List<Occurance> occurances;

	@ApiModelProperty(value = "List of details for Questionnaire Occurances")
	public List<Occurance> getOccurances() {
		return occurances;
	}

	public void setOccurances(List<Occurance> occurances) {
		this.occurances = occurances;
	}

}
