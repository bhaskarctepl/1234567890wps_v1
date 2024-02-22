package com.hillspet.wearables.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetDataExtractStreamDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;
	private Integer studyId;
	private Integer streamDeviceSeqNum;
	private Integer petId;
	private String petName;
	private String studyName;
	private String assetNumber;
	private String streamId;
	private LocalDate startDate;
	private LocalDate endDate;
	private String requestedBy;
	private int requestId;
	private int deviceSeqNum;
	
	private LocalDate extractStartDate;
	private LocalDate extractEndDate;
	private int petStudyId;
	
	private String petType;
	
	
	private int excludeFromDataExtract;
	private int duplicatePetId;
	
	


	public int getExcludeFromDataExtract() {
		return excludeFromDataExtract;
	}

	public void setExcludeFromDataExtract(int excludeFromDataExtract) {
		this.excludeFromDataExtract = excludeFromDataExtract;
	}

	public int getDuplicatePetId() {
		return duplicatePetId;
	}

	public void setDuplicatePetId(int duplicatePetId) {
		this.duplicatePetId = duplicatePetId;
	}

	public String getPetType() {
		return petType;
	}

	public void setPetType(String petType) {
		this.petType = petType;
	}

	public int getPetStudyId() {
		return petStudyId;
	}

	public void setPetStudyId(int petStudyId) {
		this.petStudyId = petStudyId;
	}

	public LocalDate getExtractStartDate() {
		return extractStartDate;
	}

	public void setExtractStartDate(LocalDate extractStartDate) {
		this.extractStartDate = extractStartDate;
	}

	public LocalDate getExtractEndDate() {
		return extractEndDate;
	}

	public void setExtractEndDate(LocalDate extractEndDate) {
		this.extractEndDate = extractEndDate;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public int getDeviceSeqNum() {
		return deviceSeqNum;
	}

	public void setDeviceSeqNum(int deviceSeqNum) {
		this.deviceSeqNum = deviceSeqNum;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

	public String getAssetNumber() {
		return assetNumber;
	}

	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}

	

	public String getStreamId() {
		return streamId;
	}

	public void setStreamId(String streamId) {
		this.streamId = streamId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
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

	public Integer getStreamDeviceSeqNum() {
		return streamDeviceSeqNum;
	}

	public void setStreamDeviceSeqNum(Integer streamDeviceSeqNum) {
		this.streamDeviceSeqNum = streamDeviceSeqNum;
	}

}
