package com.hillspet.wearables.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudyDiary {

	private int studyDiaryId;
	
	private int petId;
	private String petName;

	private int studyId;
	private String studyName;

	private int studyPhaseDayId;
	private String studyPhaseName;

	private int studyEventId;
	private String studyEventName;

	private int studyTaskId;
	private String studyTaskName;

	private String comments;

	private LocalDate createdDate;
	private int createdBy;
	private String createdByUser;
	
	
	
	
	public String getStudyPhaseName() {
		return studyPhaseName;
	}
	public void setStudyPhaseName(String studyPhaseName) {
		this.studyPhaseName = studyPhaseName;
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
	public int getStudyDairyId() {
		return studyDiaryId;
	}
	public void setStudyDairyId(int studyDairyId) {
		this.studyDiaryId = studyDairyId;
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
	public int getStudyPhaseDayId() {
		return studyPhaseDayId;
	}
	public void setStudyPhaseDayId(int studyPhaseDayId) {
		this.studyPhaseDayId = studyPhaseDayId;
	}
	public int getStudyEventId() {
		return studyEventId;
	}
	public void setStudyEventId(int studyEventId) {
		this.studyEventId = studyEventId;
	}
	public String getStudyEventName() {
		return studyEventName;
	}
	public void setStudyEventName(String studyEventName) {
		this.studyEventName = studyEventName;
	}
	public int getStudyTaskId() {
		return studyTaskId;
	}
	public void setStudyTaskId(int studyTaskId) {
		this.studyTaskId = studyTaskId;
	}
	public String getStudyTaskName() {
		return studyTaskName;
	}
	public void setStudyTaskName(String studyTaskName) {
		this.studyTaskName = studyTaskName;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public LocalDate getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedByUser() {
		return createdByUser;
	}
	public void setCreatedByUser(String createdByUser) {
		this.createdByUser = createdByUser;
	}
	
	

}
