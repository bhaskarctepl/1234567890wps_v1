package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.CustomerContactMethod;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerContactMethodResponse {

	private List<CustomerContactMethod> customerContactMethods;

	@ApiModelProperty(value = "List of details for Customer Contact Methods")
	public List<CustomerContactMethod> getCustomerContactMethods() {
		return customerContactMethods;
	}

	public void setCustomerContactMethods(List<CustomerContactMethod> customerContactMethods) {
		this.customerContactMethods = customerContactMethods;
	}

}
