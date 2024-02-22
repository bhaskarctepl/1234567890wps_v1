package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.BulkUploadExternalPetIdInfo;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BulkExtPetIdsUploadResponse extends BaseResultCollection {

	private List<BulkUploadExternalPetIdInfo> externalPetIdsList;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for all ext pet ids that matches the search criteria")
	public List<BulkUploadExternalPetIdInfo> getExternalPetIdsList() {
		return externalPetIdsList;
	}

	public void setExternalPetIdsList(List<BulkUploadExternalPetIdInfo> externalPetIdsList) {
		this.externalPetIdsList = externalPetIdsList;
	}
}
