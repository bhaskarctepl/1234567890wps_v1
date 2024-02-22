package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.PetStudyDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetStudyResponse {
	private List<PetStudyDTO> petStudy;

	@ApiModelProperty(value = "Get Pet details of particular studies list")
	public List<PetStudyDTO> getPetStudy() {
		return petStudy;
	}

	public void setPetStudy(List<PetStudyDTO> petStudy) {
		this.petStudy = petStudy;
	}
	
	
}
