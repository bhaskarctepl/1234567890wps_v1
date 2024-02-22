/**
 * 
 */
package com.hillspet.wearables.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contains all the information that needed to create Pet", value = "PetRequest")
@JsonInclude(value = Include.NON_NULL)
public class BulkAssetUploadRequest {

	@ApiModelProperty(value = "stagingId", required = true, example = "14545")
	private String stagingId;
	
	private Integer userId;
	

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getStagingId() {
		return stagingId;
	}

	public void setStagingId(String stagingId) {
		this.stagingId = stagingId;
	}	
}
