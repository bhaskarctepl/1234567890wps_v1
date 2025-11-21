package com.hillspet.wearables.response;

import java.util.List;

import com.hillspet.wearables.dto.NotificationConfigDTO;


public class StudyNotificationConfigResponse {
	
	private List<NotificationConfigDTO> notifications;

    public void setNotifications(List<NotificationConfigDTO> notifications) {
		this.notifications = notifications;
	}

	public List<NotificationConfigDTO> getNotifications() {
        return notifications;
    }

}
