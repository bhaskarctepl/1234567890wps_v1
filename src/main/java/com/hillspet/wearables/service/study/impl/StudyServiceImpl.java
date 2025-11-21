package com.hillspet.wearables.service.study.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.study.StudyDao;
import com.hillspet.wearables.dto.FeedingSchedule;
import com.hillspet.wearables.dto.FeedingScheduleConfig;
import com.hillspet.wearables.dto.FoodIntakeDetails;
import com.hillspet.wearables.dto.FoodIntakeFormatted;
import com.hillspet.wearables.dto.ImageScoringResponse;
import com.hillspet.wearables.dto.PetBreed;
import com.hillspet.wearables.dto.PetListDTO;
import com.hillspet.wearables.dto.PhaseWisePetListDTO;
import com.hillspet.wearables.dto.PreludeDataByStudyDTO;
import com.hillspet.wearables.dto.PreludeListDTO;
import com.hillspet.wearables.dto.PushNotification;
import com.hillspet.wearables.dto.Questionnaire;
import com.hillspet.wearables.dto.QuestionnaireListDTO;
import com.hillspet.wearables.dto.RecommendationDetails;
import com.hillspet.wearables.dto.StudyBasicDetails;
import com.hillspet.wearables.dto.StudyDTO;
import com.hillspet.wearables.dto.StudyDiary;
import com.hillspet.wearables.dto.StudyDietListDTO;
import com.hillspet.wearables.dto.StudyImageScale;
import com.hillspet.wearables.dto.StudyListDTO;
import com.hillspet.wearables.dto.StudyNotification;
import com.hillspet.wearables.dto.StudyPhaseQuestionnaireScheduleDTO;
import com.hillspet.wearables.dto.ThresholdByStudyReport;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.dto.filter.FeedingScheduleFilter;
import com.hillspet.wearables.dto.filter.FeedingScheduleResponseFilter;
import com.hillspet.wearables.dto.filter.ImageScaleFilter;
import com.hillspet.wearables.dto.filter.IntakeFilter;
import com.hillspet.wearables.dto.filter.PhaseWisePetListFilter;
import com.hillspet.wearables.dto.filter.QuestionnaireConfigFilter;
import com.hillspet.wearables.dto.filter.QuestionnaireResponseFilter;
import com.hillspet.wearables.dto.filter.StudyDiaryFilter;
import com.hillspet.wearables.dto.filter.StudyDietFilter;
import com.hillspet.wearables.dto.filter.StudyFilter;
import com.hillspet.wearables.jaxrs.resource.impl.PushNotificationListResponse;
import com.hillspet.wearables.request.AddCrossOverStudyRequest;
import com.hillspet.wearables.request.AddPhaseWisePetRequest;
import com.hillspet.wearables.request.AddStudyRequest;
import com.hillspet.wearables.request.FlaggedRecommendationRequest;
import com.hillspet.wearables.request.FlaggedRecommendationStatusRequest;
import com.hillspet.wearables.request.ImageScoringAssociated;
import com.hillspet.wearables.request.ImageScoringConfigRequest;
import com.hillspet.wearables.request.PlanSubscribed;
import com.hillspet.wearables.request.PushNotificationAssociated;
import com.hillspet.wearables.request.PushNotificationConfigRequest;
import com.hillspet.wearables.request.PushNotificationsAssociated;
import com.hillspet.wearables.request.QuestionnaireAssociated;
import com.hillspet.wearables.request.QuestionnaireConfigRequest;
import com.hillspet.wearables.request.StudyActivityFactorRequest;
import com.hillspet.wearables.request.StudyDietRequest;
import com.hillspet.wearables.request.StudyMobileAppConfigRequest;
import com.hillspet.wearables.request.StudyNotesRequest;
import com.hillspet.wearables.request.StudyNotificationConfigRequest;
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
import com.hillspet.wearables.response.StudyNotificationConfigResponse;
import com.hillspet.wearables.response.StudyNotificationResponse;
import com.hillspet.wearables.response.StudyPhaseQuestionnaireScheduleList;
import com.hillspet.wearables.response.StudyPlanListResponse;
import com.hillspet.wearables.response.StudyPreludeConfigResponse;
import com.hillspet.wearables.response.StudyResponse;
import com.hillspet.wearables.service.study.StudyService;

@Service
public class StudyServiceImpl implements StudyService {

	private static final Logger LOGGER = LogManager.getLogger(StudyServiceImpl.class);

	@Autowired
	private StudyDao studyDao;

	/* ---------------- STUDY SERVICES ------------------------- */

	@Override
	public int addStudy(AddStudyRequest studyRequest, Integer userId) throws ServiceExecutionException {
		LOGGER.debug("addStudy called");
		return studyDao.addStudy(studyRequest, userId);
	}

	@Override
	public void updateStudy(AddStudyRequest studyRequest, Integer userId) throws ServiceExecutionException {
		LOGGER.debug("updateStudy called");
		studyDao.updateStudy(studyRequest, userId);
		LOGGER.debug("updateStudy completed successfully");
	}

	@Override
	public StudyResponse getStudyBasicDetails(int studyId) throws ServiceExecutionException {
		LOGGER.debug("getStudyBasicDetails called");
		StudyBasicDetails studyBasicDtls = studyDao.getStudyBasicDetails(studyId);
		StudyResponse response = new StudyResponse();
		response.setStudyBasicDetails(studyBasicDtls);
		LOGGER.debug("getStudyBasicDetails completed successfully");
		return response;
	}

	@Override
	public StudyDietListResponse addStudyDiet(StudyDietRequest studyDietRequest, int studyId, Integer userId)
			throws ServiceExecutionException {
		LOGGER.debug("addStudyDiet called");
		studyDao.addStudyDiet(studyDietRequest, studyId, userId);
		LOGGER.debug("addStudyDiet completed successfully");

		StudyDietFilter filter = new StudyDietFilter();
		filter.setUserId(userId);
		filter.setStudyId(studyId);
		filter.setStartIndex(0);
		filter.setLimit(1000);
		return getStudyDiets(filter);
	}

	@Override
	public StudyDietListResponse getStudyDiets(StudyDietFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getStudyDiets called");
		Map<String, Integer> mapper = studyDao.getStudyDietListCount(filter);
		int total = mapper.get("searchedElementsCount");
		int totalCount = mapper.get("totalCount");
		List<StudyDietListDTO> studyDietList = total > 0 ? studyDao.getStudyDiets(filter) : new ArrayList<>();
		StudyDietListResponse response = new StudyDietListResponse();
		response.setStudyDiets(studyDietList);
		response.setNoOfElements(studyDietList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);
		LOGGER.debug("getStudyDiets study count is {}", studyDietList);
		LOGGER.debug("getStudyDiets completed successfully");
		return response;
	}

	@Override
	public void validateStudyDiet(int studyId, int studyDietId, int modifiedBy) throws ServiceExecutionException {
		LOGGER.debug("validateStudyDiet called");
		studyDao.validateStudyDiet(studyId, studyDietId, modifiedBy);
		LOGGER.debug("validateStudyDiet completed successfully");
	}

