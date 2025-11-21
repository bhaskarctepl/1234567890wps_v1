package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.IsdCode;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IsdCodesResponse {

	private List<IsdCode> isdCodes;

	public List<IsdCode> getIsdCodes() {
		return isdCodes;
	}

	public void setIsdCodes(List<IsdCode> isdCodes) {
		this.isdCodes = isdCodes;
	}

}
