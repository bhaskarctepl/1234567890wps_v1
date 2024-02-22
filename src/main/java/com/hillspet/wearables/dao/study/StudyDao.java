package com.hillspet.wearables.dao.study;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.FeedingSchedule;
import com.hillspet.wearables.dto.FeedingScheduleConfig;
import com.hillspet.wearables.dto.FoodIntakeDetails;
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
import com.hillspet.wearables.dto.filter.QuestionnaireResponseFilter;
import com.hillspet.wearables.dto.filter.StudyDiaryFilter;
import com.hillspet.wearables.dto.filter.StudyDietFilter;
import com.hillspet.wearables.dto.filter.StudyFilter;
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
import com.hillspet.wearables.request.StudyNotificationRequest;
import com.hillspet.wearables.request.StudyPlanRequest;
import com.hillspet.wearables.request.StudyPreludeConfigRequest;
import com.hillspet.wearables.request.StudyQuestionnaireRequest;
import com.hillspet.wearables.response.FRLookUpResponse;
import com.hillspet.wearables.response.FeedingSchedulesResponse;
import com.hillspet.wearables.response.StudyActivityFactorResponse;
import com.hillspet.wearables.response.StudyMobileAppConfigResponse;
import com.hillspet.wearables.response.StudyNotesListResponse;
import com.hillspet.wearables.response.StudyPreludeConfigResponse;

public interface StudyDao {

	/* ---------------- STUDY SERVICES ------------------------- */

	int addStudy(AddStudyRequest studyRequest, Integer userId) throws ServiceExecutionException;

	void updateStudy(AddStudyRequest studyRequest, Integer userId) throws ServiceExecutionException;

	StudyBasicDetails getStudyBasicDetails(int studyId) throws ServiceExecutionException;

	void addStudyDiet(StudyDietRequest studyDietRequest, int studyId, Integer userId) throws ServiceExecutionException;

	Map<String, Integer> getStudyDietListCount(StudyDietFilter filter) throws ServiceExecutionException;

	List<StudyDietListDTO> getStudyDiets(StudyDietFilter filter) throws ServiceExecutionException;

	void validateStudyDiet(int studyId, int studyDietId, int modifiedBy) throws ServiceExecutionException;

	void addStudyPlan(StudyPlanRequest studyPlanRequest, int studyId, Integer userId) throws ServiceExecutionException;

	List<PlanSubscribed> getStudyPlans(int studyId) throws ServiceExecutionException;

	void addStudyMobileAppConfig(StudyMobileAppConfigRequest studyMobileAppConfigRequest, int studyId, Integer userId)
			throws ServiceExecutionException;

	StudyMobileAppConfigResponse getStudyMobielAppConfigs(int studyId) throws ServiceExecutionException;

	void addNotes(StudyNotesRequest studyNotesRequest, int studyId, int phaseId, int userId)
			throws ServiceExecutionException;

	StudyNotesListResponse getStudyNotes(int studyId, int phaseId) throws ServiceExecutionException;

	void addStudyPreludeConfig(StudyPreludeConfigRequest studyPreludeConfigRequest, int studyId, Integer userId)
			throws ServiceExecutionException;

	StudyPreludeConfigResponse getStudyPreludeConfig(int studyId) throws ServiceExecutionException;

	void addStudyActivityFactorConfig(StudyActivityFactorRequest studyActivityFactorRequest, int studyId,
			Integer userId) throws ServiceExecutionException;

	StudyActivityFactorResponse getStudyActivityFactorConfig(int studyId) throws ServiceExecutionException;

	// ====================================================================================================

	void associatePlan(int studyId, int planId, LocalDate subscriptionDate, Integer userId)
			throws ServiceExecutionException;

	void disassociatePlan(int studyId, int planId, Integer userId) throws ServiceExecutionException;

	List<PetListDTO> getAssociatedPets(int studyId) throws ServiceExecutionException;

	Map<String, Integer> getStudyListCount(StudyFilter filter) throws ServiceExecutionException;

	List<StudyListDTO> getStudies(StudyFilter filter) throws ServiceExecutionException;

	List<StudyListDTO> getStudyList(int userId, String includeFuture, String includeVirtual)
			throws ServiceExecutionException;

