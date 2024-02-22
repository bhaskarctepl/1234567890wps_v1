package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class QuestionnaireResponseFilter extends BaseFilter {

	@ApiParam(name = "questionnaireId", value = "Questionnaire Id")
	@QueryParam("questionnaireId")
	private String questionnaireId;

	@ApiParam(name = "studyId", value = "Study Id")
	@QueryParam("studyId")
	private String studyId;
	
	@ApiParam(name = "phaseId", value = "Phase Id")
	@QueryParam("phaseId")
	private String phaseId;

	@ApiParam(name = "petId", value = "Pet Id")
	@QueryParam("petId")
	private String petId;

	@ApiParam(name = "startDate", value = "Start Date is the first date component value of date range")
	@QueryParam("startDate")
	private String startDate;

	@ApiParam(name = "endDate", value = "End Date is the second date component value of date range")
	@QueryParam("endDate")
	private String endDate;
	
	@ApiParam(name = "questionnaireTypeId", value = "Search Questionnaire Type Id")
	@QueryParam("questionnaireTypeId")
	private String questionnaireTypeId;
	
	@ApiParam(name = "questionnaireLevelId", value = "Search Questionnaire Level Id")
	@QueryParam("questionnaireLevelId")
	private String questionnaireLevelId;
	
	

	public String getQuestionnaireLevelId() {
		return questionnaireLevelId;
	}

	public void setQuestionnaireLevelId(String questionnaireLevelId) {
		this.questionnaireLevelId = questionnaireLevelId;
	}

	public QuestionnaireResponseFilter() {

	}

	public String getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(String questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	public String getPetId() {
		return petId;
	}

	public void setPetId(String petId) {
		this.petId = petId;
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

	public String getQuestionnaireTypeId() {
		return questionnaireTypeId;
	}

	public void setQuestionnaireTypeId(String questionnaireTypeId) {
		this.questionnaireTypeId = questionnaireTypeId;
	}

	public String getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(String phaseId) {
		this.phaseId = phaseId;
	}
	
}
