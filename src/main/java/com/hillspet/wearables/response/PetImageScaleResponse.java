package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.PetImageScale;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetImageScaleResponse extends BaseResultCollection {

	private List<PetImageScale> petImageScaleDtls;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for Pet Image Scale Dtls based on search criteria")
	public List<PetImageScale> getPetImageScaleDtls() {
		return petImageScaleDtls;
	}

	public void setPetImageScaleDtls(List<PetImageScale> petImageScaleDtls) {
		this.petImageScaleDtls = petImageScaleDtls;
	}

	
	
	
	

}
