package com.hillspet.wearables.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Contains all information that needed to extend phase", value = "ExtendPhaseRequest")
@JsonInclude(value = Include.NON_NULL)
public class ExtendPhaseRequest {
	
	private List<ExtendPhase> extendPhases;

	public List<ExtendPhase> getExtendPhases() {
		return extendPhases;
	}

	public void setExtendPhases(List<ExtendPhase> extendPhases) {
		this.extendPhases = extendPhases;
	}
	
	
	
}
