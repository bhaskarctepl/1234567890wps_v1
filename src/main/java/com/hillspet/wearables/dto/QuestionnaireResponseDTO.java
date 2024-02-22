package com.hillspet.wearables.dto;

import java.util.List;

public class QuestionnaireResponseDTO {
	private Integer questionId;
	private String question;
	private String questionImageUrl;
	private Integer questionTypeId;
	private String answer;
	private List<QuestionAnswer> answers;

	private QuestionnaireSection section;

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public QuestionnaireSection getSection() {
		return section;
	}

	public void setSection(QuestionnaireSection section) {
		this.section = section;
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

	public Integer getQuestionTypeId() {
		return questionTypeId;
	}

	public void setQuestionTypeId(Integer questionTypeId) {
		this.questionTypeId = questionTypeId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public List<QuestionAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<QuestionAnswer> answers) {
		this.answers = answers;
	}

}
