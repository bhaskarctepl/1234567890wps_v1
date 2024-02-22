package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionnaireCategory {
	private int questionnaireCategoryId;
	private String questionnaireCategory;
	private int questionnaireTypeId;
	
	

	public int getQuestionnaireTypeId() {
		return questionnaireTypeId;
	}

	public void setQuestionnaireTypeId(int questionnaireTypeId) {
		this.questionnaireTypeId = questionnaireTypeId;
	}

	public int getQuestionnaireCategoryId() {
		return questionnaireCategoryId;
	}

	public void setQuestionnaireCategoryId(int questionnaireCategoryId) {
		this.questionnaireCategoryId = questionnaireCategoryId;
	}

	public String getQuestionnaireCategory() {
		return questionnaireCategory;
	}

	public void setQuestionnaireCategory(String questionnaireCategory) {
		this.questionnaireCategory = questionnaireCategory;
	}

}
