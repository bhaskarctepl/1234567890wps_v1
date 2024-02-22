package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MobileAppConfig {

	private Integer mobileAppConfigId;
	private String mobileAppConfigName;

	public Integer getMobileAppConfigId() {
		return mobileAppConfigId;
	}

	public void setMobileAppConfigId(Integer mobileAppConfigId) {
		this.mobileAppConfigId = mobileAppConfigId;
	}

	public String getMobileAppConfigName() {
		return mobileAppConfigName;
	}

	public void setMobileAppConfigName(String mobileAppConfigName) {
		this.mobileAppConfigName = mobileAppConfigName;
	}

}
