package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class AssetFirmwareVersionsFilter extends BaseFilter {

	@ApiParam(name = "assetType", value = "Search based on asset type drop down")
	@QueryParam("assetType")
	private String assetType;

	@ApiParam(name = "assetModel", value = "Search based on asset model drop down")
	@QueryParam("assetModel")
	private String assetModel;

	public AssetFirmwareVersionsFilter() {

	}

	public AssetFirmwareVersionsFilter(String assetType, String assetModel) {
		super();
		this.assetType = assetType;
		this.assetModel = assetModel;
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

}
