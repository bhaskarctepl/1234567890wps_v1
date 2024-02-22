package com.hillspet.wearables.jaxrs.resource.impl;

import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.response.SuccessResponse;
import com.hillspet.wearables.dto.CustomUserDetails;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.dto.filter.TimerLogFilter;
import com.hillspet.wearables.jaxrs.resource.TimerLogResource;
import com.hillspet.wearables.response.TimerLogResponse;
import com.hillspet.wearables.security.Authentication;
import com.hillspet.wearables.service.timerLog.TimerLogService;

@Service
public class TimerLogResourceImpl implements TimerLogResource{

	
	private static final Logger LOGGER = LogManager.getLogger(TimerLogResourceImpl.class);

	@Autowired
	private TimerLogService timerLogService;

	@Autowired
	private Authentication authentication;

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;
	
	
	@Override
	public Response getTimerLogList(TimerLogFilter filter) {
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setUserId(userDetails.getUserId());
		TimerLogResponse response = timerLogService.getTimerLogList(filter);
		SuccessResponse<TimerLogResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}
}
