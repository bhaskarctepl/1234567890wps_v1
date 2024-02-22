package com.hillspet.wearables.dto;

import java.util.StringJoiner;

public class FoodIntakeDetails {
    private String intakeDate;
    private String intakeId;
    private String dietNumber;
    private String dietName;
    private String studyRecommendedAmount;
    private String studyQuantityOffered;
    private String studyOfferedAmount;
    private String studyConsumedAmount;
    private String studyUnit;
    private String otherFoodCategory;
    private String otherPleaseSpecify;
    private String otherConsumedAmount;
    private String otherCaloricDensity;
    private String petName;
    private String feedback;
    private String description;
    private String studyId;
    private String otherFood;

    public String getIntakeDate() {
        return intakeDate;
    }

    public void setIntakeDate(String intakeDate) {
        this.intakeDate = intakeDate;
    }

    public String getIntakeId() {
        return intakeId;
    }

    public void setIntakeId(String intakeId) {
        this.intakeId = intakeId;
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

    public String getStudyRecommendedAmount() {
        return studyRecommendedAmount;
    }

    public void setStudyRecommendedAmount(String studyRecommendedAmount) {
        this.studyRecommendedAmount = studyRecommendedAmount;
    }

    public String getStudyQuantityOffered() {
        return studyQuantityOffered;
    }

    public void setStudyQuantityOffered(String studyQuantityOffered) {
        this.studyQuantityOffered = studyQuantityOffered;
    }

    public String getStudyOfferedAmount() {
        return studyOfferedAmount;
    }

    public void setStudyOfferedAmount(String studyOfferedAmount) {
        this.studyOfferedAmount = studyOfferedAmount;
    }

    public String getStudyConsumedAmount() {
        return studyConsumedAmount;
    }

    public void setStudyConsumedAmount(String studyConsumedAmount) {
        this.studyConsumedAmount = studyConsumedAmount;
    }

    public String getStudyUnit() {
        return studyUnit;
    }

    public void setStudyUnit(String studyUnit) {
        this.studyUnit = studyUnit;
    }

    public String getOtherFoodCategory() {
        return otherFoodCategory;
    }

    public void setOtherFoodCategory(String otherFoodCategory) {
        this.otherFoodCategory = otherFoodCategory;
    }

    public String getOtherPleaseSpecify() {
        return otherPleaseSpecify;
    }

    public void setOtherPleaseSpecify(String otherPleaseSpecify) {
        this.otherPleaseSpecify = otherPleaseSpecify;
    }

    public String getOtherConsumedAmount() {
        return otherConsumedAmount;
    }

    public void setOtherConsumedAmount(String otherConsumedAmount) {
        this.otherConsumedAmount = otherConsumedAmount;
    }

    public String getOtherCaloricDensity() {
        return otherCaloricDensity;
    }

    public void setOtherCaloricDensity(String otherCaloricDensity) {
        this.otherCaloricDensity = otherCaloricDensity;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStudyId() {
        return studyId;
    }

    public void setStudyId(String studyId) {
        this.studyId = studyId;
    }

    public String getOtherFood() {
        return otherFood;
    }

    public void setOtherFood(String otherFood) {
        this.otherFood = otherFood;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FoodIntakeDetails.class.getSimpleName() + "[", "]")
                .add("intakeDate='" + intakeDate + "'")
                .add("intakeId='" + intakeId + "'")
                .add("dietNumber='" + dietNumber + "'")
                .add("dietName='" + dietName + "'")
                .add("studyRecommendedAmount='" + studyRecommendedAmount + "'")
                .add("studyQuantityOffered='" + studyQuantityOffered + "'")
                .add("studyOfferedAmount='" + studyOfferedAmount + "'")
                .add("studyConsumedAmount='" + studyConsumedAmount + "'")
                .add("studyUnit='" + studyUnit + "'")
                .add("otherFoodCategory='" + otherFoodCategory + "'")
                .add("otherPleaseSpecify='" + otherPleaseSpecify + "'")
                .add("otherConsumedAmount='" + otherConsumedAmount + "'")
                .add("otherCaloricDensity='" + otherCaloricDensity + "'")
                .add("petName='" + petName + "'")
                .add("feedback='" + feedback + "'")
                .add("description='" + description + "'")
                .add("studyId='" + studyId + "'")
                .add("otherFood='" + otherFood + "'")
                .toString();
    }
}
