package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.PetFeedingEnthusiasmScale;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetFeedingEnthusiasmScaleResponse extends BaseResultCollection {

	private List<PetFeedingEnthusiasmScale> petFeedingEnthusiasmScaleDtls;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for Pet Feeding Enthusiasm Scale Dtls based on search criteria")
	public List<PetFeedingEnthusiasmScale> getPetFeedingEnthusiasmScaleDtls() {
		return petFeedingEnthusiasmScaleDtls;
	}

	public void setPetFeedingEnthusiasmScaleDtls(List<PetFeedingEnthusiasmScale> petFeedingEnthusiasmScaleDtls) {
		this.petFeedingEnthusiasmScaleDtls = petFeedingEnthusiasmScaleDtls;
	}

}
