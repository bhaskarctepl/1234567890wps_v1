package com.hillspet.wearables.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Contains all information that needed skip question in questionnaire", value = "QuestionSkipReqeust")
@JsonInclude(value = Include.NON_NULL)
public class QuestionSkipReqeust {

	private Integer questionId;
	private Integer questionOptionId;
	private Integer skipTo;

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getQuestionOptionId() {
		return questionOptionId;
	}

	public void setQuestionOptionId(Integer questionOptionId) {
		this.questionOptionId = questionOptionId;
	}

	public Integer getSkipTo() {
		return skipTo;
	}

	public void setSkipTo(Integer skipTo) {
		this.skipTo = skipTo;
	}

}
