package com.hillspet.wearables.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hillspet.wearables.dto.CustomerSupportDetails;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerSupportTicketsDetailsResponse {
    private CustomerSupportDetails customerSupport;

    public CustomerSupportDetails getCustomerSupport() {
        return customerSupport;
    }

    public void setCustomerSupport(CustomerSupportDetails customerSupport) {
        this.customerSupport = customerSupport;
    }
}
