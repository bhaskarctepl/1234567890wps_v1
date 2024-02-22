package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.PetNote;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetNotesResponse extends BaseResultCollection{
	private List<PetNote> petNotes;

	@ApiModelProperty(value = "Get Pet Notes of particular id")
	@JsonProperty("rows")
	public List<PetNote> getPetNotes() {
		return petNotes;
	}

	public void setPetNotes(List<PetNote> petNotes) {
		this.petNotes = petNotes;
	}

}
