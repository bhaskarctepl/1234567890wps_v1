/**
 * Created Date: Jan 15, 2021
 * Class Name  : DeviceDetailsReportDao.java
 * Description : Description of the package.
 *
 * © Copyright 2020 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>        <Author>        <Date>        <Comments on Change>
 * ID                rmaram          Jan 11, 2021        Mentions the comments on change, for the new file it's not required.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.hillspet.wearables.dao.reports;

import java.util.List;
import java.util.Map;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.AssetByStudyWidgetFilter;
import com.hillspet.wearables.dto.CustomerSupportByCategory;
import com.hillspet.wearables.dto.CustomerSupportIssuesByStudy;
import com.hillspet.wearables.dto.DeviceDetailsReport;
import com.hillspet.wearables.dto.DeviceHistoryReport;
import com.hillspet.wearables.dto.DeviceInventoryReport;
import com.hillspet.wearables.dto.DeviceMalfunctionReport;
import com.hillspet.wearables.dto.DeviceTrackingReport;
import com.hillspet.wearables.dto.DevicesMalfunctionsReport;
import com.hillspet.wearables.dto.DevicesbyStudyReport;
import com.hillspet.wearables.dto.PetBfiScoreReport;
import com.hillspet.wearables.dto.PetParentReport;
import com.hillspet.wearables.dto.PointTrackerReport;
import com.hillspet.wearables.dto.PointsAccumulatedReport;
import com.hillspet.wearables.dto.StudyBasedReport;
import com.hillspet.wearables.dto.SupportListDTO;
import com.hillspet.wearables.dto.TotalAssetsByStausWidgetFilter;
import com.hillspet.wearables.dto.TotalAssetsListDTO;
import com.hillspet.wearables.dto.TotalAssetsbyStatusListDTO;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.dto.filter.IssueByStudyWidgetFilter;
import com.hillspet.wearables.dto.filter.IssueWidgetFilter;
import com.hillspet.wearables.dto.filter.PetBfiScoreReportFilter;
import com.hillspet.wearables.dto.filter.PetParentReportFilter;
import com.hillspet.wearables.dto.filter.PointTrackerFilter;
import com.hillspet.wearables.dto.filter.PointsAccumulatedReportFilter;
import com.hillspet.wearables.dto.filter.SupportFilter;

/**
 * Gets DeviceDetailsReportDao the db.
 * 
 * @author rmaram
 * @since - w1.0
 * @version - w1.0
 */
public interface ReportDao {

	public Map<String, Integer> getDeviceDetailsReportCount(BaseFilter filter) throws ServiceExecutionException;

	public List<DeviceDetailsReport> getDeviceDetailsReport(BaseFilter filter) throws ServiceExecutionException;

	public Map<String, Integer> getDeviceHistoryReportCount(BaseFilter filter) throws ServiceExecutionException;

	public List<DeviceHistoryReport> getDeviceHistoryReport(BaseFilter filter) throws ServiceExecutionException;

	public Map<String, Integer> getDeviceInventoryReportCount(BaseFilter filter) throws ServiceExecutionException;

	public List<DeviceInventoryReport> getDeviceInventoryReport(BaseFilter filter) throws ServiceExecutionException;

	public Map<String, Integer> getDeviceMalfunctionReportCount(BaseFilter filter) throws ServiceExecutionException;

	public List<DeviceMalfunctionReport> getDeviceMalfunctionReport(BaseFilter filter) throws ServiceExecutionException;

	public Map<String, Integer> getDeviceTrackingReportCount(BaseFilter filter) throws ServiceExecutionException;

	public List<DeviceTrackingReport> getDeviceTrackingReport(BaseFilter filter) throws ServiceExecutionException;

	Map<String, Integer> getStudyBasedReportCount(BaseFilter filter, int userId) throws ServiceExecutionException;

	public List<StudyBasedReport> getStudyBasedReport(BaseFilter filter, int userId) throws ServiceExecutionException;

	public List<DevicesbyStudyReport> getAssetsDevicesByStudyReport(AssetByStudyWidgetFilter filter)
			throws ServiceExecutionException;

	public List<DevicesMalfunctionsReport> getAssetsDevicesMalfunctionsReport(PointTrackerFilter filter)
			throws ServiceExecutionException;

	int getAssetsDevicesByStudyReportCount(BaseFilter filter) throws ServiceExecutionException;

	int getAssetsDevicesMalfunctionsReportCount(BaseFilter filter) throws ServiceExecutionException;

	public Map<String, Integer> getPointTrackerReportCount(PointTrackerFilter filter, int userId)
			throws ServiceExecutionException;

	public List<PointTrackerReport> getPointTrackerReport(PointTrackerFilter filter, int userId)
			throws ServiceExecutionException;

	public Map<String, Integer> getPointsAccumulatedReportCount(PointsAccumulatedReportFilter filter, int userId)
			throws ServiceExecutionException;

	public List<PointsAccumulatedReport> getPointsAccumulatedReport(PointsAccumulatedReportFilter filter, int userId)
			throws ServiceExecutionException;

	public Map<String, Integer> getCustomerSupportTicketsCount(SupportFilter filter) throws ServiceExecutionException;

	public List<SupportListDTO> getCustomerSupportTickets(SupportFilter filter) throws ServiceExecutionException;

	public List<CustomerSupportByCategory> getCustomerSupportIssueWidget(IssueWidgetFilter filter)
			throws ServiceExecutionException;

	public List<CustomerSupportIssuesByStudy> getCustomerSupportIssueByStudyWidget(IssueByStudyWidgetFilter filter)
			throws ServiceExecutionException;

	public List<TotalAssetsbyStatusListDTO> getTotalAssetsByStatus(TotalAssetsByStausWidgetFilter filter)
			throws ServiceExecutionException;

	public List<TotalAssetsListDTO> getTotalAssets() throws ServiceExecutionException;

	public List<PetParentReport> getPetParentReport(PetParentReportFilter filter, int userId)
			throws ServiceExecutionException;

	public Map<String, Integer> getPetParentReportCount(PetParentReportFilter filter, int userId)
			throws ServiceExecutionException;

	public Map<String, Integer> getPetBfiScoreReportCount(PetBfiScoreReportFilter filter)
			throws ServiceExecutionException;

	public List<PetBfiScoreReport> getPetBfiScoreReport(PetBfiScoreReportFilter filter)
			throws ServiceExecutionException;

}
