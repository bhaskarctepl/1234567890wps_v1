package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SupportMaterialTypeCountDTO extends BaseDTO {
	 
	
	private String materialType;
	private int materialCount;
	private int supportMaterialId;
	private int materialTypeId;
	
	
	
	
	
	public int getMaterialTypeId() {
		return materialTypeId;
	}
	public void setMaterialTypeId(int materialTypeId) {
		this.materialTypeId = materialTypeId;
	}
	public int getSupportMaterialId() {
		return supportMaterialId;
	}
	public void setSupportMaterialId(int supportMaterialId) {
		this.supportMaterialId = supportMaterialId;
	}
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	public int getMaterialCount() {
		return materialCount;
	}
	public void setMaterialCount(int materialCount) {
		this.materialCount = materialCount;
	}
	
	
	 
	
	
}
