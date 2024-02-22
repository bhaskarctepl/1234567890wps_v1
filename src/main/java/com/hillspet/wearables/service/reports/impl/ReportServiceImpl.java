package com.hillspet.wearables.service.reports.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.reports.ReportDao;
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
import com.hillspet.wearables.service.reports.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

	private static final Logger LOGGER = LogManager.getLogger(ReportServiceImpl.class);

	@Autowired
	private ReportDao reportDao;

	@Override
	public DeviceDetailsReportResponse getDeviceDetailsReport(BaseFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getDeviceDetailsReport called");
		Map<String, Integer> mapper = reportDao.getDeviceDetailsReportCount(filter);
		int total = mapper.get("count");
		int totalCount = mapper.get("totalCount");
		List<DeviceDetailsReport> deviceDetailsReportList = total > 0 ? reportDao.getDeviceDetailsReport(filter)
				: new ArrayList<>();

		DeviceDetailsReportResponse response = new DeviceDetailsReportResponse();
		response.setDeviceDetailsReport(deviceDetailsReportList);
		response.setNoOfElements(deviceDetailsReportList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);
		LOGGER.debug("getDeviceDetailsReport deviceDetailsReportList is {}", deviceDetailsReportList);
		LOGGER.debug("getDeviceDetailsReport completed successfully");
		return response;
	}

	@Override
	public DeviceHistoryReportResponse getDeviceHistoryReport(BaseFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getDeviceHistoryReport called");
		Map<String, Integer> mapper = reportDao.getDeviceHistoryReportCount(filter);
		int total = mapper.get("count");
		int totalCount = mapper.get("totalCount");
		List<DeviceHistoryReport> deviceHistoryReportList = total > 0 ? reportDao.getDeviceHistoryReport(filter)
				: new ArrayList<>();

		DeviceHistoryReportResponse response = new DeviceHistoryReportResponse();
		response.setDeviceHistoryReport(deviceHistoryReportList);
		response.setNoOfElements(deviceHistoryReportList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);
		LOGGER.debug("getDeviceHistoryReport deviceHistoryReportList is {}", deviceHistoryReportList);
		LOGGER.debug("getDeviceHistoryReport completed successfully");
		return response;
	}

	@Override
	public DeviceInventoryReportResponse getDeviceInventoryReport(BaseFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getDeviceInventoryReport called");
		Map<String, Integer> mapper = reportDao.getDeviceInventoryReportCount(filter);
		int total = mapper.get("count");
		int totalCount = mapper.get("totalCount");
		List<DeviceInventoryReport> deviceInventoryReportList = total > 0 ? reportDao.getDeviceInventoryReport(filter)
				: new ArrayList<>();

		DeviceInventoryReportResponse response = new DeviceInventoryReportResponse();
		response.setDeviceInventoryReport(deviceInventoryReportList);
		response.setNoOfElements(deviceInventoryReportList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);

		LOGGER.debug("getDeviceInventoryReport getDeviceInventoryReport is {}", deviceInventoryReportList);
		LOGGER.debug("getDeviceInventoryReport completed successfully");
		return response;
	}

	@Override
	public DeviceMalfunctionReportResponse getDeviceMalfunctionReport(BaseFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getDeviceMalfunctionReport called");
		Map<String, Integer> mapper = reportDao.getDeviceMalfunctionReportCount(filter);
		int total = mapper.get("count");
		int totalCount = mapper.get("totalCount");
		List<DeviceMalfunctionReport> deviceMalfunctionReportList = total > 0
				? reportDao.getDeviceMalfunctionReport(filter)
				: new ArrayList<>();

		DeviceMalfunctionReportResponse response = new DeviceMalfunctionReportResponse();
		response.setDeviceMalfunctionReport(deviceMalfunctionReportList);
		response.setNoOfElements(deviceMalfunctionReportList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);

		LOGGER.debug("getDeviceMalfunctionReport deviceDetailsReportList is {}", deviceMalfunctionReportList);
		LOGGER.debug("getDeviceMalfunctionReport completed successfully");
		return response;
	}

	@Override
	public DeviceTrackingReportResponse getDeviceTrackingReport(BaseFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getDeviceTrackingReport called");
		Map<String, Integer> mapper = reportDao.getDeviceTrackingReportCount(filter);
		int total = mapper.get("count");
		int totalCount = mapper.get("totalCount");

		List<DeviceTrackingReport> deviceTrackingReportResponseList = total > 0
				? reportDao.getDeviceTrackingReport(filter)
				: new ArrayList<>();

		DeviceTrackingReportResponse response = new DeviceTrackingReportResponse();
		response.setDeviceTrackingReport(deviceTrackingReportResponseList);
		response.setNoOfElements(deviceTrackingReportResponseList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);

		LOGGER.debug("getDeviceTrackingReport getDeviceTrackingReportList is {}", deviceTrackingReportResponseList);
		LOGGER.debug("getDeviceTrackingReport completed successfully");
		return response;
	}

	@Override
	public StudyBasedReportResponse getStudyBasedReport(BaseFilter filter, int userId)
			throws ServiceExecutionException {
		LOGGER.debug("getStudyBasedReport called");
		Map<String, Integer> mapper = reportDao.getStudyBasedReportCount(filter, userId);
		int total = mapper.get("count");
		int totalCount = mapper.get("totalCount");
		List<StudyBasedReport> studyBasedReportResponseList = total > 0 ? reportDao.getStudyBasedReport(filter, userId)
				: new ArrayList<>();

		StudyBasedReportResponse response = new StudyBasedReportResponse();
		response.setStudyBasedReportList(studyBasedReportResponseList);
		response.setNoOfElements(studyBasedReportResponseList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);

		LOGGER.debug("getStudyBasedReport getStudyBasedReportList is {}", studyBasedReportResponseList);
		LOGGER.debug("getStudyBasedReport completed successfully");
		return response;
	}

	@Override
	public DevicesbyStudyResponse getAssetsDevicesByStudyReport(AssetByStudyWidgetFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getAssetsDevicesByStudyReport called");
//		int total = reportDao.getAssetsDevicesByStudyReportCount(filter);

		List<DevicesbyStudyReport> devicesbyStudyReportList = reportDao.getAssetsDevicesByStudyReport(filter);

		DevicesbyStudyResponse response = new DevicesbyStudyResponse();
		response.setDevicesbyStudyReportList(devicesbyStudyReportList);
		response.setNoOfElements(devicesbyStudyReportList.size());
//		response.setTotalRecords(total);

		LOGGER.debug("getAssetsDevicesByStudyReport getAssetsDevicesByStudyReportList is {}", devicesbyStudyReportList);
		LOGGER.debug("getAssetsDevicesByStudyReport completed successfully");
		return response;
	}

	@Override
	public DevicesMalfunctionsResponse getAssetsDevicesMalfunctionsReport(PointTrackerFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getAssetsDevicesMalfunctionsReport called");
//		int total = reportDao.getAssetsDevicesMalfunctionsReportCount(filter);

		List<DevicesMalfunctionsReport> devicesMalfunctionsReportList = reportDao
				.getAssetsDevicesMalfunctionsReport(filter);

		DevicesMalfunctionsResponse response = new DevicesMalfunctionsResponse();
		response.setDevicesMalfunctionsReportList(devicesMalfunctionsReportList);
		;
		response.setNoOfElements(devicesMalfunctionsReportList.size());
//		response.setTotalRecords(total);

		LOGGER.debug("getAssetsDevicesMalfunctionsReport getAssetsDevicesMalfunctionsReportList is {}",
				devicesMalfunctionsReportList);
		LOGGER.debug("getAssetsDevicesMalfunctionsReport completed successfully");
		return response;
	}

	@Override
	public PointTrackerReportResponse getPointTrackerReport(PointTrackerFilter filter, int userId)
			throws ServiceExecutionException {
		LOGGER.debug("getPointTrackerReport called");
		Map<String, Integer> mapper = reportDao.getPointTrackerReportCount(filter, userId);
		int total = mapper.get("count");
		int totalCount = mapper.get("totalCount");
		List<PointTrackerReport> pointTrackerReportList = total > 0 ? reportDao.getPointTrackerReport(filter, userId)
				: new ArrayList<>();
		PointTrackerReportResponse response = new PointTrackerReportResponse();
		response.setPointTrackerReportList(pointTrackerReportList);
		response.setNoOfElements(pointTrackerReportList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);
		LOGGER.debug("getPointTrackerReport pointTrackerReportList size is {}", pointTrackerReportList);
		LOGGER.debug("getPointTrackerReport completed successfully");
		return response;
	}

	@Override
	public PointsAccumulatedReportResponse getPointsAccumulatedReport(PointsAccumulatedReportFilter filter, int userId)
			throws ServiceExecutionException {
		LOGGER.debug("getPointsAccumulatedReport called");
		Map<String, Integer> mapper = reportDao.getPointsAccumulatedReportCount(filter, userId);
		int total = mapper.get("count");
		int totalCount = mapper.get("totalCount");
		List<PointsAccumulatedReport> pointsAccumulatedReports = total > 0
				? reportDao.getPointsAccumulatedReport(filter, userId)
				: new ArrayList<>();
		PointsAccumulatedReportResponse response = new PointsAccumulatedReportResponse();
		response.setPointsAccumulatedReports(pointsAccumulatedReports);
		response.setNoOfElements(pointsAccumulatedReports.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);
		LOGGER.debug("getPointTrackerReport pointsAccumulatedReports size is {}", pointsAccumulatedReports);
		LOGGER.debug("getPointTrackerReport completed successfully");
		return response;
	}

	@Override
	public CustomerSupportListResponse getCustomerSupportTickets(SupportFilter filter)
			throws ServiceExecutionException {
		Map<String, Integer> mapper = reportDao.getCustomerSupportTicketsCount(filter);
		int total = mapper.get("count");
		int totalCount = mapper.get("totalCount");
		List<SupportListDTO> supportList = total > 0 ? reportDao.getCustomerSupportTickets(filter) : new ArrayList<>();
		CustomerSupportListResponse response = new CustomerSupportListResponse();
		response.setSupportList(supportList);
		response.setNoOfElements(supportList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);
		return response;
	}

	@Override
	public List<CustomerSupportByCategory> getCustomerSupportIssueWidget(IssueWidgetFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getCustomerSupportIssueWidget called");
		List<CustomerSupportByCategory> response = reportDao.getCustomerSupportIssueWidget(filter);
		LOGGER.debug("getCustomerSupportIssueWidget completed successfully");
		return response;
	}

	@Override
	public List<CustomerSupportIssuesByStudy> getCustomerSupportIssueByStudyWidget(IssueByStudyWidgetFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getCustomerSupportIssueByStudyWidget called");
		List<CustomerSupportIssuesByStudy> response = reportDao.getCustomerSupportIssueByStudyWidget(filter);
		LOGGER.debug("getCustomerSupportIssueByStudyWidget completed successfully");
		return response;
	}

	@Override
	public TotalAssetsReportResponse getTotalAssets() throws ServiceExecutionException {
		LOGGER.debug("getTotalAssets called");
		List<TotalAssetsListDTO> totalAssetsList = reportDao.getTotalAssets();
		TotalAssetsReportResponse response = new TotalAssetsReportResponse();

		response.setTotalAssetsList(totalAssetsList);

		response.setNoOfElements(totalAssetsList.size());
		response.setTotalRecords(totalAssetsList.get(0).getDeviceTotalCount());
		LOGGER.debug("getTotalAssets completed successfully");
		return response;
	}

	@Override
	public TotalAssetsByStatusReportResponse getTotalAssetsByStatus(TotalAssetsByStausWidgetFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getTotalAssetsByStatus called");
		List<TotalAssetsbyStatusListDTO> totalAssetsbyStatusList = reportDao.getTotalAssetsByStatus(filter);
		TotalAssetsByStatusReportResponse response = new TotalAssetsByStatusReportResponse();
		response.setTotalAssetsbyStatusList(totalAssetsbyStatusList);
		response.setNoOfElements(totalAssetsbyStatusList.size());
		response.setTotalRecords(totalAssetsbyStatusList.get(0).getTotalDeviceCount());
		LOGGER.debug("getTotalAssetsByStatus completed successfully");
		return response;
	}

	@Override
	public PetParentReportResponse getPetParentReport(PetParentReportFilter filter, int userId)
			throws ServiceExecutionException {
		LOGGER.debug("getPetParentReport called");

		Map<String, Integer> mapper = reportDao.getPetParentReportCount(filter, userId);
		int total = mapper.get("count");
		int totalCount = mapper.get("totalCount");

		List<PetParentReport> petParentReportList = total > 0 ? reportDao.getPetParentReport(filter, userId)
				: new ArrayList<>();

		PetParentReportResponse response = new PetParentReportResponse();
		response.setPetParentReport(petParentReportList);
		response.setNoOfElements(petParentReportList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);

		// long pendingCount = petParentReportList.stream().filter(e ->
		// e.getStatus().equals("Pending")).count();
		// long completedCount = petParentReportList.stream().filter(e ->
		// e.getStatus().equals("Completed")).count();

		response.setPendingCount(mapper.get("pendingCount"));
		response.setCompletedCount(mapper.get("completedCount"));

		LOGGER.debug("getPetParentReport PetParentReportCount size is {}", petParentReportList);
		LOGGER.debug("getPetParentReport completed successfully");

		return response;
	}

	@Override
	public PetBfiScoreReportResponse getPetBfiScoreReport(PetBfiScoreReportFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getPetBfiScoreReport called");

		Map<String, Integer> mapper = reportDao.getPetBfiScoreReportCount(filter);
		int total = mapper.get("count");
		int totalCount = mapper.get("totalCount");

		List<PetBfiScoreReport> petBfiScoreReports = total > 0 ? reportDao.getPetBfiScoreReport(filter)
				: new ArrayList<>();

		PetBfiScoreReportResponse response = new PetBfiScoreReportResponse();
		response.setPetBfiScoreReports(petBfiScoreReports);
		response.setNoOfElements(petBfiScoreReports.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);

		LOGGER.debug("getPetBfiScoreReport size is {}", petBfiScoreReports);
		LOGGER.debug("getPetBfiScoreReport completed successfully");

		return response;
	}

}
