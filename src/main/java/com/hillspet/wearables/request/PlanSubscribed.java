package com.hillspet.wearables.request;

import java.time.LocalDate;

import io.swagger.annotations.ApiModelProperty;

public class PlanSubscribed {

	@ApiModelProperty(value = "planId", required = true)
	private Integer planId;

	@ApiModelProperty(value = "planName", required = false)
	private String planName;

	@ApiModelProperty(value = "subscribedDate", required = true)
	private LocalDate subscribedDate;

	@Override
	public String toString() {
		return planId + "#" + subscribedDate;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public LocalDate getSubscribedDate() {
		return subscribedDate;
	}

	public void setSubscribedDate(LocalDate subscribedDate) {
		this.subscribedDate = subscribedDate;
	}

}
