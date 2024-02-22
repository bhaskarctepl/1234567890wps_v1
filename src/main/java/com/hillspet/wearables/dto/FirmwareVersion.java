package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FirmwareVersion extends BaseDTO {

	private Integer firmwareVersionId;
	private String firmwareVersionNumber;
	private String assetType;
	private String model;
	private Integer assetFrimwareVersionId;

	
	public Integer getAssetFrimwareVersionId() {
		return assetFrimwareVersionId;
	}

	public void setAssetFrimwareVersionId(Integer assetFrimwareVersionId) {
		this.assetFrimwareVersionId = assetFrimwareVersionId;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getFirmwareVersionId() {
		return firmwareVersionId;
	}

	public void setFirmwareVersionId(Integer firmwareVersionId) {
		this.firmwareVersionId = firmwareVersionId;
	}

	public String getFirmwareVersionNumber() {
		return firmwareVersionNumber;
	}

	public void setFirmwareVersionNumber(String firmwareVersionNumber) {
		this.firmwareVersionNumber = firmwareVersionNumber;
	}

}
