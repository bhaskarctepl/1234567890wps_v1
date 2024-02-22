package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.PetBfiScoreReport;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetBfiScoreReportResponse extends BaseResultCollection {

	private List<PetBfiScoreReport> petBfiScoreReports;

	@JsonProperty("rows")
	@ApiModelProperty(value = "Pet Bfi Score Report Details")
	public List<PetBfiScoreReport> getPetBfiScoreReports() {
		return petBfiScoreReports;
	}

	public void setPetBfiScoreReports(List<PetBfiScoreReport> petBfiScoreReports) {
		this.petBfiScoreReports = petBfiScoreReports;
	}

}