	@Override
	public StudyPlanListResponse addStudyPlan(StudyPlanRequest studyPlanRequest, int studyId, Integer userId)
			throws ServiceExecutionException {
		LOGGER.debug("addStudyPlan called");
		List<PlanSubscribed> studyPlanList = studyDao.addStudyPlan(studyPlanRequest, studyId, userId);
		StudyPlanListResponse response = new StudyPlanListResponse();
		response.setPlansSubscribed(studyPlanList);
		LOGGER.debug("addStudyPlan completed successfully");
		return response;
	}

	@Override
	public StudyPlanListResponse getStudyPlans(int studyId) throws ServiceExecutionException {
		LOGGER.debug("getStudyPlans called");
		List<PlanSubscribed> studyPlanList = studyDao.getStudyPlans(studyId);
		StudyPlanListResponse response = new StudyPlanListResponse();
		response.setPlansSubscribed(studyPlanList);
		LOGGER.debug("getStudyPlans completed successfully");
		return response;
	}

	@Override
	public StudyMobileAppConfigResponse addStudyMobileAppConfig(StudyMobileAppConfigRequest studyMobileAppConfigRequest,
			int studyId, Integer userId) throws ServiceExecutionException {
		LOGGER.debug("addStudyMobileAppConfig called");
		StudyMobileAppConfigResponse response = studyDao.addStudyMobileAppConfig(studyMobileAppConfigRequest, studyId,
				userId);
		LOGGER.debug("addStudyMobileAppConfig completed successfully");
		return response;
	}

	@Override
	public StudyMobileAppConfigResponse getStudyMobielAppConfigs(int studyId) throws ServiceExecutionException {
		LOGGER.debug("getStudyMobielAppConfigs called");
		StudyMobileAppConfigResponse response = studyDao.getStudyMobielAppConfigs(studyId);
		LOGGER.debug("getStudyMobielAppConfigs completed successfully");
		return response;
	}

	@Override
	public StudyNotesListResponse addNotes(StudyNotesRequest studyNotesRequest, int studyId, int phaseId, int userId)
			throws ServiceExecutionException {
		LOGGER.debug("addNotes called");
		studyDao.addNotes(studyNotesRequest, studyId, phaseId, userId);
		LOGGER.debug("addNotes completed successfully");
		return getStudyNotes(studyId, phaseId);
	}

	@Override
	public StudyNotesListResponse getStudyNotes(int studyId, int phaseId) throws ServiceExecutionException {
		LOGGER.debug("getStudyNotes called");
		StudyNotesListResponse response = studyDao.getStudyNotes(studyId, phaseId);
		LOGGER.debug("getStudyNotes completed successfully");
		return response;
	}

	@Override
	public StudyPreludeConfigResponse addStudyPreludeConfig(StudyPreludeConfigRequest studyPreludeConfigRequest,
			int studyId, Integer userId) throws ServiceExecutionException {
		LOGGER.debug("addStudyPreludeConfig called");
		StudyPreludeConfigResponse response = studyDao.addStudyPreludeConfig(studyPreludeConfigRequest, studyId,
				userId);
		LOGGER.debug("addStudyPreludeConfig completed successfully");
		return response;
	}

	@Override
	public StudyPreludeConfigResponse getStudyPreludeConfig(int studyId) throws ServiceExecutionException {
		LOGGER.debug("getStudyPreludeConfig called");
		StudyPreludeConfigResponse response = studyDao.getStudyPreludeConfig(studyId);
		LOGGER.debug("getStudyPreludeConfig completed successfully");
		return response;
	}

	@Override
	public StudyActivityFactorResponse addStudyActivityFactorConfig(
			StudyActivityFactorRequest studyActivityFactorRequest, int studyId, Integer userId)
			throws ServiceExecutionException {
		LOGGER.debug("addStudyActivityFactorConfig called");
		StudyActivityFactorResponse response = studyDao.addStudyActivityFactorConfig(studyActivityFactorRequest,
				studyId, userId);
		LOGGER.debug("addStudyActivityFactorConfig completed successfully");
		return response;
	}

	@Override
	public StudyActivityFactorResponse getStudyActivityFactorConfig(int studyId) throws ServiceExecutionException {
		LOGGER.debug("getStudyActivityFactorConfig called");
		StudyActivityFactorResponse response = studyDao.getStudyActivityFactorConfig(studyId);
		LOGGER.debug("getStudyActivityFactorConfig completed successfully");
		return response;
	}

	// ======================================================================================

	@Override
	public void associatePlan(int studyId, int planId, LocalDate subscriptionDate, Integer userId)
			throws ServiceExecutionException {
		LOGGER.debug("associatePlan called");
		studyDao.associatePlan(studyId, planId, subscriptionDate, userId);
		LOGGER.debug("associatePlan completed successfully");

	}

	@Override
	public void disassociatePlan(int studyId, int planId, Integer userId) throws ServiceExecutionException {
		LOGGER.debug("disassociatePlan called");
		studyDao.disassociatePlan(studyId, planId, userId);
		LOGGER.debug("disassociatePlan completed successfully");
	}

	@Override
	public PetsResponse getAssociatedPets(int studyId) throws ServiceExecutionException {
		LOGGER.debug("getAssociatedPets called");
		List<PetListDTO> petsList = studyDao.getAssociatedPets(studyId);

		PetsResponse response = new PetsResponse();
		response.setPets(petsList);

		LOGGER.debug("getAssociatedPets study count is {}", petsList.size());
		LOGGER.debug("getAssociatedPets completed successfully");
		return response;
	}

	@Override
	public StudyListResponse getStudies(StudyFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getStudies called");
		Map<String, Integer> mapper = studyDao.getStudyListCount(filter);
		int total = mapper.get("searchedElementsCount");
		int totalCount = mapper.get("totalCount");
		List<StudyListDTO> studyList = total > 0 ? studyDao.getStudies(filter) : new ArrayList<>();
		StudyListResponse response = new StudyListResponse();
		response.setStudies(studyList);
		response.setNoOfElements(studyList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);
		LOGGER.debug("getStudies study count is {}", studyList);
		LOGGER.debug("getStudies completed successfully");
		return response;
	}

	@Override
	public StudyListResponse getStudyList(int userId, String includeFuture, String includeVirtual)
			throws ServiceExecutionException {
		LOGGER.debug("getStudyList called");
		List<StudyListDTO> studyList = studyDao.getStudyList(userId, includeFuture, includeVirtual);

		StudyListResponse response = new StudyListResponse();
		response.setStudyList(studyList);

		LOGGER.debug("getStudyList study count is {}", studyList);
		LOGGER.debug("getStudyList completed successfully");
		return response;
	}

	@Override
	public StudyListResponse getStudyListByUser(int userId) throws ServiceExecutionException {
		LOGGER.debug("getStudyList called");
		List<StudyListDTO> studyList = studyDao.getStudyListByUser(userId);

		StudyListResponse response = new StudyListResponse();
		response.setStudyList(studyList);

		LOGGER.debug("getStudyList study count is {}", studyList);
		LOGGER.debug("getStudyList completed successfully");
		return response;
	}

