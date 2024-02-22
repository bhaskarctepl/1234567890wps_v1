package com.hillspet.wearables.response;

import java.util.List;

import com.hillspet.wearables.dto.ProductType;

public class ProductTypeListResponse {

	private List<ProductType> productType;

	public List<ProductType> getProductType() {
		return productType;
	}

	public void setProductType(List<ProductType> productType) {
		this.productType = productType;
	}

}
