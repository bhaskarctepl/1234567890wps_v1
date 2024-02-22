package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.CustomerContactReason;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerContactReasonResponse {

	private List<CustomerContactReason> customerContactReasons;

	@ApiModelProperty(value = "List of details for Customer Contact Reasons")
	public List<CustomerContactReason> getCustomerContactReasons() {
		return customerContactReasons;
	}

	public void setCustomerContactReasons(List<CustomerContactReason> customerContactReasons) {
		this.customerContactReasons = customerContactReasons;
	}

}
