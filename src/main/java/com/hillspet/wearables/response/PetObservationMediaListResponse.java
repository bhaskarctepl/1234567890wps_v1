
package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.PetObservationMediaListDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetObservationMediaListResponse extends BaseResultCollection {
	private List<PetObservationMediaListDTO> PetObservationMediaList;
	private List<PetObservationMediaListDTO> PetObservationMedia;
	
	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for Pets based on search criteria")
	
	public List<PetObservationMediaListDTO> getPetObservationMediaList() {
		return PetObservationMediaList;
	}
	public void setPetObservationMediaList(List<PetObservationMediaListDTO> petObservationMediaList) {
		PetObservationMediaList = petObservationMediaList;
	}
	public List<PetObservationMediaListDTO> getPetObservationMedia() {
		return PetObservationMedia;
	}
	public void setPetObservationMedia(List<PetObservationMediaListDTO> petObservationMedia) {
		PetObservationMedia = petObservationMedia;
	}



	

}
