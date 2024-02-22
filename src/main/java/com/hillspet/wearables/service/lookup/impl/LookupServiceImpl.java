package com.hillspet.wearables.service.lookup.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.lookup.LookupDao;
import com.hillspet.wearables.dto.AgentAction;
import com.hillspet.wearables.dto.Algorithm;
import com.hillspet.wearables.dto.AssignedUser;
import com.hillspet.wearables.dto.BfiScorer;
import com.hillspet.wearables.dto.CategoryTimer;
import com.hillspet.wearables.dto.ContactMethod;
import com.hillspet.wearables.dto.Country;
import com.hillspet.wearables.dto.CustomerContactMethod;
import com.hillspet.wearables.dto.CustomerContactReason;
import com.hillspet.wearables.dto.DefectiveSensorAction;
import com.hillspet.wearables.dto.DeviceLocation;
import com.hillspet.wearables.dto.DeviceStatus;
import com.hillspet.wearables.dto.DietDTO;
import com.hillspet.wearables.dto.EatingEnthusiasmScale;
import com.hillspet.wearables.dto.ExtractFileCategory;
import com.hillspet.wearables.dto.FeedingScheduleConfig;
import com.hillspet.wearables.dto.Frequency;
import com.hillspet.wearables.dto.ImageScoringType;
import com.hillspet.wearables.dto.InventoryStatus;
import com.hillspet.wearables.dto.Issue;
import com.hillspet.wearables.dto.MaterialCategory;
import com.hillspet.wearables.dto.MaterialType;
import com.hillspet.wearables.dto.MeasurementUnit;
import com.hillspet.wearables.dto.Menu;
import com.hillspet.wearables.dto.MenuAction;
import com.hillspet.wearables.dto.MobileAppConfig;
import com.hillspet.wearables.dto.MobileAppFBPhoneModel;
import com.hillspet.wearables.dto.MobileAppFeedbackPage;
import com.hillspet.wearables.dto.Occurance;
import com.hillspet.wearables.dto.PetBreed;
import com.hillspet.wearables.dto.PetFeedingTime;
import com.hillspet.wearables.dto.PetName;
import com.hillspet.wearables.dto.PetNameTimer;
import com.hillspet.wearables.dto.PetParent;
import com.hillspet.wearables.dto.PetParentNameTimer;
import com.hillspet.wearables.dto.PetSpecies;
import com.hillspet.wearables.dto.PetStatus;
import com.hillspet.wearables.dto.Phase;
import com.hillspet.wearables.dto.PhaseDays;
import com.hillspet.wearables.dto.PointTracker;
import com.hillspet.wearables.dto.PointTrackerActivity;
import com.hillspet.wearables.dto.PointTrackerMetric;
import com.hillspet.wearables.dto.PointTrackerRejectReason;
import com.hillspet.wearables.dto.PreDefinedInstruction;
import com.hillspet.wearables.dto.PreDefinedQuestion;
import com.hillspet.wearables.dto.PreludeStudy;
import com.hillspet.wearables.dto.Priority;
import com.hillspet.wearables.dto.ProductType;
import com.hillspet.wearables.dto.PushNotification;
import com.hillspet.wearables.dto.QuestionCategory;
import com.hillspet.wearables.dto.QuestionType;
import com.hillspet.wearables.dto.QuestionValidityPeriod;
import com.hillspet.wearables.dto.QuestionnaireCategory;
import com.hillspet.wearables.dto.QuestionnaireListDTO;
import com.hillspet.wearables.dto.QuestionnaireType;
import com.hillspet.wearables.dto.Role;
import com.hillspet.wearables.dto.RoleType;
import com.hillspet.wearables.dto.RootCause;
import com.hillspet.wearables.dto.SensorLocation;
import com.hillspet.wearables.dto.SensorName;
import com.hillspet.wearables.dto.ShipmentCompany;
import com.hillspet.wearables.dto.Status;
import com.hillspet.wearables.dto.StudyLocation;
import com.hillspet.wearables.dto.StudyName;
import com.hillspet.wearables.dto.StudyNameFilter;
import com.hillspet.wearables.dto.StudyStatus;
import com.hillspet.wearables.dto.TicketAssignedUser;
import com.hillspet.wearables.dto.TicketCategory;
import com.hillspet.wearables.dto.TicketPriority;
import com.hillspet.wearables.dto.TicketStatus;
import com.hillspet.wearables.dto.TicketType;
import com.hillspet.wearables.dto.TimeZone;
import com.hillspet.wearables.jaxrs.resource.impl.PushNotificationListResponse;
import com.hillspet.wearables.response.EatingEnthusiasmScaleResponse;
import com.hillspet.wearables.response.PetFeedingTimeResponse;
import com.hillspet.wearables.response.QuestionnaireListResponse;
import com.hillspet.wearables.service.lookup.LookupService;
import com.hillspet.wearables.service.study.StudyService;

