package com.hillspet.wearables.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contains all the information that needed to Validate Address", value = "ValidateAddressRequest")
@JsonInclude(value = Include.NON_NULL)
public class ValidateAddressRequest {

	@ApiModelProperty(value = "address", required = true, example = "12340 Boggy Creek Road, Orlando, FL, USA")
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
