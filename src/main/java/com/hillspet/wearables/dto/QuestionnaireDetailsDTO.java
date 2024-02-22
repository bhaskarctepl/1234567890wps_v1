package com.hillspet.wearables.dto;

import java.sql.Timestamp;
import java.time.LocalDate;

public class QuestionnaireDetailsDTO {
	private Integer questionnaireId;
	private String questionnaireName;
	private Integer studyId;
	private String studyName;
	private Integer petId;
	private String petName;
	private Integer petParentId;
	private String petParentName;
	private LocalDate scheduledDate;
	private Timestamp submittedDate;

	private LocalDate startDate;
	private LocalDate endDate;
	private Integer frequencyId;
	private String frequency;
	private Integer occurrenceId;
	private String occurrence;

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

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getPetParentName() {
		return petParentName;
	}

	public Integer getPetParentId() {
		return petParentId;
	}

	public void setPetParentId(Integer petParentId) {
		this.petParentId = petParentId;
	}

	public void setPetParentName(String petParentName) {
		this.petParentName = petParentName;
	}

	public LocalDate getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(LocalDate scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public Timestamp getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Timestamp submittedDate) {
		this.submittedDate = submittedDate;
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

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("QuestionnaireDetailsDTO{");
		sb.append("questionnaireName='").append(questionnaireName).append('\'');
		sb.append(", studyName='").append(studyName).append('\'');
		sb.append(", petName='").append(petName).append('\'');
		sb.append(", petParentName='").append(petParentName).append('\'');
		sb.append(", submittedDate='").append(submittedDate).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
