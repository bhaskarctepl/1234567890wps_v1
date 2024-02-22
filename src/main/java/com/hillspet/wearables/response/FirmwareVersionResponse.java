package com.hillspet.wearables.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.FirmwareVersion;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FirmwareVersionResponse {

	private FirmwareVersion firmwareVersion;

	@ApiModelProperty(value = "Get firmware version of particular id")
	public FirmwareVersion getFirmwareVersion() {
		return firmwareVersion;
	}

	public void setFirmwareVersion(FirmwareVersion firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}

}
