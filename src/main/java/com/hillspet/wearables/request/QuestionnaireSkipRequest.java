package com.hillspet.wearables.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Contains all information that needed to implement skip on questionnaire", value = "QuestionnaireSkipRequest")
@JsonInclude(value = Include.NON_NULL)
public class QuestionnaireSkipRequest {

	private Integer questionnaireId;
	private List<QuestionSkipReqeust> questions;

	public Integer getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Integer questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public List<QuestionSkipReqeust> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionSkipReqeust> questions) {
		this.questions = questions;
	}

}
