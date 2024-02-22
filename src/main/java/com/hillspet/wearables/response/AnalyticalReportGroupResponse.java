package com.hillspet.wearables.response;

import java.util.List;

import com.hillspet.wearables.dto.AnalyticalReportGroup;

public class AnalyticalReportGroupResponse {
	public List<AnalyticalReportGroup> getAnalyticalReportGroupList() {
		return analyticalReportGroupList;
	}

	public void setAnalyticalReportGroupList(List<AnalyticalReportGroup> analyticalReportGroupList) {
		this.analyticalReportGroupList = analyticalReportGroupList;
	}

	List<AnalyticalReportGroup> analyticalReportGroupList;
}
