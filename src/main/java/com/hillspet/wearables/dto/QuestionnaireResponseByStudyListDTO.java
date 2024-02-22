package com.hillspet.wearables.dto;

import java.sql.Timestamp;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionnaireResponseByStudyListDTO {
	private String petParentName;
	private Integer petId;
	private String petName;
	private Timestamp submittedDate;
	private Integer questionnaireId;
	private Integer studyId;
	private Integer questionnaireResponseId;
	private LocalDate sharedDate;

	public String getPetParentName() {
		return petParentName;
	}

	public void setPetParentName(String petParentName) {
		this.petParentName = petParentName;
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

	public Timestamp getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Timestamp submittedDate) {
		this.submittedDate = submittedDate;
	}

	public Integer getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Integer questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public Integer getQuestionnaireResponseId() {
		return questionnaireResponseId;
	}

	public void setQuestionnaireResponseId(Integer questionnaireResponseId) {
		this.questionnaireResponseId = questionnaireResponseId;
	}

	public LocalDate getSharedDate() {
		return sharedDate;
	}

	public void setSharedDate(LocalDate sharedDate) {
		this.sharedDate = sharedDate;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("QuestionnaireResponseByStudyListDTO{");
		sb.append("petParentName='").append(petParentName).append('\'');
		sb.append(", petName='").append(petName).append('\'');
		sb.append(", sharedDate='").append(sharedDate).append('\'');
		sb.append(", submittedDate=").append(submittedDate);
		sb.append('}');
		return sb.toString();
	}
}
