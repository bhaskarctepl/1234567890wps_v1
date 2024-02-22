package com.hillspet.wearables.jaxrs.resource.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.response.SuccessResponse;
import com.hillspet.wearables.dto.CustomUserDetails;
import com.hillspet.wearables.dto.PlanListDTO;
import com.hillspet.wearables.dto.filter.PlanFilter;
import com.hillspet.wearables.jaxrs.resource.PlanResource;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.response.PlansResponse;
import com.hillspet.wearables.security.Authentication;
import com.hillspet.wearables.service.plan.PlanService;

/**
 * This class plans details.
 * 
 * @author sgorle
 * @since w1.0
 * @version w1.0
 * @version Dec 7, 2020
 */
@Service
public class PlanResourceImpl implements PlanResource {

	private static final Logger LOGGER = LogManager.getLogger(PlanResourceImpl.class);

	@Autowired
	private PlanService planService;

	@Autowired
	private Authentication authentication;

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;

	@Override
	public Response getPlanList(PlanFilter filter) {
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());
		PlansResponse response = planService.getPlanList(filter);
		SuccessResponse<PlansResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	public Response getPlans() {
		List<PlanListDTO> plans = planService.getPlans();
		PlansResponse response = new PlansResponse();
		response.setPlansList(plans);
		SuccessResponse<PlansResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response addPlan(PlanListDTO planListDTO) {
		planListDTO = planService.addPlan(planListDTO);

		// Step 5: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPlanById(int planId) {
		PlanListDTO planListDTO = planService.getPlanById(planId);
		PlansResponse response = new PlansResponse();
		response.setPlanListDTO(planListDTO);
		SuccessResponse<PlansResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response updatePlan(PlanListDTO planListDTO) {
		LOGGER.info(" ********** Start of updatePlan ************");
		// process
		planService.updatePlan(planListDTO);
		// build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response deletePlan(int planId) {
		int modifiedBy = authentication.getAuthUserDetails().getUserId();

		// process
		planService.deletePlan(planId, modifiedBy);
		// build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

}
