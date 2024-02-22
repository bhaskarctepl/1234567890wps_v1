package com.hillspet.wearables.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hillspet.wearables.dto.Question;

import io.swagger.annotations.ApiModel;

/**
 * 
 * @author sgorle
 *
 */
@ApiModel(description = "Contains all information that needed to update data extract configuration details of study questionnaire", value = "StudyQuestionnaireRequest")
@JsonInclude(value = Include.NON_NULL)
public class StudyQuestionnaireRequest {

	private Integer studyQuestionnaireId;

	private List<Question> questions;

	public Integer getStudyQuestionnaireId() {
		return studyQuestionnaireId;
	}

	public void setStudyQuestionnaireId(Integer studyQuestionnaireId) {
		this.studyQuestionnaireId = studyQuestionnaireId;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

}
