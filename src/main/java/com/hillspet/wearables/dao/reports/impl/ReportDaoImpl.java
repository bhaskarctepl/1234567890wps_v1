
/**
 * Created Date: Jan 15, 2021
 * Class Name  : DeviceDetailsReportDaoImpl.java
 * Description : Description of the package.
 *
 * © Copyright 2020 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>        <Author>        <Date>        <Comments on Change>
 * ID                rmaram          Jan 15, 2021        Mentions the comments on change, for the new file it's not required.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.hillspet.wearables.dao.reports.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.constants.SQLConstants;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.common.utils.GCPClientUtil;
import com.hillspet.wearables.configuration.LocalDateTimeDeserializer;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.reports.ReportDao;
import com.hillspet.wearables.dto.AssetByStudyWidgetFilter;
import com.hillspet.wearables.dto.AssetIssueCountByStudy;
import com.hillspet.wearables.dto.AssetStatusCountByStudy;
import com.hillspet.wearables.dto.CustomerSupportByCategory;
import com.hillspet.wearables.dto.CustomerSupportIssuesByStudy;
import com.hillspet.wearables.dto.DeviceDetailsReport;
import com.hillspet.wearables.dto.DeviceHistoryReport;
import com.hillspet.wearables.dto.DeviceInventoryReport;
import com.hillspet.wearables.dto.DeviceMalfunctionReport;
import com.hillspet.wearables.dto.DeviceTrackingReport;
import com.hillspet.wearables.dto.DevicesMalfunctionsReport;
import com.hillspet.wearables.dto.DevicesbyStudyReport;
import com.hillspet.wearables.dto.PetBfiImage;
import com.hillspet.wearables.dto.PetBfiScoreReport;
import com.hillspet.wearables.dto.PetParentReport;
import com.hillspet.wearables.dto.PointTrackerReport;
import com.hillspet.wearables.dto.PointsAccumulatedReport;
import com.hillspet.wearables.dto.ScoreInfo;
import com.hillspet.wearables.dto.StudyBasedReport;
import com.hillspet.wearables.dto.SupportListDTO;
import com.hillspet.wearables.dto.TotalAssetsByStausWidgetFilter;
import com.hillspet.wearables.dto.TotalAssetsListDTO;
import com.hillspet.wearables.dto.TotalAssetsbyStatusListDTO;
import com.hillspet.wearables.dto.TotalModelListDTO;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.dto.filter.IssueByStudyWidgetFilter;
import com.hillspet.wearables.dto.filter.IssueWidgetFilter;
import com.hillspet.wearables.dto.filter.PetBfiScoreReportFilter;
import com.hillspet.wearables.dto.filter.PetParentReportFilter;
import com.hillspet.wearables.dto.filter.PointTrackerFilter;
import com.hillspet.wearables.dto.filter.PointsAccumulatedReportFilter;
import com.hillspet.wearables.dto.filter.SupportFilter;

/**
 * This class retrieves Device Details Report from DB Procedure.
 * 
 * @author rmaram
 * @version w1.0
 */
@Repository
public class ReportDaoImpl extends BaseDaoImpl implements ReportDao {

	private static final Logger LOGGER = LogManager.getLogger(ReportDaoImpl.class);

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private GCPClientUtil gcpClientUtil;

