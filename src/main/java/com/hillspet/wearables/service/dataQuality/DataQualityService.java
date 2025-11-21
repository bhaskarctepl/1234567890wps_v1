package com.hillspet.wearables.service.dataQuality;

import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.filter.DataQualityFilter;
import com.hillspet.wearables.response.DataQualityDeviceSummaryResponse;
import com.hillspet.wearables.response.DataQualityResponse;
import com.hillspet.wearables.response.DataQualityStackedResponse;
import com.hillspet.wearables.response.DataQualityStdyChartResponse;

@Service
public interface DataQualityService {

	DataQualityResponse getAlerts(DataQualityFilter filter) throws ServiceExecutionException;
	
	DataQualityResponse getAlertDetails(DataQualityFilter filter) throws ServiceExecutionException;
	
	DataQualityResponse getAlertRepeatedCount(DataQualityFilter filter) throws ServiceExecutionException;	
	
	DataQualityStdyChartResponse getStudyDonutGraphs(DataQualityFilter filter) throws ServiceExecutionException;
	
	DataQualityDeviceSummaryResponse getDeviceSummaryDonutGraph(DataQualityFilter filter) throws ServiceExecutionException;
	
	DataQualityDeviceSummaryResponse getNonQualifyingDays(DataQualityFilter filter) throws ServiceExecutionException;
	
	DataQualityStackedResponse getAlertCountStackedBarGraph(DataQualityFilter filter) throws ServiceExecutionException;
}