	@Override
	public StudyListResponse getStudiesByPetParentAndPet(int petParentId, int petId) throws ServiceExecutionException {
		LOGGER.debug("getStudiesByPetParentAndPet called");
		List<StudyListDTO> studyList = studyDao.getStudiesByPetParentAndPet(petParentId, petId);

		StudyListResponse response = new StudyListResponse();
		response.setStudyList(studyList);

		LOGGER.debug("getStudiesByPetParentAndPet study count is {}", studyList);
		LOGGER.debug("getStudiesByPetParentAndPet completed successfully");
		return response;
	}

	@Override
	public StudyResponse getStudyById(int studyId) throws ServiceExecutionException {
		LOGGER.debug("getStudyById called");
		StudyDTO studyDto = studyDao.getStudyById(studyId);

		StudyResponse response = new StudyResponse();
		response.setStudy(studyDto);

		LOGGER.debug("getStudyById completed successfully");
		return response;
	}

	@Override
	public void deleteStudy(int studyId, int modifiedBy) throws ServiceExecutionException {
		LOGGER.debug("deleteStudy called");
		studyDao.deleteStudy(studyId, modifiedBy);
		LOGGER.debug("deleteStudy completed successfully");
	}

	@Override
	public void associateQuestionnaire(int studyId, QuestionnaireAssociated questionnaireAssociated, Integer userId)
			throws ServiceExecutionException {
		LOGGER.debug("associateQuestionnaire called");
		studyDao.associateQuestionnaire(studyId, questionnaireAssociated, userId);
		LOGGER.debug("associateQuestionnaire completed successfully");

	}

	@Override
	public void updateStudyQuestionnaire(int studyId, QuestionnaireAssociated questionnaireAssociated, Integer userId)
			throws ServiceExecutionException {
		LOGGER.debug("updateStudyQuestionnaire called");
		studyDao.updateStudyQuestionnaire(studyId, questionnaireAssociated, userId);
		LOGGER.debug("updateStudyQuestionnaire completed successfully");

	}

	@Override
	public void disassociateQuestionnaire(int studyId, int questionnaireId, Integer userId)
			throws ServiceExecutionException {
		LOGGER.debug("disassociateQuestionnaire called");
		studyDao.disassociateQuestionnaire(studyId, questionnaireId, userId);
		LOGGER.debug("disassociateQuestionnaire completed successfully");
	}

	@Override
	public QuestionnaireListResponse getAssociatedQuestionnaires(int studyId) throws ServiceExecutionException {
		LOGGER.debug("getAssociatedQuestionnaires called");
		List<QuestionnaireListDTO> questionnaires = studyDao.getAssociatedQuestionnaires(studyId);

		QuestionnaireListResponse response = new QuestionnaireListResponse();
		response.setQuestionnaires(questionnaires);

		LOGGER.debug("getAssociatedQuestionnaires study count is {}", questionnaires.size());
		LOGGER.debug("getAssociatedQuestionnaires completed successfully");
		return response;
	}

	/* ---------------- STUDY NOTIFICATIONS SERVICES ------------------------- */

	@Override
	public StudyNotificationResponse getStudyNotifications(BaseFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getStudyNotifications called");
		Map<String, Integer> mapper = studyDao.getStudyNotificationCount(filter);
		int total = mapper.get("count");
		int totalCount = mapper.get("totalCount");
		List<StudyNotification> studyNotifications = total > 0 ? studyDao.getStudyNotifications(filter)
				: new ArrayList<>();
		StudyNotificationResponse response = new StudyNotificationResponse();
		response.setStudyNotifications(studyNotifications);
		response.setNoOfElements(studyNotifications.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);

		LOGGER.debug("getStudyNotifications studyNotifications is {}", studyNotifications);
		LOGGER.debug("getStudyNotifications completed successfully");
		return response;
	}

	@Override
	public void updateStudyNotificationStatus(StudyNotificationRequest studyNotificationRequest, int modifiedBy)
			throws ServiceExecutionException {
		LOGGER.debug("updateStudyNotificationStatus called");
		studyDao.updateStudyNotificationStatus(studyNotificationRequest, modifiedBy);
		LOGGER.debug("updateStudyNotificationStatus completed successfully");
	}

	@Override
	public StudyListResponse getAllStudyList() throws ServiceExecutionException {
		LOGGER.debug("getStudyList called");
		List<StudyListDTO> studyList = studyDao.getAllStudyList();
		StudyListResponse response = new StudyListResponse();
		response.setStudyList(studyList);

		LOGGER.debug("getStudyList study count is {}", studyList);
		LOGGER.debug("getStudyList completed successfully");
		return response;
	}

	@Override
	public PreludeListResponse getPreludeDataList(int studyId) throws ServiceExecutionException {
		LOGGER.debug("getPreludeDataList called");
		List<PreludeListDTO> preludeList = studyDao.getPreludeDataList(studyId);
		PreludeListResponse response = new PreludeListResponse();
		response.setPreludeList(preludeList);
		;

		LOGGER.debug("getPreludeDataList study count is {}", preludeList);
		LOGGER.debug("getPreludeDataList completed successfully");
		return response;
	}

	@Override
	public PreludeDataByStudyResponse getPreludeDataByStudy(String studyName) throws ServiceExecutionException {
		LOGGER.debug("getPreludeDataByStudy called");
		List<PreludeDataByStudyDTO> preludeList = studyDao.getPreludeDataByStudy(studyName);
		PreludeDataByStudyResponse response = new PreludeDataByStudyResponse();
		response.setPreludeDataByStudyList(preludeList);

		LOGGER.debug("getPreludeDataByStudy study count is {}", preludeList);
		LOGGER.debug("getPreludeDataByStudy completed successfully");
		return response;
	}

	// Associating study push notifications

	@Override
	public void associatePushNotifications(int studyId, PushNotificationsAssociated pushNotificationsAssociated,
			Integer userId) {
		LOGGER.debug("associatePushNotifications called");
		studyDao.associatePushNotifications(studyId, pushNotificationsAssociated, userId);
		LOGGER.debug("associatePushNotifications completed successfully");

	}

	@Override
	public void updateStudyPushNotifications(int studyId, PushNotificationsAssociated pushNotificationsAssociated,
			Integer userId) {
		LOGGER.debug("updateStudyPushNotifications called");
		studyDao.updateStudyPushNotifications(studyId, pushNotificationsAssociated, userId);
		LOGGER.debug("updateStudyPushNotifications completed successfully");

	}

	@Override
	public void disassociateStudyPushNotifications(int studyId, int notificationId, Integer userId) {
		LOGGER.debug("disassociateStudyPushNotifications called");
		studyDao.disassociateStudyPushNotifications(studyId, notificationId, userId);
		LOGGER.debug("disassociateStudyPushNotifications completed successfully");
	}

