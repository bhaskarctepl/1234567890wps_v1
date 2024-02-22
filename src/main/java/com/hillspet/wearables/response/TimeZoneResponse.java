package com.hillspet.wearables.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.GeoCodeAddress;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeZoneResponse {

	private Integer isValidAddress;

	private GeoCodeAddress address;

	public Integer getIsValidAddress() {
		return isValidAddress;
	}

	public void setIsValidAddress(Integer isValidAddress) {
		this.isValidAddress = isValidAddress;
	}

	public GeoCodeAddress getAddress() {
		return address;
	}

	public void setAddress(GeoCodeAddress address) {
		this.address = address;
	}

}
