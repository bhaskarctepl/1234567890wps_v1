package com.hillspet.wearables.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hillspet.wearables.dto.PetDeviceUnAssignRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contains all the information that needed to Unassign Asset", value = "UnassignAssetRequest")
@JsonInclude(value = Include.NON_NULL)
public class UnassignAssetRequest {
	
	
	private List<PetDeviceUnAssignRequest> petUnAssignAssets;
	
	private int userId;
	
	

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<PetDeviceUnAssignRequest> getPetUnAssignAssets() {
		return petUnAssignAssets;
	}

	public void setPetUnAssignAssets(List<PetDeviceUnAssignRequest> petUnAssignAssets) {
		this.petUnAssignAssets = petUnAssignAssets;
	}
	
}
