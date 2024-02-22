package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PointTrackerRejectReason {

	private Integer trackerRejectReasonId;
	private String reason;
	
	public Integer getTrackerRejectReasonId() {
		return trackerRejectReasonId;
	}
	public void setTrackerRejectReasonId(Integer trackerRejectReasonId) {
		this.trackerRejectReasonId = trackerRejectReasonId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

}
