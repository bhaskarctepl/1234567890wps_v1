package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DietDTO {
	private Integer dietId;
	private String dietNumber;
	private String dietName;
	private String dietDescription;
	private String brand;
	private String subBrand;
	private String companyName;
	private Double dietDensityKcalByKg;
	private Double dietDensityKcalByCup;
	private String category;
	private Integer productTypeId;
	private String productType;
	private String recipeType;
	private String materialNumber;
	private String pdmUom;
	private Double pdmDensityValue;
	private Integer isActive;
	private Integer isEditable;
	private Integer isDeleted;
	private Integer datasourceId;
	private Integer userId;
	private String createdDate;
	private String modifiedDate;

	public Integer getDietId() {
		return dietId;
	}

	public void setDietId(Integer dietId) {
		this.dietId = dietId;
	}

	public String getDietNumber() {
		return dietNumber;
	}

	public void setDietNumber(String dietNumber) {
		this.dietNumber = dietNumber;
	}

	public String getDietName() {
		return dietName;
	}

	public void setDietName(String dietName) {
		this.dietName = dietName;
	}

	public String getDietDescription() {
		return dietDescription;
	}

	public void setDietDescription(String dietDescription) {
		this.dietDescription = dietDescription;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getSubBrand() {
		return subBrand;
	}

	public void setSubBrand(String subBrand) {
		this.subBrand = subBrand;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Double getDietDensityKcalByKg() {
		return dietDensityKcalByKg;
	}

	public void setDietDensityKcalByKg(Double dietDensityKcalByKg) {
		this.dietDensityKcalByKg = dietDensityKcalByKg;
	}

	public Double getDietDensityKcalByCup() {
		return dietDensityKcalByCup;
	}

	public void setDietDensityKcalByCup(Double dietDensityKcalByCup) {
		this.dietDensityKcalByCup = dietDensityKcalByCup;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}
	
	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getRecipeType() {
		return recipeType;
	}

	public void setRecipeType(String recipeType) {
		this.recipeType = recipeType;
	}

	public String getMaterialNumber() {
		return materialNumber;
	}

	public void setMaterialNumber(String materialNumber) {
		this.materialNumber = materialNumber;
	}

	public String getPdmUom() {
		return pdmUom;
	}

	public void setPdmUom(String pdmUom) {
		this.pdmUom = pdmUom;
	}

	public Double getPdmDensityValue() {
		return pdmDensityValue;
	}

	public void setPdmDensityValue(Double pdmDensityValue) {
		this.pdmDensityValue = pdmDensityValue;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}
	
	public Integer getIsEditable() {
		return isEditable;
	}

	public void setIsEditable(Integer isEditable) {
		this.isEditable = isEditable;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getDatasourceId() {
		return datasourceId;
	}

	public void setDatasourceId(Integer datasourceId) {
		this.datasourceId = datasourceId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
}
