package com.hillspet.wearables.dao.dataquality.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.constants.SQLConstants;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.dataquality.DataQualityDao;
import com.hillspet.wearables.dto.DataQuality;
import com.hillspet.wearables.dto.DataQualityDeviceSummary;
import com.hillspet.wearables.dto.DataQualityStackedList;
import com.hillspet.wearables.dto.DataQualityStudyList;
import com.hillspet.wearables.dto.filter.DataQualityFilter;

@Repository
public class DataQualityDaoImpl extends BaseDaoImpl implements DataQualityDao {

	private static final Logger LOGGER = LogManager.getLogger(DataQualityDaoImpl.class);

	@Autowired
	private ObjectMapper mapper;

	@Override
	public List<DataQuality> getAlerts(DataQualityFilter filter) throws ServiceExecutionException {
		List<DataQuality> alerts = new ArrayList<>();
		LOGGER.debug("getAlerts called");
		try {
			jdbcTemplate.query(SQLConstants.GET_DEVICE_ALERTS_REPORT, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					DataQuality alert = new DataQuality();
					alert.setDeviceNumber(rs.getString("DEVICE_NUMBER"));
					alert.setStudyName(rs.getString("STUDY_NAME"));
					alert.setStartDate(rs.getString("FROM_DATE"));
					alert.setEndDate(rs.getString("TO_DATE"));
					alert.setFirmware(rs.getString("MFG_FIRMWARE"));
					alert.setDeviceModel(rs.getString("DEVICE_MODEL"));
					alert.setBoxNumber(rs.getString("BOX_NUMBER"));

					alerts.add(alert);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getFilterType(), filter.getFilterValue(), filter.getUserId(), filter.getRoleTypeId(), filter.getStartDate() 
					, filter.getEndDate(), filter.getDuration() , filter.getPriority());

		} catch (Exception e) {
			LOGGER.error("error while fetching getAlerts", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return alerts;
	}

	@Override
	public Map<String, Integer> getAlertsCount(DataQualityFilter filter) throws ServiceExecutionException {
		int totalCount = NumberUtils.INTEGER_ZERO;
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		LOGGER.debug("getDeviceDetailsReportCount called");
		try {
			counts = selectForObject(SQLConstants.FN_GET_DEVICE_ALERTS_REPORT_COUNT, String.class,
					filter.getSearchText(), filter.getFilterType(), filter.getFilterValue(), filter.getUserId(),
					filter.getRoleTypeId(), filter.getStartDate() 
					, filter.getEndDate(), filter.getDuration() , filter.getPriority());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceDetailsReportCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<DataQuality> getAlertDetailsList(DataQualityFilter filter) throws ServiceExecutionException {
		List<DataQuality> alerts = new ArrayList<>();
		LOGGER.debug("getAlertDetailsList called  ",filter.getAlertTypeId() );
		try {
			jdbcTemplate.query(SQLConstants.GET_DEVICE_ALERTS_DETAILS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					DataQuality alert = new DataQuality();
					alert.setAlertName(rs.getString("ALERT_DESC"));
					alert.setDeviceNumber(rs.getString("DEVICE_NUMBER"));
					alert.setAlertDate(rs.getString("ALERT_DATE"));
					alert.setAlertDetails(rs.getString("DETAILS"));

					alerts.add(alert);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(),
					filter.getAlertTypeId(), filter.getDeviceNumber(), filter.getStartDate() 
					, filter.getEndDate());

		} catch (Exception e) {
			LOGGER.error("error while fetching getAlerts", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return alerts;
	}
	
	@Override
	public List<DataQuality> getAlertRepeatedCount(DataQualityFilter filter) throws ServiceExecutionException {
		List<DataQuality> alerts = new ArrayList<>();
		LOGGER.debug("getAlertRepeatedCount called");
		try {
			jdbcTemplate.query(SQLConstants.GET_DEVICE_ALERTS_REPEAT_COUNTS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					DataQuality alert = new DataQuality();
					alert.setAlertName(rs.getString("ALERT_DESC"));
					alert.setAlertPriority(rs.getString("PRIORITY"));
					alert.setRepeatedCount(rs.getString("REPEATED"));
					alert.setAlertTypeId(rs.getInt("ALERT_TYPE_ID"));
					alert.setAlertDetails(rs.getString("DETAILS"));

					alerts.add(alert);
				}
			}, filter.getDeviceNumber(), filter.getStartDate()  , filter.getEndDate());

		} catch (Exception e) {
			LOGGER.error("error while fetching getAlertRepeatedCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return alerts;
	}
	
	

	@Override
	public Map<String, Integer> getAlertDetailsCount(DataQualityFilter filter) throws ServiceExecutionException {
		int totalCount = NumberUtils.INTEGER_ZERO;
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		LOGGER.debug("getDeviceDetailsReportCount called");
		try {
			counts = selectForObject(SQLConstants.FN_GET_DEVICE_ALERTS_DETAILS_COUNT, String.class,
					filter.getAlertTypeId(), filter.getDeviceNumber(),  filter.getStartDate() 
					, filter.getEndDate());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceDetailsReportCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public DataQualityStudyList getStudyDonutGraphs(DataQualityFilter filter) throws ServiceExecutionException {

		String chartData = "";
		DataQualityStudyList rows = null;
		LOGGER.debug("getStudyDonutGraphs called");
		try {
			chartData = selectForObject(SQLConstants.GET_DEVICE_STUDY_DONUT_GRAPH, String.class,filter.getDuration());

			if (chartData != null) {

				rows = new ObjectMapper().readValue(chartData, DataQualityStudyList.class);
			}

		} catch (Exception e) {
			LOGGER.error("chartData " + chartData);
			LOGGER.error("error while fetching getStudyDonutGraphs", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return rows;
	}

	@Override
	public DataQualityDeviceSummary getDeviceSummaryDonutGraph(DataQualityFilter filter)
			throws ServiceExecutionException {

		String chartData = "";
		DataQualityDeviceSummary rows = null;
		LOGGER.debug("getDeviceSummaryDonutGraph called");
		try {
			chartData = selectForObject(SQLConstants.GET_DEVICE_SUMMARY_DONUT_GRAPH, String.class,filter.getDuration());

			if (chartData != null) {

				rows = new ObjectMapper().readValue(chartData, DataQualityDeviceSummary.class);
			}

		} catch (Exception e) {
			LOGGER.error("chartData " + chartData);
			LOGGER.error("error while fetching getDeviceSummaryDonutGraph", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return rows;
	}
	@Override
	public DataQualityStackedList getAlertCountStackedBarGraph(DataQualityFilter filter) throws ServiceExecutionException {

		String chartData = "";
		DataQualityStackedList rows = null;
		LOGGER.debug("getStudyDonutGraphs called  ",filter.getDuration());
		try {
			chartData = selectForObject(SQLConstants.GET_DEVICE_ALERT_COUNT_STACKED, String.class,filter.getDuration());

			if (chartData != null) {

				rows = new ObjectMapper().readValue(chartData, DataQualityStackedList.class);
			}

		} catch (Exception e) {
			LOGGER.error("chartData " + chartData);
			LOGGER.error("error while fetching getStudyDonutGraphs", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return rows;
	}

	@Override
	public DataQualityDeviceSummary getNonQualifyingDays(DataQualityFilter filter) throws ServiceExecutionException {

		String chartData = "";
		DataQualityDeviceSummary rows = null;
		LOGGER.debug("getNonQualifyingDays called");
		try {
			chartData = selectForObject(SQLConstants.GET_DEVICE_NON_QUALIFYING_DAYS_GRAPH, String.class,filter.getDuration());

			if (chartData != null) {

				rows = new ObjectMapper().readValue(chartData, DataQualityDeviceSummary.class);
			}

		} catch (Exception e) {
			LOGGER.error("chartData " + chartData);
			LOGGER.error("error while fetching getNonQualifyingDays", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return rows;
	}

}
