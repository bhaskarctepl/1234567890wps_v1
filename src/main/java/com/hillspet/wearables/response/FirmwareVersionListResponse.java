package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.FirmwareVersion;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FirmwareVersionListResponse extends BaseResultCollection {

	private List<FirmwareVersion> firmwareVersions;

	private List<FirmwareVersion> firmwareVersionList;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for all firmware versions that matches the search criteria")
	public List<FirmwareVersion> getFirmwareVersionList() {
		return firmwareVersionList;
	}

	public void setFirmwareVersionList(List<FirmwareVersion> firmwareVersionList) {
		this.firmwareVersionList = firmwareVersionList;
	}

	@ApiModelProperty(value = "Get Firmware Version List")
	public List<FirmwareVersion> getFirmwareVersions() {
		return firmwareVersions;
	}

	public void setFirmwareVersions(List<FirmwareVersion> firmwareVersions) {
		this.firmwareVersions = firmwareVersions;
	}

}
