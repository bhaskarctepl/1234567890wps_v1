package com.hillspet.wearables.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class FoodIntakeFormatted {
    private String intakeDate;
    private List<String> dietNumber;
    private List<String> dietName;
    private List<String> studyRecommendedAmount;
    private List<String> studyQuantityOffered;
    private List<String> studyOfferedAmount;
    private List<String> studyConsumedAmount;
    private List<String> studyUnit;
    private List<String> otherFoodCategory;
    private List<String> otherPleaseSpecify;
    private List<String> otherConsumedAmount;
    private List<String> otherCaloricDensity;
    private List<String> petName;
    private List<String> feedback;

    public FoodIntakeFormatted(String intakeDate) {
        this.intakeDate = intakeDate;
        this.dietNumber = new ArrayList<>();
        this.dietName = new ArrayList<>();
        this.studyRecommendedAmount = new ArrayList<>();
        this.studyQuantityOffered = new ArrayList<>();
        this.studyOfferedAmount = new ArrayList<>();
        this.studyConsumedAmount = new ArrayList<>();
        this.studyUnit = new ArrayList<>();
        this.otherFoodCategory = new ArrayList<>();
        this.otherPleaseSpecify = new ArrayList<>();
        this.otherConsumedAmount = new ArrayList<>();
        this.otherCaloricDensity = new ArrayList<>();
        this.petName = new ArrayList<>();
        this.feedback = new ArrayList<>();
    }

    public String getIntakeDate() {
        return intakeDate;
    }

    public void setIntakeDate(String intakeDate) {
        this.intakeDate = intakeDate;
    }

    public List<String> getDietNumber() {
        return dietNumber;
    }

    public void setDietNumber(List<String> dietNumber) {
        this.dietNumber = dietNumber;
    }

    public List<String> getDietName() {
        return dietName;
    }

    public void setDietName(List<String> dietName) {
        this.dietName = dietName;
    }

    public List<String> getStudyRecommendedAmount() {
        return studyRecommendedAmount;
    }

    public void setStudyRecommendedAmount(List<String> studyRecommendedAmount) {
        this.studyRecommendedAmount = studyRecommendedAmount;
    }

    public List<String> getStudyQuantityOffered() {
        return studyQuantityOffered;
    }

    public void setStudyQuantityOffered(List<String> studyQuantityOffered) {
        this.studyQuantityOffered = studyQuantityOffered;
    }

    public List<String> getStudyOfferedAmount() {
        return studyOfferedAmount;
    }

    public void setStudyOfferedAmount(List<String> studyOfferedAmount) {
        this.studyOfferedAmount = studyOfferedAmount;
    }

    public List<String> getStudyConsumedAmount() {
        return studyConsumedAmount;
    }

    public void setStudyConsumedAmount(List<String> studyConsumedAmount) {
        this.studyConsumedAmount = studyConsumedAmount;
    }

    public List<String> getStudyUnit() {
        return studyUnit;
    }

    public void setStudyUnit(List<String> studyUnit) {
        this.studyUnit = studyUnit;
    }

    public List<String> getOtherFoodCategory() {
        return otherFoodCategory;
    }

    public void setOtherFoodCategory(List<String> otherFoodCategory) {
        this.otherFoodCategory = otherFoodCategory;
    }

    public List<String> getOtherPleaseSpecify() {
        return otherPleaseSpecify;
    }

    public void setOtherPleaseSpecify(List<String> otherPleaseSpecify) {
        this.otherPleaseSpecify = otherPleaseSpecify;
    }

    public List<String> getOtherConsumedAmount() {
        return otherConsumedAmount;
    }

    public void setOtherConsumedAmount(List<String> otherConsumedAmount) {
        this.otherConsumedAmount = otherConsumedAmount;
    }

    public List<String> getOtherCaloricDensity() {
        return otherCaloricDensity;
    }

    public void setOtherCaloricDensity(List<String> otherCaloricDensity) {
        this.otherCaloricDensity = otherCaloricDensity;
    }

    public List<String> getPetName() {
        return petName;
    }

    public void setPetName(List<String> petName) {
        this.petName = petName;
    }

    public List<String> getFeedback() {
        return feedback;
    }

    public void setFeedback(List<String> feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FoodIntakeFormatted.class.getSimpleName() + "{", "}")
                .add("'intakeDate' :'" + intakeDate + "'")
                .add("'dietNumber' :" + dietNumber)
                .add("'dietName' :" + dietName)
                .add("'studyRecommendedAmount' :" + studyRecommendedAmount)
                .add("'studyQuantityOffered' : " + studyQuantityOffered)
                .add("'studyOfferedAmount' : " + studyOfferedAmount)
                .add("'studyConsumedAmount' : " + studyConsumedAmount)
                .add("'studyUnit' : " + studyUnit)
                .add("'otherFoodCategory' : " + otherFoodCategory)
                .add("'otherPleaseSpecify' : " + otherPleaseSpecify)
                .add("'otherConsumedAmount' : " + otherConsumedAmount)
                .add("'otherCaloricDensity': " + otherCaloricDensity)
                .add("'petName' : " + petName)
                .add("'feedback': " + feedback)
                .toString();
    }
}
