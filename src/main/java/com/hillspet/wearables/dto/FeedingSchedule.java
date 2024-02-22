package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedingSchedule {

	private Integer feedingScheduleConfigId;
	private Integer studyId;
	private Integer phaseId;
	private Integer petId;
	private String petName;
	private Integer studyDietId;
	private Integer dietId;
	private String dietNumber;
	private String dietName;
	private Integer fromPhaseDay;
	private Integer toPhaseDay;
	private Integer byTreatmentGroup;
	private Integer isDeleted;
	private Integer userId;
	private String unitName; 
	private String scheduleDate;
	private String status;
	private double recommendFoodAmount;
	private double quantityConsumed;
	private String treatmentGroupName;
	private Integer feedingScheduleId;
	private String days;
	private Float recommendationDietAmtCups;
	private Float recommendationDietAmtGrams;
	

	public Float getRecommendationDietAmtCups() {
		return recommendationDietAmtCups;
	}

	public void setRecommendationDietAmtCups(Float recommendationDietAmtCups) {
		this.recommendationDietAmtCups = recommendationDietAmtCups;
	}

	public Float getRecommendationDietAmtGrams() {
		return recommendationDietAmtGrams;
	}

	public void setRecommendationDietAmtGrams(Float recommendationDietAmtGrams) {
		this.recommendationDietAmtGrams = recommendationDietAmtGrams;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public Integer getFeedingScheduleConfigId() {
		return feedingScheduleConfigId;
	}

	public void setFeedingScheduleConfigId(Integer feedingScheduleConfigId) {
		this.feedingScheduleConfigId = feedingScheduleConfigId;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public Integer getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(Integer phaseId) {
		this.phaseId = phaseId;
	}

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public Integer getStudyDietId() {
		return studyDietId;
	}

	public void setStudyDietId(Integer studyDietId) {
		this.studyDietId = studyDietId;
	}

	public Integer getDietId() {
		return dietId;
	}

	public void setDietId(Integer dietId) {
		this.dietId = dietId;
	}

	public String getDietNumber() {
		return dietNumber;
	}

	public void setDietNumber(String dietNumber) {
		this.dietNumber = dietNumber;
	}

	public String getDietName() {
		return dietName;
	}

	public void setDietName(String dietName) {
		this.dietName = dietName;
	}

	public Integer getFromPhaseDay() {
		return fromPhaseDay;
	}

	public void setFromPhaseDay(Integer fromPhaseDay) {
		this.fromPhaseDay = fromPhaseDay;
	}

	public Integer getToPhaseDay() {
		return toPhaseDay;
	}

	public void setToPhaseDay(Integer toPhaseDay) {
		this.toPhaseDay = toPhaseDay;
	}

	public Integer getByTreatmentGroup() {
		return byTreatmentGroup;
	}

	public void setByTreatmentGroup(Integer byTreatmentGroup) {
		this.byTreatmentGroup = byTreatmentGroup;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getRecommendFoodAmount() {
		return recommendFoodAmount;
	}

	public void setRecommendFoodAmount(double recommendFoodAmount) {
		this.recommendFoodAmount = recommendFoodAmount;
	}

	public double getQuantityConsumed() {
		return quantityConsumed;
	}

	public void setQuantityConsumed(double quantityConsumed) {
		this.quantityConsumed = quantityConsumed;
	}

	public String getTreatmentGroupName() {
		return treatmentGroupName;
	}

	public void setTreatmentGroupName(String treatmentGroupName) {
		this.treatmentGroupName = treatmentGroupName;
	}

	public Integer getFeedingScheduleId() {
		return feedingScheduleId;
	}

	public void setFeedingScheduleId(Integer feedingScheduleId) {
		this.feedingScheduleId = feedingScheduleId;
	}

}
