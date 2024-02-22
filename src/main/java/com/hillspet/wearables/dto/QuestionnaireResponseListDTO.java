package com.hillspet.wearables.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionnaireResponseListDTO extends BaseDTO {

	private Integer questionnaireId;
	private String questionnaireName;
	private String respondentPetParentName;
	private String petName;
	private String study;
	private Integer studyId;
	private LocalDate submitedON;
	private LocalDate startDate;
	private LocalDate endDate;
	private Double completionPercentage;
	
	
	
	public Double getCompletionPercentage() {
		return completionPercentage;
	}
	public void setCompletionPercentage(Double completionPercentage) {
		this.completionPercentage = completionPercentage;
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
	public Integer getStudyId() {
		return studyId;
	}
	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
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
	public String getRespondentPetParentName() {
		return respondentPetParentName;
	}
	public void setRespondentPetParentName(String respondentPetParentName) {
		this.respondentPetParentName = respondentPetParentName;
	}
	public String getPetName() {
		return petName;
	}
	public void setPetName(String petName) {
		this.petName = petName;
	}
	public String getStudy() {
		return study;
	}
	public void setStudy(String study) {
		this.study = study;
	}
	public LocalDate getSubmitedON() {
		return submitedON;
	}
	public void setSubmitedON(LocalDate submitedON) {
		this.submitedON = submitedON;
	}


}



