package com.hillspet.wearables.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PreDefinedQuestion {

	private Integer preDefinedQuestionId;
	private String preDefinedQuestion;
	private Integer questionTypeId;
	private String questionType;
	private String questionImageName;
	private String questionImageUrl;
	private List<PredefinedQuestionAnswerOption> questionAnswerOptions;
	private QuestionSliderType other;

	// ADDED FOR LONGITUDINAL DATA
	private String questionCode;

	public QuestionSliderType getOther() {
		return other;
	}

	public void setOther(QuestionSliderType other) {
		this.other = other;
	}

	public Integer getPreDefinedQuestionId() {
		return preDefinedQuestionId;
	}

	public void setPreDefinedQuestionId(Integer preDefinedQuestionId) {
		this.preDefinedQuestionId = preDefinedQuestionId;
	}

	public String getPreDefinedQuestion() {
		return preDefinedQuestion;
	}

	public void setPreDefinedQuestion(String preDefinedQuestion) {
		this.preDefinedQuestion = preDefinedQuestion;
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

	public List<PredefinedQuestionAnswerOption> getQuestionAnswerOptions() {
		return questionAnswerOptions;
	}

	public void setQuestionAnswerOptions(List<PredefinedQuestionAnswerOption> questionAnswerOptions) {
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

	public String getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}

}
