package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hillspet.wearables.request.PlanSubscribed;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudyPlanListResponse {

	private List<PlanSubscribed> plansSubscribed;

	public List<PlanSubscribed> getPlansSubscribed() {
		return plansSubscribed;
	}

	public void setPlansSubscribed(List<PlanSubscribed> plansSubscribed) {
		this.plansSubscribed = plansSubscribed;
	}
}
