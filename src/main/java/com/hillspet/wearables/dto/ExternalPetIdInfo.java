package com.hillspet.wearables.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Contains all information that needed to update Pet External Pet Id info", value = "ExternalPetIdInfo")
@JsonInclude(value = Include.NON_NULL)
public class ExternalPetIdInfo {

	private Integer petId;
	private String studyId;
	private String studyName;
	private LocalDate startDate;
	private LocalDate endDate;
	private boolean isStudyActive;
	private String studyLocation;
	private boolean isPreludeStudy;
	private boolean isExternalStudy;
	private String extPetIdOrTattooNo;
	

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
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

	public String getStudyLocation() {
		return studyLocation;
	}

	public void setStudyLocation(String studyLocation) {
		this.studyLocation = studyLocation;
	}

	public boolean isPreludeStudy() {
		return isPreludeStudy;
	}

	public void setPreludeStudy(boolean isPreludeStudy) {
		this.isPreludeStudy = isPreludeStudy;
	}

	public boolean isExternalStudy() {
		return isExternalStudy;
	}

	public void setExternalStudy(boolean isExternalStudy) {
		this.isExternalStudy = isExternalStudy;
	}

	public String getExtPetIdOrTattooNo() {
		return extPetIdOrTattooNo;
	}

	public void setExtPetIdOrTattooNo(String extPetIdOrTattooNo) {
		this.extPetIdOrTattooNo = extPetIdOrTattooNo;
	}

}
