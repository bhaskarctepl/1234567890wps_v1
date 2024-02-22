package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Occurance extends BaseDTO {

	private Integer occuranceId;
	private String occuranceName;

	public Integer getOccuranceId() {
		return occuranceId;
	}

	public void setOccuranceId(Integer occuranceId) {
		this.occuranceId = occuranceId;
	}

	public String getOccuranceName() {
		return occuranceName;
	}

	public void setOccuranceName(String occuranceName) {
		this.occuranceName = occuranceName;
	}

}
