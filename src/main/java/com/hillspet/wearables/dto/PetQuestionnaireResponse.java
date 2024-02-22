package com.hillspet.wearables.dto;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetQuestionnaireResponse {
	private String petParentName;
	private Integer petId;
	private String petName;
	private Timestamp submittedDate;
	private String submittedOn;
	private Integer questionnaireResponseId;
	private Integer questionnaireId;
	private String questionnaireName;
	private Integer questionId;
	private String questionName;
	private Integer questionTypeId;
	private String questionType;
	private String answerOpts;
	private String answer;
	private Integer studyId;
	private String studyNames;
	private String questionImageName;
	private String questionImageUrl;
	
	private String answerImage;
	private String answerUrl;
	
	private List<QuestionAnswer> answers;
	

	public String getAnswerImage() {
		return answerImage;
	}

	public void setAnswerImage(String answerImage) {
		this.answerImage = answerImage;
	}

	public String getAnswerUrl() {
		return answerUrl;
	}

	public void setAnswerUrl(String answerUrl) {
		this.answerUrl = answerUrl;
	}

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

	public String getSubmittedOn() {
		return submittedOn;
	}

	public void setSubmittedOn(String submittedOn) {
		this.submittedOn = submittedOn;
	}

	public Integer getQuestionnaireResponseId() {
		return questionnaireResponseId;
	}

	public void setQuestionnaireResponseId(Integer questionnaireResponseId) {
		this.questionnaireResponseId = questionnaireResponseId;
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

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public Integer getQuestionTypeId() {
		return questionTypeId;
	}

	public void setQuestionTypeId(Integer questionTypeId) {
		this.questionTypeId = questionTypeId;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getAnswerOpts() {
		return answerOpts;
	}

	public void setAnswerOpts(String answerOpts) {
		this.answerOpts = answerOpts;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public String getStudyNames() {
		return studyNames;
	}

	public void setStudyNames(String studyNames) {
		this.studyNames = studyNames;
	}

	public String getQuestionImageName() {
		return questionImageName;
	}

	public void setQuestionImageName(String questionImageName) {
		this.questionImageName = questionImageName;
	}

	public String getQuestionImageUrl() {
		return questionImageUrl;
	}

	public void setQuestionImageUrl(String questionImageUrl) {
		this.questionImageUrl = questionImageUrl;
	}
	
	public List<QuestionAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<QuestionAnswer> answers) {
		this.answers = answers;
	}
}
