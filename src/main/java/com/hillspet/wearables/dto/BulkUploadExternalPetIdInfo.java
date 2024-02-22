package com.hillspet.wearables.dto;

import java.time.LocalDateTime;

public class BulkUploadExternalPetIdInfo extends ExternalPetIdInfo {
	private Integer stagingId;
	private Integer petId;
	private String petName;
	private String studyId;
	private String studyName;
	private String extPetId;
	private String studyLocation;

	private String exceptionMsg;
	private String attachmentName;

	private int createdUser;
	private LocalDateTime createdDate;

	public Integer getStagingId() {
		return stagingId;
	}

	public void setStagingId(Integer stagingId) {
		this.stagingId = stagingId;
	}

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

	public String getExtPetId() {
		return extPetId;
	}

	public void setExtPetId(String extPetId) {
		this.extPetId = extPetId;
	}

	public String getStudyLocation() {
		return studyLocation;
	}

	public void setStudyLocation(String studyLocation) {
		this.studyLocation = studyLocation;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public int getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(int createdUser) {
		this.createdUser = createdUser;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

}
