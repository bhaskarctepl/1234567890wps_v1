package com.hillspet.wearables.service.role;

import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.Role;
import com.hillspet.wearables.dto.filter.RoleFilter;
import com.hillspet.wearables.response.RolesResponse;

@Service
public interface RoleService {

	public Role addRole(Role role) throws ServiceExecutionException;

	RolesResponse getRolesList(RoleFilter filter) throws ServiceExecutionException;

	public Role getRoleById(int roleId) throws ServiceExecutionException;
	
	public void deleteRole(int roleId, int modifiedBy) throws ServiceExecutionException;

	void updateRole(Role role) throws ServiceExecutionException;

	
}