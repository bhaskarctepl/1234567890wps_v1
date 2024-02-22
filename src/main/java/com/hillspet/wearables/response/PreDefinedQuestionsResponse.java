package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.PreDefinedQuestion;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PreDefinedQuestionsResponse {
	private List<PreDefinedQuestion> preDefinedQuestions;

	public List<PreDefinedQuestion> getPreDefinedQuestions() {
		return preDefinedQuestions;
	}

	public void setPreDefinedQuestions(List<PreDefinedQuestion> preDefinedQuestions) {
		this.preDefinedQuestions = preDefinedQuestions;
	}

}
