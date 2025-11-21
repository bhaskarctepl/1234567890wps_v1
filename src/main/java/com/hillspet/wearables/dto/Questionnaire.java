package com.hillspet.wearables.dto;

import java.time.LocalDate;
import java.util.List;

public class Questionnaire {

	private Integer questionnaireId;
	private String questionnaireName;
	private Integer questionnaireTypeId;
	private String questionnaireType;
	private Integer questionnaireCategoryId;
	private String questionnaireCategory;
	private Integer questionnaireLevelId;
	private String questionnaireLevel;

	private LocalDate startDate;
	private LocalDate endDate;
	private Boolean isActive;
	private Boolean isPublished;
	private List<QuestionnaireInstruction> instructions;
	private List<Question> questions;
	private List<QuestionnaireSection> sections;

	// Added for Longitudinal data
	private Integer validityPeriod;
	private String questionnaireImageName;
	private String questionnaireImageUrl;

	// Added as part of enhancements 2024
	private Integer parentQuestionnaireId;

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

	public List<QuestionnaireInstruction> getInstructions() {
		return instructions;
	}

	public void setInstructions(List<QuestionnaireInstruction> instructions) {
		this.instructions = instructions;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public List<QuestionnaireSection> getSections() {
		return sections;
	}

	public void setSections(List<QuestionnaireSection> sections) {
		this.sections = sections;
	}

	public Integer getQuestionnaireLevelId() {
		return questionnaireLevelId;
	}

	public void setQuestionnaireLevelId(Integer questionnaireLevelId) {
		this.questionnaireLevelId = questionnaireLevelId;
	}

	public String getQuestionnaireLevel() {
		return questionnaireLevel;
	}

	public void setQuestionnaireLevel(String questionnaireLevel) {
		this.questionnaireLevel = questionnaireLevel;
	}

	public Integer getValidityPeriod() {
		return validityPeriod;
	}

	public void setValidityPeriod(Integer validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	public String getQuestionnaireImageName() {
		return questionnaireImageName;
	}

	public void setQuestionnaireImageName(String questionnaireImageName) {
		this.questionnaireImageName = questionnaireImageName;
	}

	public String getQuestionnaireImageUrl() {
		return questionnaireImageUrl;
	}

	public void setQuestionnaireImageUrl(String questionnaireImageUrl) {
		this.questionnaireImageUrl = questionnaireImageUrl;
	}

	public Integer getParentQuestionnaireId() {
		return parentQuestionnaireId;
	}

	public void setParentQuestionnaireId(Integer parentQuestionnaireId) {
		this.parentQuestionnaireId = parentQuestionnaireId;
	}
}
