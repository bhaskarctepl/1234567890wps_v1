package com.hillspet.wearables.response;

import java.time.LocalDateTime;

public class StudyNotesResponse {
	private Integer studyNoteId;
	private String content;
	private LocalDateTime createdDate;
	private String userName;

	public Integer getStudyNoteId() {
		return studyNoteId;
	}

	public void setStudyNoteId(Integer studyNoteId) {
		this.studyNoteId = studyNoteId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
