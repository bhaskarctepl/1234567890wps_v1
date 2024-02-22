package com.hillspet.wearables.dao.lookup.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.constants.SQLConstants;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.common.utils.GCPClientUtil;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.lookup.LookupDao;
import com.hillspet.wearables.dto.AgentAction;
import com.hillspet.wearables.dto.Algorithm;
import com.hillspet.wearables.dto.AssignedUser;
import com.hillspet.wearables.dto.BfiScorer;
import com.hillspet.wearables.dto.CategoryTimer;
import com.hillspet.wearables.dto.ContactMethod;
import com.hillspet.wearables.dto.Country;
import com.hillspet.wearables.dto.CustomUserDetails;
import com.hillspet.wearables.dto.CustomerContactMethod;
import com.hillspet.wearables.dto.CustomerContactReason;
import com.hillspet.wearables.dto.DefectiveSensorAction;
import com.hillspet.wearables.dto.DeviceLocation;
import com.hillspet.wearables.dto.DeviceStatus;
import com.hillspet.wearables.dto.DietDTO;
import com.hillspet.wearables.dto.EatingEnthusiasmScale;
import com.hillspet.wearables.dto.ExtractFileCategory;
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
import com.hillspet.wearables.dto.PointTracker;
import com.hillspet.wearables.dto.PointTrackerActivity;
import com.hillspet.wearables.dto.PointTrackerMetric;
import com.hillspet.wearables.dto.PointTrackerRejectReason;
import com.hillspet.wearables.dto.PreDefinedInstruction;
import com.hillspet.wearables.dto.PreDefinedQuestion;
import com.hillspet.wearables.dto.PredefinedQuestionAnswerOption;
import com.hillspet.wearables.dto.PreludeStudy;
import com.hillspet.wearables.dto.Priority;
import com.hillspet.wearables.dto.ProductType;
import com.hillspet.wearables.dto.PushNotification;
import com.hillspet.wearables.dto.QuestionCategory;
import com.hillspet.wearables.dto.QuestionSliderType;
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
import com.hillspet.wearables.security.Authentication;

@Repository
public class LookupDaoImpl extends BaseDaoImpl implements LookupDao {

	private static final Logger LOGGER = LogManager.getLogger(LookupDaoImpl.class);

	@Autowired
	private Authentication authentication;

	@Autowired
	private GCPClientUtil gcpClientUtil;

	@Override
	public List<RoleType> getRoleTypes() throws ServiceExecutionException {
		List<RoleType> roleTypes = new ArrayList<>();
		LOGGER.debug("getRoleTypes called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_ROLE_TYPES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					RoleType roleType = new RoleType();
					// set the column values to fields like below
					roleType.setRoleTypeId(resultSet.getInt("role_type_id"));
					roleType.setRoleType(resultSet.getString("role_type"));
					roleTypes.add(roleType);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getRoleTypes ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return roleTypes;
	}

	@Override
	public List<Menu> getMenus(Integer roleTypeId) throws ServiceExecutionException {
		List<Menu> menus = new ArrayList<>();
		LOGGER.debug("getMenus called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_MENUS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					Menu menu = new Menu();
					// set the column values to fields like below
					menu.setMenuId(resultSet.getInt("menu_id"));
					menu.setMenuName(resultSet.getString("menu_name"));
					menu.setParentMenuId(resultSet.getInt("parent_menu_id"));
					menu.setParentMenuName(resultSet.getString("parent_menu_name"));
					menus.add(menu);
				}
			}, roleTypeId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getMenus ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return menus;
	}

