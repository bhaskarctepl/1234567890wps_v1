package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class PointTrackerFilter extends BaseFilter {
	@ApiParam(name = "startDate", value = "Start Date is the first date component value of date range")
	@QueryParam("startDate")
	private String startDate;

	@ApiParam(name = "endDate", value = "End Date is the second date component value of date range")
	@QueryParam("endDate")
	private String endDate;

	@ApiParam(name = "studyId", value = "Study type filters holds the study type study name.")
	@QueryParam("studyId")
	private String studyId;

	@ApiParam(name = "assetType", value = "Search based on asset type drop down")
	@QueryParam("assetType")
	private String assetType;

	@ApiParam(name = "assetModel", value = "Search based on asset model drop down")
	@QueryParam("assetModel")
	private String assetModel;

	public PointTrackerFilter() {

	}

	public PointTrackerFilter(String startDate, String endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
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
