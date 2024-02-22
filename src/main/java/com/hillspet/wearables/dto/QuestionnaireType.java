package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionnaireType {
	private int questionnaireTypeId;
	private String questionnaireType;

	public int getQuestionnaireTypeId() {
		return questionnaireTypeId;
	}

	public void setQuestionnaireTypeId(int questionnaireTypeId) {
		this.questionnaireTypeId = questionnaireTypeId;
	}

	public String getQuestionnaireType() {
		return questionnaireType;
	}

	public void setQuestionnaireType(String questionnaireType) {
		this.questionnaireType = questionnaireType;
	}

}
