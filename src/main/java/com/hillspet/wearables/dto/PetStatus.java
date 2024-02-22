package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetStatus extends BaseDTO {

	private Integer petStatusId;
	private String statusName;

	public PetStatus() {

	}

	public Integer getPetStatusId() {
		return petStatusId;
	}

	public void setPetStatusId(Integer petStatusId) {
		this.petStatusId = petStatusId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

}
