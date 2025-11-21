package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class AssetParam extends BaseFilter {

	@ApiParam(name = "studyId", value = "Search based on study")
	@QueryParam("studyId")
	private String studyId;

	@ApiParam(name = "status", value = "Search based on status ")
	@QueryParam("status")
	private String status;

	@ApiParam(name = "ssId", value = "Search based on ssId ")
	@QueryParam("ssId")
	private String ssId;

	@ApiParam(name = "query", value = "Search based on  query")
	@QueryParam("query")
	private String query;

	@ApiParam(name = "assetNumber", value = "Search based on assetNumber ")
	@QueryParam("assetNumber")
	private String assetNumber;

	@ApiParam(name = "boxNumber", value = "Search based on boxNumber ")
	@QueryParam("boxNumber")
	private String boxNumber;

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSsId() {
		return ssId;
	}

	public void setSsId(String ssId) {
		this.ssId = ssId;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getAssetNumber() {
		return assetNumber;
	}

	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}

	public String getBoxNumber() {
		return boxNumber;
	}

	public void setBoxNumber(String boxNumber) {
		this.boxNumber = boxNumber;
	}

}
