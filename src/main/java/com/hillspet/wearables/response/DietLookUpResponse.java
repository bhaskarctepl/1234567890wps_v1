package com.hillspet.wearables.response;

import java.util.List;

public class DietLookUpResponse {

    private List<String> companyName;
    private List<String> brand;
    private List<String> subBrand;
    private List<String> category;
    private List<String> productType;
    private List<String> recipeType;
    public List<String> getCompanyName() {
        return companyName;
    }

    public void setCompanyName(List<String> companyName) {
        this.companyName = companyName;
    }

    public List<String> getBrand() {
        return brand;
    }

    public void setBrand(List<String> brand) {
        this.brand = brand;
    }

    public List<String> getSubBrand() {
        return subBrand;
    }

    public void setSubBrand(List<String> subBrand) {
        this.subBrand = subBrand;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public List<String> getProductType() {
        return productType;
    }

    public void setProductType(List<String> productType) {
        this.productType = productType;
    }

    public List<String> getRecipeType() {
        return recipeType;
    }

    public void setRecipeType(List<String> recipeType) {
        this.recipeType = recipeType;
    }
}
