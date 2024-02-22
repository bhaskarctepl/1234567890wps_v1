package com.hillspet.wearables.jaxrs.resource.impl;

import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.response.SuccessResponse;
import com.hillspet.wearables.dto.Role;
import com.hillspet.wearables.dto.filter.RoleFilter;
import com.hillspet.wearables.jaxrs.resource.RoleResource;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.response.RoleResponse;
import com.hillspet.wearables.response.RolesResponse;
import com.hillspet.wearables.security.Authentication;
import com.hillspet.wearables.service.role.RoleService;

@Service
public class RoleResourceImpl implements RoleResource {

	private static final Logger LOGGER = LogManager.getLogger(RoleResourceImpl.class);

	@Autowired
	private RoleService roleService;

	@Autowired
	private Authentication authentication;

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;

	@Override
	public Response addRole(Role role) {

		// Step 2: process
		role = roleService.addRole(role);
		RoleResponse response = new RoleResponse();
		response.setRole(role);

		// Step 5: build a successful response
		SuccessResponse<RoleResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getRolesList(RoleFilter filter) {
		RolesResponse response = roleService.getRolesList(filter);
		SuccessResponse<RolesResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getRoleById(int roleId) {
		Role role = roleService.getRoleById(roleId);
		RoleResponse response = new RoleResponse();
		response.setRole(role);
		SuccessResponse<RoleResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response deleteRole(int roleId) {
		roleService.deleteRole(roleId, authentication.getAuthUserDetails().getUserId());

		// Step 5: build a successful response
		CommonResponse response = new CommonResponse();
		response.setMessage("Role has been deleted successfully");
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response updateRole(Role role) {
		LOGGER.info(" ********** Start of updateRole ************");
		// process
		roleService.updateRole(role);
		// build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

}