	@Override
	public Map<String, Integer> getDeviceDetailsReportCount(BaseFilter filter) throws ServiceExecutionException {
		int totalCount = NumberUtils.INTEGER_ZERO;
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		LOGGER.debug("getDeviceDetailsReportCount called");
		try {
			counts = selectForObject(SQLConstants.FN_GET_DEVICE_DETAILS_REPORT_COUNT, String.class,
					filter.getSearchText(), filter.getFilterType(), filter.getFilterValue(), filter.getUserId(),
					filter.getRoleTypeId());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceDetailsReportCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<DeviceDetailsReport> getDeviceDetailsReport(BaseFilter filter) throws ServiceExecutionException {
		List<DeviceDetailsReport> deviceDetailsReportList = new ArrayList<>();
		LOGGER.debug("getDeviceDetailsReport called");
		try {
			jdbcTemplate.query(SQLConstants.GET_DEVICE_DETAILS_REPORT, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					DeviceDetailsReport deviceDetailsReport = new DeviceDetailsReport();

					// set the column values to fields like below
					// deviceDetailsReport.setSlNumber(rs.getInt("slNo"));
					deviceDetailsReport.setDeviceType(rs.getString("DEVICE_TYPE"));
					deviceDetailsReport.setDeviceModel(rs.getString("DEVICE_MODEL"));
					deviceDetailsReport.setDeviceNumber(rs.getString("DEVICE_NUMBER"));
					if (rs.getTimestamp("ADD_DATE") != null) {
						deviceDetailsReport.setAddDate(rs.getTimestamp("ADD_DATE").toLocalDateTime());
					}
					if (rs.getString("START_DATE") != null) {
						deviceDetailsReport.setStartDate(rs.getString("START_DATE"));
					}
					deviceDetailsReport.setMfgFirmware(rs.getString("MFG_FIRMWARE"));
					deviceDetailsReport.setMfgSerialNumber(rs.getString("MFG_SERIAL_NUMBER"));
					deviceDetailsReport.setIsActive(rs.getBoolean("IS_ACTIVE"));
					String statusName = "";
					if (rs.getInt("IS_ACTIVE") == 1) {
						statusName = "Active";
					}
					if (rs.getInt("IS_ACTIVE") == 0) {
						statusName = "Inactive";
					}
					deviceDetailsReport.setStatus(statusName);
					deviceDetailsReport.setAssetStatus(rs.getString("STATUS_NAME"));
					deviceDetailsReport.setAssetStatusId(rs.getString("STATUS_ID"));

					deviceDetailsReportList.add(deviceDetailsReport);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getFilterType(), filter.getFilterValue(), filter.getUserId(), filter.getRoleTypeId());

		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceDetailsReport", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return deviceDetailsReportList;
	}

	@Override
	public Map<String, Integer> getDeviceHistoryReportCount(BaseFilter filter) throws ServiceExecutionException {
		int totalCount = NumberUtils.INTEGER_ZERO;
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		LOGGER.debug("getDeviceHistoryReportCount called");
		try {
			counts = selectForObject(SQLConstants.FN_GET_DEVICE_HISTORY_REPORT_COUNT, String.class,
					filter.getSearchText(), filter.getFilterType(), filter.getFilterValue(), filter.getUserId());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceHistoryReportCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<DeviceHistoryReport> getDeviceHistoryReport(BaseFilter filter) throws ServiceExecutionException {
		List<DeviceHistoryReport> deviceHistoryReportList = new ArrayList<>();
		LOGGER.debug("getDeviceHistoryReport called");
		try {
			jdbcTemplate.query(SQLConstants.GET_DEVICE_HISTORY_REPORT, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					DeviceHistoryReport deviceHistoryReport = new DeviceHistoryReport();

					// set the column values to fields like below
					// JDA.PET_ID, BMP.STUDY_ID, BPI.PET_NAME, BS.STUDY_NAME, BDI.MODIFIED_DATE
					// deviceHistoryReport.setSlNumber(rs.getInt("slNo"));
					deviceHistoryReport.setDeviceType(rs.getString("DEVICE_TYPE"));
					deviceHistoryReport.setDeviceModel(rs.getString("DEVICE_MODEL"));
					deviceHistoryReport.setDeviceNumber(rs.getString("DEVICE_NUMBER"));

					if (rs.getTimestamp("ASSIGN_DATE") != null)
						deviceHistoryReport.setAssignDate(rs.getTimestamp("ASSIGN_DATE").toLocalDateTime());

					if (rs.getTimestamp("UN_ASSIGN_DATE") != null)
						deviceHistoryReport.setUnAssignDate(rs.getTimestamp("UN_ASSIGN_DATE").toLocalDateTime());

					deviceHistoryReport.setPetName(rs.getString("PET_NAME"));
					deviceHistoryReport.setStudyName(rs.getString("STUDY_NAME"));
					if (deviceHistoryReport.getAssignDate() != null) {
						deviceHistoryReport.setAssignmentHistory((deviceHistoryReport.getAssignDate() != null
								? DateTimeFormatter.ofPattern("MM/dd/yyyy").format(deviceHistoryReport.getAssignDate())
										+ " - "
								: "")
								+ (deviceHistoryReport.getUnAssignDate() != null
										? DateTimeFormatter.ofPattern("MM/dd/yyyy")
												.format(deviceHistoryReport.getUnAssignDate()) + " - "
										: "")
								+ (rs.getString("PET_NAME") != null ? rs.getString("PET_NAME") + " - " : "")
								+ (rs.getString("STUDY_NAME") != null ? rs.getString("STUDY_NAME") : "")

						);
					}

					deviceHistoryReportList.add(deviceHistoryReport);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getFilterType(), filter.getFilterValue(), filter.getUserId());

		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceHistoryReport", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return deviceHistoryReportList;
	}

	@Override
	public Map<String, Integer> getDeviceInventoryReportCount(BaseFilter filter) throws ServiceExecutionException {
		int totalCount = NumberUtils.INTEGER_ZERO;
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		LOGGER.debug("getDeviceInventoryReportCount called");
		try {
			counts = selectForObject(SQLConstants.FN_GET_DEVICE_INVENTORY_REPORT_COUNT, String.class,
					filter.getSearchText(), filter.getFilterType(), filter.getFilterValue(), filter.getUserId());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceInventoryReportCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	// @Override
	public int getDeviceInventoryReportCountMock(BaseFilter filter) throws ServiceExecutionException {

		return 1;
	}

	// @Override
	public List<DeviceInventoryReport> getDeviceInventoryReportMock(BaseFilter filter)
			throws ServiceExecutionException {
		List<DeviceInventoryReport> deviceInventoryReportList = new ArrayList<>();
		LOGGER.debug("getDeviceInventoryReport called");
		DeviceInventoryReport deviceInventoryReport = new DeviceInventoryReport();

		deviceInventoryReport.setDeviceType("Sensor");
		deviceInventoryReport.setDeviceModel("");
		deviceInventoryReport.setMalfunctionedDevices("478");
		deviceInventoryReport.setTotalDevices("3618");
		deviceInventoryReport.setAvailableDevices("3346");
		deviceInventoryReport.setInUseDevices("3346");

		deviceInventoryReportList.add(deviceInventoryReport);
		return deviceInventoryReportList;
	}

	@Override
	public List<DeviceInventoryReport> getDeviceInventoryReport(BaseFilter filter) throws ServiceExecutionException {
		List<DeviceInventoryReport> deviceInventoryReportList = new ArrayList<>();
		LOGGER.debug("getDeviceInventoryReport called");
		try {
			jdbcTemplate.query(SQLConstants.GET_DEVICE_INVENTORY_REPORT, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					DeviceInventoryReport deviceInventoryReport = new DeviceInventoryReport();
					// deviceInventoryReport.setSlNumber(rs.getInt("slNo"));
					deviceInventoryReport.setDeviceType(rs.getString("DEVICE_TYPE"));
					deviceInventoryReport.setDeviceModel(rs.getString("DEVICE_MODEL"));
					deviceInventoryReport.setMalfunctionedDevices(rs.getString("MALFUNCTIONED_DEVICES"));
					deviceInventoryReport.setTotalDevices(rs.getString("TOTAL_DEVICES"));
					deviceInventoryReport.setInUseDevices(rs.getString("IN_USE_DEVICES"));
					deviceInventoryReport.setAvailableDevices(rs.getString("ASSIGNED_DEVICES"));

					deviceInventoryReportList.add(deviceInventoryReport);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getFilterType(), filter.getFilterValue(), filter.getUserId());

		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceInventoryReport", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return deviceInventoryReportList;
	}

	@Override
	public Map<String, Integer> getDeviceMalfunctionReportCount(BaseFilter filter) throws ServiceExecutionException {
		int totalCount = NumberUtils.INTEGER_ZERO;
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		LOGGER.debug("getDeviceMalfunctionReportCount called");
		try {
			counts = selectForObject(SQLConstants.FN_GET_DEVICE_MALFUNCTION_REPORT_COUNT, String.class,
					filter.getSearchText(), filter.getFilterType(), filter.getFilterValue(), filter.getUserId(),
					filter.getRoleTypeId());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceMalfunctionReportCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<DeviceMalfunctionReport> getDeviceMalfunctionReport(BaseFilter filter)
			throws ServiceExecutionException {
		List<DeviceMalfunctionReport> deviceMalfunctionReportList = new ArrayList<>();
		LOGGER.debug("getDeviceMalfunctionReport called");
		try {
			jdbcTemplate.query(SQLConstants.GET_DEVICE_MALFUNCTION_REPORT, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					DeviceMalfunctionReport deviceMalfunctionReport = new DeviceMalfunctionReport();

					deviceMalfunctionReport.setDeviceNumber(rs.getString("DEVICE_NUMBER"));
					deviceMalfunctionReport.setDeviceType(rs.getString("DEVICE_TYPE"));
					// deviceMalfunctionReport.setSlNumber(rs.getInt("slNo"));
					deviceMalfunctionReport.setStudyName(rs.getString("STUDY_NAME"));
					deviceMalfunctionReport.setNote(rs.getString("NOTE"));

					if (rs.getString("UN_ASSIGN_DATE") != null)
						deviceMalfunctionReport.setUnAssignDate(rs.getString("UN_ASSIGN_DATE"));

					// deviceDetailsReport.setAddDate(rs.getTimestamp("ADD_DATE").toLocalDateTime());
					deviceMalfunctionReportList.add(deviceMalfunctionReport);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getFilterType(), filter.getFilterValue(), filter.getUserId(), filter.getRoleTypeId());

		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceMalfunctionReport", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return deviceMalfunctionReportList;
	}

	@Override
	public Map<String, Integer> getDeviceTrackingReportCount(BaseFilter filter) throws ServiceExecutionException {
		int totalCount = NumberUtils.INTEGER_ZERO;
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		LOGGER.debug("getDeviceTrackingReportCount called");
		try {
			counts = selectForObject(SQLConstants.FN_GET_DEVICE_TRACKING_REPORT_COUNT, String.class,
					filter.getSearchText(), filter.getFilterType(), filter.getFilterValue(), filter.getUserId());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceTrackingReportCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<DeviceTrackingReport> getDeviceTrackingReport(BaseFilter filter) throws ServiceExecutionException {
		List<DeviceTrackingReport> deviceTrackingReportList = new ArrayList<>();
		LOGGER.debug("getDeviceTrackingReport called");
		try {
			jdbcTemplate.query(SQLConstants.GET_DEVICE_TRACKING_REPORT, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					DeviceTrackingReport deviceTrackingReport = new DeviceTrackingReport();

					deviceTrackingReport.setDeviceNumber(rs.getString("DEVICE_NUMBER"));
					deviceTrackingReport
							.setDeviceType(rs.getString("DEVICE_TYPE") != null ? rs.getString("DEVICE_TYPE") : "");
					// deviceTrackingReport.setSlNumber(rs.getInt("slNo"));
//					deviceTrackingReport.setStudyName(rs.getString("STUDY_NAME"));
					deviceTrackingReport
							.setDeviceModel(rs.getString("DEVICE_MODEL") != null ? rs.getString("DEVICE_MODEL") : "");
					deviceTrackingReport.setCurrentStatus(rs.getBoolean("IS_ACTIVE"));
					String statusName = "";
					if (rs.getInt("IS_ACTIVE") == 1) {
						statusName = "Active";
					}
					if (rs.getInt("IS_ACTIVE") == 0) {
						statusName = "Inactive";
					}
					deviceTrackingReport.setStatus(statusName);

					deviceTrackingReport
							.setCurrentStudyName(rs.getString("STUDY_NAME") != null ? rs.getString("STUDY_NAME") : "");
					deviceTrackingReport
							.setCurrentPetName(rs.getString("PET_NAME") != null ? rs.getString("PET_NAME") : "");
					deviceTrackingReport.setCurrentLocation(
							rs.getString("DEVICE_LOCATION") != null ? rs.getString("DEVICE_LOCATION") : "");
					deviceTrackingReport.setCurrentBatteryLevel(rs.getString("Current_Battery_Level"));
					deviceTrackingReport.setAssetStatus(rs.getString("STATUS_NAME"));
					deviceTrackingReport.setAssetStatusId(rs.getString("STATUS_ID"));

					// deviceDetailsReport.setAddDate(rs.getTimestamp("ADD_DATE").toLocalDateTime());
					deviceTrackingReportList.add(deviceTrackingReport);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getFilterType(), filter.getFilterValue(), filter.getUserId());

		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceTrackingReport", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return deviceTrackingReportList;
	}

	@Override
	public Map<String, Integer> getStudyBasedReportCount(BaseFilter filter, int userId)
			throws ServiceExecutionException {
		int totalCount = NumberUtils.INTEGER_ZERO;
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		LOGGER.debug("getStudyBasedReportCount called");
		try {
			counts = selectForObject(SQLConstants.FN_GET_STUDY_BASED_REPORT_COUNT, String.class, filter.getSearchText(),
					filter.getFilterType(), filter.getFilterValue(), userId);
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getStudyBasedReportCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<StudyBasedReport> getStudyBasedReport(BaseFilter filter, int userId) throws ServiceExecutionException {
		List<StudyBasedReport> studyBasedReportList = new ArrayList<>();
		LOGGER.debug("getStudyBasedReport called");
		try {
			jdbcTemplate.query(SQLConstants.GET_STUDY_BASED_REPORT, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					StudyBasedReport studyBasedReport = new StudyBasedReport();

					studyBasedReport.setDeviceNumber(rs.getString("DEVICE_NUMBER"));
					studyBasedReport
							.setDeviceType(rs.getString("DEVICE_TYPE") != null ? rs.getString("DEVICE_TYPE") : "");
					// studyBasedReport.setSlNumber(rs.getInt("slNo"));
					studyBasedReport.setStudyName(rs.getString("STUDY_NAME"));
					studyBasedReport
							.setDeviceModel(rs.getString("DEVICE_MODEL") != null ? rs.getString("DEVICE_MODEL") : "");
					studyBasedReport.setCurrentStatus(rs.getBoolean("IS_ACTIVE"));
					studyBasedReport.setAssetIsActive(rs.getBoolean("ASSET_IS_ACTIVE"));
					studyBasedReport.setAssetStatus(rs.getString("STATUS_NAME"));
					studyBasedReport.setAssetStatusId(rs.getString("STATUS_ID"));
					String statusName = "";
					if (rs.getInt("IS_ACTIVE") == 1) {
						statusName = "Active";
					}
					if (rs.getInt("IS_ACTIVE") == 0) {
						statusName = "Inactive";
					}
					studyBasedReport.setStatus(statusName);
					studyBasedReport.setStudyName(rs.getString("STUDY_NAME") != null ? rs.getString("STUDY_NAME") : "");
					studyBasedReport.setPetName(rs.getString("PET_NAME") != null ? rs.getString("PET_NAME") : "");
//					studyBasedReport.setCurrentLocation(rs.getString("DEVICE_LOCATION")!= null ? rs.getString("DEVICE_LOCATION") :"");
					studyBasedReport.setCurrentBatteryLevel(rs.getString("Current_Battery_Level"));

					studyBasedReportList.add(studyBasedReport);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getFilterType(), filter.getFilterValue(), userId);

		} catch (Exception e) {
			LOGGER.error("error while fetching getStudyBasedReport", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return studyBasedReportList;
	}

	@Override
	public int getAssetsDevicesByStudyReportCount(BaseFilter filter) throws ServiceExecutionException {
		int totalCount = NumberUtils.INTEGER_ZERO;
		LOGGER.debug("getAssetsDevicesByStudyReportCount called");
		try {
			totalCount = selectForObject(SQLConstants.FN_GET_ASSETS_DEVICES_BY_STUDY_REPORT_COUNT, Integer.class,
					filter.getSearchText(), filter.getFilterType(), filter.getFilterValue());
		} catch (Exception e) {
			LOGGER.error("error while fetching getAssetsDevicesByStudyReportCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return totalCount;
	}

	DevicesbyStudyReport devicesbyStudyReport;

	@Override
	public List<DevicesbyStudyReport> getAssetsDevicesByStudyReport(AssetByStudyWidgetFilter filter)
			throws ServiceExecutionException {
		List<DevicesbyStudyReport> devicesbyStudyReportList = new ArrayList<>();

		LOGGER.debug("getAssetsDevicesByStudyReport called");

		List<AssetStatusCountByStudy> assetLookUp = new ArrayList<>();
		LOGGER.debug("getAssetsDevicesByStudyReport called");
		try {
			HashMap<String, DevicesbyStudyReport> hashMap = new HashMap();
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_asset_type", filter.getAssetType());
			inputParams.put("p_model", filter.getModelType());
			inputParams.put("p_study", filter.getStudy());

			Map<String, Object> simpleJdbcCallResult = callStoredProcedure(
					SQLConstants.ASSET_DASHBOARD_GET_ASSETS_BY_STUDY_STATUS, inputParams);
			Iterator<Map.Entry<String, Object>> itr = simpleJdbcCallResult.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();

				if (key.equals(SQLConstants.RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(rs -> {
						AssetStatusCountByStudy assetStatusByStudyCount = new AssetStatusCountByStudy();
						assetStatusByStudyCount.setStatusName((String) rs.get("STATUS_NAME"));
						assetStatusByStudyCount.setStatusCount(0);
						assetLookUp.add(assetStatusByStudyCount);

					});
				}
				if (key.equals(SQLConstants.RESULT_SET_2)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(rs -> {

						if (hashMap.containsKey((String) rs.get("STUDY_NAME"))) {
							devicesbyStudyReport = hashMap.get((String) rs.get("STUDY_NAME"));
							List<AssetStatusCountByStudy> count = devicesbyStudyReport.getStatusNameCount();
							AssetStatusCountByStudy assetStatusByStudyCount = new AssetStatusCountByStudy();
							String statusName = (String) rs.get("STATUS_NAME");
							assetStatusByStudyCount.setStatusName(statusName);
							assetStatusByStudyCount.setStatusCount(((Number) rs.get("DEVICES_BY_STUDY")).intValue());
							count.add(assetStatusByStudyCount);
							devicesbyStudyReport.setStatusNameCount(count);
							hashMap.put((String) rs.get("STUDY_NAME"), devicesbyStudyReport);

						} else {
							devicesbyStudyReport = new DevicesbyStudyReport();
							List<AssetStatusCountByStudy> count = new ArrayList<>();

							devicesbyStudyReport.setDevicesbyStudy(((Number) rs.get("DEVICE_IN_STUDY")).intValue());
							devicesbyStudyReport.setTotalDevices(((Number) rs.get("TOTAL_DEVICES")).intValue());
							devicesbyStudyReport.setStudyName((String) rs.get("STUDY_NAME"));
							String statusName = (String) rs.get("STATUS_NAME");
							AssetStatusCountByStudy assetStatusByStudyCount = new AssetStatusCountByStudy();
							assetStatusByStudyCount.setStatusName(statusName);
							assetStatusByStudyCount.setStatusCount(((Number) rs.get("DEVICES_BY_STUDY")).intValue());
							count.add(assetStatusByStudyCount);
							devicesbyStudyReport.setStatusNameCount(count);
							hashMap.put((String) rs.get("STUDY_NAME"), devicesbyStudyReport);

						}
					});
				}
			}

			for (Map.Entry entry1 : hashMap.entrySet()) { // Total Results
				DevicesbyStudyReport reportObj = (DevicesbyStudyReport) entry1.getValue();

				Map<String, AssetStatusCountByStudy> map = new HashMap<>();
				for (int j = 0; j < assetLookUp.size(); j++) {
					AssetStatusCountByStudy study = assetLookUp.get(j);
					map.put(study.getStatusName(), study);
				}
				for (int i = 0; i < reportObj.getStatusNameCount().size(); i++) { // 5
					AssetStatusCountByStudy study = reportObj.getStatusNameCount().get(i);
					if (map.containsKey(study.getStatusName())) {
						map.remove(study.getStatusName());
					}
				}
				for (Map.Entry entry2 : map.entrySet()) {
					reportObj.getStatusNameCount().add((AssetStatusCountByStudy) entry2.getValue());
				}

				devicesbyStudyReportList.add(reportObj);
			}

		} catch (Exception e) {
			LOGGER.error("error while fetching getAssetsDevicesByStudyReport", e);
			throw new ServiceExecutionException(e.getMessage());

		}
		return devicesbyStudyReportList;
	}

	@Override
	public int getAssetsDevicesMalfunctionsReportCount(BaseFilter filter) throws ServiceExecutionException {
		int totalCount = NumberUtils.INTEGER_ZERO;
		LOGGER.debug("getAssetsDevicesMalfunctionsReportCount called");
		try {
			totalCount = selectForObject(SQLConstants.FN_GET_ASSETS_DEVICES_MALFUNCTIONS_REPORTT, Integer.class,
					filter.getSearchText(), filter.getFilterType(), filter.getFilterValue());
		} catch (Exception e) {
			LOGGER.error("error while fetching getAssetsDevicesMalfunctionsReportCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return totalCount;
	}

	DevicesMalfunctionsReport devicesMalfunctionsReport;

	@Override
	public List<DevicesMalfunctionsReport> getAssetsDevicesMalfunctionsReport(PointTrackerFilter filter)
			throws ServiceExecutionException {
		List<AssetIssueCountByStudy> assetIssueLookUp = new ArrayList<>();

		LOGGER.debug("getAssetsDevicesMalfunctionsReport called");
		List<DevicesMalfunctionsReport> devicesMalfunctionsReportList;
		try {
			HashMap<String, DevicesMalfunctionsReport> hashMap = new HashMap();
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_filter_type", filter.getFilterType());
			inputParams.put("p_filter_value", filter.getFilterValue());
			inputParams.put("p_study_id", filter.getStudyId());
			inputParams.put("p_asset_type", filter.getAssetType());
			inputParams.put("p_asset_model", filter.getAssetModel());
			inputParams.put("p_start_date", filter.getStartDate());
			inputParams.put("p_end_date", filter.getEndDate());

			Map<String, Object> simpleJdbcCallResult = callStoredProcedure(
					SQLConstants.ASSET_DASHBOARD_GET_ASSETS_BY_ISSUE_TYPE_STUDY, inputParams);
			Iterator<Entry<String, Object>> itr = simpleJdbcCallResult.entrySet().iterator();

			while (itr.hasNext()) { // #2
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();

				if (key.equals(SQLConstants.RESULT_SET_1)) { // 7
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(u -> {
						AssetIssueCountByStudy assetIssueCountByStudy = new AssetIssueCountByStudy();
						assetIssueCountByStudy.setIssueName(u.get("TICKET_CATEGORY_NAME").toString());
						assetIssueCountByStudy.setIssueCount(0);

						assetIssueLookUp.add(assetIssueCountByStudy);

					});
				}

				if (key.equals(SQLConstants.RESULT_SET_2)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();

					list.forEach(rs -> {
						if (hashMap.containsKey((String) rs.get("STUDY_NAME"))) {

							devicesMalfunctionsReport = hashMap.get(rs.get("STUDY_NAME"));
							List<AssetIssueCountByStudy> assetIssueCountByStudyList = devicesMalfunctionsReport
									.getIssueNameCount();

							AssetIssueCountByStudy assetIssueCountByStudy = new AssetIssueCountByStudy();

							assetIssueCountByStudy.setIssueName((String) rs.get("TICKET_CATEGORY_NAME"));
							assetIssueCountByStudy.setIssueCount(((Number) rs.get("NO_OF_CAT_TICKETS")).intValue());

							assetIssueCountByStudyList.add(assetIssueCountByStudy);

							hashMap.put((String) rs.get("STUDY_NAME"), devicesMalfunctionsReport);
						} else {

							devicesMalfunctionsReport = new DevicesMalfunctionsReport();
							devicesMalfunctionsReport.setStudyName((String) rs.get("STUDY_NAME"));
							devicesMalfunctionsReport.setTicketsCount(((Number) rs.get("NO_OF_TICKETS")).intValue());

							AssetIssueCountByStudy assetIssueCountByStudy = new AssetIssueCountByStudy();

							assetIssueCountByStudy.setIssueName((String) rs.get("TICKET_CATEGORY_NAME"));
							assetIssueCountByStudy.setIssueCount(((Number) rs.get("NO_OF_CAT_TICKETS")).intValue());

							List<AssetIssueCountByStudy> assetIssueCountByStudyList = new ArrayList<>();
							assetIssueCountByStudyList.add(assetIssueCountByStudy);

							// System.out.println(rs.get("STUDY_NAME") + " - " +
							// rs.get("TICKET_CATEGORY_NAME"));

							devicesMalfunctionsReport.setIssueNameCount(assetIssueCountByStudyList);
							hashMap.put((String) rs.get("STUDY_NAME"), devicesMalfunctionsReport);
						}
					});
				}
			}
			devicesMalfunctionsReportList = new ArrayList<>();
			for (Map.Entry entry1 : hashMap.entrySet()) { // Total Results
				DevicesMalfunctionsReport reportObj = (DevicesMalfunctionsReport) entry1.getValue();

				Map<String, AssetIssueCountByStudy> map = new HashMap<>();
				for (int j = 0; j < assetIssueLookUp.size(); j++) {
					AssetIssueCountByStudy study = assetIssueLookUp.get(j);
					map.put(study.getIssueName(), study);
				}
				for (int i = 0; i < reportObj.getIssueNameCount().size(); i++) { // 5
					AssetIssueCountByStudy study = reportObj.getIssueNameCount().get(i);
					if (map.containsKey(study.getIssueName())) {
						map.remove(study.getIssueName());
					}
				}
				for (Map.Entry entry2 : map.entrySet()) {
					reportObj.getIssueNameCount().add((AssetIssueCountByStudy) entry2.getValue());
				}

				TreeMap<String, Integer> treeMap = new TreeMap<String, Integer>();
				reportObj.getIssueNameCount();
				for (int i = 0; i <= reportObj.getIssueNameCount().size() - 1; i++) {
					treeMap.put(reportObj.getIssueNameCount().get(i).getIssueName(),
							reportObj.getIssueNameCount().get(i).getIssueCount());

				}
				reportObj.getIssueNameCount().clear();
				for (Map.Entry entry3 : treeMap.entrySet()) {
					AssetIssueCountByStudy assetIssueCountByStudy2 = new AssetIssueCountByStudy();
					assetIssueCountByStudy2.setIssueName((String) entry3.getKey());
					assetIssueCountByStudy2.setIssueCount((Integer) entry3.getValue());
					reportObj.getIssueNameCount().add(assetIssueCountByStudy2);
				}
				devicesMalfunctionsReportList.add(reportObj);
			}

		} catch (Exception e) {
			LOGGER.error("error while fetching getAssetsDevicesMalfunctionsReport", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return devicesMalfunctionsReportList;
	}

	@Override
	public Map<String, Integer> getPointTrackerReportCount(PointTrackerFilter filter, int userId)
			throws ServiceExecutionException {
		int totalCount = NumberUtils.INTEGER_ZERO;
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		LOGGER.debug("getDeviceDetailsReportCount called");
		try {
			counts = selectForObject(SQLConstants.FN_GET_POINT_TRACKER_REPORT_COUNT, String.class,
					filter.getSearchText(), filter.getFilterType(), filter.getFilterValue(), filter.getStartDate(),
					filter.getEndDate(), userId);
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceDetailsReportCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<PointTrackerReport> getPointTrackerReport(PointTrackerFilter filter, int userId)
			throws ServiceExecutionException {
		List<PointTrackerReport> pointTrackerReportList = new ArrayList<>();
		LOGGER.debug("getPointTrackerReport called");
		try {
			jdbcTemplate.query(SQLConstants.GET_POINT_TRACKER_REPORT, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PointTrackerReport pointTrackerReport = new PointTrackerReport();

					// CREATED_DATE , CAMPAIGN, PET, STUDY , ACTIVITY, BEHAVIOR POINTS STATUS
					// pointTrackerReport.setCreatedDate(rs.getTimestamp("CREATED_DATE") != null ?
					// new SimpleDateFormat("MM/dd/yyyy").format(new
					// Date(rs.getTimestamp("CREATED_DATE").getTime())) : null);
					pointTrackerReport.setCreatedDate(rs.getTimestamp("CREATED_DATE").toLocalDateTime());
					if (rs.getString("C_DATE") != null) {
						pointTrackerReport.setDate(rs.getString("C_DATE"));
					}
					pointTrackerReport.setCampaign(rs.getString("CAMPAIGN"));
					pointTrackerReport.setPet(rs.getString("PET_NAME"));
					pointTrackerReport.setStudy(rs.getString("STUDY_NAME"));
					pointTrackerReport.setActivity(rs.getString("ACTIVITY"));
					pointTrackerReport.setBehavior(rs.getString("BEHAVIOR"));
					pointTrackerReport.setStatus(rs.getString("STATUS"));
					if (rs.getInt("STATUS_ID") == 2) {
						pointTrackerReport.setPoints(rs.getString("POINTS"));
					} else {
						pointTrackerReport.setPoints("N/A");
					}
					pointTrackerReportList.add(pointTrackerReport);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getFilterType(), filter.getFilterValue(), filter.getStartDate(), filter.getEndDate(),
					userId);

		} catch (Exception e) {
			LOGGER.error("error while fetching getPointTrackerReport", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return pointTrackerReportList;
	}

	@Override
	public Map<String, Integer> getPointsAccumulatedReportCount(PointsAccumulatedReportFilter filter, int userId)
			throws ServiceExecutionException {
		int totalCount = NumberUtils.INTEGER_ZERO;
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		LOGGER.debug("getPointsAccumulatedReportCount called");
		try {
			counts = selectForObject(SQLConstants.FN_GET_POINTS_ACCUMULATED_REPORT_COUNT, String.class,
					filter.getSearchText(), filter.getFilterType(), filter.getFilterValue(), filter.getPetId(),
					filter.getPetParentId(), userId);
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPointsAccumulatedReportCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<PointsAccumulatedReport> getPointsAccumulatedReport(PointsAccumulatedReportFilter filter, int userId)
			throws ServiceExecutionException {
		List<PointsAccumulatedReport> pointTrackerReportList = new ArrayList<>();
		LOGGER.debug("getPointsAccumulatedReport called");
		try {
			jdbcTemplate.query(SQLConstants.GET_POINTS_ACCUMULATED_REPORT, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PointsAccumulatedReport pointsAccumulatedReport = new PointsAccumulatedReport();
					pointsAccumulatedReport.setPetName(rs.getString("PET_NAME"));
					pointsAccumulatedReport.setPetParentName(rs.getString("PET_PARENT_NAME"));
					pointsAccumulatedReport.setPointsAccumulated(rs.getInt("TOTAL_POINTS"));
					pointsAccumulatedReport.setPointsRedeemed(rs.getInt("POINTS_REDEMED"));
					pointsAccumulatedReport.setPointsAvailable(rs.getInt("AVAILABLE_POINTS"));
					pointTrackerReportList.add(pointsAccumulatedReport);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getFilterType(), filter.getFilterValue(), filter.getPetId(), filter.getPetParentId(),
					userId);

		} catch (Exception e) {
			LOGGER.error("error while fetching getPointsAccumulatedReport", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return pointTrackerReportList;
	}

	@Override
	public Map<String, Integer> getCustomerSupportTicketsCount(SupportFilter filter) throws ServiceExecutionException {
		int totalCount = NumberUtils.INTEGER_ZERO;
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		try {
			counts = selectForObject(SQLConstants.CUSTOMER_SUPPORT_GET_LIST_REPORT_COUNT, String.class,
					filter.getSearchText(), filter.getFilterType(), filter.getFilterValue(), filter.getStartDate(),
					filter.getEndDate(), filter.getUserId(), filter.getRoleTypeId());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while executing getCustomerSupportTicketsCount ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<SupportListDTO> getCustomerSupportTickets(SupportFilter filter) throws ServiceExecutionException {
		List<SupportListDTO> supportList = new ArrayList<>();
		try {
			String sql = SQLConstants.CUSTOMER_SUPPORT_GET_LIST_REPORT;
			jdbcTemplate.query(sql, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					SupportListDTO support = new SupportListDTO();
					// support.setSlNumber(rs.getInt("slNo"));
					support.setTicketID(rs.getInt("TICKET_ID"));
					support.setTicketTitle(rs.getString("TICKET_TITLE"));
					support.setPriority(rs.getString("PRIORITY_NAME"));
					support.setPetName(rs.getString("PET_NAME"));
					support.setPetParentName(rs.getString("PET_PARENT_NAME"));
					support.setStudy(rs.getString("STUDY_NAME"));
					support.setIssue(rs.getString("TICKET_CATEGORY_NAME"));
					support.setAssignedTo(rs.getString("USER_NAME"));
					support.setAging(rs.getInt("AGING") + " Day(s)");
					support.setPetParentAddress(rs.getString("SHIPPING_ADDRESS"));
					support.setCreatedOn(
							rs.getTimestamp("TICKET_CREATED_DATE") != null ? new SimpleDateFormat("MM/dd/yyyy")
									.format(new Date(rs.getTimestamp("TICKET_CREATED_DATE").getTime())) : null);
					support.setLastModifiedOn(
							rs.getTimestamp("LAST_MODIFIED_DATE") != null ? new SimpleDateFormat("MM/dd/yyyy")
									.format(new Date(rs.getTimestamp("LAST_MODIFIED_DATE").getTime())) : null);
					support.setStatus(rs.getString("STATUS"));
					supportList.add(support);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getFilterType(), filter.getFilterValue(), filter.getStartDate(), filter.getEndDate(),
					filter.getUserId(), filter.getRoleTypeId());
		} catch (Exception e) {
			LOGGER.error("error while executing getCustomerSupportTickets ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return supportList;
	}

	@Override
	public List<CustomerSupportByCategory> getCustomerSupportIssueWidget(IssueWidgetFilter filter)
			throws ServiceExecutionException {
		List<CustomerSupportByCategory> customerSupportIssueWidgetResponses = new ArrayList<>();
		try {
			String sql = SQLConstants.ASSET_DASHBOARD_GET_ASSETS_BY_ISSUE_TYPE;
			jdbcTemplate.query(sql, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					CustomerSupportByCategory customerSupportIssueWidgetResponse = new CustomerSupportByCategory();
					customerSupportIssueWidgetResponse.setIssueName(rs.getString("TICKET_CATEGORY_NAME"));
					customerSupportIssueWidgetResponse.setIssueCount(rs.getInt("NO_OF_TICKETS"));
					customerSupportIssueWidgetResponse.setIssuePercent(rs.getFloat("PERCENTAGE"));
					customerSupportIssueWidgetResponse.setTotalCount(rs.getInt("TOTALCOUNT"));
					customerSupportIssueWidgetResponses.add(customerSupportIssueWidgetResponse);
				}
			}, filter.getStartDate(), filter.getEndDate(), filter.getStudyId(), filter.getModelType(),
					filter.getAssertModel());
		} catch (Exception e) {
			LOGGER.error("error while executing getCustomerSupportIssueWidget ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return customerSupportIssueWidgetResponses;
	}

	@Override
	public List<CustomerSupportIssuesByStudy> getCustomerSupportIssueByStudyWidget(IssueByStudyWidgetFilter filter)
			throws ServiceExecutionException {
		List<CustomerSupportIssuesByStudy> customerSupportIssueByStudyWidgetResponseList = new ArrayList<>();
		try {
			String sql = SQLConstants.ASSET_DASHBOARD_GET_ASSETS_BY_TICKETS_STUDY;
			jdbcTemplate.query(sql, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					CustomerSupportIssuesByStudy customerSupportIssueByStudyWidgetResponse = new CustomerSupportIssuesByStudy();
					customerSupportIssueByStudyWidgetResponse.setStudyName(rs.getString("STUDY_NAME"));
					customerSupportIssueByStudyWidgetResponse.setStudyCount(rs.getInt("NO_OF_TICKETS"));
					customerSupportIssueByStudyWidgetResponse.setStudyPercentage(rs.getString("PERCENTAGE"));
					customerSupportIssueByStudyWidgetResponse.setOpenCount(rs.getInt("OPEN"));
					customerSupportIssueByStudyWidgetResponse.setInProgressCount(rs.getInt("IN_PROGRESS"));
					customerSupportIssueByStudyWidgetResponse.setClosedCount(rs.getInt("CLOSED"));
					customerSupportIssueByStudyWidgetResponseList.add(customerSupportIssueByStudyWidgetResponse);
				}
			}, filter.getStartDate(), filter.getEndDate(), filter.getAgent());
		} catch (Exception e) {
			LOGGER.error("error while executing getCustomerSupportIssueByStudyWidget ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return customerSupportIssueByStudyWidgetResponseList;
	}

	@Override
	public List<TotalAssetsListDTO> getTotalAssets() throws ServiceExecutionException {
		List<TotalAssetsListDTO> totalAssetsReportResponses = new ArrayList<>();
		try {
			int count = 0;
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("type", "type1");

			Map<String, Object> simpleJdbcCallResult = callStoredProcedure(
					SQLConstants.ASSET_DASHBOARD_GET_ASSETS_BY_ASSET_TYPE, inputParams);
			Iterator<Entry<String, Object>> itr = simpleJdbcCallResult.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();

				if (key.equals(SQLConstants.RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(u -> {
						TotalAssetsListDTO totalAssetsListDTO = new TotalAssetsListDTO();

						totalAssetsListDTO
								.setDeviceTotalCount((Integer) Integer.parseInt(u.get("TOTAL_COUNT").toString()));
						totalAssetsListDTO.setDeviceCount((Integer) Integer.parseInt(u.get("DEVICE_COUNT").toString()));
						totalAssetsListDTO.setDeviceType((String) u.get("DEVICE_TYPE"));

						totalAssetsReportResponses.add(totalAssetsListDTO);

					});
				}

				if (key.equals(SQLConstants.RESULT_SET_2)) {
					List<TotalModelListDTO> totalModelListDTOList = new ArrayList<>();
					List<Map<String, Object>> list2 = (List<Map<String, Object>>) entry.getValue();
					TotalAssetsListDTO totalAssetsDTO = new TotalAssetsListDTO();
					list2.size();

					for (int i = 0; i < totalAssetsReportResponses.size(); i++) {

						String deviceType = totalAssetsReportResponses.get(i).getDeviceType(); // Sensor
						List<TotalModelListDTO> modelArray = null;
						list2.forEach(rs -> {
//                        	count = count + 1;
//                        	System.out.println("count");
							TotalModelListDTO modelDTO = new TotalModelListDTO();
							if (rs.get("DEVICE_TYPE").equals(deviceType)) {
								modelDTO.setDeviceType((String) rs.get("DEVICE_TYPE"));
//                            	System.out.println("count" + rs.get("DEVICE_TYPE"));
								if (rs.get("DEVICE_MODEL") != null) {
									modelDTO.setModelName((String) rs.get("DEVICE_MODEL"));
									modelDTO.setModelCount(rs.get("MODEL_COUNT").toString());

								}
								totalModelListDTOList.add(modelDTO);
							}
						});

					}

					totalAssetsDTO.setModelAssociatedObject(totalModelListDTOList);
					totalAssetsReportResponses.add(totalAssetsDTO);
					/*
					 * List<Map<String, Object>> list = (List<Map<String, Object>>)
					 * entry.getValue(); list.forEach(u -> { TotalAssetsListDTO totalAssetsListDTO =
					 * new TotalAssetsListDTO();
					 * totalAssetsListDTO.setDeviceModel((String)u.get("DEVICE_MODEL")); //
					 * totalAssetsListDTO.setModelCount((Integer)u.get("MODEL_COUNT"));
					 * 
					 * totalAssetsReportResponses.add(totalAssetsListDTO);
					 * 
					 * });
					 */
				}
			}
		} catch (Exception e) {
			LOGGER.error("error while executing getTotalAssetsByStatus ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return totalAssetsReportResponses;
	}

	@Override
	public List<TotalAssetsbyStatusListDTO> getTotalAssetsByStatus(TotalAssetsByStausWidgetFilter filter)
			throws ServiceExecutionException {
		List<TotalAssetsbyStatusListDTO> totalAssetsByStatusReportResponses = new ArrayList<>();

		try {
			String sql = SQLConstants.ASSET_DASHBOARD_GET_ASSETS_BY_STATUS;
			jdbcTemplate.query(sql, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					TotalAssetsbyStatusListDTO totalAssetsByStatusReportResponse = new TotalAssetsbyStatusListDTO();
					totalAssetsByStatusReportResponse
							.setStatus(rs.getString("STATUS_NAME") != null ? rs.getString("STATUS_NAME") : "");
					totalAssetsByStatusReportResponse.setStatusId(rs.getInt("STATUS_ID"));
					totalAssetsByStatusReportResponse.setDeviceCount(rs.getInt("DEVICE_COUNT"));
					totalAssetsByStatusReportResponse.setTotalDeviceCount(rs.getInt("TOTAL_DEVICE_COUNT"));
					totalAssetsByStatusReportResponses.add(totalAssetsByStatusReportResponse);
//                   
				}
			}, filter.getLocationId(), filter.getModelType(), filter.getAssertModel());
		} catch (Exception e) {
			LOGGER.error("error while executing getTotalAssetsByStatus ", e);
//           
//            throw new ServiceExecutionException(e.getMessage());
		}

		if (totalAssetsByStatusReportResponses.isEmpty()) {
			TotalAssetsbyStatusListDTO totalAssetsByStatusReportResponse1 = new TotalAssetsbyStatusListDTO();
			totalAssetsByStatusReportResponse1.setTotalDeviceCount(0);
			totalAssetsByStatusReportResponses.add(totalAssetsByStatusReportResponse1);
		}
		return totalAssetsByStatusReportResponses;
	}

	@Override
	public List<PetParentReport> getPetParentReport(PetParentReportFilter filter, int userId)
			throws ServiceExecutionException {
		List<PetParentReport> pList = new ArrayList<>();
		LOGGER.debug("getPetParentReport called");
		try {
			jdbcTemplate.query(SQLConstants.GET_PET_PARENT_REPORT, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetParentReport parentReport = new PetParentReport();
					// parentReport.setStudy(rs.getString("STUDY_NAME"));

					parentReport.setPetName(rs.getString("PET_NAME"));
					parentReport.setAnsweredCount(rs.getInt("ANS_CNT"));
					parentReport.setExpiryDate(rs.getString("EXPIRY_DATE"));
					parentReport.setStartDate(
							rs.getDate("START_DATE") != null ? rs.getDate("START_DATE").toLocalDate() : null);
					parentReport
							.setEndDate(rs.getDate("END_DATE") != null ? rs.getDate("END_DATE").toLocalDate() : null);

					parentReport.setStatus(rs.getString("STATUS"));
					parentReport.setQuestionnaireName(rs.getString("QUESTIONNAIRE_NAME"));
					parentReport.setAnsweredOn(rs.getString("ANSWERED_ON"));

					parentReport.setStudy(rs.getString("STUDY_NAME"));

					// parentReport.setPetParentName(rs.getString("PET_PARENT_NAME"));
					// parentReport.setCompletedCount(rs.getInt("COMPLETED"));
					// parentReport.setPendingCount(rs.getInt("PENDING"));

					pList.add(parentReport);
				}
			}, filter.getFilterValue(), filter.getStartIndex(), filter.getLimit(), filter.getOrder(),
					filter.getSortBy());

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetParentReport", e);
			// throw new ServiceExecutionException(e.getMessage());
		}
		return pList;
	}

	@Override
	public Map<String, Integer> getPetParentReportCount(PetParentReportFilter filter, int userId)
			throws ServiceExecutionException {
		int totalCount = NumberUtils.INTEGER_ZERO;
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		try {
			counts = selectForObject(SQLConstants.GET_PET_PARENT_REPORT_COUNT, String.class, filter.getFilterValue());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while executing getPetParentReportCount ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public Map<String, Integer> getPetBfiScoreReportCount(PetBfiScoreReportFilter filter)
			throws ServiceExecutionException {
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		try {
			counts = selectForObject(SQLConstants.FN_GET_PET_BFI_SCORE_REPORT_COUNT, String.class,
					filter.getSearchText(), filter.getFilterType(), filter.getFilterValue(), filter.getStartDate(),
					filter.getEndDate(), filter.getUserId());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while executing getPetBfiScoreReportCount ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<PetBfiScoreReport> getPetBfiScoreReport(PetBfiScoreReportFilter filter)
			throws ServiceExecutionException {
		List<PetBfiScoreReport> petBfiScoreReports = new ArrayList<>();
		LOGGER.debug("getPetBfiScoreReport called");
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JavaTimeModule module = new JavaTimeModule();
			module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
			objectMapper.registerModule(module);

			jdbcTemplate.query(SQLConstants.GET_PET_BFI_SCORE_REPORT, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetBfiScoreReport petBfiScoreReport = new PetBfiScoreReport();
					petBfiScoreReport.setPetId(rs.getInt("PET_ID"));
					petBfiScoreReport.setPetBfiImageSetId(rs.getInt("PET_BFI_IMAGE_SET_ID"));
					petBfiScoreReport.setPetParentId(rs.getInt("PET_PARENT_ID"));

					petBfiScoreReport.setPetName(rs.getString("PET_NAME"));
					petBfiScoreReport.setPetParentName(rs.getString("PET_PARENT_NAME"));
					String fileName = rs.getString("PHOTO_NAME");

					if (StringUtils.isNotEmpty(fileName)) {
						petBfiScoreReport
								.setPhotoName(gcpClientUtil.getDownloaFiledUrl(fileName, Constants.GCP_PETPHOTO_PATH));
					}

					petBfiScoreReport.setBreedName(rs.getString("BREED_NAME"));
					petBfiScoreReport.setGender(rs.getString("GENDER"));
					petBfiScoreReport.setPetAge(rs.getString("PET_AGE"));

					petBfiScoreReport.setCapturedOn(rs.getTimestamp("CAPTURED_ON").toLocalDateTime());
					petBfiScoreReport.setScore(getAverageScore(rs.getString("SCORES")));

					List<ScoreInfo> scoreInfo = null;
					try {
						if (StringUtils.isNotEmpty(rs.getString("SCORE_INFO")))
							scoreInfo = objectMapper.readValue(rs.getString("SCORE_INFO"),
									new TypeReference<List<ScoreInfo>>() {
									});
					} catch (JsonProcessingException e) {
						LOGGER.error("error while converting SCORE_INFO json in getPetBfiScoreReport", e);
					}
					petBfiScoreReport.setScoreInfo(scoreInfo);

					List<PetBfiImage> petBfiImages = null;
					try {
						if (StringUtils.isNotEmpty(rs.getString("BFI_IMAGE_DETAILS")))
							petBfiImages = objectMapper.readValue(rs.getString("BFI_IMAGE_DETAILS"),
									new TypeReference<List<PetBfiImage>>() {
									});
					} catch (JsonProcessingException e) {
						LOGGER.error("error while converting BFI_IMAGE_DETAILS json in getPetBfiScoreReport", e);
					}
					petBfiScoreReport.setPetBfiImages(petBfiImages);

					petBfiScoreReports.add(petBfiScoreReport);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getFilterType(), filter.getFilterValue(), filter.getStartDate(), filter.getEndDate(),
					filter.getUserId());

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetBfiScoreReport", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petBfiScoreReports;
	}

	protected String getAverageScore(String scores) {
		String acutualScore = "";
		int averageScore = 0;
		if (StringUtils.isNotEmpty(scores)) {
			String[] scoreArr = scores.split(",");
			int sum = 0;
			int count = 0;
			for (String scoreStr : scoreArr) {
				if (StringUtils.isNumeric(scoreStr)) {
					sum += Integer.parseInt(scoreStr);
					count++;
				}
			}
			if (count > 0) {
				averageScore = sum / count;
			}
		}
		if (averageScore == 0) {
			acutualScore = "Unable to assess";
		} else {
			acutualScore = String.valueOf(averageScore);
		}
		return acutualScore;
	}

}