@Service
public class LookupServiceImpl implements LookupService {

	private static final Logger LOGGER = LogManager.getLogger(LookupServiceImpl.class);

	@Autowired
	private LookupDao lookupDao;

	@Autowired
	private StudyService studyService;

	@Override
	public List<RoleType> getRoleTypes() throws ServiceExecutionException {
		LOGGER.debug("getRoleTypes called");
		List<RoleType> roleTypes = lookupDao.getRoleTypes();
		LOGGER.debug("getRoleTypes list", roleTypes);
		return roleTypes;

	}

	@Override
	public List<Menu> getMenus(Integer roleTypeId) throws ServiceExecutionException {
		LOGGER.debug("getMenus called");
		List<Menu> menus = lookupDao.getMenus(roleTypeId);
		LOGGER.debug("getMenus list", menus);
		return menus;
	}

	@Override
	public List<MenuAction> getMenuActions() throws ServiceExecutionException {
		LOGGER.debug("getMenuActions called");
		List<MenuAction> menuActions = lookupDao.getMenuActions();
		LOGGER.debug("getMenuActions list", menuActions);
		return menuActions;
	}

	@Override
	public List<Role> getRoles() throws ServiceExecutionException {
		LOGGER.debug("getRoles called");
		List<Role> roles = lookupDao.getRoles();
		LOGGER.debug("getRoles list", roles);
		return roles;
	}

	@Override
	public List<Role> getActiveRoles() throws ServiceExecutionException {
		LOGGER.debug("getActiveRoles called");
		List<Role> roles = lookupDao.getActiveRoles();
		LOGGER.debug("getActiveRoles list", roles);
		return roles;
	}

	@Override
	public List<PetBreed> getPetBreeds() throws ServiceExecutionException {
		LOGGER.debug("getPetBreeds called");
		List<PetBreed> breeds = lookupDao.getPetBreeds();
		LOGGER.debug("getPetBreeds list", breeds);
		return breeds;
	}

	@Override
	public List<PetStatus> getPetStatuses() throws ServiceExecutionException {
		LOGGER.debug("getPetStatuses called");
		List<PetStatus> statuses = lookupDao.getPetStatuses();
		LOGGER.debug("getPetStatuses list", statuses);
		return statuses;
	}

	@Override
	public List<MobileAppConfig> getMobileAppConfigs() throws ServiceExecutionException {
		LOGGER.debug("getMobileAppConfigs called");
		List<MobileAppConfig> mobileAppConfigs = lookupDao.getMobileAppConfigs();
		LOGGER.debug("getMobileAppConfigs list", mobileAppConfigs);
		return mobileAppConfigs;
	}

	/* ------------ Customer Support Lookup Services Start ---------------- */

	@Override
	public List<TicketType> getTicketTypes() throws ServiceExecutionException {
		LOGGER.debug("getTicketTypes called");
		List<TicketType> ticketTypes = lookupDao.getTicketTypes();
		LOGGER.debug("getTicketTypes list", ticketTypes.size());
		return ticketTypes;
	}

	@Override
	public List<TicketPriority> getTicketPriorities() throws ServiceExecutionException {
		LOGGER.debug("getTicketPriorities called");
		List<TicketPriority> ticketPriorities = lookupDao.getTicketPriorities();
		LOGGER.debug("getTicketPriorities list", ticketPriorities.size());
		return ticketPriorities;
	}

	@Override
	public List<TicketStatus> getTicketStatus() throws ServiceExecutionException {
		LOGGER.debug("getTicketStatus called");
		List<TicketStatus> ticketStatus = lookupDao.getTicketStatus();
		LOGGER.debug("getTicketStatus list", ticketStatus.size());
		return ticketStatus;
	}

