package com.hillspet.wearables.request;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hillspet.wearables.dto.PetDataExtractList;
import io.swagger.annotations.ApiModel;

/**
 * @author sowjanya
 *
 */
@ApiModel(description = "Contains all the information that needed to create petDataExtractStreamsRequest", value = "PetDataExtractStreamsRequest")
@JsonInclude(value = Include.NON_NULL)
public class PetDataExtractStreamsRequest {

	private List<PetDataExtractList> extractStreamList;
	private Integer petId;
	private Integer userId;

	public List<PetDataExtractList> getExtractStreamList() {
		return extractStreamList;
	}

	public void setExtractStreamList(List<PetDataExtractList> extractStreamList) {
		this.extractStreamList = extractStreamList;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}

}
