package com.hillspet.wearables.jaxrs.resource.impl;

import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.response.SuccessResponse;
import com.hillspet.wearables.dto.CustomUserDetails;
import com.hillspet.wearables.dto.PetSchedQuestionnaire;
import com.hillspet.wearables.dto.filter.PetFilter;
import com.hillspet.wearables.dto.filter.PetQuestionnaireFilter;
import com.hillspet.wearables.jaxrs.resource.PetQuestionnaireResource;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.request.NotificationRequest;
import com.hillspet.wearables.request.PetQuestionnaireRequest;
import com.hillspet.wearables.response.PetQuestionnaireReccurrenceResponse;
import com.hillspet.wearables.response.PetSchedQuestionnaireListResponse;
import com.hillspet.wearables.response.PetSchedQuestionnaireResponse;
import com.hillspet.wearables.response.PetsResponse;
import com.hillspet.wearables.response.QuestionnaireViewResponse;
import com.hillspet.wearables.security.Authentication;
import com.hillspet.wearables.service.pet.PetQuestionnaireService;

public class PetQuestionnaireResourceImpl implements PetQuestionnaireResource {

	private static final Logger LOGGER = LogManager.getLogger(PetQuestionnaireResourceImpl.class);

	@Autowired
	private PetQuestionnaireService petQuestionnaireService;

	@Autowired
	private Authentication authentication;

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;

	@Override
	public Response getPetsToSchedQuestionnaire(PetFilter filter) {
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());
		PetsResponse response = petQuestionnaireService.getPetsToSchedQuestionnaire(filter);
		SuccessResponse<PetsResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response addPetQuestionnaire(PetQuestionnaireRequest petQuestionnaireRequest) {
		// Step 2: process
		petQuestionnaireRequest.setUserId(authentication.getAuthUserDetails().getUserId());
		petQuestionnaireService.addPetQuestionnaire(petQuestionnaireRequest);

		// Step 5: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		CommonResponse response = new CommonResponse();
		response.setMessage("Pet Questionnaire saved successfully");
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPetSchedQuestionnaireList(PetQuestionnaireFilter filter) {
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());
		PetSchedQuestionnaireListResponse response = petQuestionnaireService.getPetSchedQuestionnaireList(filter);
		SuccessResponse<PetSchedQuestionnaireListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPetQuestionnaireListByConfigId(PetQuestionnaireFilter filter) {
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());
		PetQuestionnaireReccurrenceResponse response = petQuestionnaireService
				.getPetQuestionnaireListByConfigId(filter);
		SuccessResponse<PetQuestionnaireReccurrenceResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPetQuestionnaireByConfigId(int petQuestionnaireConfigId) {
		PetSchedQuestionnaire petSchedQuestionnaire = petQuestionnaireService
				.getPetQuestionnaireByConfigId(petQuestionnaireConfigId);

		// Step 5: build a successful response
		PetSchedQuestionnaireResponse response = new PetSchedQuestionnaireResponse();
		response.setPetSchedQuestionnaire(petSchedQuestionnaire);
		SuccessResponse<PetSchedQuestionnaireResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response deletePetQuestionnaire(int petQuestionnaireConfigId) {
		int modifiedBy = authentication.getAuthUserDetails().getUserId();
		petQuestionnaireService.deletePetQuestionnaire(petQuestionnaireConfigId, modifiedBy);

		// Step 5: build a successful response
		CommonResponse response = new CommonResponse();
		response.setMessage("Pet Questionnaire has been deleted successfully");
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getQuestionnaireResponse(int petQuestionnaireConfigId, int questionnaireResponseId) {
		QuestionnaireViewResponse response = petQuestionnaireService.getQuestionnaireResponse(petQuestionnaireConfigId,
				questionnaireResponseId);
		SuccessResponse<QuestionnaireViewResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response sendNotification(NotificationRequest notificationRequest) {
		int userId = authentication.getAuthUserDetails().getUserId();
		notificationRequest.setUserId(userId);
		petQuestionnaireService.sendNotification(notificationRequest);
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		CommonResponse response = new CommonResponse();
		response.setMessage("Notification has been sent successfully");
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

}