	@Override
	public List<CustomerContactMethod> getCustomerContactMethods() throws ServiceExecutionException {
		LOGGER.debug("getCustomerContactMethods called");
		List<CustomerContactMethod> customerContactMethods = lookupDao.getCustomerContactMethods();
		LOGGER.debug("getCustomerContactMethods list", customerContactMethods.size());
		return customerContactMethods;
	}

	@Override
	public List<CustomerContactReason> getCustomerContactReasons() throws ServiceExecutionException {
		LOGGER.debug("getCustomerContactReasons called");
		List<CustomerContactReason> customerContactReasons = lookupDao.getCustomerContactReasons();
		LOGGER.debug("getCustomerContactReasons list", customerContactReasons.size());
		return customerContactReasons;
	}

	@Override
	public List<TicketCategory> getTicketCategory() throws ServiceExecutionException {
		LOGGER.debug("getTicketCategory called");
		List<TicketCategory> ticketCategories = lookupDao.getTicketCategory();
		LOGGER.debug("getTicketCategory list", ticketCategories.size());
		return ticketCategories;
	}

	@Override
	public List<Issue> getIssues() throws ServiceExecutionException {
		LOGGER.debug("getIssues called");
		List<Issue> issueList = lookupDao.getIssues();
		LOGGER.debug("getIssues list", issueList.size());
		return issueList;
	}

	@Override
	public List<RootCause> getRootCause(Integer issueId) throws ServiceExecutionException {
		LOGGER.debug("getRootCauseId called");
		List<RootCause> rootCauseList = lookupDao.getRootCause(issueId);
		LOGGER.debug("getRootCauseId list", rootCauseList.size());
		return rootCauseList;
	}

	@Override
	public List<AgentAction> getAgentAction(Integer testId) throws ServiceExecutionException {
		LOGGER.debug("getAgentAction called");
		List<AgentAction> agentActionList = lookupDao.getAgentAction(testId);
		LOGGER.debug("getAgentAction list", agentActionList.size());
		return agentActionList;
	}

	@Override
	public List<DefectiveSensorAction> getDefectiveSensorAction() throws ServiceExecutionException {
		LOGGER.debug("getDefectiveSensorActionId called");
		List<DefectiveSensorAction> defectiveSensorActionList = lookupDao.getDefectiveSensorAction();
		LOGGER.debug("getDefectiveSensorActionId list", defectiveSensorActionList.size());
		return defectiveSensorActionList;
	}

	@Override
	public List<PetName> getPetName() throws ServiceExecutionException {
		LOGGER.debug("getPetNameId called");
		List<PetName> petNameList = lookupDao.getPetName();
		LOGGER.debug("getPetNameId list", petNameList.size());
		return petNameList;
	}

	@Override
	public List<PetParent> getPetParentName(Integer petId) throws ServiceExecutionException {
		LOGGER.debug("getPetParentNameId called");
		List<PetParent> petParentNameList = lookupDao.getPetParentName(petId);
		LOGGER.debug("getPetParentNameId list", petParentNameList.size());
		return petParentNameList;
	}

	@Override
	public List<StudyName> getStudyName(Integer petId) throws ServiceExecutionException {
		LOGGER.debug("getStudyName called");
		List<StudyName> studyNameList = lookupDao.getStudyName(petId);
		LOGGER.debug("getStudyName list", studyNameList.size());
		return studyNameList;
	}

	@Override
	public List<StudyName> getActiveStudy() throws ServiceExecutionException {
		LOGGER.debug("getActiveStudy called");
		List<StudyName> studyNameList = lookupDao.getActiveStudy();
		LOGGER.debug("getActiveStudy list", studyNameList.size());
		return studyNameList;
	}

	@Override
	public List<SensorName> getSensorName(Integer studyId, Integer petId) throws ServiceExecutionException {
		LOGGER.debug("getSensorName called");
		List<SensorName> sensorNameList = lookupDao.getSensorName(studyId, petId);
		LOGGER.debug("getSensorName list", sensorNameList.size());
		return sensorNameList;
	}

	@Override
	public List<SensorLocation> getSensorLocation() throws ServiceExecutionException {
		LOGGER.debug("getSensorLocationId called");
		List<SensorLocation> sensorNameList = lookupDao.getSensorLocation();
		LOGGER.debug("getSensorLocationId list", sensorNameList.size());
		return sensorNameList;
	}

