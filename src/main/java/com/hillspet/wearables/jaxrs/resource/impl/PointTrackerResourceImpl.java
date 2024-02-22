
package com.hillspet.wearables.jaxrs.resource.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.dto.Warning;
import com.hillspet.wearables.common.response.SuccessResponse;
import com.hillspet.wearables.dto.CustomUserDetails;
import com.hillspet.wearables.dto.PointTracker;
import com.hillspet.wearables.dto.TrackerActivityStatusNotes;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.dto.filter.PointTrackerFilter;
import com.hillspet.wearables.format.validation.FormatValidationService;
import com.hillspet.wearables.jaxrs.resource.PointTrackerResource;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.response.PointTrackerCampaignActivitiesResponse;
import com.hillspet.wearables.response.PointTrackerResponse;
import com.hillspet.wearables.security.Authentication;
import com.hillspet.wearables.service.promotion.PointTrackerService;

@Service
public class PointTrackerResourceImpl implements PointTrackerResource {

	private static final Logger LOGGER = LogManager.getLogger(PointTrackerResourceImpl.class);

	@Autowired
	private PointTrackerService pointTrackerService;

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;

	@Autowired
	private Authentication authentication;
	
	@Autowired
	private FormatValidationService formatValidationService;

	@Override
	public Response addPointTracker(PointTracker pointTracker) {
		List<Warning> warnings = new ArrayList<>();
		// Step 1: prevalidate
//		ValidationResult validationResult = validateUserRequest(pointTracker, warnings);
//		// TODO: check validationResult is null or not. Since
//		// apiOperation.preValidate
//		// may return optional of null value.
//		if (CollectionUtils.isNotEmpty(validationResult.getErrorList())) {
//			throw new ServiceValidationException("addUser PreValidation check has failed cannot proceed further",
//					validationResult.getStatusOnError(), validationResult.getErrorList());
//		}
//		warnings.addAll(validationResult.getWarningList());

		// Step 2: process
		pointTracker.setUserId(authentication.getAuthUserDetails().getUserId());
		pointTracker = pointTrackerService.addPointTracker(pointTracker);

		// Step 5: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	
	@Override
	public Response getPointTrackerList(PointTrackerFilter filter) {
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());
		PointTrackerResponse response = pointTrackerService.getPointTrackingList(filter);
		SuccessResponse<PointTrackerResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}
	
	
	@Override
	public Response updatePointTracker(PointTracker pointTracker) {
		List<Warning> warnings = new ArrayList<>();
		pointTracker.setUserId(authentication.getAuthUserDetails().getUserId());
		pointTrackerService.updatePointTracker(pointTracker);

		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}
	
	
	
	
	@Override
	public Response getPointTrackerById(int pointTrackerId) {
		PointTracker pointTracker = pointTrackerService.getPointTrackerById(pointTrackerId);
		PointTrackerResponse response = new PointTrackerResponse();
		response.setPointTracker(pointTracker);
		SuccessResponse<PointTrackerResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
		
	}
//	private ValidationResult validateUserRequest(PointTrackerRequest pointTrackerRequest, List<Warning> warnings) {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public Response getAllPointTracker() {
//		List<PointTracker> pointTraker = pointTrackerService.getAllPointTracker();
//		PointTrackerResponse response = new PointTrackerResponse();
//		response.setPointTracker(pointTraker);
//
//		SuccessResponse<PointTrackerResponse> successResponse = new SuccessResponse<>();
//		successResponse.setServiceResponse(response);
//		return responseBuilder.buildResponse(successResponse);
//	}

//	@Override
//	public Response addAllPointTracker(PointTrackerRequest pointTrackerRequest) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	@Override
	public Response getPointTrackerActiviesList(PointTrackerFilter filter, int pointTrackerId) {
		PointTrackerCampaignActivitiesResponse response = pointTrackerService.getPointTrackerActiviesList(filter, pointTrackerId);
		SuccessResponse<PointTrackerCampaignActivitiesResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}


	@Override
	public Response updateTrackerActStatus(TrackerActivityStatusNotes trackerActivityStatusNotes) {
		// Step 1: process
		trackerActivityStatusNotes.setCreatedBy(authentication.getAuthUserDetails().getUserId());
		
		pointTrackerService.updateTrackerActStatus(trackerActivityStatusNotes);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

}
