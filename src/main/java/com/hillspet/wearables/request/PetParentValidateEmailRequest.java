package com.hillspet.wearables.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contains all information that needed to create a pet parent", value = "PetParentValidateEmailRequest")
@JsonInclude(value = Include.NON_NULL)
public class PetParentValidateEmailRequest {

	@ApiModelProperty(value = "email", required = true, example = "John@gmail.com")
	private String email;
	 
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
	
}
