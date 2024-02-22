package com.hillspet.wearables.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SupportMaterialDetailsDTO {
	
	private Integer supportMaterialId;
	private Integer supportDetailsId;
	private String assetModel;
	private String assetType;
	private String materialTypeName;
	private Integer materialTypeId;
	private String titleOrQuestion;
	
	private String urlOrAnswer; //it will hold gcp file name or answer (faq)
	private String thumbnailUrl;
	private String uploadedFileName;
	
	private Integer categoryId;
	private String categoryName;
	private Integer subCategoryId;
	private String subCategoryName;
	
	private String size;
	private String duration;
	
	private LocalDate createdDate;
	private LocalDate modifiedDate;
	

	public LocalDate getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDate modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Integer getSupportMaterialId() {
		return supportMaterialId;
	}

	public void setSupportMaterialId(Integer supportMaterialId) {
		this.supportMaterialId = supportMaterialId;
	}

	public Integer getSupportDetailsId() {
		return supportDetailsId;
	}

	public void setSupportDetailsId(Integer supportDetailsId) {
		this.supportDetailsId = supportDetailsId;
	}

	public String getAssetModel() {
		return assetModel;
	}

	public void setAssetModel(String assetModel) {
		this.assetModel = assetModel;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getMaterialTypeName() {
		return materialTypeName;
	}

	public void setMaterialTypeName(String materialTypeName) {
		this.materialTypeName = materialTypeName;
	}

	public Integer getMaterialTypeId() {
		return materialTypeId;
	}

	public void setMaterialTypeId(Integer materialTypeId) {
		this.materialTypeId = materialTypeId;
	}

	public String getTitleOrQuestion() {
		return titleOrQuestion;
	}

	public void setTitleOrQuestion(String titleOrQuestion) {
		this.titleOrQuestion = titleOrQuestion;
	}

	public String getUrlOrAnswer() {
		return urlOrAnswer;
	}

	public void setUrlOrAnswer(String urlOrAnswer) {
		this.urlOrAnswer = urlOrAnswer;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getUploadedFileName() {
		return uploadedFileName;
	}

	public void setUploadedFileName(String uploadedFileName) {
		this.uploadedFileName = uploadedFileName;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Integer subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	
}
