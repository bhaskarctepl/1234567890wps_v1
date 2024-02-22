package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class UserFilter extends BaseFilter {

	@ApiParam(name = "roleId", value = "Search based on role Id drop down")
	@QueryParam("roleId")
	private String roleId;

	public UserFilter() {

	}

	public UserFilter(String roleId) {
		super();
		this.roleId = roleId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
