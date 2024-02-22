package com.hillspet.wearables.dto;

public class QuestionAnswer {
	private String answerOptionId;
	private String answer;
	private String mediaType;
	private String mediaFileName;
	private String mediaUrl;

	public String getAnswerOptionId() {
		return answerOptionId;
	}

	public void setAnswerOptionId(String answerOptionId) {
		this.answerOptionId = answerOptionId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getMediaFileName() {
		return mediaFileName;
	}

	public void setMediaFileName(String mediaFileName) {
		this.mediaFileName = mediaFileName;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

}