	@Override
	public List<InventoryStatus> getInventoryStatus(Integer deviceId) throws ServiceExecutionException {
		LOGGER.debug("getInventoryStatusId called");
		List<InventoryStatus> inventoryStatusList = lookupDao.getInventoryStatus(deviceId);
		LOGGER.debug("getInventoryStatusId list", inventoryStatusList.size());
		return inventoryStatusList;
	}

	@Override
	public List<Priority> getPriority() throws ServiceExecutionException {
		LOGGER.debug("getPriority called");
		List<Priority> priorityList = lookupDao.getPriority();
		LOGGER.debug("getPriority list", priorityList.size());
		return priorityList;
	}

	@Override
	public List<AssignedUser> getAssignedTo() throws ServiceExecutionException {
		LOGGER.debug("getAssignedTo called");
		List<AssignedUser> assignedUserList = lookupDao.getAssignedTo();
		LOGGER.debug("getAssignedTo list", assignedUserList.size());
		return assignedUserList;
	}

	@Override
	public List<Status> getStatus() throws ServiceExecutionException {
		LOGGER.debug("getStatus called");
		List<Status> statusList = lookupDao.getStatus();
		LOGGER.debug("getStatus list", statusList.size());
		return statusList;
	}

	@Override
	public List<ContactMethod> getContactMethod() throws ServiceExecutionException {
		LOGGER.debug("getContactMethodId called");
		List<ContactMethod> contactMethodList = lookupDao.getContactMethod();
		LOGGER.debug("getContactMethodId list", contactMethodList.size());
		return contactMethodList;
	}

	@Override
	public List<String> getDeviceType() throws ServiceExecutionException {
		LOGGER.debug("getDeviceType called");
		List<String> deviceTypeList = lookupDao.getDeviceType();
		LOGGER.debug("getDeviceType list", deviceTypeList.size());
		return deviceTypeList;
	}

	@Override
	public List<String> getDeviceModel(String deviceType) throws ServiceExecutionException {
		LOGGER.debug("getDeviceModel called");
		List<String> deviceTypeList = lookupDao.getDeviceModel(deviceType);
		LOGGER.debug("getDeviceModel list", deviceTypeList.size());
		return deviceTypeList;
	}

	/* ------------ Questionnaire Lookup Services Start ---------------- */
	@Override
	public List<QuestionType> getQuestionType() throws ServiceExecutionException {
		LOGGER.debug("getQuestionType called");
		List<QuestionType> questionTypes = lookupDao.getQuestionType();
		LOGGER.debug("getQuestionType list", questionTypes.size());
		return questionTypes;
	}

	@Override
	public List<PreDefinedInstruction> getPreDefinedInstructions() throws ServiceExecutionException {
		LOGGER.debug("getPreDefinedInstructions called");
		List<PreDefinedInstruction> preDefinedInstructions = lookupDao.getPreDefinedInstructions();
		LOGGER.debug("getPreDefinedInstructions list", preDefinedInstructions.size());
		return preDefinedInstructions;
	}

	@Override
	public List<PreDefinedQuestion> getPreDefinedQuestions(String searchText) throws ServiceExecutionException {
		LOGGER.debug("getPreDefinedQuestions called");
		List<PreDefinedQuestion> preDefinedQuestions = lookupDao.getPreDefinedQuestions(searchText);
		LOGGER.debug("getPreDefinedQuestions list", preDefinedQuestions.size());
		return preDefinedQuestions;
	}

	@Override
	public QuestionnaireListResponse getQuestionnaires(int typeId) throws ServiceExecutionException {
		List<QuestionnaireListDTO> supportList = lookupDao.getQuestionnaires(typeId);
		QuestionnaireListResponse response = new QuestionnaireListResponse();
		response.setQuestionnaireList(supportList);
		response.setNoOfElements(supportList.size());
		return response;
	}

	@Override
	public QuestionnaireListResponse getPetQuestionnaires(int petId) throws ServiceExecutionException {
		List<QuestionnaireListDTO> questionnaireList = lookupDao.getPetQuestionnaires(petId);
		QuestionnaireListResponse response = new QuestionnaireListResponse();
		response.setQuestionnaireList(questionnaireList);
		response.setNoOfElements(questionnaireList.size());
		return response;
	}

