package com.hillspet.wearables.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Question {

	private Integer questionId;
	private String question;
	private Integer questionTypeId;
	private String questionType;
	private Integer questionOrder;
	private Boolean isMandatory;
	private Boolean saveForFuture;
	private Boolean isActive;
	private Boolean isUpdated;
	private List<QuestionAnswerOption> questionAnswerOptions;
	private QuestionSliderType other;

	private QuestionnaireSection section;

	private String questionImageName;
	private String questionImageUrl;

	private Boolean shuffleOptionOrder;

	// Added for Longitudinal data
	private Boolean isIncludeInDataExtract;
	private String questionCode;
	private Integer validityPeriodId;
	private String validityPeriod;
	private Integer noOfDays;
	

	private List<QuestionCategory> questionCategories;

	public QuestionSliderType getOther() {
		return other;
	}

	public void setOther(QuestionSliderType other) {
		this.other = other;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Integer getQuestionTypeId() {
		return questionTypeId;
	}

	public void setQuestionTypeId(Integer questionTypeId) {
		this.questionTypeId = questionTypeId;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public Integer getQuestionOrder() {
		return questionOrder;
	}

	public void setQuestionOrder(Integer questionOrder) {
		this.questionOrder = questionOrder;
	}

	public Boolean getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(Boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public Boolean getSaveForFuture() {
		return saveForFuture;
	}

	public void setSaveForFuture(Boolean saveForFuture) {
		this.saveForFuture = saveForFuture;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsUpdated() {
		return isUpdated;
	}

	public void setIsUpdated(Boolean isUpdated) {
		this.isUpdated = isUpdated;
	}

	public List<QuestionAnswerOption> getQuestionAnswerOptions() {
		return questionAnswerOptions;
	}

	public void setQuestionAnswerOptions(List<QuestionAnswerOption> questionAnswerOptions) {
		this.questionAnswerOptions = questionAnswerOptions;
	}

	public String getQuestionImageName() {
		return questionImageName;
	}

	public void setQuestionImageName(String questionImageName) {
		this.questionImageName = questionImageName;
	}

	public String getQuestionImageUrl() {
		return questionImageUrl;
	}

	public void setQuestionImageUrl(String questionImageUrl) {
		this.questionImageUrl = questionImageUrl;
	}

	public QuestionnaireSection getSection() {
		return section;
	}

	public void setSection(QuestionnaireSection section) {
		this.section = section;
	}

	public Boolean getIsIncludeInDataExtract() {
		return isIncludeInDataExtract;
	}

	public void setIsIncludeInDataExtract(Boolean isIncludeInDataExtract) {
		this.isIncludeInDataExtract = isIncludeInDataExtract;
	}

	public String getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}

	public Integer getValidityPeriodId() {
		return validityPeriodId;
	}

	public void setValidityPeriodId(Integer validityPeriodId) {
		this.validityPeriodId = validityPeriodId;
	}

	public String getValidityPeriod() {
		return validityPeriod;
	}

	public void setValidityPeriod(String validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	public List<QuestionCategory> getQuestionCategories() {
		return questionCategories;
	}

	public void setQuestionCategories(List<QuestionCategory> questionCategories) {
		this.questionCategories = questionCategories;
	}

	public Boolean getShuffleOptionOrder() {
		return shuffleOptionOrder;
	}

	public void setShuffleOptionOrder(Boolean shuffleOptionOrder) {
		this.shuffleOptionOrder = shuffleOptionOrder;
	}

	public Integer getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(Integer noOfDays) {
		this.noOfDays = noOfDays;
	}

	
}
