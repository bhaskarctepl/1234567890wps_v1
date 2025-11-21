package com.hillspet.wearables.dao.dataquality;

import java.util.List;
import java.util.Map;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.DataQuality;
import com.hillspet.wearables.dto.DataQualityDeviceSummary;
import com.hillspet.wearables.dto.DataQualityStackedList;
import com.hillspet.wearables.dto.DataQualityStudyList;
import com.hillspet.wearables.dto.filter.DataQualityFilter;

public interface DataQualityDao {
	List<DataQuality> getAlerts(DataQualityFilter filter) throws ServiceExecutionException;

	Map<String, Integer> getAlertsCount(DataQualityFilter filter) throws ServiceExecutionException;
	
	List<DataQuality> getAlertDetailsList(DataQualityFilter filter) throws ServiceExecutionException;
	
	List<DataQuality> getAlertRepeatedCount(DataQualityFilter filter) throws ServiceExecutionException;	

	Map<String, Integer> getAlertDetailsCount(DataQualityFilter filter) throws ServiceExecutionException;
	
	DataQualityStudyList getStudyDonutGraphs(DataQualityFilter filter) throws ServiceExecutionException;
	
	DataQualityDeviceSummary getDeviceSummaryDonutGraph(DataQualityFilter filter) throws ServiceExecutionException;
	
	DataQualityStackedList getAlertCountStackedBarGraph(DataQualityFilter filter) throws ServiceExecutionException;
	
	DataQualityDeviceSummary getNonQualifyingDays(DataQualityFilter filter) throws ServiceExecutionException;
	
	
}
