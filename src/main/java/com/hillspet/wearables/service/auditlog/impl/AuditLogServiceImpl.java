package com.hillspet.wearables.service.auditlog.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.auditlog.AuditLogDao;
import com.hillspet.wearables.dto.AuditLog;
import com.hillspet.wearables.dto.AuditLogActivity;
import com.hillspet.wearables.dto.filter.AuditFilter;
import com.hillspet.wearables.response.ActivityResponse;
import com.hillspet.wearables.response.AuditLogResponse;
import com.hillspet.wearables.service.auditlog.AuditLogService;

@Service
public class AuditLogServiceImpl implements AuditLogService {

	private static final Logger LOGGER = LogManager.getLogger(AuditLogServiceImpl.class);

	@Autowired
	private AuditLogDao auditLogDao;

	@Override
	public AuditLogResponse getAuditLogs(AuditFilter filter,int userId) throws ServiceExecutionException {
		LOGGER.debug("getAuditLogs called");
		Map<String, Integer> mapper = auditLogDao.getAuditLogCount(filter,userId);
		int total =	mapper.get("count");
		int totalCount = mapper.get("totalCount");
		List<AuditLog> auditLogs = total > 0 ? auditLogDao.getAuditLogs(filter,userId) : new ArrayList<>();
		AuditLogResponse response = new AuditLogResponse();
		response.setAuditLogs(auditLogs);
		response.setNoOfElements(auditLogs.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);
		LOGGER.debug("getAuditLogs is {}", auditLogs);
		LOGGER.debug("getAuditLogs completed successfully");
		return response;
	}

	@Override
	public AuditLog addAuditLog(AuditLog auditLog) throws ServiceExecutionException {
		return auditLogDao.addAuditLog(auditLog);
	}
	
	@Override
	public AuditLog addAuditLog(AuditLog auditLog, String platform) throws ServiceExecutionException {
		return auditLogDao.addAuditLog(auditLog, platform);
	}

	@Override
	public ActivityResponse getActivity(int entityId, int menuId) throws ServiceExecutionException {
		LOGGER.debug("getActivity called");
		List<AuditLogActivity> activities = auditLogDao.getActivity(entityId, menuId);

		ActivityResponse response = new ActivityResponse();
		response.setActivities(activities);

		LOGGER.debug("getActivity study count is {}", activities.size());
		LOGGER.debug("getActivity completed successfully");
		return response;
	}

}