	@Override
	public PushNotificationListResponse getAssociatedPushNotifications(int studyId) {
		LOGGER.debug("getAssociatedPushNotifications called");
		List<PushNotification> pushNotificationList = studyDao.getAssociatedPushNotifications(studyId);

		PushNotificationListResponse response = new PushNotificationListResponse();
		response.setPushNotifications(pushNotificationList);

		LOGGER.debug("getAssociatedPushNotifications study count is {}", pushNotificationList.size());
		LOGGER.debug("getAssociatedPushNotifications completed successfully");
		return response;
	}

	@Override
	public StudyImageScalesListResponse associatedImageScales(int studyId) throws ServiceExecutionException {
		LOGGER.debug("associatedImageScales called");
		List<StudyImageScale> imageScaleList = studyDao.associatedImageScales(studyId);

		StudyImageScalesListResponse response = new StudyImageScalesListResponse();
		response.setStudyImageScaleList(imageScaleList);

		LOGGER.debug("associatedImageScales sclaes count is {}", imageScaleList.size());
		LOGGER.debug("associatedImageScales completed successfully");
		return response;
	}

	@Override
	public PreludeListResponse getAFPreludeDataList(int studyId) throws ServiceExecutionException {
		LOGGER.debug("getAFPreludeDataList called");
		List<PreludeListDTO> preludeList = studyDao.getAFPreludeDataList(studyId);
		PreludeListResponse response = new PreludeListResponse();
		response.setPreludeList(preludeList);
		;

		LOGGER.debug("getAFPreludeDataList study count is {}", preludeList);
		LOGGER.debug("getAFPreludeDataList completed successfully");
		return response;
	}

	// ------- Services for study questionnaire data extract configuration --------
	@Override
	public QuestionnaireResponse getQuestionnaireDtlsForDataExtractConfig(Integer studyQuestionnireId)
			throws ServiceExecutionException {
		LOGGER.debug("getQuestionnaireDtlsForDataExtractConfig called");
		Questionnaire questionnaire = studyDao.getQuestionnaireDtlsForDataExtractConfig(studyQuestionnireId);
		QuestionnaireResponse response = new QuestionnaireResponse();
		response.setQuestionnaire(questionnaire);
		LOGGER.debug("getQuestionnaireDtlsForDataExtractConfig completed successfully");
		return response;
	}

	@Override
	public void updateDataExtractConfigStudyQuestionnaire(StudyQuestionnaireRequest studyQuestionnaireRequest,
			Integer userId) throws ServiceExecutionException {
		LOGGER.debug("updateDataExtractConfigStudyQuestionnaire called");
		studyDao.updateDataExtractConfigStudyQuestionnaire(studyQuestionnaireRequest, userId);
		LOGGER.debug("updateDataExtractConfigStudyQuestionnaire completed successfully");
	}

	@Override
	public PreludeDataByStudyResponse getAFPreludeDataByStudy(String studyName) throws ServiceExecutionException {
		LOGGER.debug("getAFPreludeDataByStudy called");
		List<PreludeDataByStudyDTO> preludeList = studyDao.getAFPreludeDataByStudy(studyName);
		PreludeDataByStudyResponse response = new PreludeDataByStudyResponse();
		response.setPreludeDataByStudyList(preludeList);

		LOGGER.debug("getAFPreludeDataByStudy study count is {}", preludeList);
		LOGGER.debug("getAFPreludeDataByStudy completed successfully");
		return response;
	}

	@Override
	public FeedingScheduleConfig getStudyPhaseDuration(Integer studyId, Integer phaseId)
			throws ServiceExecutionException {
		LOGGER.debug("getStudyPhaseDuration called");
		FeedingScheduleConfig response = studyDao.getStudyPhaseDuration(studyId, phaseId);
		LOGGER.debug("getStudyPhaseDuration completed successfully");
		return response;
	}

	public FRThresholdByCountResponse getThresholdByCount(FlaggedRecommendationRequest flaggedRecommendationRequest)
			throws ServiceExecutionException {
		LOGGER.debug("getThresholdByCount called");
		List<ThresholdByStudyReport> thresholdByStudyReportList = studyDao
				.getThresholdByCountReport(flaggedRecommendationRequest);
		FRThresholdByCountResponse response = new FRThresholdByCountResponse();
		response.setThresholdByStudyReportList(thresholdByStudyReportList);
		response.setNoOfElements(thresholdByStudyReportList.size());
		LOGGER.debug("getThresholdByCount ThresholdByStudyReport is {}", thresholdByStudyReportList);
		LOGGER.debug("getThresholdByCount completed successfully");
		return response;
	}

	@Override
	public FeedingScheduleConfig getStudyPhaseDiets(Integer studyId, Integer phaseId) throws ServiceExecutionException {
		LOGGER.debug("getStudyPhaseDiets called");
		FeedingScheduleConfig response = studyDao.getStudyPhaseDiets(studyId, phaseId);
		LOGGER.debug("getStudyPhaseDiets completed successfully");
		return response;
	}

	@Override
	public FeedingSchedulesResponse getFeedingScheduleList(Integer studyId, Integer phaseId,
			FeedingScheduleFilter filter) {
		LOGGER.debug("getFeedingScheduleList called");
		Map<String, Integer> mapper = studyDao.getFeedingScheduleListCount(studyId, phaseId, filter);
		int total = mapper.get("searchCount");
		int totalCount = mapper.get("totalCount");
		List<FeedingSchedule> feedingScheduleList = total > 0
				? studyDao.getFeedingScheduleList(studyId, phaseId, filter)
				: new ArrayList<>();

		FeedingSchedulesResponse response = new FeedingSchedulesResponse();
		response.setFeedingScheduleList(feedingScheduleList);
		response.setNoOfElements(feedingScheduleList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);
		LOGGER.debug("getFeedingScheduleList study count is {}", feedingScheduleList.size());
		LOGGER.debug("getFeedingScheduleList completed successfully");
		return response;
	}

	@Override
	public void deleteFeedingSchedule(int feedingScheduleConfigId, int modifiedBy) throws ServiceExecutionException {
		LOGGER.debug("deleteFeedingSchedule called");
		studyDao.deleteFeedingSchedule(feedingScheduleConfigId, modifiedBy);
		LOGGER.debug("deleteFeedingSchedule completed successfully");
	}

	@Override
	public void saveStudyPhaseFeedingSchedule(FeedingScheduleConfig feedingSchedule) throws ServiceExecutionException {
		LOGGER.debug("saveStudyPhaseFeedingSchedule called");
		studyDao.saveStudyPhaseFeedingSchedule(feedingSchedule);
		LOGGER.debug("saveStudyPhaseFeedingSchedule completed successfully");
	}

	// ----------------- Services for Adding pet,Search page and Delete Pet to Study