	List<StudyListDTO> getStudyListByUser(int userId) throws ServiceExecutionException;

	List<StudyListDTO> getAllStudyList() throws ServiceExecutionException;

	List<StudyListDTO> getStudiesByPetParentAndPet(int petParentId, int petId) throws ServiceExecutionException;

	StudyDTO getStudyById(int studyId) throws ServiceExecutionException;

	void deleteStudy(int studyId, int modifiedBy) throws ServiceExecutionException;

	void associateQuestionnaire(int studyId, QuestionnaireAssociated questionnaireAssociated, Integer userId)
			throws ServiceExecutionException;

	void updateStudyQuestionnaire(int studyId, QuestionnaireAssociated questionnaireAssociated, Integer userId)
			throws ServiceExecutionException;

	void disassociateQuestionnaire(int studyId, int questionnaireId, Integer userId) throws ServiceExecutionException;

	List<QuestionnaireListDTO> getAssociatedQuestionnaires(int studyId) throws ServiceExecutionException;

	List<PreludeListDTO> getPreludeDataList(int studyId) throws ServiceExecutionException;

	List<PreludeListDTO> getAFPreludeDataList(int studyId) throws ServiceExecutionException;

	List<PreludeDataByStudyDTO> getPreludeDataByStudy(String studyName) throws ServiceExecutionException;

	List<PreludeDataByStudyDTO> getAFPreludeDataByStudy(String studyName) throws ServiceExecutionException;

	/* ---------------- STUDY NOTIFICATIONS SERVICES ------------------------- */

	Map<String, Integer> getStudyNotificationCount(BaseFilter filter) throws ServiceExecutionException;

	List<StudyNotification> getStudyNotifications(BaseFilter filter) throws ServiceExecutionException;

	void updateStudyNotificationStatus(StudyNotificationRequest studyNotificationRequest, int modifiedBy)
			throws ServiceExecutionException;

	/*
	 * ---------------- STUDY PUSH NOTIFICATIONS SERVICES -------------------------
	 */
	void associatePushNotifications(int studyId, PushNotificationsAssociated pushNotificationsAssociated,
			Integer userId) throws ServiceExecutionException;

	void updateStudyPushNotifications(int studyId, PushNotificationsAssociated pushNotificationsAssociated,
			Integer userId) throws ServiceExecutionException;

	List<PushNotification> getAssociatedPushNotifications(int studyId) throws ServiceExecutionException;

	void disassociateStudyPushNotifications(int studyId, int notificationId, Integer userId)
			throws ServiceExecutionException;

	List<StudyImageScale> associatedImageScales(int studyId) throws ServiceExecutionException;

	// ------- Services for study questionnaire data extract configuration --------
	Questionnaire getQuestionnaireDtlsForDataExtractConfig(Integer studyQuestionnireId);

	void updateDataExtractConfigStudyQuestionnaire(StudyQuestionnaireRequest studyQuestionnaireRequest, Integer userId);

	public FeedingScheduleConfig getStudyPhaseDuration(Integer studyId, Integer phaseId)
			throws ServiceExecutionException;

	public FeedingScheduleConfig getStudyPhaseDiets(Integer studyId, Integer phaseId) throws ServiceExecutionException;

	Integer getStudyIdByName(String studyName) throws ServiceExecutionException;

	public Map<String, Integer> getFeedingScheduleListCount(Integer studyId, Integer phaseId,
			FeedingScheduleFilter filter) throws ServiceExecutionException;

	List<FeedingSchedule> getFeedingScheduleList(Integer studyId, Integer phaseId, FeedingScheduleFilter filter)
			throws ServiceExecutionException;

	public void deleteFeedingSchedule(int feedingScheduleConfigId, int modifiedBy) throws ServiceExecutionException;

	public void saveStudyPhaseFeedingSchedule(FeedingScheduleConfig feedingSchedule) throws ServiceExecutionException;

	// ----------------- Services for Adding pet,Search page and Delete Pet to Study

	void addPhaseWisePet(AddPhaseWisePetRequest addPhaseWisePetRequest, Integer userId)
			throws ServiceExecutionException;

	void removePetsFromPhase(AddPhaseWisePetRequest addPhaseWisePetRequest, Integer userId)
			throws ServiceExecutionException;

