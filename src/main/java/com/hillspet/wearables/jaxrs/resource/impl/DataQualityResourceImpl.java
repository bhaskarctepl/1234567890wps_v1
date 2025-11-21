package com.hillspet.wearables.jaxrs.resource.impl;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.response.SuccessResponse;
import com.hillspet.wearables.dto.filter.DataQualityFilter;
import com.hillspet.wearables.jaxrs.resource.DataQualityResource;
import com.hillspet.wearables.response.DataQualityDeviceSummaryResponse;
import com.hillspet.wearables.response.DataQualityResponse;
import com.hillspet.wearables.response.DataQualityStackedResponse;
import com.hillspet.wearables.response.DataQualityStdyChartResponse;
import com.hillspet.wearables.service.dataQuality.DataQualityService;

public class DataQualityResourceImpl implements DataQualityResource {

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;

	@Autowired
	private DataQualityService dataQualityService;

	@Override
	public Response getAllAlerts(DataQualityFilter filter) {
		DataQualityResponse response = dataQualityService.getAlerts(filter);
		SuccessResponse<DataQualityResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getAlertDetails(DataQualityFilter filter) {
		DataQualityResponse response = dataQualityService.getAlertDetails(filter);
		SuccessResponse<DataQualityResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getAlertRepeatedCount(DataQualityFilter filter) {
		DataQualityResponse response = dataQualityService.getAlertRepeatedCount(filter);
		SuccessResponse<DataQualityResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getStudyDonutGraphs(DataQualityFilter filter) {
		DataQualityStdyChartResponse response = dataQualityService.getStudyDonutGraphs(filter);
		SuccessResponse<DataQualityStdyChartResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getDeviceSummaryDonutGraph(DataQualityFilter filter) {
		DataQualityDeviceSummaryResponse response = dataQualityService.getDeviceSummaryDonutGraph(filter);
		SuccessResponse<DataQualityDeviceSummaryResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getNonQualifyingDays(DataQualityFilter filter) {
		DataQualityDeviceSummaryResponse response = dataQualityService.getNonQualifyingDays(filter);
		SuccessResponse<DataQualityDeviceSummaryResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getAlertCountStackedBarGraph(DataQualityFilter filter) {
		DataQualityStackedResponse response = dataQualityService.getAlertCountStackedBarGraph(filter);
		SuccessResponse<DataQualityStackedResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

}
