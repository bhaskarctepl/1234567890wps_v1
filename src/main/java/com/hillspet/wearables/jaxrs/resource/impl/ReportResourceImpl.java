package com.hillspet.wearables.jaxrs.resource.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.response.SuccessResponse;
import com.hillspet.wearables.dto.AssetByStudyWidgetFilter;
import com.hillspet.wearables.dto.CustomUserDetails;
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
import com.hillspet.wearables.jaxrs.resource.ReportResource;
import com.hillspet.wearables.response.CustomerSupportIssueByStudyWidgetResponse;
import com.hillspet.wearables.response.CustomerSupportIssueWidgetResponse;
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
import com.hillspet.wearables.security.Authentication;
import com.hillspet.wearables.service.reports.ReportService;

@Service
public class ReportResourceImpl implements ReportResource {

	@Autowired
	private ReportService reportService;

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;

	@Autowired
	private Authentication authentication;

	@Override
	public Response getDeviceDetailsReport(BaseFilter filter) {
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());
		DeviceDetailsReportResponse response = reportService.getDeviceDetailsReport(filter);
		SuccessResponse<DeviceDetailsReportResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getDeviceHistoryReport(BaseFilter filter) {
		int userId = authentication.getAuthUserDetails().getUserId();
		filter.setUserId(userId);
		DeviceHistoryReportResponse response = reportService.getDeviceHistoryReport(filter);
		SuccessResponse<DeviceHistoryReportResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getDeviceInventoryReport(BaseFilter filter) {
		int userId = authentication.getAuthUserDetails().getUserId();
		filter.setUserId(userId);
		DeviceInventoryReportResponse response = reportService.getDeviceInventoryReport(filter);
		SuccessResponse<DeviceInventoryReportResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getDeviceMalfunctionReport(BaseFilter filter) {
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());
		DeviceMalfunctionReportResponse response = reportService.getDeviceMalfunctionReport(filter);
		SuccessResponse<DeviceMalfunctionReportResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getDeviceTrackingReport(BaseFilter filter) {
		int userId = authentication.getAuthUserDetails().getUserId();
		filter.setUserId(userId);
		DeviceTrackingReportResponse response = reportService.getDeviceTrackingReport(filter);
		SuccessResponse<DeviceTrackingReportResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getStudyBasedReport(BaseFilter filter) {
		int userId = authentication.getAuthUserDetails().getUserId();
		StudyBasedReportResponse response = reportService.getStudyBasedReport(filter, userId);
		SuccessResponse<StudyBasedReportResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getAssetsDevicesByStudyReport(AssetByStudyWidgetFilter filter) {
		DevicesbyStudyResponse response = reportService.getAssetsDevicesByStudyReport(filter);
		SuccessResponse<DevicesbyStudyResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getAssetsDevicesMalfunctionsReport(PointTrackerFilter filter) {
		DevicesMalfunctionsResponse response = reportService.getAssetsDevicesMalfunctionsReport(filter);
		SuccessResponse<DevicesMalfunctionsResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPointTrackerReport(PointTrackerFilter filter) {
		int userId = authentication.getAuthUserDetails().getUserId();
		PointTrackerReportResponse response = reportService.getPointTrackerReport(filter, userId);
		SuccessResponse<PointTrackerReportResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPointsAccumulatedReport(PointsAccumulatedReportFilter filter) {
		int userId = authentication.getAuthUserDetails().getUserId();
		PointsAccumulatedReportResponse response = reportService.getPointsAccumulatedReport(filter, userId);
		SuccessResponse<PointsAccumulatedReportResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getCustomerSupportTicketReport(SupportFilter filter) {
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());
		CustomerSupportListResponse response = reportService.getCustomerSupportTickets(filter);
		SuccessResponse<CustomerSupportListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getCustomerSupportIssueWidget(IssueWidgetFilter filter) {
		List<CustomerSupportByCategory> customerSupportByCategories = reportService
				.getCustomerSupportIssueWidget(filter);
		CustomerSupportIssueWidgetResponse response = new CustomerSupportIssueWidgetResponse();
		response.setCustomerSupportByCategories(customerSupportByCategories);
		SuccessResponse<CustomerSupportIssueWidgetResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getCustomerSupportIssueByStudyWidget(IssueByStudyWidgetFilter filter) {
		List<CustomerSupportIssuesByStudy> customerSupportIssuesByStudy = reportService
				.getCustomerSupportIssueByStudyWidget(filter);
		CustomerSupportIssueByStudyWidgetResponse response = new CustomerSupportIssueByStudyWidgetResponse();
		response.setCustomerSupportIssuesByStudy(customerSupportIssuesByStudy);
		SuccessResponse<CustomerSupportIssueByStudyWidgetResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getTotalAssets() {
		TotalAssetsReportResponse response = reportService.getTotalAssets();
		SuccessResponse<TotalAssetsReportResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getTotalAssetsByStatus(TotalAssetsByStausWidgetFilter filter) {
		TotalAssetsByStatusReportResponse response = reportService.getTotalAssetsByStatus(filter);
		SuccessResponse<TotalAssetsByStatusReportResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPetParentReport(PetParentReportFilter filter) {
		int userId = authentication.getAuthUserDetails().getUserId();
		PetParentReportResponse response = reportService.getPetParentReport(filter, userId);
		SuccessResponse<PetParentReportResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPetBfiScoreReport(PetBfiScoreReportFilter filter) {
		int userId = authentication.getAuthUserDetails().getUserId();
		filter.setUserId(userId);
		PetBfiScoreReportResponse response = reportService.getPetBfiScoreReport(filter);
		SuccessResponse<PetBfiScoreReportResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

}
