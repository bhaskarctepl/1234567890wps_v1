/**
 * 
 */
package com.hillspet.wearables.response;

import java.time.LocalDate;
import java.util.List;

import com.hillspet.wearables.dto.QuestionAnswer;

/**
 * @author radepu
 * Date: Nov 11, 2024
 */
public class QuestionnaireExportDTO {
	
	private int questionnaireId;
	private String questionnaireName;
	private int studyId;
	private String studyName;
	private String fullName;
	private String email;
	private int petId;
	private String petName;
	private LocalDate submittedDate;
	private LocalDate sharedDate;
	
	private Integer questionId;
	private String question;
	private String questionImageUrl;
	private String questionCode;
	
	private List<QuestionAnswer> answers;
	
	
	/**
	 * 
	 */
	public QuestionnaireExportDTO() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public QuestionnaireExportDTO(int questionnaireId, int petId) {
		super();
		this.questionnaireId = questionnaireId;
		this.petId = petId;
	}



	public QuestionnaireExportDTO(int questionnaireId, String questionnaireName, int studyId, String studyName,
			String fullName, String email, int petId, String petName, LocalDate submittedDate, LocalDate sharedDate,
			Integer questionId, String question, String questionImageUrl, String questionCode,
			List<QuestionAnswer> answers) {
		super();
		this.questionnaireId = questionnaireId;
		this.questionnaireName = questionnaireName;
		this.studyId = studyId;
		this.studyName = studyName;
		this.fullName = fullName;
		this.email = email;
		this.petId = petId;
		this.petName = petName;
		this.submittedDate = submittedDate;
		this.sharedDate = sharedDate;
		this.questionId = questionId;
		this.question = question;
		this.questionImageUrl = questionImageUrl;
		this.questionCode = questionCode;
		this.answers = answers;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(int questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public String getQuestionnaireName() {
		return questionnaireName;
	}

	public void setQuestionnaireName(String questionnaireName) {
		this.questionnaireName = questionnaireName;
	}

	public int getStudyId() {
		return studyId;
	}

	public void setStudyId(int studyId) {
		this.studyId = studyId;
	}

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public LocalDate getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(LocalDate submittedDate) {
		this.submittedDate = submittedDate;
	}

	public LocalDate getSharedDate() {
		return sharedDate;
	}

	public void setSharedDate(LocalDate sharedDate) {
		this.sharedDate = sharedDate;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getQuestionImageUrl() {
		return questionImageUrl;
	}

	public void setQuestionImageUrl(String questionImageUrl) {
		this.questionImageUrl = questionImageUrl;
	}

	public String getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}

	public List<QuestionAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<QuestionAnswer> answers) {
		this.answers = answers;
	}
	
	

}
