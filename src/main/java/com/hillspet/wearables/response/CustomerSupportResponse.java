package com.hillspet.wearables.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hillspet.wearables.dto.CustomerSupport;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerSupportResponse {

	private CustomerSupport customerSupport;

	public CustomerSupport getCustomerSupport() {
		return customerSupport;
	}

	public void setCustomerSupport(CustomerSupport customerSupport) {
		this.customerSupport = customerSupport;
	}

}
