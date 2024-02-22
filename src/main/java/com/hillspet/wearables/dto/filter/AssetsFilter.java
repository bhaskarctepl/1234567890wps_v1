package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class AssetsFilter extends BaseFilter {

	@ApiParam(name = "assetType", value = "Search based on asset type drop down")
	@QueryParam("assetType")
	private String assetType;

	@ApiParam(name = "studyId", value = "Search based on study drop down")
	@QueryParam("studyId")
	private String studyId;

	public AssetsFilter() {

	}

	public AssetsFilter(String assetType, String studyId) {
		super();
		this.assetType = assetType;
		this.studyId = studyId;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}
}
