package com.hillspet.wearables.dao.auditlog;

import java.util.List;
import java.util.Map;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.AuditLog;
import com.hillspet.wearables.dto.AuditLogActivity;
import com.hillspet.wearables.dto.filter.AuditFilter;

public interface AuditLogDao {

	public AuditLog addAuditLog(AuditLog auditLog) throws ServiceExecutionException;
	
	public AuditLog addAuditLog(AuditLog auditLog, String platform) throws ServiceExecutionException;

	public List<AuditLog> getAuditLogs(AuditFilter filter,int userId) throws ServiceExecutionException;

	public Map<String, Integer> getAuditLogCount(AuditFilter filter,int userId) throws ServiceExecutionException;

	public List<AuditLogActivity> getActivity(int entityId, int menuId) throws ServiceExecutionException;

}
