package com.hillspet.wearables.request;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Contains all information that needed to implement skip on questionnaire", value = "QuestionnaireSkipRequest")
@JsonInclude(value = Include.NON_NULL)
public class QuestionnaireSkipRequest {

	private Integer questionnaireId;
	private List<QuestionSkipReqeust> questions;

	private Integer questionnaireTypeId;
	private Integer questionnaireCategoryId;
	private Integer questionnaireLevelId;
	private LocalDate startDate;
	private LocalDate endDate;

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

	public Integer getQuestionnaireTypeId() {
		return questionnaireTypeId;
	}

	public void setQuestionnaireTypeId(Integer questionnaireTypeId) {
		this.questionnaireTypeId = questionnaireTypeId;
	}

	public Integer getQuestionnaireCategoryId() {
		return questionnaireCategoryId;
	}

	public void setQuestionnaireCategoryId(Integer questionnaireCategoryId) {
		this.questionnaireCategoryId = questionnaireCategoryId;
	}

	public Integer getQuestionnaireLevelId() {
		return questionnaireLevelId;
	}

	public void setQuestionnaireLevelId(Integer questionnaireLevelId) {
		this.questionnaireLevelId = questionnaireLevelId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

}