	public List<MenuAction> getMenuActions() throws ServiceExecutionException {
		List<MenuAction> menuActions = new ArrayList<>();
		LOGGER.debug("getMenuActions called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_MENU_ACTIONS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					MenuAction menuAction = new MenuAction();
					// set the column values to fields like below
					menuAction.setMenuActionId(resultSet.getInt("menu_action_id"));
					menuAction.setMenuActionName(resultSet.getString("menu_action_name"));
					menuActions.add(menuAction);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getMenuActions ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return menuActions;
	}

	@Override
	public List<Role> getRoles() throws ServiceExecutionException {
		List<Role> roles = new ArrayList<>();
		LOGGER.debug("getRoles called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_ROLES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					Role role = new Role();
					// set the column values to fields like below
					role.setRoleId(resultSet.getInt("role_id"));
					role.setRoleName(resultSet.getString("role_name"));
					role.setRoleTypeId(resultSet.getInt("role_type_id"));
					roles.add(role);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getRoles ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return roles;
	}

	@Override
	public List<Role> getActiveRoles() throws ServiceExecutionException {
		List<Role> roles = new ArrayList<>();
		LOGGER.debug("getActiveRoles called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_ACTIVE_ROLES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					Role role = new Role();
					// set the column values to fields like below
					role.setRoleId(resultSet.getInt("role_id"));
					role.setRoleName(resultSet.getString("role_name"));
					role.setRoleTypeId(resultSet.getInt("role_type_id"));
					roles.add(role);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getRoles ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return roles;
	}

	@Override
	public List<PetBreed> getPetBreeds() throws ServiceExecutionException {
		List<PetBreed> breeds = new ArrayList<>();
		LOGGER.debug("getPetBreeds called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_PET_BREEDS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					PetBreed breed = new PetBreed();
					// set the column values to fields like below
					breed.setBreedId(resultSet.getInt("breed_id"));
					breed.setBreedName(resultSet.getString("breed_name"));
					breed.setSpeciesId(resultSet.getInt("SPECIES_ID"));
					breeds.add(breed);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetBreeds ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return breeds;
	}

	@Override
	public List<PetStatus> getPetStatuses() throws ServiceExecutionException {
		List<PetStatus> statuses = new ArrayList<>();
		LOGGER.debug("getPetStatuses called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_PET_STATUSES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					PetStatus petStatus = new PetStatus();
					// set the column values to fields like below
					petStatus.setPetStatusId(resultSet.getInt("pet_status_id"));
					petStatus.setStatusName(resultSet.getString("status_name"));
					statuses.add(petStatus);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetStatuses ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return statuses;
	}

	@Override
	public List<MobileAppConfig> getMobileAppConfigs() throws ServiceExecutionException {
		List<MobileAppConfig> mobileAppConfigs = new ArrayList<>();
		LOGGER.debug("getMobileAppConfigs called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_MOBILE_APP_CONFIGS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					MobileAppConfig mobileAppConfig = new MobileAppConfig();
					// set the column values to fields like below
					mobileAppConfig.setMobileAppConfigId(resultSet.getInt("mobile_app_config_id"));
					mobileAppConfig.setMobileAppConfigName(resultSet.getString("mobile_app_config_name"));
					mobileAppConfigs.add(mobileAppConfig);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getMobileAppConfigs ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return mobileAppConfigs;
	}

	@Override
	public List<TicketType> getTicketTypes() throws ServiceExecutionException {
		List<TicketType> ticketTypes = new ArrayList<>();
		LOGGER.debug("getTicketTypes called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_TICKET_TYPES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					TicketType ticketType = new TicketType();
					// set the column values to fields like below
					ticketType.setTicketTypeId(resultSet.getInt("ticket_type_id"));
					ticketType.setTicketTypeName(resultSet.getString("ticket_type_name"));
					ticketTypes.add(ticketType);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getTicketTypes ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return ticketTypes;
	}

	@Override
	public List<TicketPriority> getTicketPriorities() throws ServiceExecutionException {
		List<TicketPriority> ticketPriorities = new ArrayList<>();
		LOGGER.debug("getTicketPriorities called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_TICKEET_PRIORITIES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					TicketPriority ticketPriority = new TicketPriority();
					// set the column values to fields like below
					ticketPriority.setPriorityId(resultSet.getInt("priority_id"));
					ticketPriority.setPriorityName(resultSet.getString("priority_name"));
					ticketPriorities.add(ticketPriority);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getTicketPriorities ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return ticketPriorities;
	}

	@Override
	public List<TicketStatus> getTicketStatus() throws ServiceExecutionException {
		List<TicketStatus> ticketStatusList = new ArrayList<>();
		LOGGER.debug("getTicketStatus called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_TICKET_STATUS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					TicketStatus ticketstatus = new TicketStatus();
					// set the column values to fields like below
					ticketstatus.setTicketStatusId(resultSet.getInt("ticket_status_id"));
					ticketstatus.setStatusName(resultSet.getString("status_name"));
					ticketStatusList.add(ticketstatus);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getTicketStatus ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return ticketStatusList;
	}

	@Override
	public List<CustomerContactMethod> getCustomerContactMethods() throws ServiceExecutionException {
		List<CustomerContactMethod> customerContactMethods = new ArrayList<>();
		LOGGER.debug("getCustomerContactMethods called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_CUSTOMER_CONTACT_METHODS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					CustomerContactMethod customerContactMethod = new CustomerContactMethod();
					// set the column values to fields like below
					customerContactMethod.setContactMethodId(resultSet.getInt("contact_method_id"));
					customerContactMethod.setContactMethodName(resultSet.getString("contact_method_name"));
					customerContactMethods.add(customerContactMethod);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getCustomerContactMethods ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return customerContactMethods;
	}

	@Override
	public List<CustomerContactReason> getCustomerContactReasons() throws ServiceExecutionException {
		List<CustomerContactReason> customerContactReasons = new ArrayList<>();
		LOGGER.debug("getCustomerContactReasons called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_CUSTOMER_CONTACT_REASONS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					CustomerContactReason customerContactReason = new CustomerContactReason();
					// set the column values to fields like below
					customerContactReason.setContactReasonId(resultSet.getInt("contact_reason_id"));
					customerContactReason.setContactReasonName(resultSet.getString("contact_reason_name"));
					customerContactReasons.add(customerContactReason);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getCustomerContactReasons ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return customerContactReasons;
	}

	@Override
	public List<TicketCategory> getTicketCategory() throws ServiceExecutionException {
		List<TicketCategory> ticketCategories = new ArrayList<>();
		LOGGER.debug("getTicketCategory called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_TICKET_CATEGORIES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					TicketCategory ticketCategory = new TicketCategory();
					// set the column values to fields like below
					ticketCategory.setTicketCategoryId(resultSet.getInt("ticket_category_id"));
					ticketCategory.setTicketCategoryName(resultSet.getString("ticket_category_name"));
					ticketCategories.add(ticketCategory);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getTicketCategory ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return ticketCategories;
	}

	@Override
	public List<Issue> getIssues() throws ServiceExecutionException {
		List<Issue> issueList = new ArrayList<>();
		LOGGER.debug("getIssues called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_ISSUES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					Issue issue = new Issue();
					// set the column values to fields like below
					issue.setIssueId(resultSet.getInt("TICKET_CATEGORY_ID"));
					issue.setIssueName(resultSet.getString("TICKET_CATEGORY_NAME"));
					/*
					 * issue.setRootCauseId(resultSet.getInt("CONTACT_REASON_ID"));
					 * issue.setRootCauseName(resultSet.getString("CONTACT_REASON_NAME"));
					 */
					issueList.add(issue);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getIssues ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return issueList;
	}

	@Override
	public List<RootCause> getRootCause(Integer issueId) throws ServiceExecutionException {
		List<RootCause> rootCauseList = new ArrayList<>();
		LOGGER.debug("getRootCauseId called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_ROOT_CAUSE, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					RootCause rootCause = new RootCause();
					// set the column values to fields like below
					rootCause.setRootCauseId(resultSet.getInt("CONTACT_REASON_ID"));
					rootCause.setRootCauseName(resultSet.getString("CONTACT_REASON_NAME"));
					rootCauseList.add(rootCause);
				}
			}, issueId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getRootCause ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return rootCauseList;
	}

	@Override
	public List<AgentAction> getAgentAction(Integer testId) throws ServiceExecutionException {
		List<AgentAction> agentActionList = new ArrayList<>();
		LOGGER.debug("getAgentAction called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_AGENT_ACTION, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					AgentAction agentAction = new AgentAction();
					// set the column values to fields like below
					// check show defective sensor action missing
					agentAction.setAgentActionId(resultSet.getInt("AGENT_ACTION_ID"));
					agentAction.setAgentActionName(resultSet.getString("AGENT_ACTION_DESC"));
					agentAction.setShowDefectiveSensor(resultSet.getInt("IS_DEFECTIVE_ACTION"));
					agentActionList.add(agentAction);
				}
			}, testId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getAgentAction ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return agentActionList;
	}

	@Override
	public List<DefectiveSensorAction> getDefectiveSensorAction() throws ServiceExecutionException {
		List<DefectiveSensorAction> defectiveSensorActionList = new ArrayList<>();
		LOGGER.debug("getDefectiveSensorActionId called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_DEFENCIVE_SENSOR_ACTION, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					DefectiveSensorAction defectiveSensorAction = new DefectiveSensorAction();
					// set the column values to fields like below
					defectiveSensorAction.setDefectiveSensorActionId(resultSet.getInt("DEFECTIVE_SENSOR_ACTION_ID"));
					defectiveSensorAction
							.setDefectiveSensorActionName(resultSet.getString("DEFECTIVE_SENSOR_ACTION_NAME"));
					defectiveSensorActionList.add(defectiveSensorAction);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getDefectiveSensorAction ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return defectiveSensorActionList;
	}

	@Override
	public List<PetName> getPetName() throws ServiceExecutionException {
		List<PetName> petNameList = new ArrayList<>();
		int userId = authentication.getAuthUserDetails().getUserId();
		LOGGER.debug("getPetNameId called");
		LOGGER.debug("logged in user " + userId);
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_PET_NAME, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					PetName petName = new PetName();
					// set the column values to fields like below
					petName.setPetId(resultSet.getInt("PET_ID"));
					petName.setPetName(resultSet.getString("PET_NAME"));
					// petName.setPetParentId(resultSet.getInt("PET_PARENT_ID"));
					petNameList.add(petName);
				}
			}, userId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetName ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petNameList;
	}

	@Override
	public List<PetParent> getPetParentName(Integer petId) throws ServiceExecutionException {
		List<PetParent> petParentNameList = new ArrayList<>();
		LOGGER.debug("getPetParentNameId called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_PET_PARENT_NAME, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					PetParent petParentName = new PetParent();
					// set the column values to fields like below
					petParentName.setPetParentId(resultSet.getInt("PET_PARENT_ID"));
					petParentName.setPetParentName(resultSet.getString("FULL_NAME"));
					petParentName.setPetParentAddress(resultSet.getString("SHIPPING_ADDRESS"));
					petParentNameList.add(petParentName);

				}
			}, petId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetParentName ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petParentNameList;
	}

	@Override
	public List<StudyName> getStudyName(Integer petId) throws ServiceExecutionException {
		List<StudyName> studyNameList = new ArrayList<>();
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		LOGGER.debug("getStudyName called");
		LOGGER.debug("logged in user " + userDetails.getUserId());
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_STUDY_NAME, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					StudyName studyName = new StudyName();
					// set the column values to fields like below
					studyName.setStudyId(resultSet.getInt("STUDY_ID"));
					studyName.setStudyName(resultSet.getString("STUDY_NAME"));
					studyName
							.setEndDate(resultSet.getString("END_DATE") != null ? resultSet.getString("END_DATE") : "");
					studyName.setStartDate(
							resultSet.getString("START_DATE") != null ? resultSet.getString("START_DATE") : "");
					studyName.setStatus(resultSet.getBoolean("IS_ACTIVE"));
					studyNameList.add(studyName);
				}
			}, petId, userDetails.getUserId(), userDetails.getRoleTypeId());
		} catch (Exception e) {
			LOGGER.error("error while fetching getStudyName ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return studyNameList;
	}

	@Override
	public List<StudyName> getActiveStudy() throws ServiceExecutionException {
		List<StudyName> studyNameList = new ArrayList<>();
		CustomUserDetails userDetails = authentication.getAuthUserDetails();
		LOGGER.debug("getActiveStudy called");
		LOGGER.debug("logged in user " + userDetails.getUserId());
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_ACTIVE_STUDY, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					StudyName studyName = new StudyName();
					// set the column values to fields like below
					studyName.setStudyId(resultSet.getInt("STUDY_ID"));
					studyName.setStudyName(resultSet.getString("STUDY_NAME"));
					studyNameList.add(studyName);
				}
			}, userDetails.getUserId(), userDetails.getRoleTypeId());
		} catch (Exception e) {
			LOGGER.error("error while fetching getActiveStudy ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return studyNameList;
	}

	@Override
	public List<SensorName> getSensorName(Integer studyId, Integer petId) throws ServiceExecutionException {
		List<SensorName> sensorNameList = new ArrayList<>();
		int userId = authentication.getAuthUserDetails().getUserId();
		LOGGER.debug("getSensorName called");
		LOGGER.debug("logged in user " + userId);
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_SENSOR_NAME, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					SensorName sensorName = new SensorName();
					// set the column values to fields like below
					sensorName.setSensorId(resultSet.getInt("DEVICE_ID"));
					sensorName.setSensorName(resultSet.getString("DEVICE_NUMBER"));
					sensorName.setSensorStatusId(resultSet.getInt("STATUS_ID"));
					sensorName.setSensorLocationId(resultSet.getInt("DEVICE_LOCATION_ID"));
					sensorName.setSensorStatusName(resultSet.getString("STATUS_NAME"));
					sensorName.setSensorLocationName(resultSet.getString("DEVICE_LOCATION"));
					sensorName.setMfgSerialNumber(resultSet.getString("MFG_SERIAL_NUMBER"));
					sensorName.setMfgMacAddr(resultSet.getString("MFG_MAC_ADDR"));
					sensorName.setWifiMacAddr(resultSet.getString("WIFI_MAC_ADDR"));

					sensorNameList.add(sensorName);
				}
			}, studyId, petId, userId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getSensorName ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return sensorNameList;
	}

	@Override
	public List<SensorLocation> getSensorLocation() throws ServiceExecutionException {
		List<SensorLocation> sensorLocationList = new ArrayList<>();
		LOGGER.debug("getSensorLocationId called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_SENSOR_LOCATION, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					SensorLocation sensorName = new SensorLocation();
					// set the column values to fields like below
					sensorName.setStorageLocationId(resultSet.getInt("DEVICE_LOCATION_ID"));
					sensorName.setStorageLocationName(resultSet.getString("DEVICE_LOCATION"));
					sensorLocationList.add(sensorName);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getSensorLocation ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return sensorLocationList;
	}

	@Override
	public List<InventoryStatus> getInventoryStatus(Integer deviceId) throws ServiceExecutionException {
		List<InventoryStatus> inventoryStatusList = new ArrayList<>();
		LOGGER.debug("getInventoryStatusId called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_INVENTORY_STATUS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					InventoryStatus inventoryStatus = new InventoryStatus();
					// set the column values to fields like below
					inventoryStatus.setInventoryStatusId(resultSet.getInt("DEVICE_STATUS_ID"));
					inventoryStatus.setInventoryStatusName(resultSet.getString("STATUS_NAME"));
					// inventoryStatus.setType(resultSet.getString("TYPE"));
					inventoryStatusList.add(inventoryStatus);
				}
			}, deviceId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getInventoryStatusId ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return inventoryStatusList;
	}

	@Override
	public List<Priority> getPriority() throws ServiceExecutionException {
		List<Priority> priorityList = new ArrayList<>();
		LOGGER.debug("getPriority called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_PRIORITY, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					Priority priority = new Priority();
					// set the column values to fields like below
					priority.setPriorityId(resultSet.getInt("PRIORITY_ID"));
					priority.setPriorityName(resultSet.getString("PRIORITY_NAME"));
					priorityList.add(priority);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getPriority ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return priorityList;
	}

	@Override
	public List<AssignedUser> getAssignedTo() throws ServiceExecutionException {
		List<AssignedUser> assignedUserList = new ArrayList<>();
		LOGGER.debug("getAssignedTo called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_USERS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					AssignedUser assignedUser = new AssignedUser();
					// set the column values to fields like below
					assignedUser.setUserId(resultSet.getInt("USER_ID"));
					assignedUser.setUserName(resultSet.getString("USER_NAME"));
					assignedUserList.add(assignedUser);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getAssignedTo ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return assignedUserList;
	}

	@Override
	public List<Status> getStatus() throws ServiceExecutionException {
		List<Status> statusList = new ArrayList<>();
		LOGGER.debug("getStatus called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_STATUS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					Status status = new Status();
					// set the column values to fields like below
					status.setStatusId(resultSet.getInt("TICKET_STATUS_ID"));
					status.setStatusName(resultSet.getString("STATUS_NAME"));
					statusList.add(status);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getStatus ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return statusList;
	}

	@Override
	public List<ContactMethod> getContactMethod() throws ServiceExecutionException {
		List<ContactMethod> contactMethodList = new ArrayList<>();
		LOGGER.debug("getContactMethodId called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_CONTACT_METHOD, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					ContactMethod contactMethod = new ContactMethod();
					// set the column values to fields like below
					contactMethod.setContactMethodId(resultSet.getInt("CONTACT_METHOD_ID"));
					contactMethod.setContactMethodName(resultSet.getString("CONTACT_METHOD_NAME"));
					contactMethodList.add(contactMethod);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getContactMethod ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return contactMethodList;
	}

	@Override
	public List<String> getDeviceType() throws ServiceExecutionException {
		List<String> deviceTypeList = new ArrayList<>();
		LOGGER.debug("getDeviceType called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_DEVICE_TYPE, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					deviceTypeList.add(resultSet.getString("DEVICE_TYPE"));
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceType ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return deviceTypeList;
	}

	@Override
	public List<String> getDeviceModel(String deviceType) throws ServiceExecutionException {
		List<String> deviceModelList = new ArrayList<>();
		LOGGER.debug("getDeviceModel called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_DEVICE_MODEL, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					deviceModelList.add(resultSet.getString("DEVICE_MODEL"));
				}
			}, deviceType);
		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceModel ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return deviceModelList;
	}

	@Override
	public List<QuestionType> getQuestionType() throws ServiceExecutionException {
		List<QuestionType> questionTypes = new ArrayList<>();
		LOGGER.debug("getQuestionType called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_QUESTION_TYPES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					QuestionType questionType = new QuestionType();
					// set the column values to fields like below
					questionType.setQuestionTypeId(resultSet.getInt("question_type_id"));
					questionType.setQuestionType(resultSet.getString("question_type"));
					questionTypes.add(questionType);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getQuestionType ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return questionTypes;
	}

	@Override
	public List<PreDefinedInstruction> getPreDefinedInstructions() throws ServiceExecutionException {
		List<PreDefinedInstruction> preDefinedInstructions = new ArrayList<>();
		LOGGER.debug("getPreDefinedInstructions called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_PRE_DEFINED_INSTRUCTIONS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					PreDefinedInstruction preDefinedInstruction = new PreDefinedInstruction();
					// set the column values to fields like below
					preDefinedInstruction.setPreDefinedInstructionId(resultSet.getInt("INSTRUCTION_ID"));
					preDefinedInstruction.setPreDefinedInstruction(resultSet.getString("INSTRUCTION"));
					preDefinedInstructions.add(preDefinedInstruction);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getPreDefinedInstructions ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return preDefinedInstructions;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PreDefinedQuestion> getPreDefinedQuestions(String searchText) throws ServiceExecutionException {
		LOGGER.debug("getPreDefinedQuestions called");
		List<PreDefinedQuestion> preDefinedQuestions = new ArrayList<>();
		Map<Integer, List<PredefinedQuestionAnswerOption>> questionAnsOptsMap = new HashMap<>();
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_search_text", searchText);
			Map<String, Object> simpleJdbcCallResult = callStoredProcedure(SQLConstants.LU_GET_PRE_DEFINED_QUESTIONS,
					inputParams);
			Iterator<Entry<String, Object>> itr = simpleJdbcCallResult.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();

				if (key.equals(SQLConstants.RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(answerOpts -> {
						PredefinedQuestionAnswerOption ansOptions = new PredefinedQuestionAnswerOption();
						ansOptions.setQuestionAnswerId((Integer) answerOpts.get("PREDEFINED_QUESTION_ANSWER_ID"));
						ansOptions.setQuestionAnswer((String) answerOpts.get("ANSWER"));
						ansOptions.setAnsOptionMediaType((Integer) answerOpts.get("MEDIA_TYPE"));

						String fileName = (String) answerOpts.get("MEDIA_FILE_NAME");

						ansOptions.setAnsOptionMediaName(fileName);

						if (fileName != null && fileName != "null" && !fileName.trim().equals("")) {
							ansOptions.setAnsOptionMediaUrl(gcpClientUtil.getDownloaFiledUrl(fileName,
									Constants.GCP_QUESTIONNAIRE_QUESTION_IMAGE_PATH));
						}

						Integer questionId = (Integer) answerOpts.get("PREDEFINED_QUESTION_ID");
						questionAnsOptsMap
								.computeIfAbsent(questionId, k -> new ArrayList<PredefinedQuestionAnswerOption>())
								.add(ansOptions);

					});
				}

				if (key.equals(SQLConstants.RESULT_SET_2)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();

					list.forEach(u -> {
						PreDefinedQuestion preDefinedQuestion = new PreDefinedQuestion();
						QuestionSliderType other = new QuestionSliderType();
						Integer questionId = (Integer) u.get("PREDEFINED_QUESTION_ID");
						preDefinedQuestion.setPreDefinedQuestionId(questionId);
						preDefinedQuestion.setPreDefinedQuestion((String) u.get("QUESTION"));
						preDefinedQuestion.setQuestionTypeId((Integer) u.get("QUESTION_TYPE_ID"));
						preDefinedQuestion.setQuestionType((String) u.get("QUESTION_TYPE"));

						String fileName = (String) u.get("QUESTION_IMAGE");

						preDefinedQuestion.setQuestionImageName(fileName);
						if (fileName != null && fileName != "null" && !fileName.trim().equals("")) {
							preDefinedQuestion.setQuestionImageUrl(gcpClientUtil.getDownloaFiledUrl(fileName,
									Constants.GCP_QUESTIONNAIRE_QUESTION_IMAGE_PATH));
						}

						other.setCeil((Integer) u.get("SCALE_MAX"));

						other.setFloor((Integer) u.get("SCALE_MIN"));
						other.setCeilDescription((String) u.get("SCALE_MAX_DESC"));
						other.setFloorDescription((String) u.get("SCALE_MIN_DESC"));
						other.setTickStep((Integer) u.get("STEP_VALUE"));
						other.setIsVerticalScale(u.get("IS_VERTICAL_SCALE") != null
								&& (Integer) u.get("IS_VERTICAL_SCALE") > NumberUtils.INTEGER_ZERO ? Boolean.TRUE
										: Boolean.FALSE);
						other.setIsContinuousScale(u.get("IS_CONTINUOUS_SCALE") != null
								&& (Integer) u.get("IS_CONTINUOUS_SCALE") > NumberUtils.INTEGER_ZERO ? Boolean.TRUE
										: Boolean.FALSE);

						preDefinedQuestion.setOther(other);
						preDefinedQuestion.setQuestionAnswerOptions(
								questionAnsOptsMap.get(questionId) != null ? questionAnsOptsMap.get(questionId)
										: new ArrayList<>());
						preDefinedQuestions.add(preDefinedQuestion);
					});
				}

			}
		} catch (Exception e) {
			LOGGER.error("error while fetching getPreDefinedQuestions", e);
			throw new ServiceExecutionException(e.getMessage());
		}

		return preDefinedQuestions;
	}

	@Override
	public List<QuestionnaireListDTO> getQuestionnaires(int typeId) throws ServiceExecutionException {
		List<QuestionnaireListDTO> questionnaireList = new ArrayList<>();
		try {
			String sql = SQLConstants.QUESTIONNAIRE_GET_ALL_LIST;
			jdbcTemplate.query(sql, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					QuestionnaireListDTO questionnaire = new QuestionnaireListDTO();
					questionnaire.setQuestionnaireId(rs.getInt("QUESTIONNAIRE_ID"));
					questionnaire.setQuestionnaireName(rs.getString("QUESTIONNAIRE_NAME"));
					questionnaire.setStartDate(rs.getDate("START_DATE").toLocalDate());
					questionnaire.setEndDate(rs.getDate("END_DATE").toLocalDate());
					questionnaire.setIsActive(rs.getBoolean("IS_ACTIVE"));
					questionnaire.setIsPublished(rs.getBoolean("IS_PUBLISHED"));
					questionnaireList.add(questionnaire);
				}
			}, typeId);
		} catch (Exception e) {
			LOGGER.error("error while executing getQuestionnaires ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return questionnaireList;
	}

	@Override
	public List<QuestionnaireListDTO> getPetQuestionnaires(int petId) throws ServiceExecutionException {
		List<QuestionnaireListDTO> questionnaireList = new ArrayList<>();
		try {
			String sql = SQLConstants.QUESTIONNAIRE_GET_BY_PET;
			jdbcTemplate.query(sql, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					QuestionnaireListDTO questionnaire = new QuestionnaireListDTO();
					questionnaire.setQuestionnaireId(rs.getInt("QUESTIONNAIRE_ID"));
					questionnaire.setQuestionnaireName(rs.getString("QUESTIONNAIRE_NAME"));
					questionnaire.setStartDate(rs.getDate("START_DATE").toLocalDate());
					questionnaire.setEndDate(rs.getDate("END_DATE").toLocalDate());
					questionnaire.setIsActive(rs.getBoolean("IS_ACTIVE"));
					questionnaire.setIsPublished(rs.getBoolean("IS_PUBLISHED"));
					questionnaireList.add(questionnaire);
				}
			}, petId);
		} catch (Exception e) {
			LOGGER.error("error while executing getPetQuestionnaires ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return questionnaireList;
	}

	@Override
	public List<PointTrackerActivity> getPointTrackerActivities() throws ServiceExecutionException {
		List<PointTrackerActivity> pointTrackerActivities = new ArrayList<>();
		LOGGER.debug("getPointTrackerActivities called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_POINT_TRACKER_ACTIVITY, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					PointTrackerActivity pointTrackerActivity = new PointTrackerActivity();
					// set the column values to fields like below
					if (resultSet.getInt("ACTIVITY_ID") != 4) {
						pointTrackerActivity.setActivityId(resultSet.getInt("ACTIVITY_ID"));
						pointTrackerActivity.setActivityName(resultSet.getString("ACTIVITY_NAME"));
						pointTrackerActivities.add(pointTrackerActivity);
					}
				}
			});
			PointTrackerActivity pointTrackerActivity = new PointTrackerActivity();
			pointTrackerActivity.setActivityId(4);
			pointTrackerActivity.setActivityName("Videos");
			pointTrackerActivities.add(pointTrackerActivity);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPointTrackerActivities ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return pointTrackerActivities;
	}

	@Override
	public List<DeviceLocation> getDeviceLocations() throws ServiceExecutionException {
		List<DeviceLocation> deviceLocations = new ArrayList<>();
		LOGGER.debug("getDeviceLocations called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_DEVICE_LOCATIONS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					DeviceLocation deviceLocation = new DeviceLocation();
					// set the column values to fields like below
					if (resultSet.getInt("DEVICE_LOCATION_ID") != 4) {
						deviceLocation.setLocationId(resultSet.getInt("DEVICE_LOCATION_ID"));
						deviceLocation.setLocation(resultSet.getString("DEVICE_LOCATION"));
						deviceLocations.add(deviceLocation);
					}
				}
			});
			DeviceLocation deviceLocation = new DeviceLocation();
			deviceLocation.setLocationId(4);
			deviceLocation.setLocation("Other Storage Location");
			deviceLocations.add(deviceLocation);
		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceLocations ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return deviceLocations;
	}

	@Override
	public List<DeviceStatus> getDeviceStatuses(String showOnlyStudyStatuses) throws ServiceExecutionException {
		List<DeviceStatus> deviceStatusList = new ArrayList<>();
		LOGGER.debug("getDeviceStatuses called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_DEVICE_STATUSES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					DeviceStatus deviceStatuses = new DeviceStatus();
					// set the column values to fields like below
					deviceStatuses.setDeviceStatusId(resultSet.getInt("DEVICE_STATUS_ID"));
					deviceStatuses.setStatusName(resultSet.getString("STATUS_NAME"));
					deviceStatusList.add(deviceStatuses);
				}
			}, showOnlyStudyStatuses);
		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceStatuses ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return deviceStatusList;
	}

	@Override
	public List<MobileAppFeedbackPage> getMobileAppFeedbackPages() throws ServiceExecutionException {
		List<MobileAppFeedbackPage> mobileAppFeedbackPageList = new ArrayList<>();
		LOGGER.debug("getDeviceStatuses called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_MOBILE_APP_FEEDBACK_PAGES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					MobileAppFeedbackPage mobileAppFeedbackPages = new MobileAppFeedbackPage();
					// set the column values to fields like below
//					mobileAppFeedbackPages.setPageName(resultSet.getString("PAGE_NAME") == null ? "" : resultSet.getString("PAGE_NAME"));
					if (StringUtils.isNotBlank(resultSet.getString("PAGE_NAME"))) {
						mobileAppFeedbackPages.setPageName(resultSet.getString("PAGE_NAME"));
						mobileAppFeedbackPageList.add(mobileAppFeedbackPages);
					}
//					mobileAppFeedbackPageList.add(mobileAppFeedbackPages);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceStatuses ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return mobileAppFeedbackPageList;
	}

	@Override
	public List<MobileAppFBPhoneModel> getMobileAppFeedbackPhoneModels() throws ServiceExecutionException {
		List<MobileAppFBPhoneModel> mobileAppFeedbackPageList = new ArrayList<>();
		LOGGER.debug("getDeviceStatuses called");
		try {
			jdbcTemplate.query("call LU_GET_MOBILE_APP_PHONE_MODEL()", new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					MobileAppFBPhoneModel mobileAppFeedbackPages = new MobileAppFBPhoneModel();
					// set the column values to fields like below
					if (StringUtils.isNotBlank(resultSet.getString("PHONE_MODEL"))) {
						mobileAppFeedbackPages.setPhoneModel(resultSet.getString("PHONE_MODEL"));
						mobileAppFeedbackPageList.add(mobileAppFeedbackPages);
					}
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceStatuses ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return mobileAppFeedbackPageList;
	}

	@Override
	public List<PointTrackerMetric> getPetBehaviors(int speciesId, int behaviorTypeId)
			throws ServiceExecutionException {
		List<PointTrackerMetric> pointTrackerMetrics = new ArrayList<>();
		LOGGER.debug("getPetBehaviors called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_PET_BEHAVIORS_BY_SPECIES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					PointTrackerMetric pointTrackerMetric = new PointTrackerMetric();
					// set the column values to fields like below
					pointTrackerMetric.setMetricId(resultSet.getInt("METRIC_ID"));
					pointTrackerMetric.setMetricName(resultSet.getString("METRIC_NAME"));
					pointTrackerMetrics.add(pointTrackerMetric);
				}
			}, speciesId, behaviorTypeId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetBehaviors ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return pointTrackerMetrics;
	}

	@Override
	public List<PointTrackerMetric> getPointTrackerMetrics(int petId) throws ServiceExecutionException {
		List<PointTrackerMetric> pointTrackerMetrics = new ArrayList<>();
		LOGGER.debug("getPointTrackerActivities called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_POINT_TRACKER_METRICS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					PointTrackerMetric pointTrackerMetric = new PointTrackerMetric();
					// set the column values to fields like below
					pointTrackerMetric.setMetricId(resultSet.getInt("METRIC_ID"));
					pointTrackerMetric.setMetricName(resultSet.getString("METRIC_NAME"));
					pointTrackerMetrics.add(pointTrackerMetric);
				}
			}, petId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPointTrackerActivities ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return pointTrackerMetrics;
	}

	@Override
	public List<PointTrackerMetric> getPointTrackerMetricsById(int trackerPetPointsId)
			throws ServiceExecutionException {
		List<PointTrackerMetric> pointTrackerMetrics = new ArrayList<>();
		LOGGER.debug("getPointTrackerActivities called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_POINT_TRACKER_METRICS_BY_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					PointTrackerMetric pointTrackerMetric = new PointTrackerMetric();
					// set the column values to fields like below
					pointTrackerMetric.setMetricId(resultSet.getInt("METRIC_ID"));
					pointTrackerMetric.setMetricName(resultSet.getString("METRIC_NAME"));
					pointTrackerMetrics.add(pointTrackerMetric);
				}
			}, trackerPetPointsId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPointTrackerActivities", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return pointTrackerMetrics;
	}

	@Override
	public List<PointTrackerRejectReason> getTrackerRejectReasons() throws ServiceExecutionException {

		List<PointTrackerRejectReason> pointTrackerRejectReasonList = new ArrayList<>();
		LOGGER.debug("getTrackerRejectReasons called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_RACKER_REJECT_REASONS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					PointTrackerRejectReason pointTrackerRejectReason = new PointTrackerRejectReason();
					// set the column values to fields like below
					pointTrackerRejectReason.setTrackerRejectReasonId(resultSet.getInt("TRACKER_REJECT_REASONS_ID"));
					pointTrackerRejectReason.setReason(resultSet.getString("REASON"));
					pointTrackerRejectReasonList.add(pointTrackerRejectReason);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getTrackerRejectReasons ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return pointTrackerRejectReasonList;

	}

	@Override
	public List<StudyNameFilter> getAllStudyName() throws ServiceExecutionException {
		List<StudyNameFilter> studyNameList = new ArrayList<>();
		LOGGER.debug("getAllStudyName called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_ALL_STUDY_NAME, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					StudyNameFilter studyName = new StudyNameFilter();
					// set the column values to fields like below
					studyName.setStudyId(resultSet.getInt("STUDY_ID"));
					studyName.setStudyName(resultSet.getString("STUDY_NAME"));
					studyNameList.add(studyName);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getAllStudyName ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return studyNameList;
	}

	@Override
	public List<StudyNameFilter> getAllActiveStudyName() throws ServiceExecutionException {
		List<StudyNameFilter> studyNameList = new ArrayList<>();
		LOGGER.debug("getAllActiveStudyName called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_ACTIVE_STUDY_NAME, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					StudyNameFilter studyName = new StudyNameFilter();
					// set the column values to fields like below
					studyName.setStudyId(resultSet.getInt("STUDY_ID"));
					studyName.setStudyName(resultSet.getString("STUDY_NAME"));
					studyNameList.add(studyName);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getAllActiveStudyName ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return studyNameList;
	}

	@Override
	public List<PetSpecies> getPetSpecies() throws ServiceExecutionException {
		List<PetSpecies> speciesList = new ArrayList<>();
		LOGGER.debug("getPetSpecies called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_SPECIES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetSpecies species = new PetSpecies();
					// set the column values to fields like below
					species.setSpeciesId(rs.getInt("SPECIES_ID"));
					species.setSpeciesName(rs.getString("SPECIES_NAME"));
					speciesList.add(species);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetSpecies ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return speciesList;
	}

	@Override
	public List<DeviceLocation> getDeviceLocationsBulkUpload() throws ServiceExecutionException {
		List<DeviceLocation> deviceLocations = new ArrayList<>();
		LOGGER.debug("getDeviceLocations called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_DEVICE_LOCATIONS_BULK_UPLOAD, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					DeviceLocation deviceLocation = new DeviceLocation();
					// set the column values to fields like below
					deviceLocation.setLocationId(resultSet.getInt("DEVICE_LOCATION_ID"));
					deviceLocation.setLocation(resultSet.getString("DEVICE_LOCATION"));
					deviceLocations.add(deviceLocation);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceLocations ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return deviceLocations;
	}

	@Override
	public List<TicketAssignedUser> getAssignedToUsers() throws ServiceExecutionException {
		List<TicketAssignedUser> assignedUserList = new ArrayList<>();
		LOGGER.debug("getAssignedTo called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_USERS_ASSIGNED_TO_TICKET, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					TicketAssignedUser assignedUser = new TicketAssignedUser();
					// set the column values to fields like below
					assignedUser.setUserId(resultSet.getInt("USER_ID"));
					assignedUser.setUserName(resultSet.getString("USER_NAME"));
					assignedUserList.add(assignedUser);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getAssignedTo ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return assignedUserList;
	}

	@Override
	public List<PetParentNameTimer> getPetParentNameTimer() throws ServiceExecutionException {
		List<PetParentNameTimer> petParentNameList = new ArrayList<>();
		LOGGER.debug("getPetParentNameTimer called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_PET_PARENT_NAME_TIMER, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					PetParentNameTimer petParentNameTimer = new PetParentNameTimer();
					// set the column values to fields like below
					petParentNameTimer.setPetParentId(resultSet.getInt("PET_PARENT_ID"));
					petParentNameTimer.setPetParentName(resultSet.getString("PET_PARENT_NAME"));
					petParentNameList.add(petParentNameTimer);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetParentNameTimer ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petParentNameList;
	}

	@Override
	public List<PetNameTimer> getPetNameTimer() throws ServiceExecutionException {
		List<PetNameTimer> petNameTimerList = new ArrayList<>();
		LOGGER.debug("getPetNameTimer called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_PET_NAME_TIMER, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					PetNameTimer petNameTimer = new PetNameTimer();
					// set the column values to fields like below
					petNameTimer.setPetId(resultSet.getInt("PET_ID"));
					petNameTimer.setPetName(resultSet.getString("PET_NAME"));
					petNameTimerList.add(petNameTimer);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetNameTimer ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petNameTimerList;
	}

	@Override
	public List<CategoryTimer> getCategoryNameTimer() throws ServiceExecutionException {
		List<CategoryTimer> categoryTimerList = new ArrayList<>();
		LOGGER.debug("getCategoryNameTimer called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_CATEGORY_TIMER, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					CategoryTimer categoryTimer = new CategoryTimer();
					// set the column values to fields like below
					categoryTimer.setCategoryName(resultSet.getString("CATEGORY"));
					categoryTimerList.add(categoryTimer);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getCategoryNameTimer ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return categoryTimerList;
	}

	@Override
	public List<PreludeStudy> getPreludeStudyName() throws ServiceExecutionException {
		List<PreludeStudy> preludeStudyList = new ArrayList<>();
		LOGGER.debug("getPreludeStudyName called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_PRELUDE_STUDY, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					PreludeStudy preludeStudy = new PreludeStudy();
					// set the column values to fields like below
					preludeStudy.setStudyId(resultSet.getInt("STUDY_ID"));
					preludeStudy.setStudyName(resultSet.getString("STUDY_NAME"));
					preludeStudyList.add(preludeStudy);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getCategoryNameTimer ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return preludeStudyList;
	}

	@Override
	public List<ExtractFileCategory> getExtractFileCategory(String studyId) throws ServiceExecutionException {
		List<ExtractFileCategory> extractFileCategoryList = new ArrayList<>();
		LOGGER.debug("getExtractFileCategory called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_EXTRENAL_FILE_CATEGORY, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					ExtractFileCategory extractFileCategory = new ExtractFileCategory();
					// set the column values to fields like below
					extractFileCategory.setExtractFileCategory(resultSet.getString("EXTRACT_FILE_CATEGORY"));
					extractFileCategoryList.add(extractFileCategory);
				}
			}, studyId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getCategoryNameTimer ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return extractFileCategoryList;
	}

	@Override
	public List<PointTracker> getCampaignList() throws ServiceExecutionException {
		List<PointTracker> pointTrackerList = new ArrayList<>();
		LOGGER.debug("getCampaignList called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_ALL_CAMPAIGNS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PointTracker pointTracker = new PointTracker();
					pointTracker.setPointTrackerId(rs.getInt("POINT_TRACKER_ID"));
					pointTracker.setTrackerName(rs.getString("TRACKER_NAME"));

					Date startDate = (Date) rs.getDate("START_DATE");
					pointTracker.setStartDate((startDate.toLocalDate()));

					Date endDate = (Date) rs.getDate("END_DATE");
					pointTracker.setEndDate((endDate.toLocalDate()));
					pointTracker.setStatus((rs.getInt("STATUS")));

					pointTrackerList.add(pointTracker);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getCampaignList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return pointTrackerList;
	}

	@Override
	public List<Occurance> getQuestionnaireOccurances(String showStudyStartUp) throws ServiceExecutionException {
		List<Occurance> occurances = new ArrayList<>();
		LOGGER.debug("getQuestionnaireOccurances called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_QUESTIONNIRE_OCCURANCES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					Occurance occurance = new Occurance();
					// set the column values to fields like below
					occurance.setOccuranceId(resultSet.getInt("OCCURANCE_ID"));
					occurance.setOccuranceName(resultSet.getString("OCCURANCE_NAME"));
					occurances.add(occurance);
				}
			}, showStudyStartUp);
		} catch (Exception e) {
			LOGGER.error("error while fetching getQuestionnaireOccurances ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return occurances;
	}

	@Override
	public List<Frequency> getQuestionnaireFrequencies(String showStudyPhaseDays) throws ServiceExecutionException {
		List<Frequency> frequencies = new ArrayList<>();
		LOGGER.debug("getQuestionnaireFrequencies called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_QUESTIONNIRE_FREQUENCIES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					Frequency frequency = new Frequency();
					// set the column values to fields like below
					frequency.setFrequencyId(resultSet.getInt("FREQUENCY_ID"));
					frequency.setFrequencyName(resultSet.getString("FREQUENCY_NAME"));
					frequencies.add(frequency);
				}
			}, showStudyPhaseDays);
		} catch (Exception e) {
			LOGGER.error("error while fetching getQuestionnaireFrequencies ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return frequencies;
	}

	@Override
	public List<PushNotification> getStudyPushNotifications(String searchText) throws ServiceExecutionException {
		List<PushNotification> StudyPushNotificationList = new ArrayList<>();
		try {
			String sql = SQLConstants.STUDY_PUSH_NOTIFICATION_GET_ALL_LIST;
			jdbcTemplate.query(sql, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PushNotification studyPushNotification = new PushNotification();
					studyPushNotification.setNotificationId(rs.getInt("PUSH_NOTIFICATION_ID"));
					studyPushNotification.setNotificationName(rs.getString("NOTIFICATION_NAME"));
					StudyPushNotificationList.add(studyPushNotification);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while executing getQuestionnaires ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return StudyPushNotificationList;
	}

	public List<MaterialType> getMaterialTypeList() throws ServiceExecutionException {
		LOGGER.debug("getMaterialTypeList called");
		List<MaterialType> mList = new ArrayList<>();
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_MATERIAL_TYPE, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					MaterialType materialType = new MaterialType();
					materialType.setMaterialTypeId(rs.getInt("MATERIAL_TYPE_ID"));
					materialType.setMaterialTypeName(rs.getString("MATERIAL_TYPE_NAME"));

					mList.add(materialType);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getMaterialTypeList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return mList;
	}

	@Override
	public List<MaterialCategory> getMaterialCategoryList() throws ServiceExecutionException {
		LOGGER.debug("getMaterialTypeList called");
		List<MaterialCategory> cList = new ArrayList<>();
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_MATERIAL_CATEGORY, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					MaterialCategory materialCategory = new MaterialCategory();
					materialCategory.setCategoryId(rs.getInt("MATERIAL_CATEGORY_ID"));
					materialCategory.setCategoryName(rs.getString("MATERIAL_CATEGORY_NAME"));
					materialCategory.setParentId(rs.getInt("PARENT_ID"));

					cList.add(materialCategory);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getMaterialTypeList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return cList;
	}

	@Override
	public List<EatingEnthusiasmScale> getPetEatingEnthusiasmScales(String speciesId) throws ServiceExecutionException {
		List<EatingEnthusiasmScale> eatingEnthusiasmScales = new ArrayList<>();
		LOGGER.debug("getPetEatingEnthusiasmScale called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_ENTHUSIASM_SCALES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					EatingEnthusiasmScale eatingEnthusiasmScale = new EatingEnthusiasmScale();
					eatingEnthusiasmScale.setEnthusiasmScaleId(rs.getInt("ENTHUSIASM_SCALE_ID"));
					eatingEnthusiasmScale.setEnthusiasmScaleValue(rs.getString("ENTHUSIASM_SCALE"));
					eatingEnthusiasmScales.add(eatingEnthusiasmScale);
				}
			}, speciesId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetEatingEnthusiasmScale", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return eatingEnthusiasmScales;
	}

	@Override
	public List<PetFeedingTime> getPetFeedingTimes() throws ServiceExecutionException {
		List<PetFeedingTime> petFeedingTimes = new ArrayList<>();
		LOGGER.debug("getPetFeedingTime called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_PET_FEEDING_TIMES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetFeedingTime petFeedingTime = new PetFeedingTime();
					petFeedingTime.setFeedingTimeId(rs.getInt("PET_FEEDING_TIME_ID"));
					petFeedingTime.setFeedingTime(rs.getString("FEEDING_VALUE"));
					petFeedingTimes.add(petFeedingTime);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetFeedingTime ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petFeedingTimes;
	}

	@Override
	public List<ImageScoringType> getImageScoringTypes() throws ServiceExecutionException {
		List<ImageScoringType> imageScoringTypes = new ArrayList<>();
		LOGGER.debug("getImageScoringTypes called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_IMAGE_SCORING_TYPES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					ImageScoringType imageScoringType = new ImageScoringType();
					imageScoringType.setImageScoringTypeId(rs.getInt("SCORING_TYPE_ID"));
					imageScoringType.setImageScoringType(rs.getString("SCORING_TYPE"));
					imageScoringTypes.add(imageScoringType);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getImageScoringTypes", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return imageScoringTypes;
	}

	@Override
	public List<ShipmentCompany> getShipmentCompanies() throws ServiceExecutionException {
		List<ShipmentCompany> shipmentCompanies = new ArrayList<>();
		LOGGER.debug("getShipmentCompanies called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_SHIPMENT_COMPANIES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					ShipmentCompany ShipmentCompany = new ShipmentCompany();
					ShipmentCompany.setShipmentCompanyId(rs.getInt("SHIPMENT_COMPANY_ID"));
					ShipmentCompany.setShipmentCompanyName(rs.getString("SHIPMENT_COMPANY_NAME"));
					ShipmentCompany.setTrackingUrl(rs.getString("TRACKING_URL"));
					shipmentCompanies.add(ShipmentCompany);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getShipmentCompanies", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return shipmentCompanies;
	}

	@Override
	public List<QuestionnaireType> getQuestionnaireTypes(String showPetQuestionnaire) throws ServiceExecutionException {
		List<QuestionnaireType> questionnaireTypes = new ArrayList<>();
		LOGGER.debug("getQuestionnaireTypes called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_QUESTIONNAIRE_TYPES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					QuestionnaireType questionnaireType = new QuestionnaireType();
					questionnaireType.setQuestionnaireTypeId(rs.getInt("QUESTIONNAIRE_TYPE_ID"));
					questionnaireType.setQuestionnaireType(rs.getString("QUESTIONNAIRE_TYPE"));
					questionnaireTypes.add(questionnaireType);
				}
			}, showPetQuestionnaire);
		} catch (Exception e) {
			LOGGER.error("error while fetching getQuestionnaireTypes", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return questionnaireTypes;
	}

	@Override
	public List<QuestionnaireCategory> getQuestionnaireCategory(Integer questionnaireTypeId)
			throws ServiceExecutionException {
		List<QuestionnaireCategory> questionnaireCategories = new ArrayList<>();
		LOGGER.debug("getQuestionnaireCategory called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_QUESTIONNAIRE_CATEGORY, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					QuestionnaireCategory questionnaireCategory = new QuestionnaireCategory();
					questionnaireCategory.setQuestionnaireCategoryId(rs.getInt("QUESTIONNAIRE_CATEGORY_ID"));
					questionnaireCategory.setQuestionnaireCategory(rs.getString("QUESTIONNAIRE_CATEGORY"));
					questionnaireCategory.setQuestionnaireTypeId(rs.getInt("QUESTIONNAIRE_TYPE_ID"));
					questionnaireCategories.add(questionnaireCategory);
				}
			}, questionnaireTypeId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getQuestionnaireCategory", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return questionnaireCategories;
	}

	@Override
	public TimeZone getTimeZoneDetails(String timeZoneName) throws ServiceExecutionException {
		TimeZone timeZone = new TimeZone();
		LOGGER.debug("getTimeZoneDetails called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_TIMEZONE_BY_NAME, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					timeZone.setTimeZoneId(rs.getInt("TIMEZONE_ID"));
					timeZone.setTimeZoneName(rs.getString("STANDARD_NAME"));
					timeZone.setIsSupportDST(rs.getInt("IS_SUPPORTS_DAY_LIGHT_SAVING_TIME"));
				}
			}, timeZoneName);
		} catch (Exception e) {
			LOGGER.error("error while fetching getTimeZoneDetails", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return timeZone;
	}

	@Override
	public List<Algorithm> getAlgorithmList() throws ServiceExecutionException {
		List<Algorithm> algorithms = new ArrayList<>();
		LOGGER.debug("getAlgorithmList called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_ALGORITHMS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					Algorithm algorithm = new Algorithm();
					// set the column values to fields like below
					algorithm.setAlgorithmId(resultSet.getInt("ALGORITHM_MASTER_ID"));
					algorithm.setAlgorithmName(resultSet.getString("ALGORITHM_NAME"));
					algorithms.add(algorithm);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getAlgorithmList ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return algorithms;
	}

	@Override
	public List<QuestionCategory> getQuestionCategory() throws ServiceExecutionException {
		List<QuestionCategory> questionCategories = new ArrayList<>();
		LOGGER.debug("getQuestionCategory called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_QUESTION_CATEGORY, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					QuestionCategory questionCategory = new QuestionCategory();
					questionCategory.setQuestionCategoryId(rs.getInt("QUESTION_CATEGORY_ID"));
					questionCategory.setQuestionCategory(rs.getString("QUESTION_CATEGORY"));
					questionCategories.add(questionCategory);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getQuestionCategory", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return questionCategories;
	}

	@Override
	public List<QuestionValidityPeriod> getQuestionValidityPeriods() throws ServiceExecutionException {
		List<QuestionValidityPeriod> questionValidityPeriods = new ArrayList<>();
		LOGGER.debug("getQuestionCategory called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_QUESTION_VALIDITY_PERIODS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					QuestionValidityPeriod validityPeriod = new QuestionValidityPeriod();
					validityPeriod.setValidityPeriodId(rs.getInt("QUESTION_VALIDITY_PERIOD_ID"));
					validityPeriod.setValidityDescription(rs.getString("VALIDITY_DESCRIPTION"));
					validityPeriod.setNoOfDays(rs.getInt("NO_OF_DAYS"));
					questionValidityPeriods.add(validityPeriod);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getQuestionValidityPeriods", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return questionValidityPeriods;
	}

	@Override
	public List<Country> getCountryListy() throws ServiceExecutionException {
		List<Country> countries = new ArrayList<>();
		LOGGER.debug("getCountryListy called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_COUNTRIES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					Country country = new Country();
					country.setCountryId(rs.getInt("COUNTRY_ID"));
					country.setCountryName(rs.getString("COUNTRY_NAME"));
					countries.add(country);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getCountryListy", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return countries;
	}

	@Override
	public List<BfiScorer> getbfiScorerList() throws ServiceExecutionException {
		List<BfiScorer> bfiScorerList = new ArrayList<>();
		LOGGER.debug("getbfiScorerList called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_BFI_SCORER_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					BfiScorer bfiScorer = new BfiScorer();
					bfiScorer.setScorerId(rs.getInt("SCORER_ID"));
					bfiScorer.setScorerName(rs.getString("SCORER_NAME"));
					bfiScorerList.add(bfiScorer);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getbfiScorerList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return bfiScorerList;
	}

	public List<MeasurementUnit> getMeasurementUnits(String unitCategory) throws ServiceExecutionException {
		List<MeasurementUnit> measurementUnits = new ArrayList<>();
		LOGGER.debug("getMeasurementUnits called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_MEASUREMENT_UNITS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					MeasurementUnit measurementUnit = new MeasurementUnit();
					measurementUnit.setUnitId(rs.getInt("UNIT_ID"));
					measurementUnit.setUnitCategory(rs.getString("UNIT_CATEGORY"));
					measurementUnit.setUnit(rs.getString("UNIT"));
					measurementUnit.setUnitAbbrevation(rs.getString("UNIT_ABBREVATION"));
					measurementUnits.add(measurementUnit);
				}
			}, unitCategory);
		} catch (Exception e) {
			LOGGER.error("error while fetching getMeasurementUnits", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return measurementUnits;
	}

	@Override
	public List<Phase> getStudyPhases() throws ServiceExecutionException {
		List<Phase> studyPhases = new ArrayList<>();
		LOGGER.debug("getStudyPhases called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_STUDY_PHASES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					Phase phase = new Phase();
					phase.setStudyPhaseId(rs.getInt("STUDY_PHASE_ID"));
					phase.setStudyPhase(rs.getString("STUDY_PHASE"));
					phase.setDuration(rs.getInt("DURATION"));
					phase.setDurationUnitId(rs.getInt("DURATION_UNIT_ID"));
					studyPhases.add(phase);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getStudyPhases", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return studyPhases;
	}

	@Override
	public List<StudyStatus> getStudyStatus() throws ServiceExecutionException {
		List<StudyStatus> studyStatues = new ArrayList<>();
		LOGGER.debug("getStudyStatus called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_STUDY_STATUS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					StudyStatus status = new StudyStatus();
					status.setStudyStatusId(rs.getInt("STUDY_STATUS_ID"));
					status.setStudyStatus(rs.getString("STUDY_STATUS"));
					studyStatues.add(status);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getStudyStatus", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return studyStatues;
	}

	public List<ProductType> getProductTypeList() throws ServiceExecutionException {
		List<ProductType> productTypeList = new ArrayList<>();
		LOGGER.debug("getProductTypeList called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_PRODUCT_TYPES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					ProductType productType = new ProductType();
					productType.setProductTypeId(rs.getInt("PRODUCT_TYPE_ID"));
					productType.setProductTypeName(rs.getString("PRODUCT_TYPE"));
					productTypeList.add(productType);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getProductTypeList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return productTypeList;
	}

	public List<StudyLocation> getStudyLocations() throws ServiceExecutionException {
		List<StudyLocation> studyLocations = new ArrayList<>();
		LOGGER.debug("getStudyLocations called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_STUDY_LOCATION_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					StudyLocation studyLocation = new StudyLocation();
					studyLocation.setStudyLocationId(rs.getInt("STUDY_LOCATION_ID"));
					studyLocation.setStudyLocation(rs.getString("STUDY_LOCATION"));
					studyLocations.add(studyLocation);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getStudyLocations", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return studyLocations;
	}

	@Override
	public List<MeasurementUnit> getStudyPhaseDurationUnits() throws ServiceExecutionException {
		List<MeasurementUnit> measurementUnits = new ArrayList<>();
		LOGGER.debug("getStudyPhaseDurationUnits called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_STUDY_PHASE_DURATION_UNITS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					MeasurementUnit measurementUnit = new MeasurementUnit();
					measurementUnit.setUnitId(rs.getInt("UNIT_ID"));
					measurementUnit.setUnit(rs.getString("UNIT"));
					measurementUnits.add(measurementUnit);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getStudyPhaseDurationUnits", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return measurementUnits;
	}

	@Override
	public List<DietDTO> getDiets() throws ServiceExecutionException {
		List<DietDTO> dietDTOs = new ArrayList<>();
		LOGGER.debug("getDiets called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_DIETS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					DietDTO dietDTO = new DietDTO();
					dietDTO.setDietId(rs.getInt("DIET_ID"));
					dietDTO.setDietNumber(rs.getString("DIET_NUMBER"));
					dietDTO.setDietName(rs.getString("DIET_NAME"));
					dietDTOs.add(dietDTO);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getDiets", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return dietDTOs;
	}

	@Override
	public List<PetName> getPetsByStudy(int studyId) throws ServiceExecutionException {
		List<PetName> petNameList = new ArrayList<>();
		LOGGER.debug("getPetsByStudy called");
		try {
			jdbcTemplate.query(SQLConstants.LU_GET_PET_NAME_BY_STUDY, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					PetName petName = new PetName();
					petName.setPetId(resultSet.getInt("PET_ID"));
					petName.setPetName(resultSet.getString("PET_NAME"));
					petNameList.add(petName);
				}
			}, studyId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetsByStudy ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petNameList;
	}
}