	@Override
	public PhaseWisePetListResponse getPhaseWisePetList(PhaseWisePetListFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("PhaseWisePetListResponse called");
		Map<String, Integer> mapper = studyDao.getPhaseWisePetListCount(filter);
		int total = mapper.get("searchedElementsCount");
		int totalCount = mapper.get("totalCount");
		List<PhaseWisePetListDTO> petsList = total > 0 ? studyDao.getPhaseWisePetList(filter) : new ArrayList<>();
		PhaseWisePetListResponse response = new PhaseWisePetListResponse();
		response.setPets(petsList);
		response.setNoOfElements(petsList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);
		LOGGER.debug("PhaseWisePetListResponse  count is {}", petsList);
		LOGGER.debug("PhaseWisePetListResponse completed successfully");
		return response;
	}

	@Override
	public void addPhaseWisePet(AddPhaseWisePetRequest addPhaseWisePetRequest, Integer userId)
			throws ServiceExecutionException {
		LOGGER.debug("addPhaseWisePet called");
		studyDao.addPhaseWisePet(addPhaseWisePetRequest, userId);
		LOGGER.debug("addPhaseWisePet completed successfully");
	}

	@Override
	public void removePetsFromPhase(AddPhaseWisePetRequest addPhaseWisePetRequest, Integer userId)
			throws ServiceExecutionException {
		LOGGER.debug("removePetsFromPhase called");
		studyDao.removePetsFromPhase(addPhaseWisePetRequest, userId);
		LOGGER.debug("removePetsFromPhase completed successfully");
	}

	@Override
	public void changePetsTreatmentGroup(AddPhaseWisePetRequest addPhaseWisePetRequest, Integer userId)
			throws ServiceExecutionException {
		LOGGER.debug("changePetsTreatmentGroup called");
		studyDao.changePetsTreatmentGroup(addPhaseWisePetRequest, userId);
		LOGGER.debug("changePetsTreatmentGroup completed successfully");
	}

	@Override
	public void movePetsToNextPhase(AddPhaseWisePetRequest addPhaseWisePetRequest, Integer userId)
			throws ServiceExecutionException {
		LOGGER.debug("movePetsToNextPhase called");
		studyDao.movePetsToNextPhase(addPhaseWisePetRequest, userId);
		LOGGER.debug("movePetsToNextPhase completed successfully");
	}

	public StudyDiaryResponse getStudyDiary(int studyId, StudyDiaryFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getStudyDairy called");
		Map<String, Integer> mapper = studyDao.getStudyDiaryListCount(studyId, filter);
		int total = mapper.get("searchCount");
		int totalCount = mapper.get("totalCount");
		List<StudyDiary> studyDairyList = total > 0 ? studyDao.getStudyDiaryList(studyId, filter) : new ArrayList<>();
		StudyDiaryResponse response = new StudyDiaryResponse();
		response.setStudyDairyList(studyDairyList);
		response.setNoOfElements(studyDairyList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);
		LOGGER.debug("getStudyDiary  count is {}", studyDairyList.size());
		LOGGER.debug("getStudyDiary completed successfully");
		return response;
	}

	@Override
	public void extendPhase(AddPhaseWisePetRequest extendPhaseRequest, Integer userId)
			throws ServiceExecutionException {
		LOGGER.debug("extendPhase called");
		studyDao.extendPhase(extendPhaseRequest, userId);
		LOGGER.debug("extendPhase completed successfully");
	}

	@Override
	public FeedingSchedulesResponse getFeedingScheduleResponse(FeedingScheduleResponseFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("FeedingSchedulesResponse called");
		Map<String, Integer> mapper = studyDao.getFeedingScheduleResponseCount(filter);
		int total = mapper.get("searchCount");
		int totalCount = mapper.get("totalCount");
		List<FeedingSchedule> feedingScheduleList = total > 0 ? studyDao.getFeedingScheduleResponse(filter)
				: new ArrayList<>();
		FeedingSchedulesResponse response = new FeedingSchedulesResponse();
		response.setFeedingScheduleList(feedingScheduleList);
		response.setNoOfElements(feedingScheduleList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);
		LOGGER.debug("FeedingSchedulesResponse  count is {}", feedingScheduleList);
		LOGGER.debug("FeedingSchedulesResponse completed successfully");
		return response;
	}

	@Override
	public FeedingSchedulesResponse getFeedingScheduleByPet(int feedingScheduleId) throws ServiceExecutionException {
		FeedingSchedulesResponse response = studyDao.getFeedingScheduleByPet(feedingScheduleId);
		LOGGER.debug("getFeedingScheduleByPet completed successfully");
		return response;
	}

	@Override
	public PushNotificationConfigResponse pushNotificationConfig(
			PushNotificationConfigRequest pushNotificationConfigRequest) throws ServiceExecutionException {
		LOGGER.debug("pushNotificationConfig called");
		studyDao.pushNotificationConfig(pushNotificationConfigRequest);
		LOGGER.debug("pushNotificationConfig completed successfully");
		return getPushNotificationConfig(pushNotificationConfigRequest.getStudyId(),
				pushNotificationConfigRequest.getPhaseId());
	}

	@Override
	public PushNotificationConfigResponse getPushNotificationConfig(int studyId, int phaseId)
			throws ServiceExecutionException {
		LOGGER.debug("getPushNotificationConfig called");
		List<PushNotificationAssociated> pushNotificationsAssociated = studyDao.getPushNotificationConfig(studyId,
				phaseId);
		PushNotificationConfigResponse response = new PushNotificationConfigResponse();
		response.setPushNotificationsAssociated(pushNotificationsAssociated);
		LOGGER.debug("getPushNotificationConfig completed successfully");
		return response;
	}

	@Override
	public ImageScoresConfigResponse imageScoringConfig(ImageScoringConfigRequest imageScoringConfigRequest)
			throws ServiceExecutionException {
		LOGGER.debug("imageScoringConfig called");
		studyDao.imageScoringConfig(imageScoringConfigRequest);
		LOGGER.debug("imageScoringConfig completed successfully");
		return getImageScoringConfig(imageScoringConfigRequest.getStudyId(), imageScoringConfigRequest.getPhaseId());
	}

	@Override
	public ImageScoresConfigResponse getImageScoringConfig(int studyId, int phaseId) throws ServiceExecutionException {
		LOGGER.debug("getImageScoringConfig called");
		List<ImageScoringAssociated> imageScoringAssociatedList = studyDao.getImageScoringConfig(studyId, phaseId);
		ImageScoresConfigResponse response = new ImageScoresConfigResponse();
		response.setImageScoringConfigs(imageScoringAssociatedList);
		LOGGER.debug("getImageScoringConfig completed successfully");
		return response;
	}

	@Override
	public QuestionnaireConfigResponse questionnaireConfig(QuestionnaireConfigRequest questionnaireConfigRequest)
			throws ServiceExecutionException {
		LOGGER.debug("questionnaireConfig called");
		studyDao.questionnaireConfig(questionnaireConfigRequest);
		LOGGER.debug("questionnaireConfig completed successfully");
		return getQuestionnaireConfig(questionnaireConfigRequest.getStudyId(), questionnaireConfigRequest.getPhaseId());
	}

