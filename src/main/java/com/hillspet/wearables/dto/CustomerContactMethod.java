package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerContactMethod {
	private Integer contactMethodId;
	private String contactMethodName;

	public Integer getContactMethodId() {
		return contactMethodId;
	}

	public void setContactMethodId(Integer contactMethodId) {
		this.contactMethodId = contactMethodId;
	}

	public String getContactMethodName() {
		return contactMethodName;
	}

	public void setContactMethodName(String contactMethodName) {
		this.contactMethodName = contactMethodName;
	}

}
