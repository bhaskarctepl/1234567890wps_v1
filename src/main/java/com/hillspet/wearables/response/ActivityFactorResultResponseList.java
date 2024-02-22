package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivityFactorResultResponseList extends BaseResultCollection {

	private List<ActivityFactorResultResponse> resultList;

	@JsonProperty("rows")
	@ApiModelProperty(value = "Result List")
	public List<ActivityFactorResultResponse> getResultList() {
		return resultList;
	}

	public void setResultList(List<ActivityFactorResultResponse> resultList) {
		this.resultList = resultList;
	}
	
	
	
	
}
