package com.hillspet.wearables.jaxrs.resource.impl;

import java.time.LocalDate;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.response.SuccessResponse;
import com.hillspet.wearables.dto.CustomUserDetails;
import com.hillspet.wearables.dto.FeedingScheduleConfig;
import com.hillspet.wearables.dto.PetBreed;
import com.hillspet.wearables.dto.PetListDTO;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.dto.filter.FeedingScheduleFilter;
import com.hillspet.wearables.dto.filter.FeedingScheduleResponseFilter;
import com.hillspet.wearables.dto.filter.ImageScaleFilter;
import com.hillspet.wearables.dto.filter.IntakeFilter;
import com.hillspet.wearables.dto.filter.PhaseWisePetListFilter;
import com.hillspet.wearables.dto.filter.QuestionnaireResponseFilter;
import com.hillspet.wearables.dto.filter.StudyDiaryFilter;
import com.hillspet.wearables.dto.filter.StudyDietFilter;
import com.hillspet.wearables.dto.filter.StudyFilter;
import com.hillspet.wearables.jaxrs.resource.StudyResource;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.request.AddCrossOverStudyRequest;
import com.hillspet.wearables.request.AddPhaseWisePetRequest;
import com.hillspet.wearables.request.AddStudyRequest;
import com.hillspet.wearables.request.FlaggedRecommendationRequest;
import com.hillspet.wearables.request.FlaggedRecommendationStatusRequest;
import com.hillspet.wearables.request.ImageScoringConfigRequest;
import com.hillspet.wearables.request.PushNotificationConfigRequest;
import com.hillspet.wearables.request.PushNotificationsAssociated;
import com.hillspet.wearables.request.QuestionnaireAssociated;
import com.hillspet.wearables.request.QuestionnaireConfigRequest;
import com.hillspet.wearables.request.StudyActivityFactorRequest;
import com.hillspet.wearables.request.StudyDietRequest;
import com.hillspet.wearables.request.StudyMobileAppConfigRequest;
import com.hillspet.wearables.request.StudyNotesRequest;
import com.hillspet.wearables.request.StudyNotificationRequest;
import com.hillspet.wearables.request.StudyPlanRequest;
import com.hillspet.wearables.request.StudyPreludeConfigRequest;
import com.hillspet.wearables.request.StudyQuestionnaireRequest;
import com.hillspet.wearables.response.FRLookUpResponse;
import com.hillspet.wearables.response.FRThresholdByCountResponse;
import com.hillspet.wearables.response.FeedingSchedulesResponse;
import com.hillspet.wearables.response.FlaggedRecommendationDetailsResponse;
import com.hillspet.wearables.response.FoodIntakeResponse;
import com.hillspet.wearables.response.ImageScoresConfigResponse;
import com.hillspet.wearables.response.ImageScoringScaleResponse;
import com.hillspet.wearables.response.PetBreedResponse;
import com.hillspet.wearables.response.PetsResponse;
import com.hillspet.wearables.response.PhaseWisePetListResponse;
import com.hillspet.wearables.response.PreludeDataByStudyResponse;
import com.hillspet.wearables.response.PreludeListResponse;
import com.hillspet.wearables.response.PushNotificationConfigResponse;
import com.hillspet.wearables.response.QuestionnaireConfigResponse;
import com.hillspet.wearables.response.QuestionnaireListResponse;
import com.hillspet.wearables.response.QuestionnaireResponse;
import com.hillspet.wearables.response.StudyActivityFactorResponse;
import com.hillspet.wearables.response.StudyDiaryResponse;
import com.hillspet.wearables.response.StudyDietListResponse;
import com.hillspet.wearables.response.StudyImageScalesListResponse;
import com.hillspet.wearables.response.StudyListResponse;
import com.hillspet.wearables.response.StudyMobileAppConfigResponse;
import com.hillspet.wearables.response.StudyNotesListResponse;
import com.hillspet.wearables.response.StudyNotificationResponse;
import com.hillspet.wearables.response.StudyPhaseQuestionnaireScheduleList;
import com.hillspet.wearables.response.StudyPlanListResponse;
import com.hillspet.wearables.response.StudyPreludeConfigResponse;
import com.hillspet.wearables.response.StudyResponse;
import com.hillspet.wearables.security.Authentication;
import com.hillspet.wearables.service.study.PreludeService;
import com.hillspet.wearables.service.study.StudyService;

