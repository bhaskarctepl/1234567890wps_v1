package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetExternalInfoListDTO {
	private String externalPetId;
	private String externalPetValue;
	
	public String getExternalPetId() {
		return externalPetId;
	}
	public void setExternalPetId(String externalPetId) {
		this.externalPetId = externalPetId;
	}
	public String getExternalPetValue() {
		return externalPetValue;
	}
	public void setExternalPetValue(String externalPetValue) {
		this.externalPetValue = externalPetValue;
	}
	
	
	
	

}
