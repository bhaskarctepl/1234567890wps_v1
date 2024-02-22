package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DuplicatePetConfigDTO {
	
	private Integer primaryPetId;
	private String petName;
	
	private String dataStreamId;
	private String duplicatePets;
	
	private String petStitchGroupId;
	
	
	
	public String getPetStitchGroupId() {
		return petStitchGroupId;
	}
	public void setPetStitchGroupId(String petStitchGroupId) {
		this.petStitchGroupId = petStitchGroupId;
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
	public String getDataStreamId() {
		return dataStreamId;
	}
	public void setDataStreamId(String dataStreamId) {
		this.dataStreamId = dataStreamId;
	}
	public String getDuplicatePets() {
		return duplicatePets;
	}
	public void setDuplicatePets(String duplicatePets) {
		this.duplicatePets = duplicatePets;
	}
	
	
}
