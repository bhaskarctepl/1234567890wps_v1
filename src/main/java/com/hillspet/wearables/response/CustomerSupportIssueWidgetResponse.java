package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.CustomerSupportByCategory;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerSupportIssueWidgetResponse {
	private List<CustomerSupportByCategory> customerSupportByCategories;

	public List<CustomerSupportByCategory> getCustomerSupportByCategories() {
		return customerSupportByCategories;
	}

	public void setCustomerSupportByCategories(List<CustomerSupportByCategory> customerSupportByCategories) {
		this.customerSupportByCategories = customerSupportByCategories;
	}

}
