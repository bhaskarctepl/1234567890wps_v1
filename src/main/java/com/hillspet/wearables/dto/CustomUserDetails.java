package com.hillspet.wearables.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomUserDetails extends org.springframework.security.core.userdetails.User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer userId;
	private String userName;
	private String fullName;
	private String email;
	private String roleIds;
	private String roleName;
	private Integer roleTypeId;
	private String roleTypeName;
	private List<Menu> rolePermissions;
	private Boolean isActive;
	private Boolean needChangePwd;
	private Boolean isSuperAdmin;

	public CustomUserDetails(User user) {
		super(user.getUserName(), user.getPassword(), user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName())).collect(Collectors.toList()));
		this.userId = user.getUserId();
		this.userName = user.getUserName();
		this.fullName = user.getFullName();
		this.email = user.getEmail();
		this.roleIds = user.getRoleIds();
		this.roleName = user.getRoleName();
		this.roleTypeId = user.getRoleTypeId();
		this.roleTypeName = user.getRoleTypeName();
		this.rolePermissions = user.getRolePermissions();
		this.isActive = user.getIsActive();
		this.needChangePwd = user.getNeedChangePwd();
		this.isSuperAdmin = user.getIsSuperAdmin();
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getRoleTypeId() {
		return roleTypeId;
	}

	public void setRoleTypeId(Integer roleTypeId) {
		this.roleTypeId = roleTypeId;
	}

	public String getRoleTypeName() {
		return roleTypeName;
	}

	public void setRoleTypeName(String roleTypeName) {
		this.roleTypeName = roleTypeName;
	}

	public List<Menu> getRolePermissions() {
		return rolePermissions;
	}

	public void setRolePermissions(List<Menu> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public boolean isEnabled() {
		return this.isActive;
	}

	public Boolean getNeedChangePwd() {
		return needChangePwd;
	}

	public void setNeedChangePwd(Boolean needChangePwd) {
		this.needChangePwd = needChangePwd;
	}

	public Boolean getIsSuperAdmin() {
		return isSuperAdmin;
	}

	public void setIsSuperAdmin(Boolean isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

}