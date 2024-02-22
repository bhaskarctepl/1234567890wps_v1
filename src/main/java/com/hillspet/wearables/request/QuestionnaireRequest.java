package com.hillspet.wearables.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hillspet.wearables.dto.Questionnaire;

import io.swagger.annotations.ApiModel;

/**
 * 
 * @author sgorle
 *
 */
@ApiModel(description = "Contains all information that needed to create questionnaire", value = "QuestionnaireRequest")
@JsonInclude(value = Include.NON_NULL)
public class QuestionnaireRequest {
	
	private Questionnaire questionnaire;

	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

}
