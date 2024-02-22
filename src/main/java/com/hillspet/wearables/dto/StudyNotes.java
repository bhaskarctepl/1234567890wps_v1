package com.hillspet.wearables.dto;

import java.time.LocalDateTime;

public class StudyNotes {
	private Integer studyNoteId;
	private String notes;
	private LocalDateTime createdDate;
	private String userName;

	public Integer getStudyNoteId() {
		return studyNoteId;
	}

	public void setStudyNoteId(Integer studyNoteId) {
		this.studyNoteId = studyNoteId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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
