package com.hillspet.wearables.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionnariesByStudy {
	
	private String phaseName;
	
	private String Questionnaire;
	
	private String startDate;
	
	private String endDate;
	
	private int StudyConfidId;
	
	private int StudyId;
	
	private int PhaseId;
	
	private int QuestionnaireId;
	
	private String phaseDays;

	public String getPhaseDays() {
		return phaseDays;
	}

	public void setPhaseDays(String phaseDays) {
		this.phaseDays = phaseDays;
	}

	public int getStudyConfidId() {
		return StudyConfidId;
	}

	public void setStudyConfidId(int studyConfidId) {
		StudyConfidId = studyConfidId;
	}

	public int getStudyId() {
		return StudyId;
	}

	public void setStudyId(int studyId) {
		StudyId = studyId;
	}

	public int getPhaseId() {
		return PhaseId;
	}

	public void setPhaseId(int phaseId) {
		PhaseId = phaseId;
	}

	public int getQuestionnaireId() {
		return QuestionnaireId;
	}

	public void setQuestionnaireId(int questionnaireId) {
		QuestionnaireId = questionnaireId;
	}

	public String getPhaseName() {
		return phaseName;
	}

	public void setPhaseName(String phaseName) {
		this.phaseName = phaseName;
	}

	public String getQuestionnaire() {
		return Questionnaire;
	}

	public void setQuestionnaire(String questionnaire) {
		Questionnaire = questionnaire;
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
	
	
	

}
