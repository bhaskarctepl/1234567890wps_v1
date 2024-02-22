package com.hillspet.wearables.response;

import java.util.List;

import com.hillspet.wearables.dto.QuestionnaireDetailsDTO;
import com.hillspet.wearables.dto.QuestionnaireResponseDTO;
import com.hillspet.wearables.dto.QuestionnaireSection;

public class QuestionnaireViewResponse {
    private QuestionnaireDetailsDTO questionnaireDetails;
    private List<QuestionnaireResponseDTO> questionnaireResponseList;
    
    private List<QuestionnaireSection> sectionList;
    

    public List<QuestionnaireSection> getSectionList() {
		return sectionList;
	}

	public void setSectionList(List<QuestionnaireSection> sectionList) {
		this.sectionList = sectionList;
	}

	public QuestionnaireDetailsDTO getQuestionnaireDetails() {
        return questionnaireDetails;
    }

    public void setQuestionnaireDetails(QuestionnaireDetailsDTO questionnaireDetails) {
        this.questionnaireDetails = questionnaireDetails;
    }

    public List<QuestionnaireResponseDTO> getQuestionnaireResponseList() {
        return questionnaireResponseList;
    }

    public void setQuestionnaireResponseList(List<QuestionnaireResponseDTO> questionnaireResponseList) {
        this.questionnaireResponseList = questionnaireResponseList;
    }
}
