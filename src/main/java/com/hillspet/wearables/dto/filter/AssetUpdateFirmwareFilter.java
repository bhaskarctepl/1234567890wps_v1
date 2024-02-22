package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class AssetUpdateFirmwareFilter extends BaseFilter {

	@ApiParam(name = "assetType", value = "Search based on asset type drop down")
	@QueryParam("assetType")
	private String assetType;

	@ApiParam(name = "assetModel", value = "Search based on asset model drop down")
	@QueryParam("assetModel")
	private String assetModel;
	
	@ApiParam(name = "assetLocation", value = "Search based on asset location drop down")
	@QueryParam("assetLocation")
	private String assetLocation;

	public AssetUpdateFirmwareFilter() {

	}

	public AssetUpdateFirmwareFilter(String assetType, String assetModel, String assetLocation) {
		super();
		this.assetType = assetType;
		this.assetModel = assetModel;
		this.assetLocation = assetLocation;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getAssetModel() {
		return assetModel;
	}

	public void setAssetModel(String assetModel) {
		this.assetModel = assetModel;
	}

	public String getAssetLocation() {
		return assetLocation;
	}

	public void setAssetLocation(String assetLocation) {
		this.assetLocation = assetLocation;
	}
}
