package com.hillspet.wearables.dao.role;

import java.util.List;
import java.util.Map;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.Role;
import com.hillspet.wearables.dto.filter.BaseFilter;
import com.hillspet.wearables.dto.filter.RoleFilter;

public interface RoleDao {

	List<Role> getRolesList(RoleFilter filter) throws ServiceExecutionException;

	Role addRole(Role role) throws ServiceExecutionException;

	Map<String, Integer> getRoleCount(RoleFilter filter) throws ServiceExecutionException;

	Role getRoleById(int roleId) throws ServiceExecutionException;

	void deleteRole(int roleId, int modifiedBy) throws ServiceExecutionException;

	Role updateRole(Role role) throws ServiceExecutionException;

}
