package com.hillspet.wearables.response;

import java.util.List;

import com.hillspet.wearables.dto.BehaviorType;

import io.swagger.annotations.ApiModelProperty;

public class BehaviorTypeResponse {
	private List<BehaviorType> behaviorTypes;

	@ApiModelProperty(value = "List of details for Behavior Types")
	public List<BehaviorType> getBehaviorTypes() {
		return behaviorTypes;
	}

	public void setBehaviorTypes(List<BehaviorType> behaviorTypes) {
		this.behaviorTypes = behaviorTypes;
	}
}
