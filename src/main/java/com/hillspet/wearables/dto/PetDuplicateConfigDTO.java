package com.hillspet.wearables.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetDuplicateConfigDTO  extends BaseDTO {
	private static final long serialVersionUID = 1L;
	
	private Integer petId;
	private String streamId;
	private LocalDate startDate;
	private LocalDate endDate;
	private String petName;
	private String studyName;
	private String assetNumber;
	private String stitchGroupId;
	private String petType;
	private LocalDate extractStartDate;
	private LocalDate extractEndDate;
	
	private int configId;
	private int petStudyId;
	
	
	private Integer primaryPetId;
	private String primaryPetName;
	
	
	
	private LocalDate primaryPetStartDate;
	private LocalDate primaryPetEndDate;
	
	private String primaryStreamId;
	
	private Integer duplicatePetId;
	private String duplicatePetName;
	private String duplicatePetStreamId;
	private LocalDate duplicatePetStartDate;
	private LocalDate duplicatePetEndDate;
	
	private int isContinuation;
	
	private int excludeFromDataExtract;
	private int dupExcludeFromDataExtract;
	

	
	public int getDupExcludeFromDataExtract() {
		return dupExcludeFromDataExtract;
	}

	public void setDupExcludeFromDataExtract(int dupExcludeFromDataExtract) {
		this.dupExcludeFromDataExtract = dupExcludeFromDataExtract;
	}

	public int getExcludeFromDataExtract() {
		return excludeFromDataExtract;
	}

	public void setExcludeFromDataExtract(int excludeFromDataExtract) {
		this.excludeFromDataExtract = excludeFromDataExtract;
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

	public int getConfigId() {
		return configId;
	}

	public void setConfigId(int configId) {
		this.configId = configId;
	}

	public String getPetType() {
		return petType;
	}

	public void setPetType(String petType) {
		this.petType = petType;
	}

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
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

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public Integer getPrimaryPetId() {
		return primaryPetId;
	}

	public void setPrimaryPetId(Integer primaryPetId) {
		this.primaryPetId = primaryPetId;
	}

	public String getPrimaryPetName() {
		return primaryPetName;
	}

	public void setPrimaryPetName(String primaryPetName) {
		this.primaryPetName = primaryPetName;
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

	public LocalDate getPrimaryPetStartDate() {
		return primaryPetStartDate;
	}

	public void setPrimaryPetStartDate(LocalDate primaryPetStartDate) {
		this.primaryPetStartDate = primaryPetStartDate;
	}

	public LocalDate getPrimaryPetEndDate() {
		return primaryPetEndDate;
	}

	public void setPrimaryPetEndDate(LocalDate primaryPetEndDate) {
		this.primaryPetEndDate = primaryPetEndDate;
	}

	public String getPrimaryStreamId() {
		return primaryStreamId;
	}

	public void setPrimaryStreamId(String primaryStreamId) {
		this.primaryStreamId = primaryStreamId;
	}

	public Integer getDuplicatePetId() {
		return duplicatePetId;
	}

	public void setDuplicatePetId(Integer duplicatePetId) {
		this.duplicatePetId = duplicatePetId;
	}

	public String getDuplicatePetName() {
		return duplicatePetName;
	}

	public void setDuplicatePetName(String duplicatePetName) {
		this.duplicatePetName = duplicatePetName;
	}

	public String getDuplicatePetStreamId() {
		return duplicatePetStreamId;
	}

	public void setDuplicatePetStreamId(String duplicatePetStreamId) {
		this.duplicatePetStreamId = duplicatePetStreamId;
	}

	public LocalDate getDuplicatePetStartDate() {
		return duplicatePetStartDate;
	}

	public void setDuplicatePetStartDate(LocalDate duplicatePetStartDate) {
		this.duplicatePetStartDate = duplicatePetStartDate;
	}

	public LocalDate getDuplicatePetEndDate() {
		return duplicatePetEndDate;
	}

	public void setDuplicatePetEndDate(LocalDate duplicatePetEndDate) {
		this.duplicatePetEndDate = duplicatePetEndDate;
	}

	public int getIsContinuation() {
		return isContinuation;
	}

	public void setIsContinuation(int isContinuation) {
		this.isContinuation = isContinuation;
	}

	public String getStitchGroupId() {
		return stitchGroupId;
	}

	public void setStitchGroupId(String stitchGroupId) {
		this.stitchGroupId = stitchGroupId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
