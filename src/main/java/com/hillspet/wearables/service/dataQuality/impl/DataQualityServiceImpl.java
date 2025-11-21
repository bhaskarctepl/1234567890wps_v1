package com.hillspet.wearables.service.dataQuality.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.dataquality.DataQualityDao;
import com.hillspet.wearables.dto.DataQuality;
import com.hillspet.wearables.dto.DataQualityDeviceSummary;
import com.hillspet.wearables.dto.DataQualityStackedList;
import com.hillspet.wearables.dto.DataQualityStudyList;
import com.hillspet.wearables.dto.filter.DataQualityFilter;
import com.hillspet.wearables.response.DataQualityDeviceSummaryResponse;
import com.hillspet.wearables.response.DataQualityResponse;
import com.hillspet.wearables.response.DataQualityStackedResponse;
import com.hillspet.wearables.response.DataQualityStdyChartResponse;
import com.hillspet.wearables.service.dataQuality.DataQualityService;

@Service
public class DataQualityServiceImpl implements DataQualityService {

	private static final Logger LOGGER = LogManager.getLogger(DataQualityServiceImpl.class);

	@Autowired
	private DataQualityDao dataQualityDao;

	@Override
	public DataQualityResponse getAlerts(DataQualityFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getAlerts called");
		Map<String, Integer> mapper = dataQualityDao.getAlertsCount(filter);
		int total = mapper.get("count");
		int totalCount = mapper.get("totalCount");
		List<DataQuality> users = total > NumberUtils.INTEGER_ZERO ? dataQualityDao.getAlerts(filter)
				: new ArrayList<>();

		DataQualityResponse response = new DataQualityResponse();
		response.setAlerts(users);
		response.setNoOfElements(users.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);

		LOGGER.debug("getAlerts alerts are {}", users);
		LOGGER.debug("getAlerts completed successfully");
		return response;
	}

	@Override
	public DataQualityResponse getAlertDetails(DataQualityFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getAlertDetails called");
		Map<String, Integer> mapper = dataQualityDao.getAlertDetailsCount(filter);
		int total = mapper.get("count");
		int totalCount = mapper.get("totalCount");
		List<DataQuality> users = total > NumberUtils.INTEGER_ZERO ? dataQualityDao.getAlertDetailsList(filter)
				: new ArrayList<>();

		DataQualityResponse response = new DataQualityResponse();
		response.setAlerts(users);
		response.setNoOfElements(users.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);

		LOGGER.debug("getAlertDetails alerts are {}", users);
		LOGGER.debug("getAlertDetails completed successfully");
		return response;
	}

	@Override
	public DataQualityResponse getAlertRepeatedCount(DataQualityFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getAlertRepeatedCount called");

		List<DataQuality> users = dataQualityDao.getAlertRepeatedCount(filter);

		DataQualityResponse response = new DataQualityResponse();
		response.setAlerts(users);

		LOGGER.debug("getAlertRepeatedCount alerts are {}", users);
		LOGGER.debug("getAlertRepeatedCount completed successfully");
		return response;
	}

	@Override
	public DataQualityStdyChartResponse getStudyDonutGraphs(DataQualityFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getStudyDonutGraphs called");

		DataQualityStudyList chartList = dataQualityDao.getStudyDonutGraphs(filter);

		DataQualityStdyChartResponse response = new DataQualityStdyChartResponse();
		response.setStudyChart(chartList);

		LOGGER.debug("getStudyDonutGraphs completed successfully");
		return response;
	}

	@Override
	public DataQualityDeviceSummaryResponse getDeviceSummaryDonutGraph(DataQualityFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("DataQualityDeviceSummaryResponse called");

		DataQualityDeviceSummary chartList = dataQualityDao.getDeviceSummaryDonutGraph(filter);

		DataQualityDeviceSummaryResponse response = new DataQualityDeviceSummaryResponse();
		response.setDeviceChart(chartList);

		LOGGER.debug("DataQualityDeviceSummaryResponse completed successfully");
		return response;
	}

	@Override
	public DataQualityDeviceSummaryResponse getNonQualifyingDays(DataQualityFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getNonQualifyingDays called");

		DataQualityDeviceSummary chartList = dataQualityDao.getNonQualifyingDays(filter);

		DataQualityDeviceSummaryResponse response = new DataQualityDeviceSummaryResponse();
		response.setDeviceChart(chartList);

		LOGGER.debug("DataQualityDeviceSummaryResponse completed successfully");
		return response;
	}

	@Override
	public DataQualityStackedResponse getAlertCountStackedBarGraph(DataQualityFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getStudyDonutGraphs called");

		DataQualityStackedList chartList = dataQualityDao.getAlertCountStackedBarGraph(filter);

		DataQualityStackedResponse response = new DataQualityStackedResponse();
		response.setStudyChart(chartList);

		LOGGER.debug("getStudyDonutGraphs completed successfully");
		return response;
	}
}
