package com.hillspet.wearables.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivityFactorResultResponse {

	private String studyName;
	private String studyId;
	private String extPetValue;
	private String afCalculatedDate;
	private String algorithmName;
	private String algorithmVerion;
	private String estimatedEnergyExpenditure;
	private String estimatedStepCount;
	private String estimatedAf;
	private Float recommendedFeedAmtGrams;
	private Float recommendedFeedAmtCups;
	private String roundedRecommendedFeedAmtGrams;
	private String roundedRecommendedFeedAmtCups;
	private String feedUnits;
	private String assessmentStartDate;
	private String petName;
	private Integer petId;
	private Float energyExpenditure;

	private Boolean isFlagged;
	private String flaggedMessage;

	private Integer afId;
	private Integer recommendationStatusId;

	private short isLatestRecord;

	private String lastModifiedDate;

	public String getAssessmentStartDate() {
		return assessmentStartDate;
	}

	public void setAssessmentStartDate(String assessmentStartDate) {
		this.assessmentStartDate = assessmentStartDate;
	}

	public String getAssessmentEndDate() {
		return assessmentEndDate;
	}

	public void setAssessmentEndDate(String assessmentEndDate) {
		this.assessmentEndDate = assessmentEndDate;
	}

	public String getQualifyingDays() {
		return qualifyingDays;
	}

	public void setQualifyingDays(String qualifyingDays) {
		this.qualifyingDays = qualifyingDays;
	}

	public Float getTotalEnergyExpenditure() {
		return totalEnergyExpenditure;
	}

	public void setTotalEnergyExpenditure(Float totalEnergyExpenditure) {
		this.totalEnergyExpenditure = totalEnergyExpenditure;
	}

	public Float getRestingEnergyRequirement() {
		return restingEnergyRequirement;
	}

	public void setRestingEnergyRequirement(Float restingEnergyRequirement) {
		this.restingEnergyRequirement = restingEnergyRequirement;
	}

	public String getAdjustmentFactor() {
		return adjustmentFactor;
	}

	public void setAdjustmentFactor(String adjustmentFactor) {
		this.adjustmentFactor = adjustmentFactor;
	}

	public String getAdjustedEnergyExpenditure() {
		return adjustedEnergyExpenditure;
	}

	public void setAdjustedEnergyExpenditure(String adjustedEnergyExpenditure) {
		this.adjustedEnergyExpenditure = adjustedEnergyExpenditure;
	}

	public String getFeedDensity() {
		return feedDensity;
	}

	public void setFeedDensity(String feedDensity) {
		this.feedDensity = feedDensity;
	}

	public String getAfVersion() {
		return afVersion;
	}

	public void setAfVersion(String afVersion) {
		this.afVersion = afVersion;
	}

	private String assessmentEndDate;
	private String qualifyingDays;
	private Float totalEnergyExpenditure;
	private Float restingEnergyRequirement;
	private String adjustmentFactor;
	private String adjustedEnergyExpenditure;
	private String feedDensity;
	private String afVersion;

	private Float manualQuantityGrams;
	private Float manualQuantityCups;
	private short afRecommendationException;

	private String comments;

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

	public String getExtPetValue() {
		return extPetValue;
	}

	public void setExtPetValue(String extPetValue) {
		this.extPetValue = extPetValue;
	}

	public String getAfCalculatedDate() {
		return afCalculatedDate;
	}

	public void setAfCalculatedDate(String afCalculatedDate) {
		this.afCalculatedDate = afCalculatedDate;
	}

	public String getAlgorithmName() {
		return algorithmName;
	}

	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}

	public String getAlgorithmVerion() {
		return algorithmVerion;
	}

	public void setAlgorithmVerion(String algorithmVerion) {
		this.algorithmVerion = algorithmVerion;
	}


	public String getEstimatedEnergyExpenditure() {
		return estimatedEnergyExpenditure;
	}

	public void setEstimatedEnergyExpenditure(String estimatedEnergyExpenditure) {
		this.estimatedEnergyExpenditure = estimatedEnergyExpenditure;
	}

	public String getEstimatedStepCount() {
		return estimatedStepCount;
	}

	public void setEstimatedStepCount(String estimatedStepCount) {
		this.estimatedStepCount = estimatedStepCount;
	}

	public String getEstimatedAf() {
		return estimatedAf;
	}

	public void setEstimatedAf(String estimatedAf) {
		this.estimatedAf = estimatedAf;
	}

	public String getFeedUnits() {
		return feedUnits;
	}

	public void setFeedUnits(String feedUnits) {
		this.feedUnits = feedUnits;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getRoundedRecommendedFeedAmtGrams() {
		return roundedRecommendedFeedAmtGrams;
	}

	public void setRoundedRecommendedFeedAmtGrams(String roundedRecommendedFeedAmtGrams) {
		this.roundedRecommendedFeedAmtGrams = roundedRecommendedFeedAmtGrams;
	}

	public String getRoundedRecommendedFeedAmtCups() {
		return roundedRecommendedFeedAmtCups;
	}

	public void setRoundedRecommendedFeedAmtCups(String roundedRecommendedFeedAmtCups) {
		this.roundedRecommendedFeedAmtCups = roundedRecommendedFeedAmtCups;
	}

	public Float getEnergyExpenditure() {
		return energyExpenditure;
	}

	public void setEnergyExpenditure(Float energyExpenditure) {
		this.energyExpenditure = energyExpenditure;
	}

	public Float getRecommendedFeedAmtGrams() {
		return recommendedFeedAmtGrams;
	}

	public void setRecommendedFeedAmtGrams(Float recommendedFeedAmtGrams) {
		this.recommendedFeedAmtGrams = recommendedFeedAmtGrams;
	}

	public Float getRecommendedFeedAmtCups() {
		return recommendedFeedAmtCups;
	}

	public void setRecommendedFeedAmtCups(Float recommendedFeedAmtCups) {
		this.recommendedFeedAmtCups = recommendedFeedAmtCups;
	}

	public Boolean getFlagged() {
		return isFlagged;
	}

	public void setFlagged(Boolean flagged) {
		isFlagged = flagged;
	}

	public String getFlaggedMessage() {
		return flaggedMessage;
	}

	public void setFlaggedMessage(String flaggedMessage) {
		this.flaggedMessage = flaggedMessage;
	}

	public Float getManualQuantityGrams() {
		return manualQuantityGrams;
	}

	public void setManualQuantityGrams(Float manualQuantityGrams) {
		this.manualQuantityGrams = manualQuantityGrams;
	}

	public Float getManualQuantityCups() {
		return manualQuantityCups;
	}

	public void setManualQuantityCups(Float manualQuantityCups) {
		this.manualQuantityCups = manualQuantityCups;
	}

	public Integer getAfId() {
		return afId;
	}

	public void setAfId(Integer afId) {
		this.afId = afId;
	}

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

	public Integer getRecommendationStatusId() {
		return recommendationStatusId;
	}

	public void setRecommendationStatusId(Integer recommendationStatusId) {
		this.recommendationStatusId = recommendationStatusId;
	}

	public short getIsLatestRecord() {
		return isLatestRecord;
	}

	public void setIsLatestRecord(short isLatestRecord) {
		this.isLatestRecord = isLatestRecord;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public short getAfRecommendationException() {
		return afRecommendationException;
	}

	public void setAfRecommendationException(short afRecommendationException) {
		this.afRecommendationException = afRecommendationException;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
