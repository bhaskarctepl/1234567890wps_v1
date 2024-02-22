package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.PetLegLength;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetLegLengthResponse {

	private List<PetLegLength> legLengthHistory;

	public List<PetLegLength> getLegLengthHistory() {
		return legLengthHistory;
	}

	public void setLegLengthHistory(List<PetLegLength> legLengthHistory) {
		this.legLengthHistory = legLengthHistory;
	}

}
