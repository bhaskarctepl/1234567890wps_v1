package com.hillspet.wearables.dto;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class AssetByStudyWidgetFilter {
	
	 @ApiParam(name = "modelType", value = "Assert Type")
	    @QueryParam("modelType")
	    private String modelType;

	    @ApiParam(name = "assetType", value = "Asset Type")
	    @QueryParam("assetType")
	    private String assetType;
	    
	    @ApiParam(name = "study", value = "study Id")
	    @QueryParam("study")
	    private int study;

		public int getStudy() {
			return study;
		}

		public void setStudy(int study) {
			this.study = study;
		}

		public String getModelType() {
			return modelType;
		}

		public void setModelType(String modelType) {
			this.modelType = modelType;
		}

		public String getAssetType() {
			return assetType;
		}

		public void setAssetType(String assetType) {
			this.assetType = assetType;
		}
	    
	    

}
