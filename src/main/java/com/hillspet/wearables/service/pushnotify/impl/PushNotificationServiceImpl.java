package com.hillspet.wearables.service.pushnotify.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.pushnotify.PushNotificationDao;
import com.hillspet.wearables.dto.PushNotification;
import com.hillspet.wearables.dto.PushNotificationRequest;
import com.hillspet.wearables.dto.filter.PushNotificationFilter;
import com.hillspet.wearables.jaxrs.resource.impl.PushNotificationListResponse;
import com.hillspet.wearables.service.pushnotify.PushNotificationService;

@Service
public class PushNotificationServiceImpl implements PushNotificationService {

	private static final Logger LOGGER = LogManager.getLogger(PushNotificationServiceImpl.class);

	@Autowired
	private PushNotificationDao pushNotificationDao;

	@Override
	public void addPushNotification(PushNotificationRequest pushNotificationRequest) throws ServiceExecutionException {
		LOGGER.debug("addPushNotification called");
		pushNotificationDao.addPushNotification(pushNotificationRequest);
		LOGGER.debug("addPushNotification called");
	}

	@Override
	public PushNotificationListResponse getPushNotificationList(PushNotificationFilter filter)
			throws ServiceExecutionException {
		LOGGER.debug("getPushNotificationList called");
		Map<String, Integer> mapper = pushNotificationDao.getPushNotificationCount(filter);
		int total = mapper.get("count");
		int totalCount = mapper.get("totalCount");
		List<PushNotification> pushNotifications = total > 0 ? pushNotificationDao.getPushNotificationList(filter)
				: new ArrayList<>();
		PushNotificationListResponse response = new PushNotificationListResponse();
		response.setPushNotifications(pushNotifications);
		response.setNoOfElements(pushNotifications.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);
		LOGGER.debug("getPushNotificationList  count is {}", pushNotifications);
		LOGGER.debug("getPushNotificationList completed successfully");
		return response;
	}

	@Override
	public PushNotification getPushNotificationById(int pushNotificationId) throws ServiceExecutionException {
		LOGGER.info("getPushNotificationById called");
		return pushNotificationDao.getPushNotificationById(pushNotificationId);
	}

	@Override
	public void deletePushNotification(int pushNotificationId, int modifiedBy) throws ServiceExecutionException {
		LOGGER.info(" ******** Start Of deletePushNotification() *********** ");
		pushNotificationDao.deletePushNotification(pushNotificationId, modifiedBy);
	}

	@Override
	public void updatePushNotification(PushNotificationRequest pushNotificationRequest)
			throws ServiceExecutionException {
		LOGGER.debug("updatePushNotification called");
		pushNotificationDao.updatePushNotification(pushNotificationRequest);
		LOGGER.debug("updatePushNotification completed successfully");
	}

}
