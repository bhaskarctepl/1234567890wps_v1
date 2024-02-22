package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.FirmwareVersion;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FirmwareVersionsResponse extends BaseResultCollection {

	private List<FirmwareVersion> firmwareVersions;

	@ApiModelProperty(value = "List of details for Firmware Versions")
	public List<FirmwareVersion> getFirmwareVersions() {
		return firmwareVersions;
	}

	public void setFirmwareVersions(List<FirmwareVersion> firmwareVersions) {
		this.firmwareVersions = firmwareVersions;
	}

}
