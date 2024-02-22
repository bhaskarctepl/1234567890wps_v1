package com.hillspet.wearables.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hillspet.wearables.dto.PetStudyDevice;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Contains all the information that needed to create Pet", value = "PetRequest")
@JsonInclude(value = Include.NON_NULL)
public class AssociateNewStudyRequest {
	
	public AssociateNewStudyRequest() {
		// TODO Auto-generated constructor stub
	}
	private Integer studyId;
	private String assignedOnDate;
	private Integer petId;
	private Integer userId;
	private String externalPetInfoId;

	
	public String getExternalPetInfoId() {
		return externalPetInfoId;
	}
	public void setExternalPetInfoId(String externalPetInfoId) {
		this.externalPetInfoId = externalPetInfoId;
	}
	public String getAssignedOnDate() {
		return assignedOnDate;
	}
	public void setAssignedOnDate(String assignedOnDate) {
		this.assignedOnDate = assignedOnDate;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getStudyId() {
		return studyId;
	}
	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}
	public Integer getPetId() {
		return petId;
	}
	public void setPetId(Integer petId) {
		this.petId = petId;
	}
	

}
