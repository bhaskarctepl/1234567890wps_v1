
package com.hillspet.wearables.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hillspet.wearables.dto.NotificationConfigDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contains all information needed to configure Notification settings for a study", value = "StudyNotificationConfigRequest")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudyNotificationConfigRequest {

    @ApiModelProperty(value = "List of notification configurations for the study", required = true)
    private List<NotificationConfigDTO> notifications;

    public List<NotificationConfigDTO> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationConfigDTO> notifications) {
        this.notifications = notifications;
    }

}

