package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PredefinedQuestionAnswerOption {
	private Integer questionAnswerId;
	private String questionAnswer;

	private String ansOptionMediaName;
	private String ansOptionMediaUrl;
	private Integer ansOptionMediaType;

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

}
