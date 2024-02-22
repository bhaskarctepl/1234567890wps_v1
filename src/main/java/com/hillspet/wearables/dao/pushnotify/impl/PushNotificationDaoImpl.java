package com.hillspet.wearables.dao.pushnotify.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.constants.SQLConstants;
import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.pushnotify.PushNotificationDao;
import com.hillspet.wearables.dto.PushNotification;
import com.hillspet.wearables.dto.PushNotificationRequest;
import com.hillspet.wearables.dto.filter.PushNotificationFilter;

@Repository
public class PushNotificationDaoImpl extends BaseDaoImpl implements PushNotificationDao {

	private static final Logger LOGGER = LogManager.getLogger(PushNotificationDaoImpl.class);

	@Autowired
	private ObjectMapper mapper;

	@Override
	public void addPushNotification(PushNotificationRequest pushNotificationRequest) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_notification_name", pushNotificationRequest.getNotificationName());
		inputParams.put("p_notification_msg", pushNotificationRequest.getNotificationMessage());
		inputParams.put("p_is_active", pushNotificationRequest.getStatus());
		inputParams.put("p_start_date", pushNotificationRequest.getStartDate());
		inputParams.put("p_end_date", pushNotificationRequest.getEndDate());
		inputParams.put("p_created_by", pushNotificationRequest.getCreatedBy());
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PUSH_NOTIFICATOIN_INSERT, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");

			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				// getting the inserted flag value
				Integer pushNotificationId = (int) outParams.get("last_insert_id");
				LOGGER.info("Push Notifiation has been created successfully, Push Notifiation id is ",
						pushNotificationId);
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"addPushNotification service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.STUY_PUSH_NOTIFICATION_ALREADY_EXISTS,
									pushNotificationRequest.getNotificationName())));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing addFirmwareVersion ", e);
			throw new ServiceExecutionException(e.getMessage());
		}

	}

	@Override
	public Map<String, Integer> getPushNotificationCount(PushNotificationFilter filter)
			throws ServiceExecutionException {
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		LOGGER.debug("getReportListCount called");
		try {
			counts = selectForObject(SQLConstants.FN_GET_PUSH_NOTIFICATOIN_LIST_COUNT, String.class,
					filter.getSearchText(), filter.getStatusId(), filter.getStartDate(), filter.getEndDate());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getReportListCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<PushNotification> getPushNotificationList(PushNotificationFilter filter)
			throws ServiceExecutionException {

		List<PushNotification> pushNotifications = new ArrayList<PushNotification>();
		LOGGER.debug("getPushNotificationList called");
		try {
			jdbcTemplate.query(SQLConstants.PUSH_NOTIFICATOIN_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PushNotification pushNotification = new PushNotification();
					pushNotification.setNotificationId(rs.getInt("PUSH_NOTIFICATION_ID"));
					pushNotification.setNotificationName(rs.getString("NOTIFICATION_NAME"));
					pushNotification.setNotificationMessage(rs.getString("NOTIFICATION_MESSAGE"));
					pushNotification.setStatus(rs.getInt("IS_ACTIVE"));
					pushNotification.setStartDate(rs.getString("START_DATE") == null ? "" : rs.getString("START_DATE"));
					pushNotification.setEndDate(rs.getString("END_DATE") == null ? "" : rs.getString("END_DATE"));
					pushNotification.setCreatedDate(rs.getTimestamp("CREATED_DATE").toLocalDateTime());

					pushNotifications.add(pushNotification);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getStatusId(), filter.getStartDate(), filter.getEndDate());

		} catch (Exception e) {
			LOGGER.error("error while fetching getReportList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return pushNotifications;
	}

	@Override
	public PushNotification getPushNotificationById(int pushNotificationId) throws ServiceExecutionException {
		final PushNotification pushNotification = new PushNotification();
		LOGGER.debug("getPushNotificationById called");
		try {
			jdbcTemplate.query(SQLConstants.PUSH_NOTIFICATOIN_GET_BY_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					pushNotification.setNotificationId(rs.getInt("PUSH_NOTIFICATION_ID"));
					pushNotification.setNotificationName(rs.getString("NOTIFICATION_NAME"));
					pushNotification.setNotificationMessage(rs.getString("NOTIFICATION_MESSAGE"));
					pushNotification.setStatus(rs.getInt("IS_ACTIVE"));
					pushNotification.setStartDate(rs.getString("START_DATE") == null ? "" : rs.getString("START_DATE"));
					pushNotification.setEndDate(rs.getString("END_DATE") == null ? "" : rs.getString("END_DATE"));
					pushNotification.setCreatedDate(rs.getTimestamp("CREATED_DATE").toLocalDateTime());
				}
			}, pushNotificationId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPushNotificationList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return pushNotification;

	}

	@Override
	public void deletePushNotification(int pushNotificationId, int modifiedBy) throws ServiceExecutionException {
		LOGGER.debug("deletePushNotification called");
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_push_notification_id", pushNotificationId);
		inputParams.put("p_modified_by", modifiedBy);
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PUSH_NOTIFICATOIN_DELETE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isNotEmpty(errorMsg) || statusFlag < NumberUtils.INTEGER_ONE) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"deletePushNotification validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.ALREADY_BEING_REFERENCED, errorMsg)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing deletePushNotification ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public void updatePushNotification(PushNotificationRequest pushNotificationRequest)
			throws ServiceExecutionException {
		LOGGER.debug("deletePushNotification called");
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_push_notification_id", pushNotificationRequest.getNotificationId());
		inputParams.put("p_notification_name", pushNotificationRequest.getNotificationName());
		inputParams.put("p_notification_msg", pushNotificationRequest.getNotificationMessage());
		inputParams.put("p_is_active", pushNotificationRequest.getStatus());
		inputParams.put("p_modified_by", pushNotificationRequest.getCreatedBy());
		inputParams.put("p_start_date", pushNotificationRequest.getStartDate());
		inputParams.put("p_end_date", pushNotificationRequest.getEndDate());
		LOGGER.info("inputParams::" + inputParams);
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.PUSH_NOTIFICATOIN_UPDATE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isNotEmpty(errorMsg) || statusFlag < NumberUtils.INTEGER_ONE) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"updatePushNotification service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.STUY_PUSH_NOTIFICATION_ALREADY_EXISTS,
									pushNotificationRequest.getNotificationName())));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing updatePushNotification ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

}
