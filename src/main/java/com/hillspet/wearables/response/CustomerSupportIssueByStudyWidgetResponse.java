package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.CustomerSupportIssuesByStudy;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerSupportIssueByStudyWidgetResponse {
	private List<CustomerSupportIssuesByStudy> customerSupportIssuesByStudy;

	public List<CustomerSupportIssuesByStudy> getCustomerSupportIssuesByStudy() {
		return customerSupportIssuesByStudy;
	}

	public void setCustomerSupportIssuesByStudy(List<CustomerSupportIssuesByStudy> customerSupportIssuesByStudy) {
		this.customerSupportIssuesByStudy = customerSupportIssuesByStudy;
	}

}
