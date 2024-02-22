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
import com.hillspet.wearables.dto.QuestionCategory;
import com.hillspet.wearables.dto.filter.QuestionCategoryFilter;
import com.hillspet.wearables.jaxrs.resource.QuestionCategoryResource;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.request.QuestionCategoryRequest;
import com.hillspet.wearables.response.QuestionCategoryListResponse;
import com.hillspet.wearables.response.QuestionCategoryResponse;
import com.hillspet.wearables.security.Authentication;
import com.hillspet.wearables.service.questionnaire.QuestionCategoryService;

/**
 * This class plans details.
 * 
 * @author sgorle
 * @since w1.0
 * @version w1.0
 * @version September 14, 2023
 */
@Service
public class QuestionCategoryResourceImpl implements QuestionCategoryResource {

	private static final Logger LOGGER = LogManager.getLogger(QuestionCategoryResourceImpl.class);

	@Autowired
	private QuestionCategoryService questionCategoryService;

	@Autowired
	private Authentication authentication;

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;

	@Override
	public Response addQuestionCategory(QuestionCategoryRequest questionCategoryRequest) {
		int createdBy = authentication.getAuthUserDetails().getUserId();
		questionCategoryService.addQuestionCategory(questionCategoryRequest, createdBy);
		// Build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response updateQuestionCategory(QuestionCategoryRequest questionCategoryRequest) {
		LOGGER.info(" ********** Start of updateQuestionCategory ************");
		int modifiedBy = authentication.getAuthUserDetails().getUserId();
		questionCategoryService.updateQuestionCategory(questionCategoryRequest, modifiedBy);
		// build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response deleteQuestionCategory(int questionCategoryId) {
		int modifiedBy = authentication.getAuthUserDetails().getUserId();

		questionCategoryService.deleteQuestionCategory(questionCategoryId, modifiedBy);
		// build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getQuestionCategoryList(QuestionCategoryFilter filter) {
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());
		QuestionCategoryListResponse response = questionCategoryService.getQuestionCategoryList(filter);
		SuccessResponse<QuestionCategoryListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getQuestionCategories() {
		List<QuestionCategory> QuestionCategories = questionCategoryService.getQuestionCategories();
		QuestionCategoryListResponse response = new QuestionCategoryListResponse();
		response.setQuestionCategories(QuestionCategories);
		SuccessResponse<QuestionCategoryListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getQuestionCategoryById(int questionCategoryId) {
		QuestionCategory questionCategory = questionCategoryService.getQuestionCategoryById(questionCategoryId);
		QuestionCategoryResponse response = new QuestionCategoryResponse();
		response.setQuestionCategory(questionCategory);
		SuccessResponse<QuestionCategoryResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}
}
