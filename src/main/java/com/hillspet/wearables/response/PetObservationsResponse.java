package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.PetObservation;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetObservationsResponse extends BaseResultCollection {

	private List<PetObservation> petObservations;

	@JsonProperty("rows")
	@ApiModelProperty(value = "Get Pet Observations of particular id")
	public List<PetObservation> getPetObservations() {
		return petObservations;
	}

	public void setPetObservations(List<PetObservation> petObservations) {
		this.petObservations = petObservations;
	}

}
