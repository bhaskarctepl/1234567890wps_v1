package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.PetExternalInfoListDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetExternalInfoResponse extends BaseResultCollection {
	private List<PetExternalInfoListDTO> externalPetInfo;

	public List<PetExternalInfoListDTO> getExternalPetInfo() {
		return externalPetInfo;
	}

	public void setExternalPetInfo(List<PetExternalInfoListDTO> externalPetInfo) {
		this.externalPetInfo = externalPetInfo;
	}
	
	
}
