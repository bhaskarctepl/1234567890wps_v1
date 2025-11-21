package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.request.PushNotificationAssociated;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PushNotificationConfigResponse extends CommonResponse {

	private List<PushNotificationAssociated> pushNotificationsAssociated;

	public List<PushNotificationAssociated> getPushNotificationsAssociated() {
		return pushNotificationsAssociated;
	}

	public void setPushNotificationsAssociated(List<PushNotificationAssociated> pushNotificationsAssociated) {
		this.pushNotificationsAssociated = pushNotificationsAssociated;
	}

}
