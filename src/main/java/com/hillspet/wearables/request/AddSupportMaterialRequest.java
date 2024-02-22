package com.hillspet.wearables.request;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author radepu
 *
 */
@ApiModel(description = "Contains all information that needed to create support materials", value = "SupportMaterialRequest")
@JsonInclude(value = Include.NON_NULL)
public class AddSupportMaterialRequest {
	
	@ApiModelProperty(value = "materialTypeId", required = true)
	private String materialTypeId;

	@ApiModelProperty(value = "categoryId", required = true)
	private String categoryId;
	
	@ApiModelProperty(value = "documentType", required = false)
	private String subCategoryId;
	
	@ApiModelProperty(value = "assetType", required = false)
	private String assetType;
	
	@ApiModelProperty(value = "assetModel", required = false)
	private String assetModel;
	
	@ApiModelProperty(value = "assetModel", required = false)
	private String title;
	
	@ApiModelProperty(value = "uploadedFileNames", required = false)
	private List<FileUploadRequest> uploadedFileNames;
	
	@ApiModelProperty(value = "faqList", required = false)
	private List<FAQRequest> faqList;
	
	private Integer userId;
	
	@ApiModelProperty(value = "supportMaterialId", required = false)
	private Integer supportMaterialId;
	

	public Integer getSupportMaterialId() {
		return supportMaterialId;
	}

	public void setSupportMaterialId(Integer supportMaterialId) {
		this.supportMaterialId = supportMaterialId;
	}

	public String getMaterialTypeId() {
		return materialTypeId;
	}

	public void setMaterialTypeId(String materialTypeId) {
		this.materialTypeId = materialTypeId;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<FileUploadRequest> getUploadedFileNames() {
		return uploadedFileNames;
	}

	public void setUploadedFileNames(List<FileUploadRequest> uploadedFileNames) {
		this.uploadedFileNames = uploadedFileNames;
	}

	public List<FAQRequest> getFaqList() {
		return faqList;
	}

	public void setFaqList(List<FAQRequest> faqList) {
		this.faqList = faqList;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
	
	
}
