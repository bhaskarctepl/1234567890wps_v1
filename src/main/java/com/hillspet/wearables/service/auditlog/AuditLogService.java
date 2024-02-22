package com.hillspet.wearables.service.auditlog;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.AuditLog;
import com.hillspet.wearables.dto.filter.AuditFilter;
import com.hillspet.wearables.response.ActivityResponse;
import com.hillspet.wearables.response.AuditLogResponse;

public interface AuditLogService {

	public AuditLog addAuditLog(AuditLog auditLog) throws ServiceExecutionException;
	
	public AuditLog addAuditLog(AuditLog auditLog, String platform) throws ServiceExecutionException;

	public AuditLogResponse getAuditLogs(AuditFilter filter,int userId) throws ServiceExecutionException;

	public ActivityResponse getActivity(int entityId, int menuId) throws ServiceExecutionException;

}
