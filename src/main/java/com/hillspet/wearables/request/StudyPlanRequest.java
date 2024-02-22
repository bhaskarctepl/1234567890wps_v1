package com.hillspet.wearables.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contains all information that needed to map plan to a study", value = "StudyPlanRequest")
@JsonInclude(value = Include.NON_NULL)
public class StudyPlanRequest {

	@ApiModelProperty(value = "plansSubscribed", required = true)
	private List<PlanSubscribed> plansSubscribed;

	public List<PlanSubscribed> getPlansSubscribed() {
		return plansSubscribed;
	}

	public void setPlansSubscribed(List<PlanSubscribed> plansSubscribed) {
		this.plansSubscribed = plansSubscribed;
	}

}
