package com.hillspet.wearables.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contains all information that needed to create pet questionnaire", value = "PetQuestionnaireRequest")
@JsonInclude(value = Include.NON_NULL)
public class PetQuestionnaireRequest {

	@ApiModelProperty(value = "petIds", required = true)
	private List<Integer> petIds;

	@ApiModelProperty(value = "questionnairesAssociated", required = true)
	private List<PetQuestionnaireAssociated> questionnairesAssociated;

	private Integer userId;

	public List<Integer> getPetIds() {
		return petIds;
	}

	public void setPetIds(List<Integer> petIds) {
		this.petIds = petIds;
	}

	public List<PetQuestionnaireAssociated> getQuestionnairesAssociated() {
		return questionnairesAssociated;
	}

	public void setQuestionnairesAssociated(List<PetQuestionnaireAssociated> questionnairesAssociated) {
		this.questionnairesAssociated = questionnairesAssociated;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
