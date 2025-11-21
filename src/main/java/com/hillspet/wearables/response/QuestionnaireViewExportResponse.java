/**
 * 
 */
package com.hillspet.wearables.response;

import java.time.LocalDate;
import java.util.List;

import com.hillspet.wearables.dto.QuestionnaireResponseDTO;

/**
 * @author radepu Date: Nov 11, 2024
 */
public class QuestionnaireViewExportResponse {

	private int questionnaireId;
	private String questionnaireName;

	private String studyName;
	private String petParentName;
	private String petParentEmail;
	private int petId;
	private String petName;
	
	private LocalDate sharedDate;
	private LocalDate submittedDate;

	private List<QuestionnaireResponseDTO> questionnaireResponseList;
	
	
	public QuestionnaireViewExportResponse(int questionnaireId, String questionnaireName,
			String studyName, String petParentName, String petParentEmail, int petId, String petName,
			LocalDate sharedDate, LocalDate submittedDate, List<QuestionnaireResponseDTO> questionnaireResponseList) {
		super();
		this.questionnaireId = questionnaireId;
		this.questionnaireName = questionnaireName;
		this.studyName = studyName;
		this.petParentName = petParentName;
		this.petParentEmail = petParentEmail;
		this.petId = petId;
		this.petName = petName;
		this.sharedDate = sharedDate;
		this.submittedDate = submittedDate;
		this.questionnaireResponseList = questionnaireResponseList;
	}

	public QuestionnaireViewExportResponse() {
		// TODO Auto-generated constructor stub
	}

	public LocalDate getSharedDate() {
		return sharedDate;
	}



	public void setSharedDate(LocalDate sharedDate) {
		this.sharedDate = sharedDate;
	}



	public LocalDate getSubmittedDate() {
		return submittedDate;
	}



	public void setSubmittedDate(LocalDate submittedDate) {
		this.submittedDate = submittedDate;
	}



	public List<QuestionnaireResponseDTO> getQuestionnaireResponseList() {
		return questionnaireResponseList;
	}



	public void setQuestionnaireResponseList(List<QuestionnaireResponseDTO> questionnaireResponseList) {
		this.questionnaireResponseList = questionnaireResponseList;
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

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

	public String getPetParentName() {
		return petParentName;
	}

	public void setPetParentName(String petParentName) {
		this.petParentName = petParentName;
	}

	public String getPetParentEmail() {
		return petParentEmail;
	}

	public void setPetParentEmail(String petParentEmail) {
		this.petParentEmail = petParentEmail;
	}

	 
}
