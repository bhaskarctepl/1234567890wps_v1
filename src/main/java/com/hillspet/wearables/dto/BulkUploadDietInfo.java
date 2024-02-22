package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BulkUploadDietInfo {
    private Integer uploadId;
    private String dietNo;
    private String dietName;
    private String companyName;
    private String brand;
    private String subBrand;
    private String category;
    private Double calDensityKcalKg;
    private Double calDensityKcalCup;
    private int createdUser;
    private String exceptionMsg;
    private String attachmentName;
    private String materialNumber;
    private String productType;
    private Integer productTypeId;
    private Double pdmDensityValue;
    private String pdmDensityUOM;
    private String dietDescription;
    private LocalDateTime lastModifiedDate;
    private String recipeType;

    public Integer getUploadId() {
        return uploadId;
    }

    public void setUploadId(Integer uploadId) {
        this.uploadId = uploadId;
    }

    public String getDietNo() {
        return dietNo;
    }

    public void setDietNo(String dietNo) {
        this.dietNo = dietNo;
    }

    public String getDietName() {
        return dietName;
    }

    public void setDietName(String dietName) {
        this.dietName = dietName;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getCalDensityKcalKg() {
        return calDensityKcalKg;
    }

    public void setCalDensityKcalKg(Double calDensityKcalKg) {
        this.calDensityKcalKg = calDensityKcalKg;
    }

    public Double getCalDensityKcalCup() {
        return calDensityKcalCup;
    }

    public void setCalDensityKcalCup(Double calDensityKcalCup) {
        this.calDensityKcalCup = calDensityKcalCup;
    }

    public int getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(int createdUser) {
        this.createdUser = createdUser;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getMaterialNumber() {
        return materialNumber;
    }

    public void setMaterialNumber(String materialNumber) {
        this.materialNumber = materialNumber;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Integer getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Integer productTypeId) {
        this.productTypeId = productTypeId;
    }

    public Double getPdmDensityValue() {
        return pdmDensityValue;
    }

    public void setPdmDensityValue(Double pdmDensityValue) {
        this.pdmDensityValue = pdmDensityValue;
    }

    public String getPdmDensityUOM() {
        return pdmDensityUOM;
    }

    public void setPdmDensityUOM(String pdmDensityUOM) {
        this.pdmDensityUOM = pdmDensityUOM;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDietDescription() {
        return dietDescription;
    }

    public void setDietDescription(String dietDescription) {
        this.dietDescription = dietDescription;
    }

    public String getRecipeType() {
        return recipeType;
    }

    public void setRecipeType(String recipeType) {
        this.recipeType = recipeType;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BulkUploadDietInfo.class.getSimpleName() + "[", "]")
                .add("uploadId=" + uploadId)
                .add("dietNo='" + dietNo + "'")
                .add("dietName='" + dietName + "'")
                .add("companyName='" + companyName + "'")
                .add("brand='" + brand + "'")
                .add("subBrand='" + subBrand + "'")
                .add("category='" + category + "'")
                .add("calDensityKcalKg=" + calDensityKcalKg)
                .add("calDensityKcalCup=" + calDensityKcalCup)
                .add("createdUser=" + createdUser)
                .add("exceptionMsg='" + exceptionMsg + "'")
                .add("attachmentName='" + attachmentName + "'")
                .add("materialNumber='" + materialNumber + "'")
                .add("productType='" + productType + "'")
                .add("productTypeId=" + productTypeId)
                .add("pdmDensityValue=" + pdmDensityValue)
                .add("pdmDensityUOM='" + pdmDensityUOM + "'")
                .add("dietDescription='" + dietDescription + "'")
                .add("lastModifiedDate=" + lastModifiedDate)
                .add("recipeType='" + recipeType + "'")
                .toString();
    }
}
