package com.hillspet.wearables.response;

import java.util.List;

import com.hillspet.wearables.dto.MobileAppFeedbackPage;

import io.swagger.annotations.ApiModelProperty;


public class MobileAppFeedbackPageResponse {
	private List<MobileAppFeedbackPage> MobileAppFeedbackPages;
	@ApiModelProperty(value = "List of details for Mobile App Feedback Pages")
	public List<MobileAppFeedbackPage> getMobileAppFeedbackPages() {
		return MobileAppFeedbackPages;
	}

	public void setMobileAppFeedbackPages(List<MobileAppFeedbackPage> mobileAppFeedbackPages) {
		MobileAppFeedbackPages = mobileAppFeedbackPages;
	}
}