	/* ------------ PointTracker Lookup Services Start ---------------- */
	@Override
	public List<PointTrackerActivity> getPointTrackerActivities() throws ServiceExecutionException {
		LOGGER.debug("getPointTrackerActivities called");
		List<PointTrackerActivity> pointTrackerActivities = lookupDao.getPointTrackerActivities();
		LOGGER.debug("getPointTrackerActivities list", pointTrackerActivities.size());
		return pointTrackerActivities;
	}

	@Override
	public List<PointTrackerMetric> getPetBehaviors(int speciesId, int behaviorTypeId)
			throws ServiceExecutionException {
		LOGGER.debug("getPetBehaviors called");
		List<PointTrackerMetric> pointTrackerMetrics = lookupDao.getPetBehaviors(speciesId, behaviorTypeId);
		LOGGER.debug("getPetBehaviors list", pointTrackerMetrics.size());
		return pointTrackerMetrics;
	}

	@Override
	public List<PointTrackerMetric> getPointTrackerMetrics(int petId) throws ServiceExecutionException {
		LOGGER.debug("getPointTrackerActivities called");
		List<PointTrackerMetric> pointTrackerMetrics = lookupDao.getPointTrackerMetrics(petId);
		LOGGER.debug("pointTrackerMetric list", pointTrackerMetrics.size());
		return pointTrackerMetrics;
	}

	@Override
	public List<PointTrackerMetric> getPointTrackerMetricsById(int trackerPetPointsId)
			throws ServiceExecutionException {
		LOGGER.debug("getPointTrackerMetricsById called");
		List<PointTrackerMetric> pointTrackerMetrics = lookupDao.getPointTrackerMetricsById(trackerPetPointsId);
		LOGGER.debug("getPointTrackerMetricsById list", pointTrackerMetrics.size());
		return pointTrackerMetrics;
	}

	@Override
	public List<PointTrackerRejectReason> getTrackerRejectReasons() throws ServiceExecutionException {
		LOGGER.debug("getTrackerRejectReasons called");
		List<PointTrackerRejectReason> trackerRejectReasonList = lookupDao.getTrackerRejectReasons();
		LOGGER.debug("getTrackerRejectReasons list", trackerRejectReasonList.size());
		return trackerRejectReasonList;
	}

	@Override
	public List<DeviceLocation> getDeviceLocations() throws ServiceExecutionException {
		LOGGER.debug("getDeviceLocations called");
		List<DeviceLocation> deviceLocations = lookupDao.getDeviceLocations();
		LOGGER.debug("getDeviceLocations list", deviceLocations.size());
		return deviceLocations;
	}

	/*
	 * ------------ Asset Management Status Lookup Services Start ----------------
	 */
	@Override
	public List<DeviceStatus> getDeviceStatuses(String showOnlyStudyStatuses) throws ServiceExecutionException {
		LOGGER.debug("getDeviceStatuses called");
		List<DeviceStatus> deviceStatuses = lookupDao.getDeviceStatuses(showOnlyStudyStatuses);
		LOGGER.debug("getDeviceStatus list", deviceStatuses.size());
		return deviceStatuses;
	}
	/* ------------ Asset Management Status Lookup Services End ---------------- */

	/*
	 * ------------ Mobile App Feedback Pages Lookup Services Start ----------------
	 */
	@Override
	public List<MobileAppFeedbackPage> getMobileAppFeedbackPages() throws ServiceExecutionException {
		LOGGER.debug("getDeviceStatuses called");
		List<MobileAppFeedbackPage> mobileAppFeedbackPages = lookupDao.getMobileAppFeedbackPages();
		LOGGER.debug("getMobileAppFeedbackPages list", mobileAppFeedbackPages.size());
		return mobileAppFeedbackPages;
	}

	@Override
	public List<MobileAppFBPhoneModel> getMobileAppPhoneModels() throws ServiceExecutionException {
		LOGGER.debug("getMobileAppPhoneModels called");
		List<MobileAppFBPhoneModel> mobileAppFeedbackPages = lookupDao.getMobileAppFeedbackPhoneModels();
		LOGGER.debug("getMobileAppPhoneModels list", mobileAppFeedbackPages.size());
		return mobileAppFeedbackPages;
	}
	/*
	 * ------------ Mobile App Feedback Pages Lookup Services End ----------------
	 */

