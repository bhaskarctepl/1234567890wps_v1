package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.QuestionnaireResponseListDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionnaireResponseListResponse extends BaseResultCollection {

	private List<QuestionnaireResponseListDTO> questionnaireResponseList;
	private List<QuestionnaireResponseListDTO> questionnairesResponse;
	@JsonProperty("rows")
	@ApiModelProperty(value = "Get the Questionnaires based on search criteria")
	public List<QuestionnaireResponseListDTO> getQuestionnaireResponseList() {
		return questionnaireResponseList;
	}
	public void setQuestionnaireResponseList(List<QuestionnaireResponseListDTO> questionnaireResponseList) {
		this.questionnaireResponseList = questionnaireResponseList;
	}
	public List<QuestionnaireResponseListDTO> getQuestionnairesResponse() {
		return questionnairesResponse;
	}
	public void setQuestionnairesResponse(List<QuestionnaireResponseListDTO> questionnairesResponse) {
		this.questionnairesResponse = questionnairesResponse;
	}

	
}
