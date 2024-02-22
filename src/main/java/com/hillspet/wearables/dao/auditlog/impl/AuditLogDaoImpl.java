package com.hillspet.wearables.dao.auditlog.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.constants.SQLConstants;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.common.utils.WearablesUtils;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.auditlog.AuditLogDao;
import com.hillspet.wearables.dto.AuditLog;
import com.hillspet.wearables.dto.AuditLogActivity;
import com.hillspet.wearables.dto.filter.AuditFilter;

@Repository
public class AuditLogDaoImpl extends BaseDaoImpl implements AuditLogDao {

	private static final Logger LOGGER = LogManager.getLogger(AuditLogDaoImpl.class);

	@Autowired
	private ObjectMapper mapper;

	@Override
	public AuditLog addAuditLog(AuditLog auditLog) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_action_name", auditLog.getActionName());
		inputParams.put("p_module_name", auditLog.getModuleName());
		inputParams.put("p_menu_id", auditLog.getMenuId());
		inputParams.put("p_table_name", auditLog.getTableName());
		inputParams.put("p_entity_id", auditLog.getEntityId());
		inputParams.put("p_audit_message", auditLog.getAuditMessage());
		inputParams.put("p_user_id", auditLog.getUserId());
		inputParams.put("p_created_by", auditLog.getCreatedBy());
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.AUDIT_LOG_INSERT, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isNotEmpty(errorMsg) || statusFlag < NumberUtils.INTEGER_ONE) {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing addAuditLog {}", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return auditLog;
	}

	@Override
	public AuditLog addAuditLog(AuditLog auditLog, String platform) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_action_name", auditLog.getActionName());
		inputParams.put("p_module_name", auditLog.getModuleName());
		inputParams.put("p_menu_id", auditLog.getMenuId());
		inputParams.put("p_table_name", auditLog.getTableName());
		inputParams.put("p_entity_id", auditLog.getEntityId());
		inputParams.put("p_audit_message", auditLog.getAuditMessage());
		inputParams.put("p_request_from", WearablesUtils.getRequestFrom(platform));
		inputParams.put("p_user_id", auditLog.getUserId());
		inputParams.put("p_created_by", auditLog.getCreatedBy());
		inputParams.put("p_created_by", auditLog.getCreatedBy());
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.AUDIT_LOG_WITH_REQUEST_FROM_INSERT,
					inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isNotEmpty(errorMsg) || statusFlag < NumberUtils.INTEGER_ONE) {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing addAuditLog {}", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return auditLog;
	}

	@Override
	public List<AuditLog> getAuditLogs(AuditFilter filter, int userId) throws ServiceExecutionException {
		List<AuditLog> auditLogs = new ArrayList<>();
		try {
			jdbcTemplate.query(SQLConstants.AUDIT_LOG_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					AuditLog auditLog = new AuditLog();
					auditLog.setSlNumber(rs.getInt("slNo"));
					auditLog.setUserName(rs.getString("USER_NAME"));
					auditLog.setActionName(rs.getString("ACTION_NAME"));
					auditLog.setModuleName(rs.getString("MODULE_NAME"));
					auditLog.setAuditMessage(rs.getString("AUDIT_MESSAGE"));
					auditLog.setAuditTimeStamp(rs.getTimestamp("AUDIT_TIMESTAMP").toLocalDateTime());
					auditLog.setRequestFrom(rs.getString("REQUEST_FROM"));
					auditLogs.add(auditLog);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getModuleId(), filter.getActionName(), filter.getStartDate(), filter.getEndDate(), userId, filter.getRequestFromId());
		} catch (Exception e) {
			throw new ServiceExecutionException(e.getMessage());
		}
		return auditLogs;
	}

	@Override
	public Map<String, Integer> getAuditLogCount(AuditFilter filter, int userId) throws ServiceExecutionException {
		int totalCount = NumberUtils.INTEGER_ZERO;
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		try {
			counts = selectForObject(SQLConstants.AUDIT_LOG_COUNT, String.class, filter.getSearchText(),
					filter.getModuleId(), filter.getActionName(), filter.getStartDate(), filter.getEndDate(), userId, filter.getRequestFromId());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<AuditLogActivity> getActivity(int entityId, int menuId) throws ServiceExecutionException {
		List<AuditLogActivity> activities = new ArrayList<>();
		LOGGER.debug("getActivity called");
		try {
			jdbcTemplate.query(SQLConstants.AUDIT_LOG_GET_ACTIVITY, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					AuditLogActivity auditLogActivity = new AuditLogActivity();
					auditLogActivity.setContactName(rs.getString("USER_NAME"));
					auditLogActivity.setActivity(rs.getString("ACTION_NAME"));
					auditLogActivity.setDescription(rs.getString("AUDIT_MESSAGE"));
					auditLogActivity.setDate(rs.getTimestamp("AUDIT_TIMESTAMP").toLocalDateTime());
					activities.add(auditLogActivity);
				}
			}, entityId, menuId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getActivity", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return activities;
	}

}
