package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.MobileAppSelfOnboardingDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MobileAppSelfOnboardingResponse extends BaseResultCollection {

	private List<MobileAppSelfOnboardingDTO> mobileAppSelfOnboardingDTOList;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for mobile app feeedback list search criteria")
	public List<MobileAppSelfOnboardingDTO> getMobileAppSelfOnboarding() {
		return mobileAppSelfOnboardingDTOList;
	}

	public void setMobileAppSelfOnboarding(List<MobileAppSelfOnboardingDTO> mobileAppSelfOnboardingDTOList) {
		this.mobileAppSelfOnboardingDTOList = mobileAppSelfOnboardingDTOList;
	}

}
