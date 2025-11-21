package com.hillspet.wearables.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PointTrackerQuestionnaire {

	private Integer phaseId;

	private Integer questionnaireId;

	private Integer questionnaireConfigId;

	private String phaseName;

	public String getPhaseDays() {
		return phaseDays;
	}

	public void setPhaseDays(String phaseDays) {
		this.phaseDays = phaseDays;
	}

	private String questionnaireName;

	private String startDate;

	private String endDate;

	private String phaseDays;

	public Integer getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(Integer phaseId) {
		this.phaseId = phaseId;
	}

	public Integer getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Integer questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public Integer getQuestionnaireConfigId() {
		return questionnaireConfigId;
	}

	public void setQuestionnaireConfigId(Integer questionnaireConfigId) {
		this.questionnaireConfigId = questionnaireConfigId;
	}

	public String getPhaseName() {
		return phaseName;
	}

	public void setPhaseName(String phaseName) {
		this.phaseName = phaseName;
	}

	public String getQuestionnaireName() {
		return questionnaireName;
	}

	public void setQuestionnaireName(String questionnaireName) {
		this.questionnaireName = questionnaireName;
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
