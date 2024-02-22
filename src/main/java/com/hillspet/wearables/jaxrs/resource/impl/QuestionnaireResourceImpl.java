
/**
 * Created Date: 08-Dec-2020
 * Class Name  : CustomerSupportImpl.java
 * Description : Description of the package.
 *
 * © Copyright 2020 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>        <Author>        <Date>        <Comments on Change>
 * ID                sgorle          08-Dec-2020        Mentions the comments on change, for the new file it's not required.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.hillspet.wearables.jaxrs.resource.impl;

import javax.validation.Valid;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.response.SuccessResponse;
import com.hillspet.wearables.dto.Question;
import com.hillspet.wearables.dto.Questionnaire;
import com.hillspet.wearables.dto.QuestionnaireInstruction;
import com.hillspet.wearables.dto.filter.QuestionnaireFilter;
import com.hillspet.wearables.dto.filter.QuestionnaireResponseByStudyFilter;
import com.hillspet.wearables.dto.filter.QuestionnaireResponseFilter;
import com.hillspet.wearables.jaxrs.resource.QuestionnaireResource;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.request.QuestionnaireRequest;
import com.hillspet.wearables.request.QuestionnaireSkipRequest;
import com.hillspet.wearables.response.PetQuestionnaireResponseList;
import com.hillspet.wearables.response.QuestionnaireListResponse;
import com.hillspet.wearables.response.QuestionnaireResponse;
import com.hillspet.wearables.response.QuestionnaireResponseByStudyListResponse;
import com.hillspet.wearables.response.QuestionnaireResponseListResponse;
import com.hillspet.wearables.response.QuestionnaireViewResponse;
import com.hillspet.wearables.security.Authentication;
import com.hillspet.wearables.service.questionnaire.QuestionnaireService;

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
public class QuestionnaireResourceImpl implements QuestionnaireResource {

	private static final Logger LOGGER = LogManager.getLogger(QuestionnaireResourceImpl.class);

	@Autowired
	private QuestionnaireService questionnaireService;

	@Autowired
	private Authentication authentication;

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;

	@Override
	public Response addQuestionnaire(QuestionnaireRequest questionnaireRequest) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		Integer questionnaireId = questionnaireService.addQuestionnaire(questionnaireRequest, userId);
		LOGGER.info("Questionnarei inserted successfully {}", questionnaireId);
		
		// Step 2: build a successful response
		QuestionnaireResponse questionnaireResponse = new QuestionnaireResponse();
		Questionnaire questionnaire = new Questionnaire();
		questionnaire.setQuestionnaireId(questionnaireId);
		questionnaireResponse.setQuestionnaire(questionnaire);
		SuccessResponse<QuestionnaireResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(questionnaireResponse);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response updateQuestionnaire(QuestionnaireRequest questionnaireRequest) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		questionnaireService.updateQuestionnaire(questionnaireRequest, userId);

		// Step 2: build a successful response
		QuestionnaireResponse questionnaireResponse = new QuestionnaireResponse();
		Questionnaire questionnaire = new Questionnaire();
		questionnaire.setQuestionnaireId(questionnaireRequest.getQuestionnaire().getQuestionnaireId());
		questionnaireResponse.setQuestionnaire(questionnaire);
		SuccessResponse<QuestionnaireResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(questionnaireResponse);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getQuestionnaireById(int questionnaireId) {
		QuestionnaireResponse response = questionnaireService.getQuestionnaireById(questionnaireId);
		SuccessResponse<QuestionnaireResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getQuestionnaires(QuestionnaireFilter filter) {
		QuestionnaireListResponse response = questionnaireService.getQuestionnaires(filter);
		SuccessResponse<QuestionnaireListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response deleteQuestionnaire(int questionnaireId) {
		int modifiedBy = authentication.getAuthUserDetails().getUserId();

		// Step 1: process
		questionnaireService.deleteQuestionnaire(questionnaireId, modifiedBy);
		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response addNewInstruction(int questionnaireId, QuestionnaireInstruction questionnaireInstruction) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		questionnaireService.addNewInstruction(questionnaireId, userId, questionnaireInstruction);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response updateInstruction(int questionnaireId, QuestionnaireInstruction questionnaireInstruction) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		questionnaireService.updateInstruction(questionnaireId, userId, questionnaireInstruction);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response deleteInstruction(int questionnaireId, int instructionId) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		questionnaireService.deleteInstruction(questionnaireId, userId, instructionId);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response addNewQuestion(int questionnaireId, Question question) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		questionnaireService.addNewQuestion(questionnaireId, userId, question);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response updateQuestion(int questionnaireId, Question question) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		questionnaireService.updateQuestion(questionnaireId, userId, question);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response deleteQuestion(int questionnaireId, int questionId) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		questionnaireService.deleteQuestion(questionnaireId, userId, questionId);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getActiveQuestionnaires() {
		QuestionnaireListResponse response = questionnaireService.getActiveQuestionnaires();
		SuccessResponse<QuestionnaireListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getQuestionnaireResponseList(QuestionnaireResponseFilter filter) {
		int userId = authentication.getAuthUserDetails().getUserId();
		filter.setUserId(userId);
		QuestionnaireResponseListResponse response = questionnaireService.getQuestionnaireResponseList(filter);
		SuccessResponse<QuestionnaireResponseListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getQuestionnaireResponseByStudyList(QuestionnaireResponseByStudyFilter filter) {
		QuestionnaireResponseByStudyListResponse response = questionnaireService
				.getQuestionnaireResponseByStudyList(filter);
		SuccessResponse<QuestionnaireResponseByStudyListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getViewQuestionnaireResponse(int questionnaireResponseId, int studyId) {
		QuestionnaireViewResponse response = questionnaireService.getViewQuestionnaireResponse(questionnaireResponseId,
				studyId);
		SuccessResponse<QuestionnaireViewResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getQuestionnaireResponseByPet(QuestionnaireResponseFilter filter) {
		PetQuestionnaireResponseList response = questionnaireService.getQuestionnaireResponseByPet(filter);
		SuccessResponse<PetQuestionnaireResponseList> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response implementSkipOnQuestionnaire(QuestionnaireSkipRequest questionnaireSkipRequest) {
		int modifiedBy = authentication.getAuthUserDetails().getUserId();

		// Step 1: process
		questionnaireService.implementSkipOnQuestionnaire(questionnaireSkipRequest, modifiedBy);
		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}
}
