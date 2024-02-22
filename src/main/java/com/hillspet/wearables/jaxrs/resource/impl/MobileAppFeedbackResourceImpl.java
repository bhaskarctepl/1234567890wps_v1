
/**
 * Created Date: Nov 9, 2020
 * Class Name  : MobileAppFeedbackResourceImpl.java
 * Description : Description of the package.
 *
 * © Copyright 2008 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>        <Author>        <Date>        <Comments on Change>
 * ID                rmaram          Nov 9, 2020        Mentions the comments on change, for the new file it's not required.
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
import com.hillspet.wearables.dto.CustomUserDetails;
import com.hillspet.wearables.dto.filter.MobileAppFeedbackFilter;
import com.hillspet.wearables.jaxrs.resource.MobileAppFeedbackResource;
import com.hillspet.wearables.response.MobileAppFeedbackResponse;
import com.hillspet.wearables.security.Authentication;
import com.hillspet.wearables.service.mobileapp.MobileAppFeedbackService;

/**
 * This class if for providing Mobile App Feed Back list details.
 * 
 * @author rmaram
 * @since w1.0
 * @version w1.0
 * @version Nov 9, 2020
 */
@Service
public class MobileAppFeedbackResourceImpl implements MobileAppFeedbackResource {

	private static final Logger LOGGER = LogManager.getLogger(MobileAppFeedbackResourceImpl.class);

	@Autowired
	private MobileAppFeedbackService mobileAppFeedbackService;
	
	@Autowired
	private Authentication authentication;

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;

	@Override
	public Response getMobileAppFeedback(MobileAppFeedbackFilter filter) {
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());
		MobileAppFeedbackResponse response = mobileAppFeedbackService.getMobilAppFeedback(filter);
		SuccessResponse<MobileAppFeedbackResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
		
	}

}
