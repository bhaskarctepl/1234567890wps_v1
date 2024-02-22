package com.hillspet.wearables.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class PetDataExtractList {
	private Integer petId;
	private String streamId;
	private Integer streamDeviceSequence;
	private String extractStartDate;
	private String extractEndDate;
	private Integer excludeFromDataExtract;
	private Integer duplicatePetId;
	
	
	
	public Integer getExcludeFromDataExtract() {
		return excludeFromDataExtract;
	}
	public void setExcludeFromDataExtract(Integer excludeFromDataExtract) {
		this.excludeFromDataExtract = excludeFromDataExtract;
	}
	public Integer getDuplicatePetId() {
		return duplicatePetId;
	}
	public void setDuplicatePetId(Integer duplicatePetId) {
		this.duplicatePetId = duplicatePetId;
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
	public String getExtractStartDate() {
		return extractStartDate;
	}
	public void setExtractStartDate(String extractStartDate) {
		this.extractStartDate = extractStartDate;
	}
	public String getExtractEndDate() {
		return extractEndDate;
	}
	public void setExtractEndDate(String extractEndDate) {
		this.extractEndDate = extractEndDate;
	}
	public Integer getStreamDeviceSequence() {
		return streamDeviceSequence;
	}
	public void setStreamDeviceSequence(Integer streamDeviceSequence) {
		this.streamDeviceSequence = streamDeviceSequence;
	}
	
	

}
