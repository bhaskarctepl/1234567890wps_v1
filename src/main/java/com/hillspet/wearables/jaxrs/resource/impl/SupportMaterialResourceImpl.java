
/**
 * Created Date: 08-Dec-2020
 * Class Name  : SupportMaterialResourceImpl.java
 * Description : Description of the package.
 *
 * © Copyright 2020 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>        <Author>        <Date>        <Comments on Change>
 * ID                Rajesh          08-Dec-2020        Mentions the comments on change, for the new file it's not required.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.hillspet.wearables.jaxrs.resource.impl;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.response.SuccessResponse;
import com.hillspet.wearables.dto.CustomUserDetails;
import com.hillspet.wearables.dto.SupportMaterialDetails;
import com.hillspet.wearables.dto.filter.SupportMaterialFilter;
import com.hillspet.wearables.jaxrs.resource.SupportMaterialResource;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.request.AddSupportMaterialRequest;
import com.hillspet.wearables.request.UpdateSupportMaterialRequest;
import com.hillspet.wearables.response.SupportMaterialTypeCountResponse;
import com.hillspet.wearables.response.SupportMaterialsDetailsListResponse;
import com.hillspet.wearables.security.Authentication;
import com.hillspet.wearables.service.support.SupportMaterialService;

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
 * @author sgorle
 * @version Wearables Portal Relase Version..
 * @since Available Since Wearables Portal Version.
 * @see New line seperated Classes or Interfaces related to this class.
 */
@Service
public class SupportMaterialResourceImpl implements SupportMaterialResource {

	private static final Logger LOGGER = LogManager.getLogger(SupportMaterialResourceImpl.class);

	@Autowired
	private SupportMaterialService supportService;

	@Autowired
	private Authentication authentication;

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;


	@Override
	public Response addSupportMaterial(List<AddSupportMaterialRequest> addSupportMaterialRequest) {
		LOGGER.debug("addSupportMaterial called");
		Integer userId = authentication.getAuthUserDetails().getUserId();
		addSupportMaterialRequest.get(0).setUserId(userId);
		supportService.addSuportMaterial(addSupportMaterialRequest); 

		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		LOGGER.debug("addSupportMaterial end");
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getMaterialDetailsListById(int materialId,SupportMaterialFilter filter) {
		LOGGER.debug("getMaterialDetailsListById called");
		filter.setSupportMaterialId(materialId);
		SupportMaterialsDetailsListResponse response = supportService.getMaterialDetailsListById(filter);
		SuccessResponse<SupportMaterialsDetailsListResponse> successResponse = new SuccessResponse<SupportMaterialsDetailsListResponse>();
		successResponse.setServiceResponse(response);
		
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getSupportMaterialTypeCountList(SupportMaterialFilter filter) {
		LOGGER.debug("getSupportMaterialTypeCountList called");
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());
		if(StringUtils.isNotBlank(filter.getFilterValue())) {
			filter.setMaterialType(Integer.parseInt(filter.getFilterValue()));  //Selected Material Type Value	
		}
		SupportMaterialTypeCountResponse response = supportService.getSupportMaterialTypeCountList(filter);
		SuccessResponse<SupportMaterialTypeCountResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getMaterialDetailsById(int materialId) {
		LOGGER.debug("getMaterialDetailsById called");
		SupportMaterialDetails response = supportService.getSupportMaterialDetails(materialId);
		SuccessResponse<SupportMaterialDetails> successResponse = new SuccessResponse<SupportMaterialDetails>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response updateSupportMaterial(UpdateSupportMaterialRequest updateSupportMaterialRequest) {
		LOGGER.debug("updateSupportMaterial called");
		Integer userId = authentication.getAuthUserDetails().getUserId();
		updateSupportMaterialRequest.setUserId(userId);
		supportService.updateSupportMaterial(updateSupportMaterialRequest);
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response validateTitle(@Valid List<AddSupportMaterialRequest> addSupportMaterialRequest) {
		LOGGER.debug("validateTitle called");
		Map<String, String> response=supportService.validateTitle(addSupportMaterialRequest); 
		SuccessResponse<Map<String, String>> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response deleteSupportMaterial(int supportMaterialId) {
		int modifiedBy = authentication.getAuthUserDetails().getUserId();
		supportService.deleteSupportMaterial(supportMaterialId, modifiedBy);

		// Step 5: build a successful response
		CommonResponse response = new CommonResponse();
		response.setMessage("Support Material Deleted Successfully");
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

}
