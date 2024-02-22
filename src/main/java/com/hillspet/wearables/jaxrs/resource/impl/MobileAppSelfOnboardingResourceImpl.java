
/**
 * Created Date: Jan 5, 2021
 * Class Name  : MobileAppSelfOnboardingResourceImpl.java
 *
 * © Copyright 2020 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>        <Author>        <Date>        <Comments on Change>
 * ID                rmaram         Jan 5, 2021        Mentions the comments on change, for the new file it's not required.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package com.hillspet.wearables.jaxrs.resource.impl;

import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.response.SuccessResponse;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.jaxrs.resource.MobileAppSelfOnboardingResource;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.response.MobileAppSelfOnboardingResponse;
import com.hillspet.wearables.service.mobileapp.MobileAppSelfOnboardingService;

/**
 * This class if for providing Mobile App Self On Boarding list details.
 * 
 * @author rmaram
 * @since w1.0
 * @version w1.0
 * @version Jan 5, 2021
 */
@Service
public class MobileAppSelfOnboardingResourceImpl implements MobileAppSelfOnboardingResource {

	private static final Logger LOGGER = LogManager.getLogger(MobileAppFeedbackResourceImpl.class);

	@Autowired
	private MobileAppSelfOnboardingService mobileAppSelfOnboardingService;

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;

	@Override
	public Response getMobileAppSelfOnboarding(BaseFilter filter) {
		MobileAppSelfOnboardingResponse response = mobileAppSelfOnboardingService.getMobilAppSelfOnboarding(filter);
		SuccessResponse<MobileAppSelfOnboardingResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);

	}

	@Override
	public Response assignStudyToPet(int petId, int studyId) {
		mobileAppSelfOnboardingService.assignStudyToPet(petId, studyId);
		// Step 5: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
		
	}

}
