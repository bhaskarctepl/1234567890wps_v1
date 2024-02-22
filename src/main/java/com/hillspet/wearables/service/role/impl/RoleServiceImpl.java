package com.hillspet.wearables.service.role.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.constants.StringLiterals;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.role.RoleDao;
import com.hillspet.wearables.dto.Role;
import com.hillspet.wearables.dto.filter.RoleFilter;
import com.hillspet.wearables.response.RolesResponse;
import com.hillspet.wearables.service.role.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	private static final Logger LOGGER = LogManager.getLogger(RoleServiceImpl.class);

	@Autowired
	private RoleDao roleDao;

	@Override
	public RolesResponse getRolesList(RoleFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getRolesList called");
		Map<String, Integer> mapper = roleDao.getRoleCount(filter);;
		int total =	mapper.get("count");
		int totalCount = mapper.get("totalCount");
		List<Role> rolesList = total > 0 ? roleDao.getRolesList(filter) : new ArrayList<>();
		RolesResponse response = new RolesResponse();
		response.setRolesList(rolesList);
		response.setNoOfElements(rolesList.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);
		LOGGER.debug("getRolesList completed rolesList", rolesList);
		return response;
	}

	@Override
	public Role addRole(Role role) throws ServiceExecutionException {
		LOGGER.debug("addRole called");
		HashMap<Integer, Integer> permissionMap = role.getPermissionMap();
		role.setPermissions(getRolePermissionsByMap(permissionMap));
		role = roleDao.addRole(role);
		LOGGER.debug("addRole called");
		return role;
	}

	@Override
	public Role getRoleById(int roleId) throws ServiceExecutionException {
		return roleDao.getRoleById(roleId);
	}

	@Override
	public void deleteRole(int roleId, int modifiedBy) throws ServiceExecutionException {
		LOGGER.info(" ******** Start Of deleteRole() *********** ");
		roleDao.deleteRole(roleId, modifiedBy);
	}

	@Override
	public void updateRole(Role role) throws ServiceExecutionException {
		LOGGER.info("updateRole called");
		HashMap<Integer, Integer> permissionMap = role.getPermissionMap();
		role.setPermissions(getRolePermissionsByMap(permissionMap));
		roleDao.updateRole(role);
		LOGGER.info("updateRole completed successfully");
	}

	public String getRolePermissionsByMap(HashMap<Integer, Integer> permissionMap) {
		StringBuffer permissionsStringBuffer = new StringBuffer();

		if (MapUtils.isNotEmpty(permissionMap)) {
			permissionMap.entrySet().stream().forEach(entry -> {
				permissionsStringBuffer.append(entry.getKey() + StringLiterals.HASH.getCode() + entry.getValue()
						+ StringLiterals.COMMA.getCode());
			});
		}
		String permissions = permissionsStringBuffer.toString();

		if (permissions.endsWith(",")) {
			permissions = permissions.substring(0, permissions.length() - 1);
		}
		return permissions;
	}

}
