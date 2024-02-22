package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.PointTrackerRejectReason;


@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackerRejectReasonResponse {

	public List<PointTrackerRejectReason> getTrackerRejectReasons() {
		return trackerRejectReasons;
	}

	public void setTrackerRejectReasons(List<PointTrackerRejectReason> trackerRejectReasons) {
		this.trackerRejectReasons = trackerRejectReasons;
	}

	private List<PointTrackerRejectReason> trackerRejectReasons;


}