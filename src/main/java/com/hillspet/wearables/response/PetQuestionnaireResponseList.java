package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.PetQuestionnaireResponse;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetQuestionnaireResponseList extends BaseResultCollection {

	@JsonProperty("rows")
	@ApiModelProperty(value = "Get the Support tickets based on search criteria")
	private List<PetQuestionnaireResponse> petQuestionnaireResponses;

	public List<PetQuestionnaireResponse> getPetQuestionnaireResponses() {
		return petQuestionnaireResponses;
	}

	public void setPetQuestionnaireResponses(List<PetQuestionnaireResponse> petQuestionnaireResponses) {
		this.petQuestionnaireResponses = petQuestionnaireResponses;
	}

}
