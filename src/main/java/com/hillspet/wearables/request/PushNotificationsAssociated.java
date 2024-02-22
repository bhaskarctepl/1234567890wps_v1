
package com.hillspet.wearables.request;

import java.time.LocalDate;

import io.swagger.annotations.ApiModelProperty;

public class PushNotificationsAssociated {

	@ApiModelProperty(value = "notificationId", required = true)
	private Integer notificationId;

	@ApiModelProperty(value = "notificationName", required = false)
	private String notificationName;

	@ApiModelProperty(value = "startDate", required = true)
	private LocalDate startDate;

	@ApiModelProperty(value = "endDate", required = true)
	private LocalDate endDate;
	
	@ApiModelProperty(value = "displayTime", required = false)
	private String displayTime;
	
	@ApiModelProperty(value = "displayTime", required = true)
	private String frequency;

	@Override
	public String toString() {
		return notificationId + "#" + startDate + "#" + endDate;
	}

	
	public Integer getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}

	public String getNotificationName() {
		return notificationName;
	}


	public void setNotificationName(String notificationName) {
		this.notificationName = notificationName;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}


	public String getDisplayTime() {
		return displayTime;
	}


	public void setDisplayTime(String displayTime) {
		this.displayTime = displayTime;
	}


	public String getFrequency() {
		return frequency;
	}


	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
}
