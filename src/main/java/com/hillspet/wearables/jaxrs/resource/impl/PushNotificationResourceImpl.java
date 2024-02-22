package com.hillspet.wearables.jaxrs.resource.impl;

import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.response.SuccessResponse;
import com.hillspet.wearables.dto.PushNotification;
import com.hillspet.wearables.dto.PushNotificationRequest;
import com.hillspet.wearables.dto.filter.PushNotificationFilter;
import com.hillspet.wearables.jaxrs.resource.PushNotificationResource;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.response.PushNotificationResponse;
import com.hillspet.wearables.security.Authentication;
import com.hillspet.wearables.service.pushnotify.PushNotificationService;

@Service
public class PushNotificationResourceImpl implements PushNotificationResource {

	private static final Logger LOGGER = LogManager.getLogger(PushNotificationResourceImpl.class);

	@Autowired
	private PushNotificationService pushNotificationService;

	@Autowired
	private Authentication authentication;

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;

	@Override
	public Response addPushNotification(PushNotificationRequest pushNotificationRequest) {
		pushNotificationService.addPushNotification(pushNotificationRequest);
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPushNotificationList(PushNotificationFilter filter) {
		PushNotificationListResponse response = pushNotificationService.getPushNotificationList(filter);
		SuccessResponse<PushNotificationListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPushNotificationById(int pushNotificationId) {
		PushNotification pushNotification = pushNotificationService.getPushNotificationById(pushNotificationId);
		PushNotificationResponse response = new PushNotificationResponse();
		response.setPushNotification(pushNotification);
		SuccessResponse<PushNotificationResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response deletePushNotification(int pushNotificationId) {
		pushNotificationService.deletePushNotification(pushNotificationId,
				authentication.getAuthUserDetails().getUserId());

		// Step 5: build a successful response
		CommonResponse response = new CommonResponse();
		response.setMessage("Push Notification has been deleted successfully");
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response updatePushNotification(PushNotificationRequest pushNotificationRequest) {
		LOGGER.info(" ********** Start of updatePushNotification ************");
		// process
		pushNotificationService.updatePushNotification(pushNotificationRequest);
		// build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

}
