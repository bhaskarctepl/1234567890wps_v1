/**
 * 
 */
package com.hillspet.wearables.response;

import java.time.LocalDate;
import java.util.List;

import com.hillspet.wearables.dto.QuestionnaireResponseDTO;

/**
 * @author radepu
 * Date: Nov 11, 2024
 */
public class QuestionnireViewExportPetDTO {

	private int petId;
	private String petName;
	private LocalDate sharedDate;
	private LocalDate submittedDate;

	private List<QuestionnaireResponseDTO> questionnaireResponseList;

	public QuestionnireViewExportPetDTO(int petId, String petName, LocalDate sharedDate, LocalDate submittedDate,
			List<QuestionnaireResponseDTO> questionnaireResponseList) {
		super();
		this.petId = petId;
		this.petName = petName;
		this.sharedDate = sharedDate;
		this.submittedDate = submittedDate;
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
	
	
}