	@Override
	public void deleteQuestionnaireConfig(int studyId, int phaseId, int questionnaireConfigId, Integer userId)
			throws ServiceExecutionException {
		LOGGER.debug("deleteQuestionnaireConfig called");
		studyDao.deleteQuestionnaireConfig(studyId, phaseId, questionnaireConfigId, userId);
		LOGGER.debug("deleteQuestionnaireConfig completed successfully");

	}

	@Override
	public QuestionnaireConfigResponse getQuestionnaireConfig(int studyId, int phaseId)
			throws ServiceExecutionException {
		LOGGER.debug("getQuestionnaireConfig called");
		List<QuestionnaireAssociated> questionnaireAssociateds = studyDao.getQuestionnaireConfig(studyId, phaseId);
		QuestionnaireConfigResponse response = new QuestionnaireConfigResponse();
		response.setQuestionnaireConfigs(questionnaireAssociateds);
		LOGGER.debug("getQuestionnaireConfig completed successfully");
		return response;
	}

	@Override
	public QuestionnaireConfigResponse getQuestionnaireConfigList(QuestionnaireConfigFilter filter, Boolean isQuesIdReq)
			throws ServiceExecutionException {
		LOGGER.debug("getQuestionnaireConfigList called");
		Map<String, Integer> mapper = studyDao.getQuestionnaireConfigListCount(filter, isQuesIdReq);
		int searchCount = mapper.get("count");
		int totalCount = mapper.get("totalCount");
		List<QuestionnaireAssociated> questionnaireAssociatedScaleList = searchCount > 0
				? studyDao.getQuestionnaireConfigList(filter)
				: new ArrayList<>();
		QuestionnaireConfigResponse response = new QuestionnaireConfigResponse();
		response.setQuestionnaireConfigsList(questionnaireAssociatedScaleList);
		response.setNoOfElements(questionnaireAssociatedScaleList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(searchCount);
		LOGGER.debug("getQuestionnaireConfigList  count is {}", questionnaireAssociatedScaleList);
		LOGGER.debug("getQuestionnaireConfigList completed successfully");
		return response;
	}

	@Override
	public ImageScoringScaleResponse getImageScoringResponse(ImageScaleFilter filter) throws ServiceExecutionException {
		LOGGER.debug("ImageScoringScaleResponse called");
		Map<String, Integer> mapper = studyDao.getImageScoringResponseCount(filter);
		int searchCount = mapper.get("searchCount");
		int totalCount = mapper.get("totalCount");
		List<ImageScoringResponse> imageScoringScaleList = searchCount > 0 ? studyDao.getImageScoringResponse(filter)
				: new ArrayList<>();
		ImageScoringScaleResponse response = new ImageScoringScaleResponse();
		response.setImageScoringList(imageScoringScaleList);
		response.setNoOfElements(imageScoringScaleList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(searchCount);
		LOGGER.debug("ImageScoringScaleResponse  count is {}", imageScoringScaleList);
		LOGGER.debug("ImageScoringScaleResponse completed successfully");
		return response;
	}

	@Override
	public StudyPhaseQuestionnaireScheduleList getQuestionnaireResponse(QuestionnaireResponseFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getQuestionnaireResponse called");
		Map<String, Integer> mapper = studyDao.getQuestionnaireResponseCount(filter);
		int searchCount = mapper.get("searchCount");
		int totalCount = mapper.get("totalCount");
		List<StudyPhaseQuestionnaireScheduleDTO> questionnaireList = searchCount > 0
				? studyDao.getQuestionnaireResponse(filter)
				: new ArrayList<>();
		StudyPhaseQuestionnaireScheduleList response = new StudyPhaseQuestionnaireScheduleList();
		response.setStudyPhaseQuestionnaireScheduleDTO(questionnaireList);
		response.setNoOfElements(questionnaireList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(searchCount);
		LOGGER.debug("getQuestionnaireResponse searchCount is {}", searchCount);
		LOGGER.debug("getQuestionnaireResponse completed successfully");
		return response;
	}

	@Override
	public FeedingSchedulesResponse getPetFoodIntakeHistory(FeedingScheduleResponseFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getPetFoodIntakeHistory called");
		Map<String, Integer> mapper = studyDao.getPetFoodIntakeHistoryCount(filter);
		int total = mapper.get("searchCount");
		int totalCount = mapper.get("totalCount");
		List<FeedingSchedule> feedingScheduleList = total > 0 ? studyDao.getPetFoodIntakeHistory(filter)
				: new ArrayList<>();
		FeedingSchedulesResponse response = new FeedingSchedulesResponse();
		response.setFeedingScheduleList(feedingScheduleList);
		response.setNoOfElements(feedingScheduleList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);
		LOGGER.debug("getPetFoodIntakeHistory  count is {}", feedingScheduleList);
		LOGGER.debug("getPetFoodIntakeHistory completed successfully");
		return response;
	}

	@Override
	public List<PetListDTO> getPets(int study_id) throws ServiceExecutionException {
		LOGGER.debug("getPets called");
		List<PetListDTO> pets = studyDao.getPets(study_id);
		LOGGER.debug("getPets pet count is {}", pets.size());
		LOGGER.debug("getPets completed successfully");
		return pets;
	}

	public FRLookUpResponse getFRFilterData(FlaggedRecommendationRequest flaggedRecommendationRequest)
			throws ServiceExecutionException {
		LOGGER.debug("getFRFilterData called");
		LOGGER.debug("getFRFilterData completed successfully");
		return studyDao.getFRFilterData(flaggedRecommendationRequest);
	}

	@Override
	public FlaggedRecommendationDetailsResponse getRecommendationListForStudy(FlaggedRecommendationRequest request)
			throws ServiceExecutionException {
		LOGGER.debug("getRecommendationListForStudy start");
		HashMap<String, Integer> countMap = studyDao.getRecommendationListForStudyCount(request);
		int total = countMap.get("searchCount");
		int totalCount = countMap.get("totalCount");
		List<RecommendationDetails> dietList = total > 0 ? studyDao.getRecommendationListForStudy(request)
				: new ArrayList<>();
		FlaggedRecommendationDetailsResponse response = new FlaggedRecommendationDetailsResponse();
		response.setRecommendationDetails(dietList);
		response.setNoOfElements(dietList.size());
		response.setSearchElments(total);
		response.setTotalRecords(totalCount);
		LOGGER.debug("getRecommendationListForStudy is {}", dietList);
		LOGGER.debug("getRecommendationListForStudy completed successfully");
		return response;
	}

	/*
	 * To get food intake
	 */
	@Override
	public FoodIntakeResponse getFoodIntake(IntakeFilter intakeFilter) throws ServiceExecutionException {
		LOGGER.debug("getFoodIntake start");
		HashMap<String, Integer> countMap = studyDao.getFoodIntakeListCount(intakeFilter);
		List<FoodIntakeDetails> intakeDetailsList = countMap.get("count") > 0 ? studyDao.getFoodIntake(intakeFilter)
				: new ArrayList<>();
		intakeDetailsList.forEach(System.out::println);
		/*
		 * List<FoodIntakeFormatted> foodIntakeFormattedList=
		 * this.processForTableFormat2(intakeDetailsList); FoodIntakeResponse response =
		 * new FoodIntakeResponse();
		 * response.setFoodIntakeDetails(foodIntakeFormattedList);
		 * response.setNoOfElements(foodIntakeFormattedList.size());
		 */
		FoodIntakeResponse response = new FoodIntakeResponse();
		response.setIntakeDetailsList(intakeDetailsList);
		response.setNoOfElements(intakeDetailsList.size());
		response.setTotalRecords(countMap.get("totalCount"));
		response.setSearchElments(countMap.get("count"));
		LOGGER.debug("getFoodIntake is {}", intakeDetailsList);
		LOGGER.debug("getFoodIntake completed successfully");
		return response;
	}

	/*
	 * To process the records as per desired response
	 */
	@SuppressWarnings("unused")
	private List<FoodIntakeFormatted> processForTableFormat(List<FoodIntakeDetails> intakeDetailsList) {
		HashMap<String, FoodIntakeFormatted> map = new HashMap<>(intakeDetailsList.size());
		intakeDetailsList.forEach(x -> {
			if (!map.containsKey(x.getIntakeDate())) {
				FoodIntakeFormatted foodIntakeFormatted = new FoodIntakeFormatted(x.getIntakeDate());
				// if(x.getDietName() != null) {
				foodIntakeFormatted.getDietName().add(x.getDietName());
				// }
				// if(x.getDietNumber() != null) {
				foodIntakeFormatted.getDietNumber().add(x.getDietNumber());
				// }
				// if(x.getPetName() != null) {
				foodIntakeFormatted.getPetName().add(x.getPetName());
				// }
				// if(x.getStudyOfferedAmount() != null){
				foodIntakeFormatted.getStudyOfferedAmount().add(x.getStudyOfferedAmount());
				// }
				// if(x.getStudyConsumedAmount() != null) {
				foodIntakeFormatted.getStudyConsumedAmount().add(x.getStudyConsumedAmount());
				// }
				// if(x.getStudyRecommendedAmount() != null) {
				foodIntakeFormatted.getStudyRecommendedAmount().add(x.getStudyRecommendedAmount());
				// }
				// if(x.getOtherFoodCategory() != null) {
				foodIntakeFormatted.getOtherFoodCategory().add(x.getOtherFoodCategory());
				// }
				// if(x.getOtherPleaseSpecify() != null) {
				foodIntakeFormatted.getOtherPleaseSpecify().add(x.getOtherPleaseSpecify());
				// }
				// if(x.getOtherConsumedAmount() != null) {
				foodIntakeFormatted.getOtherConsumedAmount().add(x.getOtherConsumedAmount());
				// }
				// if(x.getOtherCaloricDensity() != null) {
				foodIntakeFormatted.getOtherCaloricDensity().add(x.getOtherCaloricDensity());
				// }
				// if(x.getFeedback() != null) {
				foodIntakeFormatted.getFeedback().add(x.getFeedback());
				// }
				// if(x.getDescription() != null) {
				foodIntakeFormatted.getFeedback().add(x.getDescription());
				// }
				map.put(x.getIntakeDate(), new FoodIntakeFormatted(x.getIntakeDate()));
			} else {
				FoodIntakeFormatted foodIntakeFormatted = map.get(x.getIntakeDate());
				if (!foodIntakeFormatted.getDietName().contains(x.getDietName()) // && x.getDietName()!=null
				) {
					foodIntakeFormatted.getDietName().add(x.getDietName());
				}
				if (!foodIntakeFormatted.getDietNumber().contains(x.getDietNumber()) // && x.getDietNumber()!= null
				) {
					foodIntakeFormatted.getDietNumber().add(x.getDietNumber());
				}
				if (!foodIntakeFormatted.getPetName().contains(x.getPetName()) // && x.getPetName()!= null
				) {
					foodIntakeFormatted.getPetName().add(x.getPetName());
				}
				if (!foodIntakeFormatted.getStudyOfferedAmount().contains(x.getStudyOfferedAmount()) // &&
																										// x.getStudyOfferedAmount()!=
																										// null
				) {
					foodIntakeFormatted.getStudyOfferedAmount().add(x.getStudyOfferedAmount());
				}
				if (!foodIntakeFormatted.getStudyConsumedAmount().contains(x.getStudyConsumedAmount()) // &&
																										// x.getStudyConsumedAmount()
																										// != null
				) {
					foodIntakeFormatted.getStudyConsumedAmount().add(x.getStudyConsumedAmount());
				}
				if (!foodIntakeFormatted.getStudyRecommendedAmount().contains(x.getStudyRecommendedAmount()) // &&
																												// x.getStudyRecommendedAmount()!=
																												// null
				) {
					foodIntakeFormatted.getStudyRecommendedAmount().add(x.getStudyRecommendedAmount());
				}
				if (!foodIntakeFormatted.getOtherFoodCategory().contains(x.getOtherFoodCategory()) // &&
																									// x.getOtherFoodCategory()
																									// != null
				) {
					foodIntakeFormatted.getOtherFoodCategory().add(x.getOtherFoodCategory());
				}
				if (!foodIntakeFormatted.getOtherPleaseSpecify().contains(x.getOtherPleaseSpecify()) // &&
																										// x.getOtherPleaseSpecify()
																										// != null
				) {
					foodIntakeFormatted.getOtherPleaseSpecify().add(x.getOtherPleaseSpecify());
				}
				if (!foodIntakeFormatted.getOtherConsumedAmount().contains(x.getOtherConsumedAmount()) // &&
																										// x.getOtherConsumedAmount()
																										// != null
				) {
					foodIntakeFormatted.getOtherConsumedAmount().add(x.getOtherConsumedAmount());
				}
				if (!foodIntakeFormatted.getOtherCaloricDensity().contains(x.getOtherCaloricDensity()) // &&
																										// x.getOtherCaloricDensity()
																										// != null
				) {
					foodIntakeFormatted.getOtherCaloricDensity().add(x.getOtherCaloricDensity());
				}
				if (!foodIntakeFormatted.getFeedback().contains(x.getFeedback()) // && x.getFeedback()!= null
				) {
					foodIntakeFormatted.getFeedback().add(x.getFeedback());
				}
				if (!foodIntakeFormatted.getFeedback().contains(x.getDescription()) // && x.getDescription() != null
				) {
					foodIntakeFormatted.getFeedback().add(x.getDescription());
				}
			}
		});
		return new ArrayList<>(map.values());
	}

	/*
	 * To process the records as per desired response
	 */
	@SuppressWarnings("unused")
	private List<FoodIntakeFormatted> processForTableFormat2(List<FoodIntakeDetails> intakeDetailsList) {
		// STUDY PAGE: DATE PET ---- DIET NAME,DIET NUMBER, OFFERED AMOUNT,CONSUMED
		// AMOUNT, RECOMMENDED AMOUNT
		// PET PAGE: DATE ---- DIET NAME,DIET NUMBER, OFFERED AMOUNT,CONSUMED AMOUNT,
		// RECOMMENDED AMOUNT
		HashMap<String, FoodIntakeFormatted> map = new HashMap<>(intakeDetailsList.size());
		intakeDetailsList.forEach(x -> {
			if (!map.containsKey(
					x.getIntakeDate() + "@@" + x.getIntakeId() + "@@" + x.getPetName() + "@@" + x.getDietNumber())) {
				FoodIntakeFormatted foodIntakeFormatted = new FoodIntakeFormatted(x.getIntakeDate());
				foodIntakeFormatted.getDietName().add(x.getDietName());
				foodIntakeFormatted.getDietNumber().add(x.getDietNumber());
				foodIntakeFormatted.getPetName().add(x.getPetName());
				foodIntakeFormatted.getStudyOfferedAmount().add(x.getStudyOfferedAmount());
				foodIntakeFormatted.getStudyConsumedAmount().add(x.getStudyConsumedAmount());
				foodIntakeFormatted.getStudyRecommendedAmount().add(x.getStudyRecommendedAmount());
				foodIntakeFormatted.getOtherFoodCategory().add(x.getOtherFoodCategory());
				foodIntakeFormatted.getOtherPleaseSpecify().add(x.getOtherPleaseSpecify());
				foodIntakeFormatted.getOtherConsumedAmount().add(x.getOtherConsumedAmount());
				foodIntakeFormatted.getOtherCaloricDensity().add(x.getOtherCaloricDensity());
				foodIntakeFormatted.getFeedback().add(x.getFeedback());
				foodIntakeFormatted.getFeedback().add(x.getDescription());
				map.put(x.getIntakeDate() + "@@" + x.getIntakeId() + "@@" + x.getPetName() + "@@" + x.getDietNumber(),
						new FoodIntakeFormatted(x.getIntakeDate()));
			} else {
				FoodIntakeFormatted foodIntakeFormatted = map.get(
						x.getIntakeDate() + "@@" + x.getIntakeId() + "@@" + x.getPetName() + "@@" + x.getDietNumber());
				if (!foodIntakeFormatted.getDietName().contains(x.getDietName()) && x.getDietName() != null) {
					foodIntakeFormatted.getDietName().add(x.getDietName());
				}
				if (!foodIntakeFormatted.getDietNumber().contains(x.getDietNumber()) && x.getDietNumber() != null) {
					foodIntakeFormatted.getDietNumber().add(x.getDietNumber());
				}
				if (!foodIntakeFormatted.getPetName().contains(x.getPetName()) && x.getPetName() != null) {
					foodIntakeFormatted.getPetName().add(x.getPetName());
				}
				if (!foodIntakeFormatted.getStudyOfferedAmount().contains(x.getStudyOfferedAmount())
						&& x.getStudyOfferedAmount() != null) {
					foodIntakeFormatted.getStudyOfferedAmount().add(x.getStudyOfferedAmount());
				}
				if (!foodIntakeFormatted.getStudyConsumedAmount().contains(x.getStudyConsumedAmount())
						&& x.getStudyConsumedAmount() != null) {
					foodIntakeFormatted.getStudyConsumedAmount().add(x.getStudyConsumedAmount());
				}
				if (!foodIntakeFormatted.getStudyRecommendedAmount().contains(x.getStudyRecommendedAmount())
						&& x.getStudyRecommendedAmount() != null) {
					foodIntakeFormatted.getStudyRecommendedAmount().add(x.getStudyRecommendedAmount());
				}
				if (!foodIntakeFormatted.getOtherFoodCategory().contains(x.getOtherFoodCategory()) // &&
																									// x.getOtherFoodCategory()
																									// != null
				) {
					foodIntakeFormatted.getOtherFoodCategory().add(x.getOtherFoodCategory());
				}
				if (!foodIntakeFormatted.getOtherPleaseSpecify().contains(x.getOtherPleaseSpecify()) // &&
																										// x.getOtherPleaseSpecify()
																										// != null
				) {
					foodIntakeFormatted.getOtherPleaseSpecify().add(x.getOtherPleaseSpecify());
				}
				if (!foodIntakeFormatted.getOtherConsumedAmount().contains(x.getOtherConsumedAmount()) // &&
																										// x.getOtherConsumedAmount()
																										// != null
				) {
					foodIntakeFormatted.getOtherConsumedAmount().add(x.getOtherConsumedAmount());
				}
				if (!foodIntakeFormatted.getOtherCaloricDensity().contains(x.getOtherCaloricDensity()) // &&
																										// x.getOtherCaloricDensity()
																										// != null
				) {
					foodIntakeFormatted.getOtherCaloricDensity().add(x.getOtherCaloricDensity());
				}
				if (!foodIntakeFormatted.getFeedback().contains(x.getFeedback()) // && x.getFeedback()!= null
				) {
					foodIntakeFormatted.getFeedback().add(x.getFeedback());
				}
				if (!foodIntakeFormatted.getFeedback().contains(x.getDescription()) // && x.getDescription() != null
				) {
					foodIntakeFormatted.getFeedback().add(x.getDescription());
				}
			}
		});
		return new ArrayList<>(map.values());
	}

	@Override
	public void updateStatus(FlaggedRecommendationStatusRequest request) throws ServiceExecutionException {
		LOGGER.debug("updateStatus called");
		studyDao.updateStatus(request);
		LOGGER.debug("updateStatus completed successfully");
	}

	@Override
	public int createCrossOverStudy(AddCrossOverStudyRequest addCrossOverStudyRequest)
			throws ServiceExecutionException {
		LOGGER.debug("createCrossOverStudy called");
		int studyId = studyDao.createCrossOverStudy(addCrossOverStudyRequest);
		LOGGER.debug("createCrossOverStudy completed successfully");
		return studyId;
	}

	@Override
	public List<PetBreed> getPetBreeds(int studyId, int phaseId) throws ServiceExecutionException {
		LOGGER.debug("getPetBreeds called");
		List<PetBreed> breeds = studyDao.getPetBreeds(studyId, phaseId);
		LOGGER.debug("getPetBreeds list", breeds);
		return breeds;
	}

	@Override
	public void addStudyNotificationConfig(StudyNotificationConfigRequest studyNotificationConfigRequest, int studyId,
			Integer userId) throws ServiceExecutionException {
		LOGGER.debug("addStudyNotificationConfig called");
		studyDao.addStudyNotificationConfig(studyNotificationConfigRequest, studyId, userId);
		LOGGER.debug("addStudyNotificationConfig completed successfully");
	}

	@Override
	public StudyNotificationConfigResponse getStudyNotificationConfigs(int studyId) throws ServiceExecutionException {
		LOGGER.debug("getStudyNotificationConfigs called");
		StudyNotificationConfigResponse response = studyDao.getStudyNotificationConfigs(studyId);
		LOGGER.debug("getStudyNotificationConfigs completed successfully");
		return response;
	}

}
