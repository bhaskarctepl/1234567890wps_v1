package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class RoleFilter extends BaseFilter {

	@ApiParam(name = "roleTypeId", value = "Search based on role type Id drop down")
	@QueryParam("roleTypeId")
	private String roleTypeIds;

	public RoleFilter() {

	}

	public RoleFilter(String roleTypeIds) {
		super();
		this.roleTypeIds = roleTypeIds;
	}

	public String getRoleTypeIds() {
		return roleTypeIds;
	}

	public void setRoleTypeIds(String roleTypeIds) {
		this.roleTypeIds = roleTypeIds;
	}

	
 

	
	

	

}
