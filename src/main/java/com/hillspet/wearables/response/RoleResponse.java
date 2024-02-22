package com.hillspet.wearables.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.Role;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleResponse {

	private Role role;

	public void setRole(Role role) {
		this.role = role;
	}

	@ApiModelProperty(value = "Get role of particular id")
	public Role getRole() {
		return role;
	}
}
