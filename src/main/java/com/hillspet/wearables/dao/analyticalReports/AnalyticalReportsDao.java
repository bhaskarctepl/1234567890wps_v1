package com.hillspet.wearables.dao.analyticalReports;

import java.util.List;
import java.util.Map;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.AnalyticalReport;
import com.hillspet.wearables.dto.AnalyticalReportGroup;
import com.hillspet.wearables.dto.ManageReports;
import com.hillspet.wearables.dto.PreludeReport;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.dto.filter.PreludeReportFilter;

public interface AnalyticalReportsDao {

	List<AnalyticalReportGroup> getReportGroups() throws ServiceExecutionException;

	ManageReports addReport(ManageReports manageReports, String platform) throws ServiceExecutionException;
	
	ManageReports updateReport(ManageReports manageReports, String platform) throws ServiceExecutionException;
	
	ManageReports getReportById(int reportId, String platform) throws ServiceExecutionException;
	
	void deleteReport(int reportId, int modifiedBy, String platform) throws ServiceExecutionException;

	Map<String, Integer> getReportListCount(BaseFilter filter, String platform) throws ServiceExecutionException;

	List<AnalyticalReport> getReportList(BaseFilter filter, String platform) throws ServiceExecutionException;
	
	List<AnalyticalReport> getReportsByReportGroupId(BaseFilter filter, String platform) throws ServiceExecutionException;

	Map<String, Integer> getReportsByReportGroupIdCount(BaseFilter filter, String platform) throws ServiceExecutionException;
	
	List<PreludeReport> getPreludeReport(PreludeReportFilter filter, String platform) throws ServiceExecutionException;

	Map<String, Integer> getPreludeReportCount(PreludeReportFilter filter, String platform) throws ServiceExecutionException;
}
