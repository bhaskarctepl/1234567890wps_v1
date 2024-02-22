package com.hillspet.wearables.response;

import java.util.List;

import com.hillspet.wearables.dto.QuestionnaireCategory;

public class QuestionnaireCategoryListResponse {
	private List<QuestionnaireCategory> questionnaireCategories;

	public List<QuestionnaireCategory> getQuestionnaireCategories() {
		return questionnaireCategories;
	}

	public void setQuestionnaireCategories(List<QuestionnaireCategory> questionnaireCategories) {
		this.questionnaireCategories = questionnaireCategories;
	}
	
	
}
