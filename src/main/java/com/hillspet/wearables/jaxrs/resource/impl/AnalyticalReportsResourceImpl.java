package com.hillspet.wearables.jaxrs.resource.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.dto.Warning;
import com.hillspet.wearables.common.response.SuccessResponse;
import com.hillspet.wearables.dto.AnalyticalReportGroup;
import com.hillspet.wearables.dto.CustomUserDetails;
import com.hillspet.wearables.dto.ManageReports;
import com.hillspet.wearables.dto.PointTracker;
import com.hillspet.wearables.dto.PreludeReport;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.dto.filter.PreludeReportFilter;
import com.hillspet.wearables.jaxrs.resource.AnalyticalReportsResource;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.response.AnalyticalReportGroupResponse;
import com.hillspet.wearables.response.AnalyticalReportResponse;
import com.hillspet.wearables.response.ManageReportResponse;
import com.hillspet.wearables.response.PlansResponse;
import com.hillspet.wearables.response.PointTrackerResponse;
import com.hillspet.wearables.response.PreludeReportResponse;
import com.hillspet.wearables.security.Authentication;
import com.hillspet.wearables.service.analyticalReports.AnalyticalReportsService;
import com.hillspet.wearables.response.GCSignedUrlResponse;

@Service
public class AnalyticalReportsResourceImpl implements  AnalyticalReportsResource{
	private static final Logger LOGGER = LogManager.getLogger(AnalyticalReportsResourceImpl.class);

	@Autowired
	private AnalyticalReportsService analyticalReportsService;

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;

	@Autowired
	private Authentication authentication;
	
	@Override
	public Response getReportGroups() {
		List<AnalyticalReportGroup> analyticalReportGroupList = analyticalReportsService.getReportGroups();
		AnalyticalReportGroupResponse response = new AnalyticalReportGroupResponse();
		response.setAnalyticalReportGroupList(analyticalReportGroupList);
		SuccessResponse<AnalyticalReportGroupResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}
	
	
	@Override
	public Response addReport(ManageReports manageReports, String platform) {
		List<Warning> warnings = new ArrayList<>();
		
//		manageReports.setUserId(authentication.getAuthUserDetails().getUserId());
		manageReports = analyticalReportsService.addReport(manageReports, platform);

		// Step 5: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}
	
	
	@Override
	public Response updateReport(ManageReports manageReports, String platform) {
		List<Warning> warnings = new ArrayList<>();
		
//		manageReports.setUserId(authentication.getAuthUserDetails().getUserId());
		manageReports = analyticalReportsService.updateReport(manageReports, platform);

		// Step 5: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}
	
	@Override
	public Response getReportById(int reportId, String platform) {
		ManageReports manageReports = analyticalReportsService.getReportById(reportId, platform);
		ManageReportResponse response = new ManageReportResponse();
		response.setManageReports(manageReports);;
		SuccessResponse<ManageReportResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
		
	}
	
	@Override
	public Response deleteReport(int reportId, String platform) {
		int modifiedBy = authentication.getAuthUserDetails().getUserId();
		
		// process
		analyticalReportsService.deleteReport(reportId, modifiedBy, platform);
		// build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}
	
	
	@Override
	public Response getReportList(BaseFilter filter, String platform) {
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setUserId(userDetails.getUserId());
		AnalyticalReportResponse response = analyticalReportsService.getReportList(filter, platform);
		SuccessResponse<AnalyticalReportResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}
	
	@Override
	public Response getReportsByReportGroupId(BaseFilter filter, String platform) {
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setUserId(userDetails.getUserId());
		AnalyticalReportResponse response = analyticalReportsService.getReportsByReportGroupId(filter, platform);
		SuccessResponse<AnalyticalReportResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}
	
	
	@Override
	public Response getPreludeReport(PreludeReportFilter filter, String platform) {
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setUserId(userDetails.getUserId());
		PreludeReportResponse response = analyticalReportsService.getPreludeReport(filter, platform);
		SuccessResponse<PreludeReportResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}
	
	@Override
	public Response getPreludeMediaSignedUrl(String filePath) {		
		GCSignedUrlResponse response = analyticalReportsService.getPreludeMediaSignedUrl(filePath);
		SuccessResponse<GCSignedUrlResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	};
}
