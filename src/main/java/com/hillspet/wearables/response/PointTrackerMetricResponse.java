package com.hillspet.wearables.response;

import java.util.List;

import com.hillspet.wearables.dto.PointTrackerMetric;

public class PointTrackerMetricResponse {
	private List<PointTrackerMetric> pointTrackerMetrics;

	public List<PointTrackerMetric> getPointTrackerMetrics() {
		return pointTrackerMetrics;
	}

	public void setPointTrackerMetrics(List<PointTrackerMetric> pointTrackerMetrics) {
		this.pointTrackerMetrics = pointTrackerMetrics;
	}
}
