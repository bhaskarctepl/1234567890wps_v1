package com.hillspet.wearables.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetParentReport extends BaseDTO {

	private String study;
	private String questionnaireName;
	private String petParentName;
	private Integer completedCount;
	private Integer pendingCount;
	
	private String petName;
	private String expiryDate;
	private Integer answeredCount;
	private LocalDate startDate;
	private LocalDate endDate;
	private String answeredOn;
	
	private String status;
	
	
	public String getAnsweredOn() {
		return answeredOn;
	}
	public void setAnsweredOn(String answeredOn) {
		this.answeredOn = answeredOn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getPetName() {
		return petName;
	}
	public void setPetName(String petName) {
		this.petName = petName;
	}
	
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public Integer getAnsweredCount() {
		return answeredCount;
	}
	public void setAnsweredCount(Integer answeredCount) {
		this.answeredCount = answeredCount;
	}
	public String getStudy() {
		return study;
	}
	public void setStudy(String study) {
		this.study = study;
	}
	public String getQuestionnaireName() {
		return questionnaireName;
	}
	public void setQuestionnaireName(String questionnaireName) {
		this.questionnaireName = questionnaireName;
	}
	public String getPetParentName() {
		return petParentName;
	}
	public void setPetParentName(String petParentName) {
		this.petParentName = petParentName;
	}
	public Integer getCompletedCount() {
		return completedCount;
	}
	public void setCompletedCount(Integer completedCount) {
		this.completedCount = completedCount;
	}
	public Integer getPendingCount() {
		return pendingCount;
	}
	public void setPendingCount(Integer pendingCount) {
		this.pendingCount = pendingCount;
	}
	
	
	
}
