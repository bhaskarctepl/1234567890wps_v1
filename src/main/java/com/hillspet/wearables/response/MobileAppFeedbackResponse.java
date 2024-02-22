package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.MobileAppFeedback;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MobileAppFeedbackResponse extends BaseResultCollection {

	private List<MobileAppFeedback> mobileAppFeedbackList;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for mobile app feeedback list search criteria")
	public List<MobileAppFeedback> getMobileAppFeeback() {
		return mobileAppFeedbackList;
	}

	public void setMobileAppFeeback(List<MobileAppFeedback> mobileAppFeedbackList) {
		this.mobileAppFeedbackList = mobileAppFeedbackList;
	}

}
