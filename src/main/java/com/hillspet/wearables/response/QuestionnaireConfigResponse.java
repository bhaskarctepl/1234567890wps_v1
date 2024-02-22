package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hillspet.wearables.request.QuestionnaireAssociated;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionnaireConfigResponse {

	private List<QuestionnaireAssociated> questionnaireConfigs;

	public List<QuestionnaireAssociated> getQuestionnaireConfigs() {
		return questionnaireConfigs;
	}

	public void setQuestionnaireConfigs(List<QuestionnaireAssociated> questionnaireConfigs) {
		this.questionnaireConfigs = questionnaireConfigs;
	}

}
