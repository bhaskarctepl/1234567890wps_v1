package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.Asset;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetResponse extends BaseResultCollection {

	private List<Asset> assetList;
	private List<Asset> unConfiguredAssetList;

	@JsonProperty("rows")
	@ApiModelProperty(value = "Assets List")
	public List<Asset> getAssetList() {
		return assetList;
	}

	public void setAssetList(List<Asset> assetList) {
		this.assetList = assetList;
	}

	public List<Asset> getUnConfiguredAssetList() {
		return unConfiguredAssetList;
	}

	public void setUnConfiguredAssetList(List<Asset> unConfiguredAssetList) {
		this.unConfiguredAssetList = unConfiguredAssetList;
	}

}
