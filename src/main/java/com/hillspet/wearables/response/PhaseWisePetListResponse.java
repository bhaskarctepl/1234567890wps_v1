package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.PhaseWisePetListDTO;
 
import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhaseWisePetListResponse extends BaseResultCollection {

	private List<PhaseWisePetListDTO> pets;
	private List<PhaseWisePetListDTO> petList;
	
	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for Pets based on search criteria")
	public List<PhaseWisePetListDTO> getPets() {
		return pets;
	}
	public void setPets(List<PhaseWisePetListDTO> pets) {
		this.pets = pets;
	}
	
	@ApiModelProperty(value = "List of details for the Pet list")
	public List<PhaseWisePetListDTO> getPetList() {
		return petList;
	}
	public void setPetList(List<PhaseWisePetListDTO> petList) {
		this.petList = petList;
	}
	
	
}