	@Override
	public List<StudyNameFilter> getAllStudyName() throws ServiceExecutionException {
		LOGGER.debug("getStudyName called");
		List<StudyNameFilter> studyNameList = lookupDao.getAllStudyName();
		LOGGER.debug("getStudyName list", studyNameList.size());
		return studyNameList;
	}

	@Override
	public List<StudyNameFilter> getAllActiveStudyName() throws ServiceExecutionException {
		LOGGER.debug("getAllActiveStudyName called");
		List<StudyNameFilter> studyNameList = lookupDao.getAllActiveStudyName();
		LOGGER.debug("getAllActiveStudyName list", studyNameList.size());
		return studyNameList;
	}

	@Override
	public List<PetSpecies> getPetSpecies() throws ServiceExecutionException {
		LOGGER.debug("getPetSpecies called");
		List<PetSpecies> speciesList = lookupDao.getPetSpecies();
		LOGGER.debug("getPetSpecies list", speciesList.size());
		return speciesList;
	}

	@Override
	public List<TicketAssignedUser> getAssignedToUsers() throws ServiceExecutionException {
		LOGGER.debug("getAssignedToUsers called");
		List<TicketAssignedUser> assignedUserList = lookupDao.getAssignedToUsers();
		LOGGER.debug("getAssignedToUsers list", assignedUserList.size());
		return assignedUserList;
	}

	@Override
	public List<PetParentNameTimer> getPetParentNameTimer() throws ServiceExecutionException {
		LOGGER.debug("getPetParentNameTimer called");
		List<PetParentNameTimer> petParentNameList = lookupDao.getPetParentNameTimer();
		LOGGER.debug("getPetParentNameTimer list", petParentNameList.size());
		return petParentNameList;
	}

	@Override
	public List<PetNameTimer> getPetNameTimer() throws ServiceExecutionException {
		LOGGER.debug("getPetNameTimer called");
		List<PetNameTimer> petNameList = lookupDao.getPetNameTimer();
		LOGGER.debug("getPetNameTimer list", petNameList.size());
		return petNameList;
	}

	@Override
	public List<CategoryTimer> getCategoryNameTimer() throws ServiceExecutionException {
		LOGGER.debug("getCategoryNameTimer called");
		List<CategoryTimer> categoryList = lookupDao.getCategoryNameTimer();
		LOGGER.debug("getCategoryNameTimer list", categoryList.size());
		return categoryList;
	}

	@Override
	public List<PreludeStudy> getPreludeStudyName() throws ServiceExecutionException {
		LOGGER.debug("getPreludeStudyName called");
		List<PreludeStudy> categoryList = lookupDao.getPreludeStudyName();
		LOGGER.debug("getPreludeStudyName list", categoryList.size());
		return categoryList;
	}

	@Override
	public List<ExtractFileCategory> getExtractFileCategory(String studyId) throws ServiceExecutionException {
		LOGGER.debug("getExtractFileCategory called");
		List<ExtractFileCategory> categoryList = lookupDao.getExtractFileCategory(studyId);
		LOGGER.debug("getExtractFileCategory list", categoryList.size());
		return categoryList;
	}

	@Override
	public List<PointTracker> getCampaignList() throws ServiceExecutionException {
		LOGGER.debug("getCampaignList called");
		List<PointTracker> pointTrackers = lookupDao.getCampaignList();
		LOGGER.debug("getCampaignList list", pointTrackers.size());
		return pointTrackers;
	}

	@Override
	public List<Occurance> getQuestionnaireOccurances(String showStudyStartUp) throws ServiceExecutionException {
		LOGGER.debug("getQuestionnaireOccurances called");
		List<Occurance> occurances = lookupDao.getQuestionnaireOccurances(showStudyStartUp);
		LOGGER.debug("getQuestionnaireOccurances list", occurances.size());
		return occurances;
	}

	@Override
	public List<Frequency> getQuestionnaireFrequencies(String showStudyPhaseDays) throws ServiceExecutionException {
		LOGGER.debug("getQuestionnaireFrequencies called");
		List<Frequency> frequencies = lookupDao.getQuestionnaireFrequencies(showStudyPhaseDays);
		LOGGER.debug("getQuestionnaireFrequencies list", frequencies.size());
		return frequencies;
	}

