package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionValidityPeriod {

	private Integer validityPeriodId;
	private String validityDescription;
	private Integer noOfDays;

	public Integer getValidityPeriodId() {
		return validityPeriodId;
	}

	public void setValidityPeriodId(Integer validityPeriodId) {
		this.validityPeriodId = validityPeriodId;
	}

	public String getValidityDescription() {
		return validityDescription;
	}

	public void setValidityDescription(String validityDescription) {
		this.validityDescription = validityDescription;
	}

	public Integer getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(Integer noOfDays) {
		this.noOfDays = noOfDays;
	}

}
