package com.hillspet.wearables.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Contains all information that needed to Get Pet Studies", value = "PetRequest")
@JsonInclude(value = Include.NON_NULL)
public class PetStudyDTO extends BaseDTO {
	
	private Integer petId;
	private Integer studyId;
	private String studyName;
	private LocalDate startDate;
	private LocalDate endDate;
	private boolean isStudyActive;
	private Integer petStudyId;
	
	private Integer userId;
	private String pricipleInvestigator;
	
	private boolean isAssigned;
	private boolean isExternalStudy;
	
	/*private LocalDate assignedDate;
	private LocalDate unAssignedDate;
	private boolean isAssetAssigned;*/
	
	

	public Integer getPetId() {
		return petId;
	}

	public boolean isExternalStudy() {
		return isExternalStudy;
	}

	public void setExternalStudy(boolean isExternalStudy) {
		this.isExternalStudy = isExternalStudy;
	}

	public Integer getPetStudyId() {
		return petStudyId;
	}

	public void setPetStudyId(Integer petStudyId) {
		this.petStudyId = petStudyId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public boolean isStudyActive() {
		return isStudyActive;
	}

	public void setStudyActive(boolean isStudyActive) {
		this.isStudyActive = isStudyActive;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPricipleInvestigator() {
		return pricipleInvestigator;
	}

	public void setPricipleInvestigator(String pricipleInvestigator) {
		this.pricipleInvestigator = pricipleInvestigator;
	}

	public boolean isAssigned() {
		return isAssigned;
	}

	public void setAssigned(boolean isAssigned) {
		this.isAssigned = isAssigned;
	}

	
	
 
 
	
}