/**
 * This class if for providing study details.
 * 
 * @author sgorle
 * @since w1.0
 * @version w1.0
 * @version Nov 9, 2020
 */
@Service
public class StudyResourceImpl implements StudyResource {

	private static final Logger LOGGER = LogManager.getLogger(StudyResourceImpl.class);

	@Autowired
	private StudyService studyService;

	@Autowired
	private PreludeService preludeService;

	@Autowired
	private Authentication authentication;

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;

	/* ---------------- STUDY SERVICES ------------------------- */

	@Override
	public Response AddStudy(AddStudyRequest studyRequest) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		int studyId = studyService.addStudy(studyRequest, userId);

		// Step 2: build a successful response
		CommonResponse response = new CommonResponse();
		response.setId(studyId);
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response updateStudy(AddStudyRequest studyRequest) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		studyService.updateStudy(studyRequest, userId);

		// Step 2: build a successful response
		CommonResponse response = new CommonResponse();
		response.setId(studyRequest.getStudyId());
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getStudyBasicDetails(int studyId) {
		StudyResponse response = studyService.getStudyBasicDetails(studyId);
		SuccessResponse<StudyResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response addStudyDiet(StudyDietRequest studyDietRequest, int studyId) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		studyService.addStudyDiet(studyDietRequest, studyId, userId);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getStudyDiets(StudyDietFilter filter, int studyId) {
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setUserId(userDetails.getUserId());
		filter.setStudyId(studyId);
		StudyDietListResponse response = studyService.getStudyDiets(filter);
		SuccessResponse<StudyDietListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response validateStudyDiet(int studyId, int studyDietId) {
		int modifiedBy = authentication.getAuthUserDetails().getUserId();
		// process
		studyService.validateStudyDiet(studyId, studyDietId, modifiedBy);
		// build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response addStudyPlan(StudyPlanRequest studyPlanRequest, int studyId) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		studyService.addStudyPlan(studyPlanRequest, studyId, userId);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getStudyPlans(int studyId) {
		StudyPlanListResponse response = studyService.getStudyPlans(studyId);
		SuccessResponse<StudyPlanListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response addStudyMobileAppConfig(StudyMobileAppConfigRequest studyMobileAppConfigRequest, int studyId) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		studyService.addStudyMobileAppConfig(studyMobileAppConfigRequest, studyId, userId);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getStudyMobielAppConfigs(int studyId) {
		StudyMobileAppConfigResponse response = studyService.getStudyMobielAppConfigs(studyId);
		SuccessResponse<StudyMobileAppConfigResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response addNotes(StudyNotesRequest studyNotesRequest, int studyId, int phaseId) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		studyService.addNotes(studyNotesRequest, studyId, phaseId, userId);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getStudyNotes(int studyId, int phaseId) {
		StudyNotesListResponse response = studyService.getStudyNotes(studyId, phaseId);
		SuccessResponse<StudyNotesListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response addStudyPreludeConfig(StudyPreludeConfigRequest studyPreludeConfigRequest, int studyId) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		studyService.addStudyPreludeConfig(studyPreludeConfigRequest, studyId, userId);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getStudyPreludeConfig(int studyId) {
		StudyPreludeConfigResponse response = studyService.getStudyPreludeConfig(studyId);
		SuccessResponse<StudyPreludeConfigResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response addStudyActivityFactorConfig(StudyActivityFactorRequest studyActivityFactorRequest, int studyId) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		studyService.addStudyActivityFactorConfig(studyActivityFactorRequest, studyId, userId);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getStudyActivityFactorConfig(int studyId) {
		StudyActivityFactorResponse response = studyService.getStudyActivityFactorConfig(studyId);
		SuccessResponse<StudyActivityFactorResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	// ====================================================================================================================

	@Override
	public Response associatePlan(int studyId, int planId, String subscriptionDate) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		studyService.associatePlan(studyId, planId, LocalDate.parse(subscriptionDate), userId);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response disassociatePlan(int studyId, int planId) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		studyService.disassociatePlan(studyId, planId, userId);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response associatedPets(int studyId) {
		PetsResponse response = studyService.getAssociatedPets(studyId);
		SuccessResponse<PetsResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getStudies(StudyFilter filter) {
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setIsSuper(userDetails.getIsSuperAdmin());
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());
		StudyListResponse response = studyService.getStudies(filter);
		SuccessResponse<StudyListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getStudyById(int studyId) {
		StudyResponse response = studyService.getStudyById(studyId);
		SuccessResponse<StudyResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getStudyList(String includeFuture, String includeVirtual) {
		int userId = authentication.getAuthUserDetails().getUserId();
		StudyListResponse response = studyService.getStudyList(userId, includeFuture, includeVirtual);
		SuccessResponse<StudyListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getStudiesByPetParentAndPet(int petParentId, int petId) {
		StudyListResponse response = studyService.getStudiesByPetParentAndPet(petParentId, petId);
		SuccessResponse<StudyListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response deleteStudy(int studyId) {
		int modifiedBy = authentication.getAuthUserDetails().getUserId();

		// process
		studyService.deleteStudy(studyId, modifiedBy);
		// build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response associateQuestionnaire(int studyId, int questionnaireId, String startDate, String endDate) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		QuestionnaireAssociated questionnaireAssociated = new QuestionnaireAssociated();
		questionnaireAssociated.setQuestionnaireId(questionnaireId);
		questionnaireAssociated.setStartDate(startDate);
		questionnaireAssociated.setEndDate(endDate);
		studyService.associateQuestionnaire(studyId, questionnaireAssociated, userId);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response updateStudyQuestionnaire(int studyId, int questionnaireId, String startDate, String endDate) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		QuestionnaireAssociated questionnaireAssociated = new QuestionnaireAssociated();
		questionnaireAssociated.setQuestionnaireId(questionnaireId);
		questionnaireAssociated.setStartDate(startDate);
		questionnaireAssociated.setEndDate(endDate);
		studyService.updateStudyQuestionnaire(studyId, questionnaireAssociated, userId);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response disassociateQuestionnaire(int studyId, int questionnaireId) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		studyService.disassociateQuestionnaire(studyId, questionnaireId, userId);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response associatedQuestionnaires(int studyId) {
		QuestionnaireListResponse response = studyService.getAssociatedQuestionnaires(studyId);
		SuccessResponse<QuestionnaireListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	/* ---------------- STUDY NOTIFICATIONS SERVICES ------------------------- */

	@Override
	public Response getStudyNotifications(BaseFilter filter) {
		Integer userId = authentication.getAuthUserDetails().getUserId();
		Integer roleTypeId = authentication.getAuthUserDetails().getRoleTypeId();
		filter.setUserId(userId);
		filter.setRoleTypeId(roleTypeId);
		StudyNotificationResponse response = studyService.getStudyNotifications(filter);
		SuccessResponse<StudyNotificationResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response updateStudyNotificationStatus(StudyNotificationRequest studyNotificationRequest) {
		studyService.updateStudyNotificationStatus(studyNotificationRequest,
				authentication.getAuthUserDetails().getUserId());

		// Step 1: build a successful response
		CommonResponse response = new CommonResponse();
		response.setMessage("Study notification status has been updated successfully");
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getStudyListByUser() {
		Integer userId = authentication.getAuthUserDetails().getUserId();

		StudyListResponse response = studyService.getStudyListByUser(userId);
		SuccessResponse<StudyListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getAllStudyList() {

		StudyListResponse response = studyService.getAllStudyList();
		SuccessResponse<StudyListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPreludeDataList(int studyId) {

		PreludeListResponse response = studyService.getPreludeDataList(studyId);
		SuccessResponse<PreludeListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPreludeDataByStudy(String studyName) {

		PreludeDataByStudyResponse response = studyService.getPreludeDataByStudy(studyName);
		SuccessResponse<PreludeDataByStudyResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	// Study Push Notifications
	@Override
	public Response associatePushNotifications(int studyId, int notificationId, String startDate, String endDate) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		PushNotificationsAssociated pushNotificationsAssociated = new PushNotificationsAssociated();
		pushNotificationsAssociated.setNotificationId(notificationId);
		pushNotificationsAssociated.setStartDate(LocalDate.parse(startDate));
		pushNotificationsAssociated.setEndDate(LocalDate.parse(endDate));
		studyService.associatePushNotifications(studyId, pushNotificationsAssociated, userId);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response updateStudyPushNotifications(int studyId, int notificationId, String startDate, String endDate) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		PushNotificationsAssociated pushNotificationsAssociated = new PushNotificationsAssociated();
		pushNotificationsAssociated.setNotificationId(notificationId);
		pushNotificationsAssociated.setStartDate(LocalDate.parse(startDate));
		pushNotificationsAssociated.setEndDate(LocalDate.parse(endDate));
		studyService.updateStudyPushNotifications(studyId, pushNotificationsAssociated, userId);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response disassociatePushNotifications(int studyId, int notificationId) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		studyService.disassociateStudyPushNotifications(studyId, notificationId, userId);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response associatedPushNotifications(int studyId) {
		PushNotificationListResponse response = studyService.getAssociatedPushNotifications(studyId);
		SuccessResponse<PushNotificationListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response associatedImageScales(int studyId) {
		StudyImageScalesListResponse response = studyService.associatedImageScales(studyId);
		SuccessResponse<StudyImageScalesListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getAFPreludeDataList(int studyId) {

		PreludeListResponse response = studyService.getAFPreludeDataList(studyId);
		SuccessResponse<PreludeListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getAFPreludeDataByStudy(String studyName) {

		PreludeDataByStudyResponse response = studyService.getAFPreludeDataByStudy(studyName);
		SuccessResponse<PreludeDataByStudyResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	// ------- Services for study questionnaire data extract configuration --------
	@Override
	public Response getQuestionnaireDtlsForDataExtractConfig(Integer studyQuestionnireId) {
		QuestionnaireResponse response = studyService.getQuestionnaireDtlsForDataExtractConfig(studyQuestionnireId);
		SuccessResponse<QuestionnaireResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response updateDataExtractConfigStudyQuestionnaire(StudyQuestionnaireRequest studyQuestionnaireRequest) {
		LOGGER.info("in updateDataExtractConfigStudyQuestionnaire");
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		studyService.updateDataExtractConfigStudyQuestionnaire(studyQuestionnaireRequest, userId);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response loadPreludeData() {

		preludeService.loadPreludeData();

		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(new CommonResponse());
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getStudyPhaseDuration(Integer studyId, Integer phaseId) {
		FeedingScheduleConfig response = studyService.getStudyPhaseDuration(studyId, phaseId);
		SuccessResponse<FeedingScheduleConfig> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	public Response getThresholdByCount(FlaggedRecommendationRequest flaggedRecommendationRequest) {
		int userId = authentication.getAuthUserDetails().getUserId();
		FRThresholdByCountResponse response = studyService.getThresholdByCount(flaggedRecommendationRequest);
		SuccessResponse<FRThresholdByCountResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getStudyPhaseDiets(Integer studyId, Integer phaseId) {
		FeedingScheduleConfig response = studyService.getStudyPhaseDiets(studyId, phaseId);
		SuccessResponse<FeedingScheduleConfig> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	public Response getFRFilterData(FlaggedRecommendationRequest flaggedRecommendationRequest) {
		int userId = authentication.getAuthUserDetails().getUserId();
		FRLookUpResponse response = studyService.getFRFilterData(flaggedRecommendationRequest);
		SuccessResponse<FRLookUpResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getFeedingScheduleList(Integer studyId, Integer phaseId, FeedingScheduleFilter filter) {
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setIsSuper(userDetails.getIsSuperAdmin());
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());

		FeedingSchedulesResponse response = studyService.getFeedingScheduleList(studyId, phaseId, filter);
		SuccessResponse<FeedingSchedulesResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);

	}

	@Override
	public Response deleteFeedingSchedule(int feedingScheduleConfigId) {
		int modifiedBy = authentication.getAuthUserDetails().getUserId();

		studyService.deleteFeedingSchedule(feedingScheduleConfigId, modifiedBy);
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response saveStudyPhaseFeedingSchedule(FeedingScheduleConfig feedingSchedule) {
		// Step 1: get login user details
		Integer userId = authentication.getAuthUserDetails().getUserId();
		feedingSchedule.setUserId(userId);

		// Step 2: calling saveStudyPhaseFeedingSchedule method to save the feeding the
		// schedule
		studyService.saveStudyPhaseFeedingSchedule(feedingSchedule);

		// Step 3: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPhaseWisePetList(int studyId, int phaseId, PhaseWisePetListFilter filter) {
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setIsSuper(userDetails.getIsSuperAdmin());
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());
		filter.setStudyId(studyId);
		filter.setPhaseId(phaseId);
		PhaseWisePetListResponse response = studyService.getPhaseWisePetList(filter);
		SuccessResponse<PhaseWisePetListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	public Response getRecommendationListForStudy(FlaggedRecommendationRequest flaggedRecommendationRequest) {
		FlaggedRecommendationDetailsResponse response = studyService
				.getRecommendationListForStudy(flaggedRecommendationRequest);
		SuccessResponse<FlaggedRecommendationDetailsResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	/*
	* Get food intake values for give time period,studyId and petId
	* */
	@Override
	public Response getFoodIntake(IntakeFilter intakeFilter) {
		FoodIntakeResponse response = studyService.getFoodIntake(intakeFilter);
		SuccessResponse<FoodIntakeResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response addPhaseWisePet(int studyId, int phaseId, AddPhaseWisePetRequest addPhaseWisePetRequest) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		addPhaseWisePetRequest.setStudyId(studyId);
		addPhaseWisePetRequest.setPhaseId(phaseId);
		studyService.addPhaseWisePet(addPhaseWisePetRequest, userId);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response removePetsFromPhase(int studyId, int phaseId, AddPhaseWisePetRequest addPhaseWisePetRequest) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		addPhaseWisePetRequest.setStudyId(studyId);
		addPhaseWisePetRequest.setPhaseId(phaseId);
		studyService.removePetsFromPhase(addPhaseWisePetRequest, userId);
		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response changePetsTreatmentGroup(int studyId, int phaseId, AddPhaseWisePetRequest addPhaseWisePetRequest) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		addPhaseWisePetRequest.setStudyId(studyId);
		addPhaseWisePetRequest.setPhaseId(phaseId);
		studyService.changePetsTreatmentGroup(addPhaseWisePetRequest, userId);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response movePetsToNextPhase(int studyId, int phaseId, AddPhaseWisePetRequest addPhaseWisePetRequest) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		addPhaseWisePetRequest.setStudyId(studyId);
		addPhaseWisePetRequest.setPhaseId(phaseId);
		studyService.movePetsToNextPhase(addPhaseWisePetRequest, userId);

		// Step 2: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	public Response getStudyDiary(int studyId, StudyDiaryFilter filter) {
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setIsSuper(userDetails.getIsSuperAdmin());
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());

		StudyDiaryResponse response = studyService.getStudyDiary(studyId, filter);
		SuccessResponse<StudyDiaryResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response extendPhase(int studyId, int phaseId, AddPhaseWisePetRequest extendPhaseRequest) {
		Integer userId = authentication.getAuthUserDetails().getUserId();
		extendPhaseRequest.setStudyId(studyId);
		extendPhaseRequest.setPhaseId(phaseId);
		studyService.extendPhase(extendPhaseRequest, userId);

		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getFeedingScheduleResponse(int studyId, int phaseId, FeedingScheduleResponseFilter filter) {
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setIsSuper(userDetails.getIsSuperAdmin());
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());
		filter.setStudyId(studyId);
		filter.setPhaseId(phaseId);

		FeedingSchedulesResponse response = studyService.getFeedingScheduleResponse(filter);
		SuccessResponse<FeedingSchedulesResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getFeedingScheduleByPet(int feedingScheduleId) {
		FeedingSchedulesResponse response = studyService.getFeedingScheduleByPet(feedingScheduleId);
		SuccessResponse<FeedingSchedulesResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response pushNotificationConfig(int studyId, int phaseId,
			PushNotificationConfigRequest pushNotificationConfigRequest) {
		// Step 2: process
		pushNotificationConfigRequest.setStudyId(studyId);
		pushNotificationConfigRequest.setPhaseId(phaseId);
		pushNotificationConfigRequest.setUserId(authentication.getAuthUserDetails().getUserId());
		studyService.pushNotificationConfig(pushNotificationConfigRequest);

		// Step 5: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		CommonResponse response = new CommonResponse();
		response.setMessage("Study Push Notification Config saved successfully");
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPushNotificationConfig(int studyId, int phaseId) {
		PushNotificationConfigResponse response = studyService.getPushNotificationConfig(studyId, phaseId);
		SuccessResponse<PushNotificationConfigResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response imageScoringConfig(int studyId, int phaseId, ImageScoringConfigRequest imageScoringConfigRequest) {
		// Step 2: process
		imageScoringConfigRequest.setStudyId(studyId);
		imageScoringConfigRequest.setPhaseId(phaseId);
		imageScoringConfigRequest.setUserId(authentication.getAuthUserDetails().getUserId());
		studyService.imageScoringConfig(imageScoringConfigRequest);

		// Step 5: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		CommonResponse response = new CommonResponse();
		response.setMessage("Study Image Scoring Config saved successfully");
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getImageScoringConfig(int studyId, int phaseId) {
		ImageScoresConfigResponse response = studyService.getImageScoringConfig(studyId, phaseId);
		SuccessResponse<ImageScoresConfigResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response questionnaireConfig(int studyId, int phaseId,
			QuestionnaireConfigRequest questionnaireConfigRequest) {
		// Step 2: process
		questionnaireConfigRequest.setStudyId(studyId);
		questionnaireConfigRequest.setPhaseId(phaseId);
		questionnaireConfigRequest.setUserId(authentication.getAuthUserDetails().getUserId());
		studyService.questionnaireConfig(questionnaireConfigRequest);

		// Step 5: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		CommonResponse response = new CommonResponse();
		response.setMessage("Study Questionnaire Config saved successfully");
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getQuestionnaireConfig(int studyId, int phaseId) {
		// Step 2: process
		QuestionnaireConfigResponse response = studyService.getQuestionnaireConfig(studyId, phaseId);

		// Step 5: build a successful response
		SuccessResponse<QuestionnaireConfigResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getImageScoringResponse(int studyId, int phaseId, ImageScaleFilter filter) {
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setIsSuper(userDetails.getIsSuperAdmin());
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());
		filter.setStudyId(studyId);
		filter.setPhaseId(phaseId);

		ImageScoringScaleResponse response = studyService.getImageScoringResponse(filter);
		SuccessResponse<ImageScoringScaleResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getQuestionnaireResponse(int studyId, int phaseId, QuestionnaireResponseFilter filter) {
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setIsSuper(userDetails.getIsSuperAdmin());
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());
		filter.setStudyId(studyId + "");
		filter.setPhaseId(phaseId + "");

		StudyPhaseQuestionnaireScheduleList response = studyService.getQuestionnaireResponse(filter);
		SuccessResponse<StudyPhaseQuestionnaireScheduleList> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPetFoodIntakeHistory(int studyId, int phaseId, int petId, FeedingScheduleResponseFilter filter) {
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		filter.setIsSuper(userDetails.getIsSuperAdmin());
		filter.setUserId(userDetails.getUserId());
		filter.setRoleTypeId(userDetails.getRoleTypeId());
		filter.setStudyId(studyId);
		filter.setPhaseId(phaseId);
		filter.setPetId(petId);
		;
		FeedingSchedulesResponse response = studyService.getPetFoodIntakeHistory(filter);
		SuccessResponse<FeedingSchedulesResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPets(int study_id) {
		List<PetListDTO> pets = studyService.getPets(study_id);
		PetsResponse response = new PetsResponse();
		response.setPets(pets);
		SuccessResponse<PetsResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	public Response updateStatus(FlaggedRecommendationStatusRequest request) {
		int userId = authentication.getAuthUserDetails().getUserId();
		request.setUserId(userId);
		// Step 1: process
		studyService.updateStatus(request);
		// Step 2: build a successful response
		CommonResponse response = new CommonResponse();
		response.setMessage("action status updated successfully");
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response createCrossOverStudy(AddCrossOverStudyRequest addCrossOverStudyRequest) {
		// Step 1: process
		Integer userId = authentication.getAuthUserDetails().getUserId();
		addCrossOverStudyRequest.setUserId(userId);
		int studyId = studyService.createCrossOverStudy(addCrossOverStudyRequest);

		// Step 2: build a successful response
		CommonResponse response = new CommonResponse();
		response.setId(studyId);
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPetBreeds(int studyId, int phaseId) {
		List<PetBreed> breeds = studyService.getPetBreeds(studyId, phaseId);
		PetBreedResponse response = new PetBreedResponse();
		response.setBreeds(breeds);
		SuccessResponse<PetBreedResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}
}
