package com.hillspet.wearables.dao.pushnotify;

import java.util.List;
import java.util.Map;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.PushNotification;
import com.hillspet.wearables.dto.PushNotificationRequest;
import com.hillspet.wearables.dto.filter.PushNotificationFilter;

public interface PushNotificationDao {

	Map<String, Integer> getPushNotificationCount(PushNotificationFilter filter) throws ServiceExecutionException;

	void addPushNotification(PushNotificationRequest pushNotificationRequest) throws ServiceExecutionException;

	List<PushNotification> getPushNotificationList(PushNotificationFilter filter) throws ServiceExecutionException;

	PushNotification getPushNotificationById(int pushNotificationId) throws ServiceExecutionException;

	void deletePushNotification(int pushNotificationId, int modifiedBy) throws ServiceExecutionException;

	void updatePushNotification(PushNotificationRequest pushNotificationRequest) throws ServiceExecutionException;

}
