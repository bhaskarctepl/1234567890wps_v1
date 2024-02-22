package com.hillspet.wearables.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetSchedQuestionnaire {

	private Integer petQuestionnaireConfigId;
	private Integer questionnaireId;
	private String questionnaireName;
	private LocalDate startDate;
	private LocalDate endDate;
	private Integer frequencyId;
	private String frequency;
	private Integer occurrenceId;
	private String occurrence;
	private String pets;
	private List<PetQuestionnaireReccurrence> petQuestionnaireReccurrences;
	private Boolean isDeleted;
	private int responseCount;

	public Integer getPetQuestionnaireConfigId() {
		return petQuestionnaireConfigId;
	}

	public void setPetQuestionnaireConfigId(Integer petQuestionnaireConfigId) {
		this.petQuestionnaireConfigId = petQuestionnaireConfigId;
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

	public Integer getFrequencyId() {
		return frequencyId;
	}

	public void setFrequencyId(Integer frequencyId) {
		this.frequencyId = frequencyId;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public Integer getOccurrenceId() {
		return occurrenceId;
	}

	public void setOccurrenceId(Integer occurrenceId) {
		this.occurrenceId = occurrenceId;
	}

	public String getOccurrence() {
		return occurrence;
	}

	public void setOccurrence(String occurrence) {
		this.occurrence = occurrence;
	}

	public String getPets() {
		return pets;
	}

	public void setPets(String pets) {
		this.pets = pets;
	}

	public List<PetQuestionnaireReccurrence> getPetQuestionnaireReccurrences() {
		return petQuestionnaireReccurrences;
	}

	public void setPetQuestionnaireReccurrences(List<PetQuestionnaireReccurrence> petQuestionnaireReccurrences) {
		this.petQuestionnaireReccurrences = petQuestionnaireReccurrences;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getResponseCount() {
		return responseCount;
	}

	public void setResponseCount(int responseCount) {
		this.responseCount = responseCount;
	}

}
