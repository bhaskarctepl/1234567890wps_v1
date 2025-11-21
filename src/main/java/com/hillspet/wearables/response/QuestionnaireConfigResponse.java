package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.request.QuestionnaireAssociated;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionnaireConfigResponse extends BaseResultCollection {

	private List<QuestionnaireAssociated> questionnaireConfigs;

	private List<QuestionnaireAssociated> questionnaireConfigsList;

	private String message;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for Study questionnaire config list based on search criteria")
	public List<QuestionnaireAssociated> getQuestionnaireConfigsList() {
		return questionnaireConfigsList;
	}

	public void setQuestionnaireConfigsList(List<QuestionnaireAssociated> questionnaireConfigsList) {
		this.questionnaireConfigsList = questionnaireConfigsList;
	}

	public List<QuestionnaireAssociated> getQuestionnaireConfigs() {
		return questionnaireConfigs;
	}

	public void setQuestionnaireConfigs(List<QuestionnaireAssociated> questionnaireConfigs) {
		this.questionnaireConfigs = questionnaireConfigs;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
