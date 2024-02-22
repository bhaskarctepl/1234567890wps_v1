package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.Role;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RolesResponse extends BaseResultCollection {

	private List<Role> roles;

	private List<Role> roleList;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for Roles beased on search")
	public List<Role> getRolesList() {
		return roleList;
	}

	public void setRolesList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@ApiModelProperty(value = "Get Roles List")
	public List<Role> getRoles() {
		return roles;
	}
}
