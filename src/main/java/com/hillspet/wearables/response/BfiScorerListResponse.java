package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hillspet.wearables.dto.BfiScorer;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BfiScorerListResponse {
	List<BfiScorer> bfiScorerList;

	public List<BfiScorer> getBfiScorerList() {
		return bfiScorerList;
	}

	public void setBfiScorerList(List<BfiScorer> bfiScorerList) {
		this.bfiScorerList = bfiScorerList;
	}

}
