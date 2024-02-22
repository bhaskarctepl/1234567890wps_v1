package com.hillspet.wearables.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Contains all the information that needed to create data stream", value = "DataStreamRequest")
@JsonInclude(value = Include.NON_NULL)
public class DataStreamGroupRequest {
	
	private List<DuplicatePetRequest> duplicatePetsList;
	private Integer userId;
	
	public List<DuplicatePetRequest> getDuplicatePetsList() {
		return duplicatePetsList;
	}
	public void setDuplicatePetsList(List<DuplicatePetRequest> duplicatePetsList) {
		this.duplicatePetsList = duplicatePetsList;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
	

	 
	
}
