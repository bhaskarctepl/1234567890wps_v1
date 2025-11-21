package com.hillspet.wearables.service.study;

import java.time.LocalDate;
import java.util.List;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.FeedingScheduleConfig;
import com.hillspet.wearables.dto.PetBreed;
import com.hillspet.wearables.dto.PetListDTO;
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
import com.hillspet.wearables.request.ImageScoringConfigRequest;
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

/**
 * This is the service class for implementing study notification list details.
 * 
 * @author sgorle
 * @since w1.0
 * @version w1.0
 * 
 */
public interface StudyService {

	/* ---------------- STUDY SERVICES ------------------------- */

	int addStudy(AddStudyRequest studyRequest, Integer userId) throws ServiceExecutionException;

	void updateStudy(AddStudyRequest studyRequest, Integer userId) throws ServiceExecutionException;

	StudyResponse getStudyBasicDetails(int studyId) throws ServiceExecutionException;

	StudyDietListResponse addStudyDiet(StudyDietRequest studyDietRequest, int studyId, Integer userId)
			throws ServiceExecutionException;

	StudyDietListResponse getStudyDiets(StudyDietFilter filter) throws ServiceExecutionException;

	void validateStudyDiet(int studyId, int studyDietId, int modifiedBy) throws ServiceExecutionException;

	StudyPlanListResponse addStudyPlan(StudyPlanRequest studyPlanRequest, int studyId, Integer userId)
			throws ServiceExecutionException;

	StudyPlanListResponse getStudyPlans(int studyId) throws ServiceExecutionException;

	StudyMobileAppConfigResponse addStudyMobileAppConfig(StudyMobileAppConfigRequest studyMobileAppConfigRequest,
			int studyId, Integer userId) throws ServiceExecutionException;

	StudyMobileAppConfigResponse getStudyMobielAppConfigs(int studyId) throws ServiceExecutionException;

	StudyNotesListResponse addNotes(StudyNotesRequest studyNotesRequest, int studyId, int phaseId, int userId)
			throws ServiceExecutionException;

	StudyNotesListResponse getStudyNotes(int studyId, int phaseId) throws ServiceExecutionException;

	StudyPreludeConfigResponse addStudyPreludeConfig(StudyPreludeConfigRequest studyPreludeConfigRequest, int studyId,
			Integer userId) throws ServiceExecutionException;

	StudyPreludeConfigResponse getStudyPreludeConfig(int studyId) throws ServiceExecutionException;

	StudyActivityFactorResponse addStudyActivityFactorConfig(StudyActivityFactorRequest studyActivityFactorRequest,
			int studyId, Integer userId) throws ServiceExecutionException;

	StudyActivityFactorResponse getStudyActivityFactorConfig(int studyId) throws ServiceExecutionException;

	// ==================================================================================================

	void associatePlan(int studyId, int planId, LocalDate subscriptionDate, Integer userId)
			throws ServiceExecutionException;

	void disassociatePlan(int studyId, int planId, Integer userId) throws ServiceExecutionException;

	PetsResponse getAssociatedPets(int studyId) throws ServiceExecutionException;

	StudyListResponse getStudies(StudyFilter filter) throws ServiceExecutionException;

	StudyListResponse getStudyList(int userId, String includeFuture, String includeVirtual)
			throws ServiceExecutionException;

	StudyListResponse getStudyListByUser(int usrId) throws ServiceExecutionException;

	StudyListResponse getAllStudyList() throws ServiceExecutionException;

	StudyListResponse getStudiesByPetParentAndPet(int petParentId, int petId) throws ServiceExecutionException;

	StudyResponse getStudyById(int studyId) throws ServiceExecutionException;

	void deleteStudy(int studyId, int modifiedBy) throws ServiceExecutionException;

	void associateQuestionnaire(int studyId, QuestionnaireAssociated questionnaireAssociated, Integer userId)
			throws ServiceExecutionException;

	void updateStudyQuestionnaire(int studyId, QuestionnaireAssociated questionnaireAssociated, Integer userId)
			throws ServiceExecutionException;

	void disassociateQuestionnaire(int studyId, int questionnaireId, Integer userId) throws ServiceExecutionException;

	QuestionnaireListResponse getAssociatedQuestionnaires(int studyId) throws ServiceExecutionException;

	PreludeListResponse getPreludeDataList(int studyId) throws ServiceExecutionException;

	PreludeListResponse getAFPreludeDataList(int studyId) throws ServiceExecutionException;

	PreludeDataByStudyResponse getPreludeDataByStudy(String studyName) throws ServiceExecutionException;

	PreludeDataByStudyResponse getAFPreludeDataByStudy(String studyName) throws ServiceExecutionException;

	/* ---------------- STUDY NOTIFICATIONS SERVICES ------------------------- */

	StudyNotificationResponse getStudyNotifications(BaseFilter filter) throws ServiceExecutionException;

	void updateStudyNotificationStatus(StudyNotificationRequest studyNotificationRequest, int modifiedBy)
			throws ServiceExecutionException;

	/*
	 * ---------------- STUDY PUSH NOTIFICATIONS SERVICES -------------------------
	 */

	void associatePushNotifications(int studyId, PushNotificationsAssociated pushNotificationsAssociated,
			Integer userId) throws ServiceExecutionException;

	void updateStudyPushNotifications(int studyId, PushNotificationsAssociated pushNotificationsAssociated,
			Integer userId) throws ServiceExecutionException;

	void disassociateStudyPushNotifications(int studyId, int notificationId, Integer userId)
			throws ServiceExecutionException;

