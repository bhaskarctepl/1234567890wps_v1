package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.MobileAppConfig;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MobileAppConfigResponse {

	private List<MobileAppConfig> mobileAppConfigs;

	@ApiModelProperty(value = "List of details for Mobile App Configs")
	public List<MobileAppConfig> getMobileAppConfigs() {
		return mobileAppConfigs;
	}

	public void setMobileAppConfigs(List<MobileAppConfig> mobileAppConfigs) {
		this.mobileAppConfigs = mobileAppConfigs;
	}

}
