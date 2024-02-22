package com.hillspet.wearables.response;

import java.util.List;

import com.hillspet.wearables.dto.StudyNotes;

public class StudyNotesListResponse {

	private List<StudyNotes> notes;

	public List<StudyNotes> getNotes() {
		return notes;
	}

	public void setNotes(List<StudyNotes> notes) {
		this.notes = notes;
	}
}
