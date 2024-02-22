package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class SupportMaterialFilter extends BaseFilter{

	@ApiParam(name = "startDate", value = "Start Date is the first date component value of date range")
	@QueryParam("startDate")
	private String startDate;

	@ApiParam(name = "endDate", value = "End Date is the second date component value of date range")
	@QueryParam("endDate")
	private String endDate;
	
	@ApiParam(name = "materialType", value = "Material Type used for dropdown value.")
	@QueryParam("materialType")
	private Integer materialType;
	
	@ApiParam(name = "materialType", value = "Material Type used for dropdown value.")
	@QueryParam("categoryId")
	private String categoryId;
	
	@ApiParam(name = "materialType", value = "Material Type used for dropdown value.")
	@QueryParam("subCategoryId")
	private String subCategoryId;
	
	@ApiParam(name = "assetType", value = "Material Type used for dropdown value.")
	@QueryParam("assetType")
	private String assetType;
	
	@ApiParam(name = "assetModel", value = "Material Type used for dropdown value.")
	@QueryParam("assetModel")
	private String assetModel;
	
	private Integer supportMaterialId;
	
	

	public Integer getSupportMaterialId() {
		return supportMaterialId;
	}

	public void setSupportMaterialId(Integer supportMaterialId) {
		this.supportMaterialId = supportMaterialId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(String subCategoryId) {
		this.subCategoryId = subCategoryId;
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

	public Integer getMaterialType() {
		return materialType;
	}

	public void setMaterialType(Integer materialType) {
		this.materialType = materialType;
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

	
}
