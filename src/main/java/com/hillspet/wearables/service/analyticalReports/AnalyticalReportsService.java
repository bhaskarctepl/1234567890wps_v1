package com.hillspet.wearables.service.analyticalReports;

import java.util.List;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.AnalyticalReportGroup;
import com.hillspet.wearables.dto.ManageReports;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.dto.filter.PreludeReportFilter;
import com.hillspet.wearables.response.AnalyticalReportResponse;
import com.hillspet.wearables.response.GCSignedUrlResponse;
import com.hillspet.wearables.response.PreludeReportResponse;

public interface AnalyticalReportsService {

	List<AnalyticalReportGroup> getReportGroups() throws ServiceExecutionException;
	public ManageReports addReport(ManageReports manageReports, String platform) throws ServiceExecutionException;
	public ManageReports updateReport(ManageReports manageReports, String platform) throws ServiceExecutionException;
	public ManageReports getReportById(int reportId, String platform) throws ServiceExecutionException;
	void deleteReport(int reportId, int modifiedBy, String platform) throws ServiceExecutionException;
	AnalyticalReportResponse getReportList(BaseFilter filter, String platform) throws ServiceExecutionException;
	AnalyticalReportResponse getReportsByReportGroupId(BaseFilter filter, String platform) throws ServiceExecutionException;
	PreludeReportResponse getPreludeReport(PreludeReportFilter filter, String platform) throws ServiceExecutionException;
	GCSignedUrlResponse getPreludeMediaSignedUrl(String filePath) throws ServiceExecutionException;
}
