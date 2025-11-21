package com.hillspet.wearables.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionnaireListDTO extends BaseDTO {
	private Integer questionnaireId;
	private String questionnaireName;
	private Integer questionnaireTypeId;
	private String questionnaireType;
	private Integer questionnaireCategoryId;
	private String questionnaireCategory;

	private LocalDate startDate;
	private LocalDate endDate;
	private Boolean isActive;
	private Boolean isPublished;
	private Boolean isRepublished;

	private String questionnaireLevel;

	public String getQuestionnaireLevel() {
		return questionnaireLevel;
	}

	public void setQuestionnaireLevel(String questionnaireLevel) {
		this.questionnaireLevel = questionnaireLevel;
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

	public Integer getQuestionnaireTypeId() {
		return questionnaireTypeId;
	}

	public void setQuestionnaireTypeId(Integer questionnaireTypeId) {
		this.questionnaireTypeId = questionnaireTypeId;
	}

	public String getQuestionnaireType() {
		return questionnaireType;
	}

	public void setQuestionnaireType(String questionnaireType) {
		this.questionnaireType = questionnaireType;
	}

	public Integer getQuestionnaireCategoryId() {
		return questionnaireCategoryId;
	}

	public void setQuestionnaireCategoryId(Integer questionnaireCategoryId) {
		this.questionnaireCategoryId = questionnaireCategoryId;
	}

	public String getQuestionnaireCategory() {
		return questionnaireCategory;
	}

	public void setQuestionnaireCategory(String questionnaireCategory) {
		this.questionnaireCategory = questionnaireCategory;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}

	public Boolean getIsRepublished() {
		return isRepublished;
	}

	public void setIsRepublished(Boolean isRepublished) {
		this.isRepublished = isRepublished;
	}

}