	@Override
	public PushNotificationListResponse getStudyPushNotifications(String searchText) throws ServiceExecutionException {
		List<PushNotification> pushNotifications = lookupDao.getStudyPushNotifications(searchText);

		PushNotificationListResponse response = new PushNotificationListResponse();
		response.setPushNotifications(pushNotifications);
		response.setNoOfElements(pushNotifications.size());
		return response;
	}

	@Override
	public List<MaterialType> getMaterialTypeList() throws ServiceExecutionException {
		LOGGER.debug("getMaterialTypeList called");
		List<MaterialType> materialTypes = lookupDao.getMaterialTypeList();
		LOGGER.debug("materialTypes list", materialTypes);
		return materialTypes;
	}

	@Override
	public List<MaterialCategory> getMaterialCategoryList() throws ServiceExecutionException {
		LOGGER.debug("getMaterialCategoryList called");
		List<MaterialCategory> roleTypes = lookupDao.getMaterialCategoryList();
		LOGGER.debug("getMaterialCategoryList list", roleTypes);
		return roleTypes;
	}

	@Override
	public EatingEnthusiasmScaleResponse getPetEatingEnthusiasmScales(String speciesId) throws ServiceExecutionException {
		LOGGER.debug("getPetEatingEnthusiasmScale called");
		List<EatingEnthusiasmScale> eatingEnthusiasmScales = lookupDao.getPetEatingEnthusiasmScales(speciesId);
		EatingEnthusiasmScaleResponse response = new EatingEnthusiasmScaleResponse();
		response.setEatingEnthusiasmScales(eatingEnthusiasmScales);
		LOGGER.debug("getPetEatingEnthusiasmScale end");
		return response;
	}

	@Override
	public PetFeedingTimeResponse getPetFeedingTimes() throws ServiceExecutionException {
		LOGGER.debug("getPetFeedingTime called");
		List<PetFeedingTime> petFeedingTimes = lookupDao.getPetFeedingTimes();
		PetFeedingTimeResponse response = new PetFeedingTimeResponse();
		response.setPetFeedingTimes(petFeedingTimes);
		LOGGER.debug("getPetFeedingTime end");
		return response;
	}

	@Override
	public List<ImageScoringType> getImageScoringTypes() throws ServiceExecutionException {
		LOGGER.debug("getImageScoringTypes called");
		List<ImageScoringType> imageScoringTypes = lookupDao.getImageScoringTypes();
		LOGGER.debug("getImageScoringTypes end");
		return imageScoringTypes;
	}

	@Override
	public List<ShipmentCompany> getShipmentCompanies() throws ServiceExecutionException {
		LOGGER.debug("getShipmentCompanies called");
		List<ShipmentCompany> shipmentCompanies = lookupDao.getShipmentCompanies();
		LOGGER.debug("getShipmentCompanies end");
		return shipmentCompanies;
	}

	@Override
	public List<QuestionnaireType> getQuestionnaireTypes(String showPetQuestionnaire) throws ServiceExecutionException {
		LOGGER.debug("getQuestionnaireTypes called");
		List<QuestionnaireType> questionnaireTypes = lookupDao.getQuestionnaireTypes(showPetQuestionnaire);
		LOGGER.debug("getQuestionnaireTypes end");
		return questionnaireTypes;
	}

	@Override
	public List<QuestionnaireCategory> getQuestionnaireCategory(Integer questionnaireTypeId)
			throws ServiceExecutionException {
		LOGGER.debug("getQuestionnaireCategory called");
		List<QuestionnaireCategory> questionnaireCategories = lookupDao.getQuestionnaireCategory(questionnaireTypeId);
		LOGGER.debug("getQuestionnaireCategory end");
		return questionnaireCategories;
	}

	@Override
	public TimeZone getTimeZoneDetails(String timeZoneName) throws ServiceExecutionException {
		LOGGER.debug("getTimeZoneDetails called");
		TimeZone timeZone = lookupDao.getTimeZoneDetails(timeZoneName);
		LOGGER.debug("getTimeZoneDetails end");
		return timeZone;
	}

	@Override
	public List<Algorithm> getAlgorithmList() throws ServiceExecutionException {
		LOGGER.debug("getAlgorithmList called");
		List<Algorithm> algorithms = lookupDao.getAlgorithmList();
		LOGGER.debug("getAlgorithmList end");
		return algorithms;
	}

