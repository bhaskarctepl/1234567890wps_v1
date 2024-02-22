package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.AssetHistory;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AssetHistoryResponse {
	private List<AssetHistory> assetHistory;

	public List<AssetHistory> getAssetHistory() {
		return assetHistory;
	}

	public void setAssetHistory(List<AssetHistory> assetHistory) {
		this.assetHistory = assetHistory;
	}
}