	Map<String, Integer> getPhaseWisePetListCount(PhaseWisePetListFilter filter) throws ServiceExecutionException;

	List<PhaseWisePetListDTO> getPhaseWisePetList(PhaseWisePetListFilter filter) throws ServiceExecutionException;

	public void changePetsTreatmentGroup(AddPhaseWisePetRequest addPhaseWisePetRequest, Integer userId)
			throws ServiceExecutionException;

	public void movePetsToNextPhase(AddPhaseWisePetRequest addPhaseWisePetRequest, Integer userId)
			throws ServiceExecutionException;

	Map<String, Integer> getStudyDiaryListCount(int studyId, StudyDiaryFilter filter) throws ServiceExecutionException;

	List<StudyDiary> getStudyDiaryList(int studyId, StudyDiaryFilter filter) throws ServiceExecutionException;

	public void extendPhase(AddPhaseWisePetRequest extendPhaseRequest, Integer userId) throws ServiceExecutionException;

	Map<String, Integer> getFeedingScheduleResponseCount(FeedingScheduleResponseFilter filter)
			throws ServiceExecutionException;

	List<FeedingSchedule> getFeedingScheduleResponse(FeedingScheduleResponseFilter filter)
			throws ServiceExecutionException;

	public FeedingSchedulesResponse getFeedingScheduleByPet(int feedingScheduleId) throws ServiceExecutionException;

	public void pushNotificationConfig(PushNotificationConfigRequest imageScoringConfigRequest)
			throws ServiceExecutionException;

	public List<PushNotificationAssociated> getPushNotificationConfig(int studyId, int phaseId)
			throws ServiceExecutionException;

	void imageScoringConfig(ImageScoringConfigRequest imageScoringConfigRequest) throws ServiceExecutionException;

	List<ImageScoringAssociated> getImageScoringConfig(int studyId, int phaseId) throws ServiceExecutionException;

	void questionnaireConfig(QuestionnaireConfigRequest questionnaireConfigRequest) throws ServiceExecutionException;

	List<QuestionnaireAssociated> getQuestionnaireConfig(int studyId, int phaseId) throws ServiceExecutionException;

	Map<String, Integer> getImageScoringResponseCount(ImageScaleFilter filter) throws ServiceExecutionException;

	List<ImageScoringResponse> getImageScoringResponse(ImageScaleFilter filter) throws ServiceExecutionException;

	public Map<String, Integer> getQuestionnaireResponseCount(QuestionnaireResponseFilter filter)
			throws ServiceExecutionException;

	public List<StudyPhaseQuestionnaireScheduleDTO> getQuestionnaireResponse(QuestionnaireResponseFilter filter)
			throws ServiceExecutionException;

	Map<String, Integer> getPetFoodIntakeHistoryCount(FeedingScheduleResponseFilter filter)
			throws ServiceExecutionException;

	List<FeedingSchedule> getPetFoodIntakeHistory(FeedingScheduleResponseFilter filter)
			throws ServiceExecutionException;

	List<PetListDTO> getPets(int study_id) throws ServiceExecutionException;

	List<ThresholdByStudyReport> getThresholdByCountReport(FlaggedRecommendationRequest flaggedRecommendationRequest)
			throws ServiceExecutionException;

	FRLookUpResponse getFRFilterData(FlaggedRecommendationRequest flaggedRecommendationRequest)
			throws ServiceExecutionException;

	HashMap<String, Integer> getRecommendationListForStudyCount(FlaggedRecommendationRequest request)
			throws ServiceExecutionException;

	List<RecommendationDetails> getRecommendationListForStudy(FlaggedRecommendationRequest request)
			throws ServiceExecutionException;

	List<FoodIntakeDetails> getFoodIntake(IntakeFilter intakeFilter) throws ServiceExecutionException;

	HashMap<String, Integer> getFoodIntakeListCount(IntakeFilter intakeFilter) throws ServiceExecutionException;

	void updateStatus(FlaggedRecommendationStatusRequest request) throws ServiceExecutionException;

	int createCrossOverStudy(AddCrossOverStudyRequest addCrossOverStudyRequest) throws ServiceExecutionException;

	List<PetBreed> getPetBreeds(int studyId, int phaseId) throws ServiceExecutionException;
}
