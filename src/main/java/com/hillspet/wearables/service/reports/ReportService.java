
/**
 * Created Date: Jan 10, 2021
 * Class Name  : DeviceDetailsReportService.java
 * Description : Description of the package.
 *
 * © Copyright 2020 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>        <Author>        <Date>        <Comments on Change>
 * ID                rmaram          Jan 17, 2021        Mentions the comments on change, for the new file it's not required.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.hillspet.wearables.service.reports;

import java.util.List;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.AssetByStudyWidgetFilter;
import com.hillspet.wearables.dto.CustomerSupportByCategory;
import com.hillspet.wearables.dto.CustomerSupportIssuesByStudy;
import com.hillspet.wearables.dto.TotalAssetsByStausWidgetFilter;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.dto.filter.IssueByStudyWidgetFilter;
import com.hillspet.wearables.dto.filter.IssueWidgetFilter;
import com.hillspet.wearables.dto.filter.PetBfiScoreReportFilter;
import com.hillspet.wearables.dto.filter.PetParentReportFilter;
import com.hillspet.wearables.dto.filter.PointTrackerFilter;
import com.hillspet.wearables.dto.filter.PointsAccumulatedReportFilter;
import com.hillspet.wearables.dto.filter.SupportFilter;
import com.hillspet.wearables.response.CustomerSupportListResponse;
import com.hillspet.wearables.response.DeviceDetailsReportResponse;
import com.hillspet.wearables.response.DeviceHistoryReportResponse;
import com.hillspet.wearables.response.DeviceInventoryReportResponse;
import com.hillspet.wearables.response.DeviceMalfunctionReportResponse;
import com.hillspet.wearables.response.DeviceTrackingReportResponse;
import com.hillspet.wearables.response.DevicesMalfunctionsResponse;
import com.hillspet.wearables.response.DevicesbyStudyResponse;
import com.hillspet.wearables.response.PetBfiScoreReportResponse;
import com.hillspet.wearables.response.PetParentReportResponse;
import com.hillspet.wearables.response.PointTrackerReportResponse;
import com.hillspet.wearables.response.PointsAccumulatedReportResponse;
import com.hillspet.wearables.response.StudyBasedReportResponse;
import com.hillspet.wearables.response.TotalAssetsByStatusReportResponse;
import com.hillspet.wearables.response.TotalAssetsReportResponse;

/**
 * This is the service class for implementing Device Details Report Service
 * details.
 * 
 * @author rmaram
 * @since w1.0
 * @version w1.0
 * 
 */
public interface ReportService {

	DeviceDetailsReportResponse getDeviceDetailsReport(BaseFilter filter) throws ServiceExecutionException;

	DeviceHistoryReportResponse getDeviceHistoryReport(BaseFilter filter) throws ServiceExecutionException;

	DeviceInventoryReportResponse getDeviceInventoryReport(BaseFilter filter) throws ServiceExecutionException;

	DeviceMalfunctionReportResponse getDeviceMalfunctionReport(BaseFilter filter) throws ServiceExecutionException;

	DeviceTrackingReportResponse getDeviceTrackingReport(BaseFilter filter) throws ServiceExecutionException;

	StudyBasedReportResponse getStudyBasedReport(BaseFilter filter, int userId) throws ServiceExecutionException;

	DevicesbyStudyResponse getAssetsDevicesByStudyReport(AssetByStudyWidgetFilter filter)
			throws ServiceExecutionException;

	DevicesMalfunctionsResponse getAssetsDevicesMalfunctionsReport(PointTrackerFilter filter)
			throws ServiceExecutionException;

	PointTrackerReportResponse getPointTrackerReport(PointTrackerFilter filter, int userId)
			throws ServiceExecutionException;

	PointsAccumulatedReportResponse getPointsAccumulatedReport(PointsAccumulatedReportFilter filter, int userId)
			throws ServiceExecutionException;

	CustomerSupportListResponse getCustomerSupportTickets(SupportFilter filter) throws ServiceExecutionException;

	List<CustomerSupportByCategory> getCustomerSupportIssueWidget(IssueWidgetFilter filter)
			throws ServiceExecutionException;

	List<CustomerSupportIssuesByStudy> getCustomerSupportIssueByStudyWidget(IssueByStudyWidgetFilter filter)
			throws ServiceExecutionException;

	TotalAssetsByStatusReportResponse getTotalAssetsByStatus(TotalAssetsByStausWidgetFilter filter)
			throws ServiceExecutionException;

	TotalAssetsReportResponse getTotalAssets() throws ServiceExecutionException;

	PetParentReportResponse getPetParentReport(PetParentReportFilter filter, int userId)
			throws ServiceExecutionException;

	PetBfiScoreReportResponse getPetBfiScoreReport(PetBfiScoreReportFilter filter) throws ServiceExecutionException;

}
