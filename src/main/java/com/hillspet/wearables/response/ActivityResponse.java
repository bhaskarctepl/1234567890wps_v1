package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hillspet.wearables.dto.AuditLogActivity;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivityResponse {
	
	private List<AuditLogActivity> activities;
	
	@ApiModelProperty(value = "List of Audit Log Activity details by menuId and entityId")
	public List<AuditLogActivity> getActivities() {
		return activities;
	}

	public void setActivities(List<AuditLogActivity> activities) {
		this.activities = activities;
	}
	
	

}
