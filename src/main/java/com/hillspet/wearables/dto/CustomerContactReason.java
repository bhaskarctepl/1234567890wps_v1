package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerContactReason {
	private Integer contactReasonId;
	private String contactReasonName;

	public Integer getContactReasonId() {
		return contactReasonId;
	}

	public void setContactReasonId(Integer contactReasonId) {
		this.contactReasonId = contactReasonId;
	}

	public String getContactReasonName() {
		return contactReasonName;
	}

	public void setContactReasonName(String contactReasonName) {
		this.contactReasonName = contactReasonName;
	}

}
