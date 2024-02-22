package com.hillspet.wearables.dto;

public class PreludeReport {
	private  Integer extractId;
	private  Integer studyId;
	private String extractName;
	private String extractDate;
	private String extractFileUrl;
	private String modifiedDate;
	private String createDate;
	private String extractFileCategory;
	private String studyName;
	
	public String getStudyName() {
		return studyName;
	}
	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}
	public String getExtractFileCategory() {
		return extractFileCategory;
	}
	public void setExtractFileCategory(String extractFileCategory) {
		this.extractFileCategory = extractFileCategory;
	}
	public Integer getExtractId() {
		return extractId;
	}
	public void setExtractId(Integer extractId) {
		this.extractId = extractId;
	}
	public Integer getStudyId() {
		return studyId;
	}
	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}
	public String getExtractName() {
		return extractName;
	}
	public void setExtractName(String extractName) {
		this.extractName = extractName;
	}
	public String getExtractDate() {
		return extractDate;
	}
	public void setExtractDate(String extractDate) {
		this.extractDate = extractDate;
	}
	public String getExtractFileUrl() {
		return extractFileUrl;
	}
	public void setExtractFileUrl(String extractFileUrl) {
		this.extractFileUrl = extractFileUrl;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	

}
