package com.hillspet.wearables.dao.user.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

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
import com.hillspet.wearables.common.constants.StringLiterals;
import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.common.utils.WearablesUtils;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.user.UserDao;
import com.hillspet.wearables.dto.AssociatedStudy;
import com.hillspet.wearables.dto.AuditLog;
import com.hillspet.wearables.dto.Menu;
import com.hillspet.wearables.dto.Role;
import com.hillspet.wearables.dto.User;
import com.hillspet.wearables.dto.filter.UserFilter;

@Repository
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);

	@Autowired
	private ObjectMapper mapper;

	@Override
	public User addUser(User user) {
		Map<String, Object> inputParams = new HashMap<>();

		inputParams.put("p_user_name", user.getUserName());
		inputParams.put("p_user_pwd", user.getPassword());
		inputParams.put("p_first_name", user.getFirstName());
		inputParams.put("p_last_name", user.getLastName());
		inputParams.put("p_full_name", user.getFullName());
		inputParams.put("p_email", user.getEmail());
		inputParams.put("p_secondary_email", user.getSecondaryEmail());
		inputParams.put("p_phone_number", user.getPhoneNumber());
		inputParams.put("p_future_studies", user.getFutureStudies());
		inputParams.put("p_inactive_studies", user.getInactiveStudies());
		inputParams.put("p_is_active", user.getIsActive());
		inputParams.put("p_role_id", user.getRoleIds());
		inputParams.put("p_study_permissions", user.getStudyPermissions());
		inputParams.put("p_created_by", user.getCreatedBy());
		inputParams.put("p_created_by", user.getCreatedBy());
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.USER_INSERT, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				// getting the inserted flag value
				Integer userId = (int) outParams.get("last_insert_id");
				user.setUserId(userId);
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException("addUser service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.USER_NAME_ALREADY_EXISTS,
									user.getUserName())));
				} else if (statusFlag == -3) {
					throw new ServiceExecutionException("addUser service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList(
									new WearablesError(WearablesErrorCode.EMAIL_ALREADY_EXISTS, user.getEmail())));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing addUser ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return user;
	}

	@Override
	public User updateUser(User user) {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_user_id", user.getUserId());
		inputParams.put("p_user_name", user.getUserName());
		inputParams.put("p_first_name", user.getFirstName());
		inputParams.put("p_last_name", user.getLastName());
		inputParams.put("p_full_name", user.getFullName());
		inputParams.put("p_email", user.getEmail());
		inputParams.put("p_secondary_email", user.getSecondaryEmail());
		inputParams.put("p_phone_number", user.getPhoneNumber());
		inputParams.put("p_future_studies", user.getFutureStudies());
		inputParams.put("p_inactive_studies", user.getInactiveStudies());
		inputParams.put("p_is_active", user.getIsActive());
		inputParams.put("p_role_id", user.getRoleIds());
		inputParams.put("p_study_permissions", user.getStudyPermissions());
		inputParams.put("p_modified_by", user.getModifiedBy());
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.USER_UPDATE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isNotEmpty(errorMsg) || statusFlag < NumberUtils.INTEGER_ONE) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException("updateUser service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.USER_NAME_ALREADY_EXISTS,
									user.getUserName())));
				} else if (statusFlag == -3) {
					throw new ServiceExecutionException("addUser service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList(
									new WearablesError(WearablesErrorCode.EMAIL_ALREADY_EXISTS, user.getEmail())));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing updateUser ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return user;
	}

	@Override
	public void updateUserPassword(int userId, String userName, String password, boolean needChangePassword,
			int modifiedBy, String platform) {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_user_id", userId);
		inputParams.put("p_user_name", userName);
		inputParams.put("p_user_pwd", password);
		inputParams.put("p_modified_by", modifiedBy);
		inputParams.put("p_need_change_pwd", needChangePassword);
		inputParams.put("p_reqeust_from", WearablesUtils.getRequestFrom(platform));
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.USER_UPDATE_PASSWORD, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isNotEmpty(errorMsg) || statusFlag < NumberUtils.INTEGER_ONE) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"updateUserPassword service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.USER_NAME_ALREADY_EXISTS, userId)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing updateUserPassword ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public void deleteUser(int userId, int modifiedBy) {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_user_id", userId);
		inputParams.put("p_modified_by", modifiedBy);
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.USER_DELETE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isNotEmpty(errorMsg) || (int) statusFlag < NumberUtils.INTEGER_ONE) {
				if (statusFlag == -2) {
					throw new ServiceExecutionException("deleteRole validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.USER_CANNOT_DELETE, errorMsg)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing deleteUser ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<User> findByUsername(String userName) {
		final User user = new User();
		List<Menu> rolePermissions = new ArrayList<>();
		LOGGER.debug("findByUsername called");
		try {
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_user_name", userName);

			Map<String, Object> simpleJdbcCallResult = callStoredProcedure(
					SQLConstants.LOGIN_GET_USER_DETAILS_BY_USER_NAME, inputParams);
			Iterator<Entry<String, Object>> itr = simpleJdbcCallResult.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();

				if (key.equals(SQLConstants.RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(u -> {
						user.setUserId((Integer) u.get("USER_ID"));
						user.setUserName((String) u.get("USER_NAME"));
						user.setPassword((String) u.get("USER_PASSWORD"));
						user.setFullName((String) u.get("FULL_NAME"));
						user.setEmail((String) u.get("EMAIL"));
						user.setPhoneNumber((String) u.get("PHONE_NUMBER"));
						user.setIsActive(
								(Integer) u.get("IS_ACTIVE") > NumberUtils.INTEGER_ZERO ? Boolean.TRUE : Boolean.FALSE);
						user.setNeedChangePwd(
								(Integer) u.get("NEED_CHANGE_PWD") > NumberUtils.INTEGER_ZERO ? Boolean.TRUE
										: Boolean.FALSE);
						user.setIsSuperAdmin((Integer) u.get("IS_SUPER_ADMIN") > NumberUtils.INTEGER_ZERO ? Boolean.TRUE
								: Boolean.FALSE);

						if (u.get("roles") != null) {
							user.setRoleTypeId((Integer) u.get("ROLE_TYPE_ID"));
							user.setRoleTypeName((String) u.get("ROLE_TYPE"));

							String roles = (String) u.get("roles");
							List<Role> roleList = new ArrayList<>();
							for (String roleStr : roles.split(StringLiterals.SEPERATOR.getCode())) { // Added by Rajesh
								Role role = new Role();
								role.setRoleId(Integer.valueOf(roleStr.split("###")[0]));
								role.setRoleName(roleStr.split("###")[1]);
								roleList.add(role);
							}

							user.setRoles(roleList);
							user.setRoleIds((String) u.get("ROLE_ID"));
							user.setRoleName((String) u.get("ROLE_NAME"));
						}
					});
				}

				if (key.equals(SQLConstants.RESULT_SET_2)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(menuPermission -> {
						Menu menu = new Menu((Integer) menuPermission.get("MENU_ID"),
								(String) menuPermission.get("MENU_NAME"),
								(Integer) menuPermission.get("PARENT_MENU_ID"),
								(String) menuPermission.get("PARENT_MENU_NAME"),
								(Integer) menuPermission.get("MENU_ACTION_ID"),
								(String) menuPermission.get("MENU_ACTION_NAME"));
						rolePermissions.add(menu);
					});
				}
			}

			LOGGER.debug("findByUsername completed successfully");
			if (user.getUserId() != null) {
				user.setRolePermissions(rolePermissions);
				return Optional.ofNullable(user);
			} else {
				return Optional.empty();
			}
		} catch (Exception e) {
			LOGGER.error("error while fetching findByUsername", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public User getUserById(int userId) throws ServiceExecutionException {
		final User user = new User();
		List<AssociatedStudy> associatedStudies = new ArrayList<>();
		List<AuditLog> auditLogs = new ArrayList<>();
		LOGGER.debug("getUserById called");
		try {
			// in params
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_user_id", userId);

			Map<String, Object> simpleJdbcCallResult = callStoredProcedure(SQLConstants.USER_GET_BY_ID, inputParams);
			Iterator<Entry<String, Object>> itr = simpleJdbcCallResult.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();

				if (key.equals(SQLConstants.RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();

					list.forEach(u -> {
						user.setUserId((Integer) u.get("USER_ID"));
						user.setUserName((String) u.get("USER_NAME"));
						user.setPassword((String) u.get("USER_PASSWORD"));
						user.setFullName((String) u.get("FULL_NAME"));
						user.setFirstName((String) u.get("FIRST_NAME"));
						user.setLastName((String) u.get("LAST_NAME"));
						user.setEmail((String) u.get("EMAIL"));
						user.setSecondaryEmail((String) u.get("SECONDARY_EMAIL"));
						user.setPhoneNumber((String) u.get("PHONE_NUMBER"));
						user.setFutureStudies((Integer) u.get("ASSOCIATE_FUTURE_STUDY"));
						user.setInactiveStudies((Integer) u.get("ASSOCIATE_PAST_STUDY"));
						user.setIsActive(
								(Integer) u.get("IS_ACTIVE") > NumberUtils.INTEGER_ZERO ? Boolean.TRUE : Boolean.FALSE);
						if (u.get("ROLE_ID") != null) {
							user.setRoleIds((String) u.get("ROLE_ID"));
							user.setRoleName((String) u.get("ROLE_NAME"));
							user.setRoleTypeId((Integer) u.get("ROLE_TYPE_ID"));
							user.setRoleTypeName((String) u.get("ROLE_TYPE"));
						}

						if (u.get("STUDY_ID") != null && (Integer) u.get("STUDY_ID") > NumberUtils.INTEGER_ZERO
								&& user.getRoleTypeId() != 1) {
							AssociatedStudy associatedStudy = new AssociatedStudy();
							associatedStudy.setStudyId((Integer) u.get("STUDY_ID"));
							associatedStudy.setStudyName((String) u.get("STUDY_NAME"));
							associatedStudy.setPrincipleInvestigator((String) u.get("PRINCIPLE_INVESTIGATOR"));
							associatedStudy.setPermissionId((Integer) u.get("MENU_ACTION_ID"));
							associatedStudy.setPermission((String) u.get("MENU_ACTION_NAME"));
							associatedStudy.setIsActive((Boolean) u.get("STUDY_STATUS"));
							// Timestamp timestamp = (Timestamp) u.get("DATE_ADDED");
							associatedStudy.setCreatedDate((LocalDateTime) u.get("DATE_ADDED"));
							associatedStudies.add(associatedStudy);
						}
					});

				}

				if (key.equals(SQLConstants.RESULT_SET_2)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(audit -> {
						AuditLog auditLog = new AuditLog();
						auditLog.setUserName((String) audit.get("USER_NAME"));
						auditLog.setActionName((String) audit.get("ACTION_NAME"));
						auditLog.setModuleName((String) audit.get("MODULE_NAME"));
						auditLog.setAuditMessage((String) audit.get("AUDIT_MESSAGE"));
						// Timestamp timestamp = (Timestamp) audit.get("AUDIT_TIMESTAMP");
						auditLog.setAuditTimeStamp((LocalDateTime) audit.get("AUDIT_TIMESTAMP"));
						auditLogs.add(auditLog);
					});
				}
			}
			user.setAssociatedStudies(associatedStudies);
			user.setAuditLogs(auditLogs);
		} catch (Exception e) {
			LOGGER.error("error while fetching getUserById", e);
			throw new ServiceExecutionException(e.getMessage());
		}

		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> getUserCount(UserFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getUserCount called");
		String counts;
		HashMap<String, Integer> map = new HashMap<>();
		try {
			counts = selectForObject(SQLConstants.USER_LIST_COUNT, String.class, filter.getSearchText(),
					filter.getStatusId(), filter.getRoleId());
			map = mapper.readValue(counts, HashMap.class);
		} catch (Exception e) {
			LOGGER.error("error while fetching getUserCount", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<User> getAllUsers(UserFilter filter) throws ServiceExecutionException {
		List<User> users = new ArrayList<>();
		LOGGER.debug("getAllUsers called");
		try {
			jdbcTemplate.query(SQLConstants.USER_GET_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					User user = new User();
					user.setSlNumber(rs.getInt("slNo"));
					user.setUserId(rs.getInt("USER_ID"));
					user.setUserName(rs.getString("USER_NAME"));
					user.setFullName(rs.getString("FULL_NAME"));
					user.setEmail(rs.getString("EMAIL"));
					user.setPhoneNumber(rs.getString("PHONE_NUMBER"));
					user.setIsActive(rs.getBoolean("IS_ACTIVE"));
					if (rs.getString("ROLE_ID") != null) {
						user.setRoleIds(rs.getString("ROLE_ID"));
						user.setRoleName(rs.getString("ROLE_NAME"));
						user.setRoleTypeId(rs.getInt("ROLE_TYPE_ID"));
					}
					users.add(user);
				}
			}, filter.getStartIndex(), filter.getLimit(), filter.getOrder(), filter.getSortBy(), filter.getSearchText(),
					filter.getStatusId(), filter.getRoleId());

		} catch (Exception e) {
			LOGGER.error("error while fetching getAllUsers", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return users;
	}

	@Override
	public List<String> getPasswordHistoryById(int userId) {
		List<String> passwords = new ArrayList<>();
		LOGGER.debug("getPasswordHistoryById called");
		try {
			jdbcTemplate.query(SQLConstants.USER_GET_PASSWORD_HISTORY_BY_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					passwords.add(rs.getString("USER_PASSWORD"));
				}
			}, userId);
			LOGGER.debug("getPasswordHistoryById completed successfully");
		} catch (Exception e) {
			LOGGER.error("error while fetching getPasswordHistoryById", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return passwords;
	}

	@Override
	public User updateUserProfile(User user) throws ServiceExecutionException {
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_user_id", user.getUserId());
		inputParams.put("p_full_name", user.getFullName());
		inputParams.put("p_modified_by", user.getModifiedBy());
		try {
			Map<String, Object> outParams = callStoredProcedure(SQLConstants.USER_PROFILE_UPDATE, inputParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isNotEmpty(errorMsg) || statusFlag < NumberUtils.INTEGER_ONE) {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing updateUser ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return user;
	}

}