	PushNotificationListResponse getAssociatedPushNotifications(int studyId) throws ServiceExecutionException;

	StudyImageScalesListResponse associatedImageScales(int studyId) throws ServiceExecutionException;

	// ------- Services for study questionnaire data extract configuration --------
	QuestionnaireResponse getQuestionnaireDtlsForDataExtractConfig(Integer studyQuestionnireId)
			throws ServiceExecutionException;

	void updateDataExtractConfigStudyQuestionnaire(StudyQuestionnaireRequest studyQuestionnaireRequest, Integer userId)
			throws ServiceExecutionException;

	public FeedingScheduleConfig getStudyPhaseDuration(Integer studyId, Integer phaseId)
			throws ServiceExecutionException;

	public FeedingScheduleConfig getStudyPhaseDiets(Integer studyId, Integer phaseId) throws ServiceExecutionException;

	public FeedingSchedulesResponse getFeedingScheduleList(Integer studyId, Integer phaseId,
			FeedingScheduleFilter filter) throws ServiceExecutionException;

	public void deleteFeedingSchedule(int feedingScheduleConfigId, int modifiedBy) throws ServiceExecutionException;

	public void saveStudyPhaseFeedingSchedule(FeedingScheduleConfig feedingSchedule) throws ServiceExecutionException;

	/* ---------------- PHASE SERVICES ------------------------- */

	void addPhaseWisePet(AddPhaseWisePetRequest addPhaseWisePetRequest, Integer userId)
			throws ServiceExecutionException;

	void removePetsFromPhase(AddPhaseWisePetRequest addPhaseWisePetRequest, Integer userId)
			throws ServiceExecutionException;

	PhaseWisePetListResponse getPhaseWisePetList(PhaseWisePetListFilter filter) throws ServiceExecutionException;

	public void changePetsTreatmentGroup(AddPhaseWisePetRequest addPhaseWisePetRequest, Integer userId)
			throws ServiceExecutionException;

	public void movePetsToNextPhase(AddPhaseWisePetRequest addPhaseWisePetRequest, Integer userId)
			throws ServiceExecutionException;

	public StudyDiaryResponse getStudyDiary(int studyId, StudyDiaryFilter filter) throws ServiceExecutionException;

	public void extendPhase(AddPhaseWisePetRequest extendPhaseRequest, Integer userId) throws ServiceExecutionException;

	FeedingSchedulesResponse getFeedingScheduleResponse(FeedingScheduleResponseFilter filter)
			throws ServiceExecutionException;

	FeedingSchedulesResponse getFeedingScheduleByPet(int feedingScheduleId) throws ServiceExecutionException;

	PushNotificationConfigResponse pushNotificationConfig(PushNotificationConfigRequest pushNotificationConfigRequest)
			throws ServiceExecutionException;

	public PushNotificationConfigResponse getPushNotificationConfig(int studyId, int phaseId)
			throws ServiceExecutionException;

	ImageScoresConfigResponse imageScoringConfig(ImageScoringConfigRequest imageScoringConfigRequest)
			throws ServiceExecutionException;

	ImageScoresConfigResponse getImageScoringConfig(int studyId, int phaseId) throws ServiceExecutionException;

	QuestionnaireConfigResponse questionnaireConfig(QuestionnaireConfigRequest questionnaireConfigRequest)
			throws ServiceExecutionException;

	QuestionnaireConfigResponse getQuestionnaireConfig(int studyId, int phaseId) throws ServiceExecutionException;

	QuestionnaireConfigResponse getQuestionnaireConfigList(QuestionnaireConfigFilter filter, Boolean isQuesIdReq)
			throws ServiceExecutionException;

	ImageScoringScaleResponse getImageScoringResponse(ImageScaleFilter filter) throws ServiceExecutionException;

	public StudyPhaseQuestionnaireScheduleList getQuestionnaireResponse(QuestionnaireResponseFilter filter)
			throws ServiceExecutionException;

	FeedingSchedulesResponse getPetFoodIntakeHistory(FeedingScheduleResponseFilter filter)
			throws ServiceExecutionException;

	List<PetListDTO> getPets(int study_id) throws ServiceExecutionException;

	FRThresholdByCountResponse getThresholdByCount(FlaggedRecommendationRequest flaggedRecommendationRequest)
			throws ServiceExecutionException;

	FRLookUpResponse getFRFilterData(FlaggedRecommendationRequest flaggedRecommendationRequest)
			throws ServiceExecutionException;

	FlaggedRecommendationDetailsResponse getRecommendationListForStudy(
			FlaggedRecommendationRequest flaggedRecommendationRequest) throws ServiceExecutionException;

	FoodIntakeResponse getFoodIntake(IntakeFilter intakeFilter) throws ServiceExecutionException;

	void updateStatus(FlaggedRecommendationStatusRequest request) throws ServiceExecutionException;

	int createCrossOverStudy(AddCrossOverStudyRequest addCrossOverStudyRequest) throws ServiceExecutionException;

	List<PetBreed> getPetBreeds(int studyId, int phaseId) throws ServiceExecutionException;

	void addStudyNotificationConfig(StudyNotificationConfigRequest studyNotificationConfigRequest, int studyId,
			Integer userId) throws ServiceExecutionException;

	StudyNotificationConfigResponse getStudyNotificationConfigs(int studyId) throws ServiceExecutionException;

	void deleteQuestionnaireConfig(int studyId, int phaseId, int questionnaireConfigId, Integer userId)
			throws ServiceExecutionException;

}