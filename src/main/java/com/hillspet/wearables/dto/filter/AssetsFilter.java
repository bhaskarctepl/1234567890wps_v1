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

	@ApiParam(name = "assetModel", value = "Search based on asset model")
	@QueryParam("assetModel")
	private String assetModel;

	@ApiParam(name = "assetNumber", value = "Search based on asset number")
	@QueryParam("assetNumber")
	private String assetNumber;

	@ApiParam(name = "assetLocation", value = "Search based on asset location")
	@QueryParam("assetLocation")
	private String assetLocation;

	@ApiParam(name = "isWhiteListed", value = "Search based on isWhiteListed 1 or 0 ")
	@QueryParam("isWhiteListed")
	private String isWhiteListed;

	@ApiParam(name = "boxNumber", value = "Search based on Box Number ")
	@QueryParam("boxNumber")
	private String boxNumber;

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

	public String getAssetModel() {
		return assetModel;
	}

	public void setAssetModel(String assetModel) {
		this.assetModel = assetModel;
	}

	public String getAssetNumber() {
		return assetNumber;
	}

	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}

	public String getAssetLocation() {
		return assetLocation;
	}

	public void setAssetLocation(String assetLocation) {
		this.assetLocation = assetLocation;
	}

	public String getIsWhiteListed() {
		return isWhiteListed;
	}

	public void setIsWhiteListed(String isWhiteListed) {
		this.isWhiteListed = isWhiteListed;
	}

	public String getBoxNumber() {
		return boxNumber;
	}

	public void setBoxNumber(String boxNumber) {
		this.boxNumber = boxNumber;
	}

}
