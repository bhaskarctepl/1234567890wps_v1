package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hillspet.wearables.dto.PhaseDays;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhaseDaysResponse {
	private List<PhaseDays> phaseDays;

	public List<PhaseDays> getPhaseDays() {
		return phaseDays;
	}

	public void setPhaseDays(List<PhaseDays> phaseDays) {
		this.phaseDays = phaseDays;
	}
	
	
}
