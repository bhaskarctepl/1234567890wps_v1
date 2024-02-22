package com.hillspet.wearables.request;

import io.swagger.annotations.ApiModelProperty;

public class PetQuestionnaireAssociated {

	@ApiModelProperty(value = "petQuestionnaireId", required = true)
	private Integer petQuestionnaireId;

	@ApiModelProperty(value = "questionnaireId", required = true)
	private Integer questionnaireId;

	@ApiModelProperty(value = "questionnaireName", required = false)
	private String questionnaireName;

	@ApiModelProperty(value = "occuranceId", required = true)
	private Integer occuranceId;

	@ApiModelProperty(value = "frequencyId", required = true)
	private Integer frequencyId;

	@ApiModelProperty(value = "startDate", required = true)
	private String startDate;

	@ApiModelProperty(value = "endDate", required = true)
	private String endDate;

	@ApiModelProperty(value = "occurance", required = true)
	private String occurance;

	@ApiModelProperty(value = "frequency", required = true)
	private String frequency;

	@ApiModelProperty(value = "isActive", required = false)
	private int isActive;

	public Integer getPetQuestionnaireId() {
		return petQuestionnaireId;
	}

	public void setPetQuestionnaireId(Integer petQuestionnaireId) {
		this.petQuestionnaireId = petQuestionnaireId;
	}

	public Integer getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Integer questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public String getQuestionnaireName() {
		return questionnaireName;
	}

	public void setQuestionnaireName(String questionnaireName) {
		this.questionnaireName = questionnaireName;
	}

	public Integer getOccuranceId() {
		return occuranceId;
	}

	public void setOccuranceId(Integer occuranceId) {
		this.occuranceId = occuranceId;
	}

	public Integer getFrequencyId() {
		return frequencyId;
	}

	public void setFrequencyId(Integer frequencyId) {
		this.frequencyId = frequencyId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getOccurance() {
		return occurance;
	}

	public void setOccurance(String occurance) {
		this.occurance = occurance;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

}
