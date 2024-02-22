package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.QuestionnaireListDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionnaireListResponse extends BaseResultCollection {

	private List<QuestionnaireListDTO> questionnaireList;
	private List<QuestionnaireListDTO> questionnaires;

	@JsonProperty("rows")
	@ApiModelProperty(value = "Get the Questionnaires based on search criteria")
	public List<QuestionnaireListDTO> getQuestionnaireList() {
		return questionnaireList;
	}

	public void setQuestionnaireList(List<QuestionnaireListDTO> questionnaireList) {
		this.questionnaireList = questionnaireList;
	}

	public List<QuestionnaireListDTO> getQuestionnaires() {
		return questionnaires;
	}

	public void setQuestionnaires(List<QuestionnaireListDTO> questionnaires) {
		this.questionnaires = questionnaires;
	}
	
	
}
