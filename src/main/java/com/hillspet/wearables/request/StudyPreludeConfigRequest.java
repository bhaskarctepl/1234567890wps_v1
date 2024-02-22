package com.hillspet.wearables.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hillspet.wearables.dto.PreludeMandatory;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contains all information that needed to configure prelude fileds", value = "StudyPreludeConfigRequest")
@JsonInclude(value = Include.NON_NULL)
public class StudyPreludeConfigRequest {

	@ApiModelProperty(value = "preludeAssociated", required = false)
	private List<PreludeMandatory> preludeMandatory;

	@ApiModelProperty(value = "preludeAssociated", required = false)
	private List<PreludeAssociated> preludeAssociated;

	public List<PreludeMandatory> getPreludeMandatory() {
		return preludeMandatory;
	}

	public void setPreludeMandatory(List<PreludeMandatory> preludeMandatory) {
		this.preludeMandatory = preludeMandatory;
	}

	public List<PreludeAssociated> getPreludeAssociated() {
		return preludeAssociated;
	}

	public void setPreludeAssociated(List<PreludeAssociated> preludeAssociated) {
		this.preludeAssociated = preludeAssociated;
	}

}
