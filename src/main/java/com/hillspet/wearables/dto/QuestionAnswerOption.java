package com.hillspet.wearables.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionAnswerOption {
	private Integer questionAnswerId;
	private String questionAnswer;
	private Boolean submitQuestionnaire;
	private Integer skipTo;

	private String ansOptionMediaName;
	private String ansOptionMediaUrl;
	private Integer ansOptionMediaType;

	// Added for Longitudinal data
	private List<QuestionCategory> answerCategories;

	public Integer getQuestionAnswerId() {
		return questionAnswerId;
	}

	public void setQuestionAnswerId(Integer questionAnswerId) {
		this.questionAnswerId = questionAnswerId;
	}

	public String getQuestionAnswer() {
		return questionAnswer;
	}

	public void setQuestionAnswer(String questionAnswer) {
		this.questionAnswer = questionAnswer;
	}

	public Boolean getSubmitQuestionnaire() {
		return submitQuestionnaire;
	}

	public void setSubmitQuestionnaire(Boolean submitQuestionnaire) {
		this.submitQuestionnaire = submitQuestionnaire;
	}

	public Integer getSkipTo() {
		return skipTo;
	}

	public void setSkipTo(Integer skipTo) {
		this.skipTo = skipTo;
	}

	public String getAnsOptionMediaName() {
		return ansOptionMediaName;
	}

	public void setAnsOptionMediaName(String ansOptionMediaName) {
		this.ansOptionMediaName = ansOptionMediaName;
	}

	public String getAnsOptionMediaUrl() {
		return ansOptionMediaUrl;
	}

	public void setAnsOptionMediaUrl(String ansOptionMediaUrl) {
		this.ansOptionMediaUrl = ansOptionMediaUrl;
	}

	public Integer getAnsOptionMediaType() {
		return ansOptionMediaType;
	}

	public void setAnsOptionMediaType(Integer ansOptionMediaType) {
		this.ansOptionMediaType = ansOptionMediaType;
	}

	public List<QuestionCategory> getAnswerCategories() {
		return answerCategories;
	}

	public void setAnswerCategories(List<QuestionCategory> answerCategories) {
		this.answerCategories = answerCategories;
	}

}
