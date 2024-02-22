package com.hillspet.wearables.response;

import java.util.List;

import com.hillspet.wearables.dto.QuestionnaireType;

public class QuestionnaireTypeResponse {
	private List<QuestionnaireType> questionnaireTypes;

	public List<QuestionnaireType> getQuestionnaireTypes() {
		return questionnaireTypes;
	}

	public void setQuestionnaireTypes(List<QuestionnaireType> questionnaireTypes) {
		this.questionnaireTypes = questionnaireTypes;
	}
	
}
