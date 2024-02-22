package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.BehaviorHistory;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BehaviorHistoryResponse {
	private List<BehaviorHistory> behaviorHistory;

	@JsonProperty("rows")
	public List<BehaviorHistory> getBehaviorHistory() {
		return behaviorHistory;
	}

	public void setBehaviorHistory(List<BehaviorHistory> behaviorHistory) {
		this.behaviorHistory = behaviorHistory;
	}

}
