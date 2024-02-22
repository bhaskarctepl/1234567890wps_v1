package com.hillspet.wearables.service.pushnotify;

import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.PushNotification;
import com.hillspet.wearables.dto.PushNotificationRequest;
import com.hillspet.wearables.dto.filter.PushNotificationFilter;
import com.hillspet.wearables.jaxrs.resource.impl.PushNotificationListResponse;

@Service
public interface PushNotificationService {

	public void addPushNotification(PushNotificationRequest pushNotificationRequest) throws ServiceExecutionException;

	PushNotificationListResponse getPushNotificationList(PushNotificationFilter filter) throws ServiceExecutionException;

	public PushNotification getPushNotificationById(int pushNotificationId) throws ServiceExecutionException;

	public void deletePushNotification(int pushNotificationId, int modifiedBy) throws ServiceExecutionException;

	void updatePushNotification(PushNotificationRequest pushNotificationRequest) throws ServiceExecutionException;

}