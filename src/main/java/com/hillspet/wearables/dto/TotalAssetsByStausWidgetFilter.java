package com.hillspet.wearables.dto;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class TotalAssetsByStausWidgetFilter {

	
	  @ApiParam(name = "locationId", value = "study Id")
	    @QueryParam("locationId")
	    private Integer locationId;

	    @ApiParam(name = "modelType", value = "Assert Type")
	    @QueryParam("modelType")
	    private String modelType;

	    @ApiParam(name = "assertModel", value = "Assert Model")
	    @QueryParam("assertModel")
	    private String assertModel;

		public Integer getLocationId() {
			return locationId;
		}

		public void setLocationId(Integer locationId) {
			this.locationId = locationId;
		}

		public String getModelType() {
			return modelType;
		}

		public void setModelType(String modelType) {
			this.modelType = modelType;
		}

		public String getAssertModel() {
			return assertModel;
		}

		public void setAssertModel(String assertModel) {
			this.assertModel = assertModel;
		}
	    
	    
}
