package com.hillspet.wearables.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contains all the information that needed to create data stream", value = "DataStreamRequest")
@JsonInclude(value = Include.NON_NULL)
public class DuplicatePetRequest {

	@ApiModelProperty(value = "primaryPetId", required = true, example = "Primary Pet Id")
	private int primaryPetId;

	@ApiModelProperty(value = "primaryStreamId", required = true, example = "Duplicate Pet Id")
	private String primaryStreamId;
	
	@ApiModelProperty(value = "primaryExtractStartDate", required = true, example = "Primary Extact Start Date")
	private String primaryExtractStartDate;
	
	@ApiModelProperty(value = "primaryExtractEndDate", required = true, example = "Primary Extract End Date")
	private String primaryExtractEndDate;
	
	@ApiModelProperty(value = "duplicatePetId", required = true, example = "Pet Id Number")
	private String duplicatePetId;
	
	@ApiModelProperty(value = "duplicateStreamId", required = true, example = "Duplicate Pet Id Number")
	private String duplicateStreamId;

	@ApiModelProperty(value = "duplicateExtractStartDate", required = true, example = "Duplicate Extact Start Date")
	private String duplicateExtractStartDate;
	
	@ApiModelProperty(value = "duplicateExtractEndDate", required = true, example = "Duplicate Extract End Date")
	private String duplicateExtractEndDate;
	
	@ApiModelProperty(value = "stitchGroupId", required = true, example = "Duplicate Extract End Date")
	private String stitchGroupId;
	
	@ApiModelProperty(value = "primaryPetStudyDeviceId", required = true, example = "primaryPetStudyDeviceId")
	private Integer primaryPetStudyDeviceId;
	
	@ApiModelProperty(value = "dupPetStudyDeviceId", required = true, example = "dupPetStudyDeviceId")
	private Integer dupPetStudyDeviceId;
	
	private Integer excludeFromDataExtract;
	
	private Integer dupExcludeFromDataExtract;
	
	

	public Integer getDupExcludeFromDataExtract() {
		return dupExcludeFromDataExtract;
	}

	public void setDupExcludeFromDataExtract(Integer dupExcludeFromDataExtract) {
		this.dupExcludeFromDataExtract = dupExcludeFromDataExtract;
	}

	public Integer getExcludeFromDataExtract() {
		return excludeFromDataExtract;
	}

	public void setExcludeFromDataExtract(Integer excludeFromDataExtract) {
		this.excludeFromDataExtract = excludeFromDataExtract;
	}

	public Integer getPrimaryPetStudyDeviceId() {
		return primaryPetStudyDeviceId;
	}

	public void setPrimaryPetStudyDeviceId(Integer primaryPetStudyDeviceId) {
		this.primaryPetStudyDeviceId = primaryPetStudyDeviceId;
	}

	public Integer getDupPetStudyDeviceId() {
		return dupPetStudyDeviceId;
	}

	public void setDupPetStudyDeviceId(Integer dupPetStudyDeviceId) {
		this.dupPetStudyDeviceId = dupPetStudyDeviceId;
	}

	public String getStitchGroupId() {
		return stitchGroupId;
	}

	public void setStitchGroupId(String stitchGroupId) {
		this.stitchGroupId = stitchGroupId;
	}

	public int getPrimaryPetId() {
		return primaryPetId;
	}

	public void setPrimaryPetId(int primaryPetId) {
		this.primaryPetId = primaryPetId;
	}

	public String getPrimaryStreamId() {
		return primaryStreamId;
	}

	public void setPrimaryStreamId(String primaryStreamId) {
		this.primaryStreamId = primaryStreamId;
	}

	public String getPrimaryExtractStartDate() {
		return primaryExtractStartDate;
	}

	public void setPrimaryExtractStartDate(String primaryExtractStartDate) {
		this.primaryExtractStartDate = primaryExtractStartDate;
	}

	public String getPrimaryExtractEndDate() {
		return primaryExtractEndDate;
	}

	public void setPrimaryExtractEndDate(String primaryExtractEndDate) {
		this.primaryExtractEndDate = primaryExtractEndDate;
	}

	public String getDuplicatePetId() {
		return duplicatePetId;
	}

	public void setDuplicatePetId(String duplicatePetId) {
		this.duplicatePetId = duplicatePetId;
	}

	

	public String getDuplicateStreamId() {
		return duplicateStreamId;
	}

	public void setDuplicateStreamId(String duplicateStreamId) {
		this.duplicateStreamId = duplicateStreamId;
	}

	public String getDuplicateExtractStartDate() {
		return duplicateExtractStartDate;
	}

	public void setDuplicateExtractStartDate(String duplicateExtractStartDate) {
		this.duplicateExtractStartDate = duplicateExtractStartDate;
	}

	public String getDuplicateExtractEndDate() {
		return duplicateExtractEndDate;
	}

	public void setDuplicateExtractEndDate(String duplicateExtractEndDate) {
		this.duplicateExtractEndDate = duplicateExtractEndDate;
	}

	
	

}
