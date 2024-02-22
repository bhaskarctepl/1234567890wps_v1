package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class QuestionnaireViewResponseFilter {
	@ApiParam(name = "questionnaireId")
	@QueryParam("questionnaireId")
	private String questionnaireId;

	@ApiParam(name = "studyId")
	@QueryParam("studyId")
	private String studyId;

	@ApiParam(name = "petId")
	@QueryParam("petId")
	private String petId;

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

}
