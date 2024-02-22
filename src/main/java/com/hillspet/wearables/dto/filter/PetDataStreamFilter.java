package com.hillspet.wearables.dto.filter;

import java.util.List;

import javax.ws.rs.QueryParam;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Get Primary Pet & Duplicate Pets Data Streams", value = "PetDataStreamFilter")
@JsonInclude(value = Include.NON_NULL)
public class PetDataStreamFilter extends BaseFilter {

	public PetDataStreamFilter() {
	}

	@ApiModelProperty(name = "primaryPetId", required = true)
	@QueryParam("primaryPetId")
	private Integer primaryPetId;

	@ApiModelProperty(name = "duplicatePetIds", required = true)
	@QueryParam("duplicatePetIds")
	private String duplicatePetIds;

	public Integer getPrimaryPetId() {
		return primaryPetId;
	}

	public void setPrimaryPetId(Integer primaryPetId) {
		this.primaryPetId = primaryPetId;
	}

	public String getDuplicatePetIds() {
		return duplicatePetIds;
	}

	public void setDuplicatePetIds(String duplicatePetIds) {
		this.duplicatePetIds = duplicatePetIds;
	}
	
	

	
}
