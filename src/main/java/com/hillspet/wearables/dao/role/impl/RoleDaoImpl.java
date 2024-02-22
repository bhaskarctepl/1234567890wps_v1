package com.hillspet.wearables.dao.role.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.constants.SQLConstants;
import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.role.RoleDao;
import com.hillspet.wearables.dto.Menu;
import com.hillspet.wearables.dto.Role;
import com.hillspet.wearables.dto.filter.RoleFilter;

@Repository
public class RoleDaoImpl extends BaseDaoImpl implements RoleDao {

	private static final Logger LOGGER = LogManager.getLogger(RoleDaoImpl.class);

	@Autowired
	private ObjectMapper mapper;

	@Override
	public List<Role> getRolesList(RoleFilter filter) {
		List<Role> roleList = new ArrayList<>();
		LOGGER.debug("getRolesList called");
		try {
			jdbcTemplate.query(SQLConstants.ROLE_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					Role role = new Role();
					role.setSlNumber(rs.getInt("slNo"));
					role.setRoleId(rs.getInt("role_id"));
					role.setRoleName(rs.getString("role_name"));
					role.setRoleType(rs.getString("role_type"));
					if (rs.getString("permissions") != null)
						role.setPermissions(rs.getString("permissions").trim());
					role.setIsActive(rs.getBoolean("is_active"));
					roleList.add(role);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getStatusId(), filter.getRoleTypeIds());

		} catch (Exception e) {
			LOGGER.error("error while fetching getFirmwareVersions", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return roleList;
	}

	@Override
	public Role addRole(Role role) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_role_name", role.getRoleName());
		inputParams.put("p_role_type_id", role.getRoleTypeId());
		inputParams.put("p_menu_permission_list", role.getPermissions());
		inputParams.put("p_is_active", role.getIsActive());
		inputParams.put("p_created_by", role.getCreatedBy());
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.ROLE_INSERT, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");

			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				// getting the inserted flag value
				Integer roleId = (int) outParams.get("last_insert_id");
				role.setRoleId(roleId);
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException("addRole service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList(
									new WearablesError(WearablesErrorCode.ROLE_ALREADY_EXISTS, role.getRoleName())));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing addFirmwareVersion ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return role;
	}

	@Override
	public Map<String, Integer> getRoleCount(RoleFilter filter) throws ServiceExecutionException {
		int totalCount = NumberUtils.INTEGER_ZERO;
		LOGGER.debug("getRoleCount called");
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		try {
			counts = selectForObject(SQLConstants.ROLE_LIST_COUNT, String.class, filter.getSearchText(),
					filter.getStatusId(), filter.getRoleTypeIds());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getRoleCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public Role getRoleById(int roleId) throws ServiceExecutionException {
		final Role role = new Role();
		List<Menu> menuList = new ArrayList<Menu>();
		// List<MenuAction> menuActionList = new ArrayList<MenuAction>();
		LOGGER.debug("getRoleById called");
		try {
			jdbcTemplate.query(SQLConstants.ROLE_GET_BY_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					Menu menu = new Menu();
					// MenuAction menuAction = new MenuAction();

					role.setRoleId(rs.getInt("role_id"));
					role.setRoleName(rs.getString("role_name"));
					role.setRoleTypeId(rs.getInt("role_type_id"));
					role.setRoleType(rs.getString("role_type"));
					role.setIsActive(rs.getBoolean("is_active"));
					menu.setMenuId(rs.getInt("menu_id"));
					menu.setMenuName(rs.getString("menu_name"));
					menu.setMenuActionId(rs.getInt("menu_action_id"));
					menu.setMenuActionName(rs.getString("menu_action_name"));

					menuList.add(menu);
				}
			}, roleId);

			role.setMenulist(menuList);
			// role.setMenuActionlist(menuActionList);
		} catch (Exception e) {
			LOGGER.error("error while fetching getRoleById", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return role;
	}

	@Override
	public void deleteRole(int roleId, int modifiedBy) {
		LOGGER.info(" ******** Start Of deleteRole() *********** ");
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_role_id", roleId);
		inputParams.put("p_modified_by", modifiedBy);
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.ROLE_DELETE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isNotEmpty(errorMsg) || (int) statusFlag < NumberUtils.INTEGER_ONE) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException("deleteRole validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.ROLE_CANNOT_DELETE)));
				} else if (statusFlag == -3) {
					throw new ServiceExecutionException("deleteRole validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.ROLE_CANNOT_DELETE_ACTIVE)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing deleteRole ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public Role updateRole(Role role) {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_role_id", role.getRoleId());
		inputParams.put("p_role_name", role.getRoleName());
		inputParams.put("p_role_type_id", role.getRoleTypeId());
		inputParams.put("p_menu_permission_list", role.getPermissions());
		inputParams.put("p_is_active", role.getIsActive());
		inputParams.put("p_modified_by", role.getModifiedBy());
		LOGGER.info("inputParams::" + inputParams);
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.ROLE_UPDATE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isNotEmpty(errorMsg) || statusFlag < NumberUtils.INTEGER_ONE) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException("updateRole service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList(
									new WearablesError(WearablesErrorCode.ROLE_ALREADY_EXISTS, role.getRoleName())));
				}
				if (statusFlag == -3) {
					throw new ServiceExecutionException("updateRole cannot be incativated the referenced role ",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.ROLE_CANNOT_INACTIVE)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing updateUser ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return role;
	}
}