	@Override
	public List<QuestionCategory> getQuestionCategory() throws ServiceExecutionException {
		LOGGER.debug("getQuestionCategory called");
		List<QuestionCategory> questionCategories = lookupDao.getQuestionCategory();
		LOGGER.debug("getQuestionCategory end");
		return questionCategories;
	}

	@Override
	public List<QuestionValidityPeriod> getQuestionValidityPeriods() throws ServiceExecutionException {
		LOGGER.debug("getQuestionCategory called");
		List<QuestionValidityPeriod> questionValidityPeriods = lookupDao.getQuestionValidityPeriods();
		LOGGER.debug("getQuestionCategory end");
		return questionValidityPeriods;
	}

	@Override
	public List<Country> getCountryList() throws ServiceExecutionException {
		LOGGER.debug("getCountryListy called");
		List<Country> countries = lookupDao.getCountryListy();
		LOGGER.debug("getCountryListy end");
		return countries;
	}

	@Override
	public List<BfiScorer> getbfiScorerList() throws ServiceExecutionException {
		LOGGER.debug("getbfiScorerList called");
		List<BfiScorer> bfiScorerList = lookupDao.getbfiScorerList();
		LOGGER.debug("getbfiScorerList end");
		return bfiScorerList;
	}

	@Override
	public List<MeasurementUnit> getMeasurementUnits(String unitCategory) throws ServiceExecutionException {
		LOGGER.debug("getMeasurementUnits called");
		List<MeasurementUnit> measurementUnits = lookupDao.getMeasurementUnits(unitCategory);
		LOGGER.debug("getMeasurementUnits end");
		return measurementUnits;
	}

	@Override
	public List<Phase> getStudyPhases() throws ServiceExecutionException {
		LOGGER.debug("getStudyPhases called");
		List<Phase> studyPhases = lookupDao.getStudyPhases();
		LOGGER.debug("getStudyPhases end");
		return studyPhases;
	}

	@Override
	public List<StudyStatus> getStudyStatus() throws ServiceExecutionException {
		LOGGER.debug("getStudyStatus called");
		List<StudyStatus> statuses = lookupDao.getStudyStatus();
		LOGGER.debug("getStudyStatus end");
		return statuses;
	}

	public List<ProductType> getProductTypeList() throws ServiceExecutionException {
		LOGGER.debug("getProductTypeList called");
		List<ProductType> productTypeList = lookupDao.getProductTypeList();
		LOGGER.debug("getProductTypeList end");
		return productTypeList;
	}

	@Override
	public List<StudyLocation> getStudyLocations() throws ServiceExecutionException {
		LOGGER.debug("getStudyLocations called");
		List<StudyLocation> studyLocations = lookupDao.getStudyLocations();
		LOGGER.debug("getStudyLocations end");
		return studyLocations;
	}

	@Override
	public List<MeasurementUnit> getStudyPhaseDurationUnits() throws ServiceExecutionException {
		LOGGER.debug("getStudyPhaseDurationUnits called");
		List<MeasurementUnit> measurementUnits = lookupDao.getStudyPhaseDurationUnits();
		LOGGER.debug("getStudyPhaseDurationUnits end");
		return measurementUnits;
	}

	@Override
	public List<PhaseDays> getPhaseDays(int studyId, int phaseId) throws ServiceExecutionException {
		LOGGER.debug("getPhaseDays called");
		FeedingScheduleConfig config = studyService.getStudyPhaseDuration(studyId, phaseId);
		int phaseDuration = config.getPhaseDuration();
		List<PhaseDays> phaseList = new ArrayList<>();
		for (int i = 1; i <= phaseDuration; i++) {
			phaseList.add(new PhaseDays(i, "Day " + i));
		}
		LOGGER.debug("getPhaseDays called");
		return phaseList;
	}

	@Override
	public List<DietDTO> getDiets() throws ServiceExecutionException {
		LOGGER.debug("getDiets called");
		List<DietDTO> dietDTOs = lookupDao.getDiets();
		LOGGER.debug("getDiets end");
		return dietDTOs;
	}

	@Override
	public List<PetName> getPetsByStudy(int studyId) throws ServiceExecutionException {
		LOGGER.debug("getPetsByStudy called");
		List<PetName> petNameList = lookupDao.getPetsByStudy(studyId);
		LOGGER.debug("getPetsByStudy list", petNameList.size());
		return petNameList;
	}
}
