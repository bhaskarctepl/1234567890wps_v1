package com.hillspet.wearables.response;

import java.util.List;

import com.hillspet.wearables.dto.PointTrackerActivity;

public class PointTrackerActivityResponse {
	private List<PointTrackerActivity> pointTrackerActivities;

	public List<PointTrackerActivity> getPointTrackerActivities() {
		return pointTrackerActivities;
	}

	public void setPointTrackerActivities(List<PointTrackerActivity> pointTrackerActivities) {
		this.pointTrackerActivities = pointTrackerActivities;
	}
}
