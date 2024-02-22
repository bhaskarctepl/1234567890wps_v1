
/**
 * Created Date: 02-Dec-2020
 * Class Name  : AuditResourceImpl.java
 * Description : Description of the package.
 *
 * © Copyright 2020 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>        <Author>        <Date>        <Comments on Change>
 * ID                agujjar          02-Dec-2020        Mentions the comments on change, for the new file it's not required.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.hillspet.wearables.jaxrs.resource.impl;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.response.SuccessResponse;
import com.hillspet.wearables.dto.filter.AuditFilter;
import com.hillspet.wearables.jaxrs.resource.AuditLogResource;
import com.hillspet.wearables.response.ActivityResponse;
import com.hillspet.wearables.response.AuditLogResponse;
import com.hillspet.wearables.security.Authentication;
import com.hillspet.wearables.service.auditlog.AuditLogService;

/**
 * Enter detailed explanation of the class here..
 * <p>
 * This class implementation of the <tt>Interface or class Name</tt> interface
 * or class. In addition to implementing the <tt>Interface Name</tt> interface,
 * this class provides methods to do other operations. (Mention other methods
 * purpose)
 *
 * <p>
 * More Description about the class need to be entered here.
 *
 * @author agujjar
 * @version Wearables Portal Relase Version..
 * @since Available Since Wearables Portal Version.
 * @see New line seperated Classes or Interfaces related to this class.
 */
@Service
public class AuditLogResourceImpl implements AuditLogResource {

	@Autowired
	private AuditLogService auditLogService;

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;
	@Autowired
	private Authentication authentication;


	@Override
	public Response getAuditLogs(AuditFilter filter) {
		AuditLogResponse response = auditLogService.getAuditLogs(filter, authentication.getAuthUserDetails().getUserId());
		SuccessResponse<AuditLogResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getActivity(int entityId, int menuId) {
		ActivityResponse response = auditLogService.getActivity(entityId, menuId);
		SuccessResponse<ActivityResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

}
