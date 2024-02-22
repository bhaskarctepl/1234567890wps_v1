package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.AssetType;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AssetTypeResponse {

	

	private List<AssetType> AssetTypeList;

	@ApiModelProperty(value = "List of details for Asset Type ")
	public List<AssetType> getAssetTypeList() {
		return AssetTypeList;
	}

	public void setAssetTypeList(List<AssetType> assetTypeList) {
		AssetTypeList = assetTypeList;
	}
}
