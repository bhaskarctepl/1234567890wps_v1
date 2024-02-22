
/**
 * Created Date: 08-Dec-2020
 * Class Name  : SupportDaoImpl.java
 * Description : Description of the package.
 *
 * © Copyright 2020 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>      <Author>        <Date>        <Comments on Change>
 * ID                sgorle          08-Dec-2020   Mentions the comments on change, for the new file it's not required.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.hillspet.wearables.dao.support.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.constants.SQLConstants;
import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.common.utils.GCPClientUtil;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.support.SupportDao;
import com.hillspet.wearables.dto.CustomerSupport;
import com.hillspet.wearables.dto.CustomerSupportDetails;
import com.hillspet.wearables.dto.CustomerSupportHistory;
import com.hillspet.wearables.dto.SupportListDTO;
import com.hillspet.wearables.dto.filter.SupportFilter;
import com.hillspet.wearables.request.CustomerSupportRequest;
import com.hillspet.wearables.request.CustomerSupportUpdateRequest;

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
@Repository
public class SupportDaoImpl extends BaseDaoImpl implements SupportDao {

	private static final Logger LOGGER = LogManager.getLogger(SupportDaoImpl.class);

	@Autowired
	private GCPClientUtil gcpClientUtil;

	@Autowired
	private ObjectMapper mapper;

	@Override
	@Transactional
	public void addCustomerSupport(CustomerSupportRequest customerSupportRequest, Integer userId)
			throws ServiceExecutionException {
		// Timestamp now = Timestamp.from(Instant.now());
		Timestamp now = jdbcTemplate.queryForObject(SQLConstants.CUSTOMER_SUPPORT_GET_NOW, Timestamp.class);
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_ticket_title", customerSupportRequest.getTicketTitle());
		inputParams.put("p_assigned_to", customerSupportRequest.getAssignedTo());
		inputParams.put("p_priority_id", customerSupportRequest.getPriorityId());
		inputParams.put("p_status_id", customerSupportRequest.getStatusId());
		inputParams.put("p_pet_id", customerSupportRequest.getPetName());
		inputParams.put("p_pet_parent_id", customerSupportRequest.getPetParentName());
		inputParams.put("p_contact_method_id", customerSupportRequest.getContactMethod());
		inputParams.put("p_pet_parent_address", customerSupportRequest.getPetParentAddress());
		inputParams.put("p_study_id", customerSupportRequest.getStudyId());
		inputParams.put("p_issue_id", customerSupportRequest.getIssue());
		inputParams.put("p_root_cause_id", customerSupportRequest.getRootCause());
		inputParams.put("p_device_id", customerSupportRequest.getSensorId());
		inputParams.put("p_sensor_location_id", customerSupportRequest.getSensorLocation());
		inputParams.put("p_inventory_status_id", customerSupportRequest.getInventoryStatus());
		inputParams.put("p_agent_action_id_initial", customerSupportRequest.getAgentSystemActionInitial());
		inputParams.put("p_agent_action_id_secondary", customerSupportRequest.getAgentSystemActionSecondary());
		inputParams.put("p_agent_action_id_tertiary", customerSupportRequest.getAgentSystemActionTertiary());
		inputParams.put("p_defective_sensor_id", customerSupportRequest.getDefectiveSensorAction());
		inputParams.put("p_note", customerSupportRequest.getNotes());
		inputParams.put("p_other_device_location", customerSupportRequest.getSensorOtherLocation());
		inputParams.put("p_inventory_study_id", customerSupportRequest.getInventoryStudyStatus());
		inputParams.put("p_created_by", userId);
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.CUSTOMER_SUPPORT_INSERT, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				// getting the inserted flag value
				Integer ticketId = (int) outParams.get("last_insert_id");
				LOGGER.info("Customer Support Ticket has been created successfully, Ticket id is ", ticketId);
				if (customerSupportRequest.getUploadedFileNames() != null) {
					for (Map<String, String> map : customerSupportRequest.getUploadedFileNames()) {
						for (Map.Entry<String, String> entry : map.entrySet()) {
							this.addCustomerSupportUploadFile(entry.getKey(), entry.getValue(), ticketId,
									"Customer Support", userId, now, customerSupportRequest.getStatusId(), 0);
						}
					}
				}
			} else {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing addCustomerSupport ", e);
			throw new ServiceExecutionException(e.getMessage());
		}

	}

	@Override
	@Transactional
	public void updateCustomerSupport(CustomerSupportUpdateRequest customerSupportUpdateRequest, Integer userId)
			throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		// Timestamp now = Timestamp.from(Instant.now());
		Timestamp now = jdbcTemplate.queryForObject(SQLConstants.CUSTOMER_SUPPORT_GET_NOW, Timestamp.class);
		inputParams.put("p_ticket_id", customerSupportUpdateRequest.getTicketId());
		inputParams.put("p_ticket_title", customerSupportUpdateRequest.getTicketTitle());
		inputParams.put("p_assigned_to", customerSupportUpdateRequest.getAssignedTo());
		inputParams.put("p_priority_id", customerSupportUpdateRequest.getPriorityId());
		inputParams.put("p_status_id", customerSupportUpdateRequest.getStatusId());
		inputParams.put("p_pet_id", customerSupportUpdateRequest.getPetNameId());
		inputParams.put("p_pet_parent_id", customerSupportUpdateRequest.getPetParentNameId());
		inputParams.put("p_contact_method_id", customerSupportUpdateRequest.getContactMethodId());
		inputParams.put("p_pet_parent_address", customerSupportUpdateRequest.getPetParentAddress());
		inputParams.put("p_study_id", customerSupportUpdateRequest.getStudyId());
		inputParams.put("p_issue_id", customerSupportUpdateRequest.getIssueId());
		inputParams.put("p_root_cause_id", customerSupportUpdateRequest.getRootCauseId());
		inputParams.put("p_device_id", customerSupportUpdateRequest.getSensorId());
		inputParams.put("p_sensor_location_id", customerSupportUpdateRequest.getSensorLocationId());
		inputParams.put("p_inventory_status_id", customerSupportUpdateRequest.getInventoryStatusId());
		inputParams.put("p_agent_action_id_initial", customerSupportUpdateRequest.getAgentSystemActionInitialId());
		inputParams.put("p_agent_action_id_secondary", customerSupportUpdateRequest.getAgentSystemActionSecondaryId());
		inputParams.put("p_agent_action_id_tertiary", customerSupportUpdateRequest.getAgentSystemActionTertiaryId());
		inputParams.put("p_defective_sensor_id", customerSupportUpdateRequest.getDefectiveSensorActionId());
		inputParams.put("p_note", customerSupportUpdateRequest.getNotes());
		inputParams.put("p_other_device_location", customerSupportUpdateRequest.getSensorOtherLocation());
		inputParams.put("p_inventory_study_id", customerSupportUpdateRequest.getInventoryStudyStatus());
		inputParams.put("p_updated_by", userId);
		inputParams.put("p_modified_date", now);
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.CUSTOMER_SUPPORT_UPDATE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			inputParams.put("p_modified_date", now);
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				LOGGER.info("Customer Support Ticket has been updated successfully, Ticket id is ",
						customerSupportUpdateRequest.getTicketId());
				if (customerSupportUpdateRequest.getUploadedFileNames() != null) {
					for (Map<String, String> map : customerSupportUpdateRequest.getUploadedFileNames()) {
						for (Map.Entry<String, String> entry : map.entrySet()) {
							this.addCustomerSupportUploadFile(entry.getKey(), entry.getValue(),
									customerSupportUpdateRequest.getTicketId(), "Customer Support", userId, now,
									customerSupportUpdateRequest.getStatusId(), 1);
						}
					}
				}
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"updateCustomerSupport service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.SUPPORT_TICKET_ALREADY_CLOSED,
									customerSupportUpdateRequest.getTicketId())));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing updateCustomerSupport", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public CustomerSupport getCustomerSupportTicketById(int ticketId) throws ServiceExecutionException {
		final CustomerSupport customerSupport = new CustomerSupport();
		LOGGER.debug("getCustomerSupportTicketById called");

		try {
			// in params
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_ticket_id", ticketId);
			Map<String, Object> simpleJdbcCallResult = callStoredProcedure(SQLConstants.CUSTOMER_SUPPORT_GET_BY_ID,
					inputParams);
			Iterator<Entry<String, Object>> itr = simpleJdbcCallResult.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();

				if (key.equals(SQLConstants.RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(u -> {
						customerSupport.setTicketId((Integer) u.get("TICKET_ID"));
						customerSupport.setTicketTitle((String) u.get("TICKET_TITLE"));
						customerSupport.setAssignedTo((Integer) u.get("ASSIGNED_TO_USER_ID"));
						customerSupport.setAssignedName((String) u.get("ASSIGNED_TO_USER_NAME"));
						customerSupport.setPriorityId((Integer) u.get("PRIORITY_ID"));
						customerSupport.setStatusId((Integer) u.get("TICKET_STATUS_ID"));
						customerSupport.setPetId((Integer) u.get("PET_ID"));
						customerSupport.setPetName((String) u.get("PET_NAME"));
						customerSupport.setPetParentId((Integer) u.get("PET_PARENT_ID"));
						customerSupport.setPetParentName((String) u.get("PET_PARENT_NAME"));
						customerSupport.setContactMethodId((Integer) u.get("CONTACT_METHOD_ID"));
						customerSupport.setStudyId((Integer) u.get("STUDY_ID"));
						customerSupport.setStudyName((String) u.get("STUDY_NAME"));
						customerSupport.setIssueId((Integer) u.get("TICKET_CATEGORY_ID"));
						customerSupport.setIssueName((String) u.get("TICKET_CATEGORY_NAME"));
						customerSupport.setRootCauseId((Integer) u.get("CONTACT_REASON_ID"));
						customerSupport.setRootCauseName((String) u.get("CONTACT_REASON_NAME"));
						customerSupport.setSensorId((Integer) u.get("DEVICE_ID"));
						customerSupport.setSensorName((String) u.get("DEVICE_NUMBER"));
						customerSupport.setSensorLocationId((Integer) u.get("DEVICE_LOCATION_ID"));
						customerSupport.setSensorLocationName((String) u.get("DEVICE_LOCATION"));
						customerSupport.setInventoryStatusName((String) u.get("STATUS_NAME"));
						customerSupport.setInventoryStatusId((Integer) u.get("INVENTORY_STATUS_ID"));
						customerSupport.setAgentSystemActionInitialId((Integer) u.get("AGENT_ACTION_1"));
						customerSupport.setAgentSystemActionSecondaryId((Integer) u.get("AGENT_ACTION_2"));
						customerSupport.setAgentSystemActionTertiaryId((Integer) u.get("AGENT_ACTION_3"));
						customerSupport.setDefectiveSensorActionId((Integer) u.get("DEFECTIVE_SENSOR_ACTION_ID"));
						customerSupport.setAgentSystemActionInitialName((String) u.get("AGENT_ACTION_INITIAL"));
						customerSupport.setAgentSystemActionSecondaryName((String) u.get("AGENT_ACTION_SECONDARY"));
						customerSupport.setAgentSystemActionTertiaryName((String) u.get("AGENT_ACTION_TERTIARY"));
						customerSupport.setDefectiveSensorActionName((String) u.get("DEFECTIVE_SENSOR_ACTION_NAME"));
						customerSupport.setAgentSystemActionInitialShowDefective(
								(Integer) u.get("AGENT_ACTION_INITIAL_IS_DEFECTIVE_ACTION"));
						customerSupport.setAgentSystemActionSecondaryShowDefective(
								(Integer) u.get("AGENT_ACTION_SECONDARY_IS_DEFECTIVE_ACTION"));
						customerSupport.setAgentSystemActionTertiaryShowDefective(
								(Integer) u.get("AGENT_ACTION_TERTIARY_IS_DEFECTIVE_ACTION"));
						customerSupport.setTicketCreatedDate(((LocalDateTime) u.get("TICKET_CREATED_DATE")).toString());
						customerSupport.setCreatedBy(((String) u.get("createdBy")));
						customerSupport.setSensorOtherLocation((String) u.get("DEVICE_LOCATION_OTHERS"));
						customerSupport
								.setStudyStartDate(u.get("START_DATE") != null ? u.get("START_DATE").toString() : "");
						customerSupport.setStudyEndDate(u.get("END_DATE") != null ? u.get("END_DATE").toString() : "");
						customerSupport.setStudyStatus((Boolean) u.get("IS_ACTIVE"));
						// customerSupport.setInventoryStudyStatus((Integer)
						// u.get("INVENTORY_STUDY_ID"));
						customerSupport.setPetParentAddress((String) u.get("SHIPPING_ADDRESS"));
						customerSupport.setNotes((String) u.get("NOTES"));
						customerSupport.setUploadedFiles(this.getGetUploadedFileNames(customerSupport.getTicketId(),
								"Customer Support", Constants.GCP_CUSTOMER_SUPPORT_PATH));
					});
				}
			}
		} catch (Exception e) {
			LOGGER.error("error while fetching getCustomerSupportTicketById", e);
			throw new ServiceExecutionException(e.getMessage());
		}

		return customerSupport;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getCustomerSupportTicketsCount(SupportFilter filter) throws ServiceExecutionException {
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		try {
			counts = selectForObject(SQLConstants.CUSTOMER_SUPPORT_GET_LIST_COUNT, String.class, filter.getSearchText(),
					filter.getPriorityId(), filter.getStatusId(), filter.getAssignedToId(), filter.getStartDate(),
					filter.getEndDate(), filter.getUserId(), filter.getRoleTypeId());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while executing getCustomerSupportTicketsCount ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<SupportListDTO> getCustomerSupportTickets(SupportFilter filter) throws ServiceExecutionException {
		List<SupportListDTO> supportList = new ArrayList<>();
		try {
			String sql = SQLConstants.CUSTOMER_SUPPORT_GET_LIST;
			jdbcTemplate.query(sql, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					SupportListDTO support = new SupportListDTO();
					support.setSlNumber(rs.getInt("slNo"));
					support.setTicketID(rs.getInt("TICKET_ID"));
					support.setTicketTitle(rs.getString("TICKET_TITLE"));
					support.setPriority(rs.getString("PRIORITY_NAME"));
					support.setPetName(rs.getString("PET_NAME"));
					support.setPetParentName(rs.getString("PET_PARENT_NAME"));
					support.setStudy(rs.getString("STUDY_NAME"));
					support.setIssue(rs.getString("TICKET_CATEGORY_NAME"));
					support.setAssignedTo(rs.getString("USER_NAME"));
					support.setAging(rs.getInt("AGING") + " Day(s)");
					support.setPetParentAddress(rs.getString("SHIPPING_ADDRESS"));
					support.setLastModifiedOn(rs.getString("LAST_MODIFIED_DATE"));
					support.setStatus(rs.getString("STATUS"));
					supportList.add(support);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getSortBy(), filter.getOrder(), filter.getSearchText(),
					filter.getPriorityId(), filter.getStatusId(), filter.getAssignedToId(), filter.getStartDate(),
					filter.getEndDate(), filter.getUserId(), filter.getRoleTypeId());
		} catch (Exception e) {
			LOGGER.error("error while executing getCustomerSupportTickets ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return supportList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerSupportHistory> getCustomerSupportTicketsHistory(int ticketId)
			throws ServiceExecutionException {
		List<CustomerSupportHistory> resolutions = new ArrayList<>();
		LOGGER.debug("getCustomerSupportTicketById called");

		try {
			// in params
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_ticket_id", ticketId);
			Map<String, Object> simpleJdbcCallResult = callStoredProcedure(
					SQLConstants.CUSTOMER_SUPPORT_HISTORY_GET_BY_ID, inputParams);
			Iterator<Entry<String, Object>> itr = simpleJdbcCallResult.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();
				if (key.equals(SQLConstants.RESULT_SET_1)) {
					List<Map<String, Object>> list1 = (List<Map<String, Object>>) entry.getValue();
					list1.forEach(resolution -> {
						CustomerSupportHistory ticketResolution = new CustomerSupportHistory();
						// ticketResolution.setTicketId((Integer) resolution.get("TICKET_ID"));
						ticketResolution.setResolutionProvidedBy((String) resolution.get("RESOLUTION_PROVIDED_BY"));
						ticketResolution.setResolutionDate(((LocalDateTime) resolution.get("RES_DATE")).toString());
						List<String> arrList = Arrays
								.asList((resolution.get("RESOLUTION_DETAILS").toString()).split("@#"));
						ticketResolution.setResolutionDetails(IntStream.range(0, arrList.size())
								.map(i -> arrList.size() - 1 - i).mapToObj(arrList::get).collect(Collectors.toList()));
						// ticketResolution.setResolutionStatusId((Integer)
						// resolution.get("STATUS_ID"));
						// ticketResolution.setResolutionStatus((String) resolution.get("STATUS_NAME"));
						resolutions.add(ticketResolution);
					});
				}
			}
		} catch (Exception e) {
			LOGGER.error("error while fetching getCustomerSupportTicketById", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return resolutions;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CustomerSupportDetails getCustomerSupportTicketsDetails(int ticketId) throws ServiceExecutionException {
		final CustomerSupportDetails customerSupport = new CustomerSupportDetails();
		LOGGER.debug("getCustomerSupportTicketsDetails called");
		try {
			// in params
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_ticket_id", ticketId);
			Map<String, Object> simpleJdbcCallResult = callStoredProcedure(
					SQLConstants.CUSTOMER_SUPPORT_DETAILS_GET_BY_ID, inputParams);
			Iterator<Entry<String, Object>> itr = simpleJdbcCallResult.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();
				if (key.equals(SQLConstants.RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(u -> {
						customerSupport.setTicketId((Integer) u.get("TICKET_ID"));
						customerSupport.setTicketTitle((String) u.get("TICKET_TITLE"));
						customerSupport.setTicketCreatedDate(((LocalDateTime) u.get("TICKET_CREATED_DATE")).toString());
						customerSupport.setAssignedTo((String) u.get("ASSIGNED_TO"));
						customerSupport.setPriority((String) u.get("PRIORITY_NAME"));
						customerSupport.setStatus((String) u.get("STATUS_NAME"));
						customerSupport.setPetName((String) u.get("PET_NAME"));
						customerSupport.setPetParentName((String) u.get("PET_PARENT_NAME"));
						customerSupport.setContactMethod((String) u.get("CONTACT_METHOD_NAME"));
						customerSupport.setStudyName((String) u.get("STUDY_NAME"));
						customerSupport.setIssueName((String) u.get("TICKET_CATEGORY_NAME"));
						customerSupport.setRootCauseName((String) u.get("CONTACT_REASON_NAME"));
						customerSupport.setSensorName((String) u.get("DEVICE_NUMBER"));
						customerSupport.setMfgSerialNumber((String) u.get("MFG_SERIAL_NUMBER"));
						customerSupport.setMfgMacAddr((String) u.get("MFG_MAC_ADDR"));
						customerSupport.setWifiMacAddr((String) u.get("WIFI_MAC_ADDR"));
						customerSupport.setSensorLocationName((String) u.get("STORAGE_LOCATION_NAME"));
						customerSupport.setAgentSystemActionInitial((String) u.get("AGENT_ACTION_INITIAL"));
						customerSupport.setAgentSystemActionSecondary((String) u.get("AGENT_ACTION_SECONDARY"));
						customerSupport.setAgentSystemActionTertiary((String) u.get("AGENT_ACTION_TERTIARY"));
						customerSupport.setDefectiveSensorAction((String) u.get("DEFECTIVE_SENSOR_ACTION_NAME"));
						customerSupport.setInventoryStatusName((String) u.get("INVENTORY_STATUS_NAME"));
						customerSupport.setPetParentAddress((String) u.get("SHIPPING_ADDRESS"));
						customerSupport.setNotes((String) u.get("NOTES"));
						customerSupport.setUploadedFiles(this.getGetUploadedFileNames(customerSupport.getTicketId(),
								"Customer Support", Constants.GCP_CUSTOMER_SUPPORT_PATH));
						customerSupport
								.setStudyStartDate(u.get("START_DATE") != null ? u.get("START_DATE").toString() : "");
						customerSupport.setStudyEndDate(u.get("END_DATE") != null ? u.get("END_DATE").toString() : "");
						customerSupport.setStudyStatus((Boolean) u.get("IS_ACTIVE"));
					});
				}
			}
		} catch (Exception e) {
			LOGGER.error("error while fetching getCustomerSupportTicketById", e);
			throw new ServiceExecutionException(e.getMessage());
		}

		return customerSupport;
	}

	@Override
	@Transactional
	public Integer addCustomerSupportUploadFile(String fileName, String gcpName, Integer ticketId, String entity,
			Integer userId, Timestamp now, int status, int update) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_file_name", fileName);
		inputParams.put("p_gcp_name", gcpName);
		inputParams.put("p_entity_id", ticketId);
		inputParams.put("p_entity_name", entity);
		inputParams.put("p_created_by", userId);
		inputParams.put("p_modified_date", now);
		inputParams.put("p_update", update);
		inputParams.put("p_status_id", status);
		Integer insertedId;
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.CUSTOMER_SUPPORT_INSERT_FILE_NAME,
					inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				insertedId = (int) outParams.get("last_insert_id");
				LOGGER.info("Customer Support Ticket file names inserted successfully, Id is ", insertedId);
			} else {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing addCustomerSupportUploadFile ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return insertedId;
	}

	@Override
	public List<Map<String, String>> getGetUploadedFileNames(Integer entityId, String entityName, String folderName)
			throws ServiceExecutionException {
		// "Customer Support"
		List<Map<String, String>> fileList = new ArrayList<>();
		try {
			String sql = SQLConstants.CUSTOMER_SUPPORT_GET_UPLOADED_FILE;
			jdbcTemplate.query(sql, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					Map<String, String> map = new HashMap<>();
					map.put("originalFileName", rs.getString("UPLOAD_FILE_NAME"));
					map.put("gcFileName", rs.getString("GCP_UPLOAD_NAME"));
					map.put("url", gcpClientUtil.getDownloaFiledUrl(rs.getString("GCP_UPLOAD_NAME"), folderName));
					map.put("attachmentId", rs.getString("B_ATTACHMENT_ID"));
					fileList.add(map);
				}
			}, entityId, entityName);
		} catch (Exception e) {
			LOGGER.error("error while executing getGetUploadedFileNames ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return fileList;
	}

	@Override
	@Transactional
	public void deleteFile(int attachmentId, int modifiedBy, int statusId) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_attachment_id", attachmentId);
		inputParams.put("p_modified_by", modifiedBy);
		inputParams.put("p_status_id", statusId);
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.FILE_DELETE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			if (StringUtils.isNotEmpty(errorMsg) || (int) outParams.get("out_flag") < NumberUtils.INTEGER_ONE) {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing deleteFile ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

}
