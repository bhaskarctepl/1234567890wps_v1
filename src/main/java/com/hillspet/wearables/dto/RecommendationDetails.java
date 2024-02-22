package com.hillspet.wearables.dto;

import java.util.StringJoiner;

public class RecommendationDetails {
    private Integer petId;
    private String petName;
    private Integer dietId;
    private String dietNumber;
    private String dietName;
    private String threshold;
    private String deviations;
    private String scheduleDate;
    private Float recommendationDietAmtCups;
    private Float recommendationDietAmtGrams;
    private Float manualQualityCups;
    private Float manualQualityGrams;
    private short isFlagged;
    private short recommendationStatusId;
    private String recommendationStatus;
    private String comments;
    private String feedUnits;
    private Integer afId;
    private Integer studyId;
    private short isLatestRecord;
    private short afRecommendationException;
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

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public String getDeviations() {
        return deviations;
    }

    public void setDeviations(String deviations) {
        this.deviations = deviations;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

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

    public Float getManualQualityCups() {
        return manualQualityCups;
    }

    public void setManualQualityCups(Float manualQualityCups) {
        this.manualQualityCups = manualQualityCups;
    }

    public Float getManualQualityGrams() {
        return manualQualityGrams;
    }

    public void setManualQualityGrams(Float manualQualityGrams) {
        this.manualQualityGrams = manualQualityGrams;
    }

    public short getIsFlagged() {
        return isFlagged;
    }

    public void setIsFlagged(short isFlagged) {
        this.isFlagged = isFlagged;
    }

    public short getRecommendationStatusId() {
        return recommendationStatusId;
    }

    public void setRecommendationStatusId(short recommendationStatusId) {
        this.recommendationStatusId = recommendationStatusId;
    }

    public String getRecommendationStatus() {
        return recommendationStatus;
    }

    public void setRecommendationStatus(String recommendationStatus) {
        this.recommendationStatus = recommendationStatus;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getFeedUnits() {
        return feedUnits;
    }

    public void setFeedUnits(String feedUnits) {
        this.feedUnits = feedUnits;
    }

    public Integer getAfId() {
        return afId;
    }

    public void setAfId(Integer afId) {
        this.afId = afId;
    }

    public Integer getStudyId() {
        return studyId;
    }

    public void setStudyId(Integer studyId) {
        this.studyId = studyId;
    }

    public short getIsLatestRecord() {
        return isLatestRecord;
    }

    public void setIsLatestRecord(short isLatestRecord) {
        this.isLatestRecord = isLatestRecord;
    }

    public short getAfRecommendationException() {
        return afRecommendationException;
    }

    public void setAfRecommendationException(short afRecommendationException) {
        this.afRecommendationException = afRecommendationException;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RecommendationDetails.class.getSimpleName() + "[", "]")
                .add("petId=" + petId)
                .add("petName='" + petName + "'")
                .add("dietId=" + dietId)
                .add("dietNumber='" + dietNumber + "'")
                .add("dietName='" + dietName + "'")
                .add("threshold='" + threshold + "'")
                .add("deviations='" + deviations + "'")
                .add("scheduleDate='" + scheduleDate + "'")
                .add("recommendationDietAmtCups=" + recommendationDietAmtCups)
                .add("recommendationDietAmtGrams=" + recommendationDietAmtGrams)
                .add("manualQualityCups=" + manualQualityCups)
                .add("manualQualityGrams=" + manualQualityGrams)
                .add("isFlagged=" + isFlagged)
                .add("recommendationStatusId=" + recommendationStatusId)
                .add("recommendationStatus='" + recommendationStatus + "'")
                .add("comments='" + comments + "'")
                .add("feedUnits='" + feedUnits + "'")
                .add("afId=" + afId)
                .add("studyId=" + studyId)
                .add("isLatestRecord=" + isLatestRecord)
                .add("afRecommendationException=" + afRecommendationException)
                .toString();
    }
}
