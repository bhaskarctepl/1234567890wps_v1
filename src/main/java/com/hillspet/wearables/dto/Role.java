package com.hillspet.wearables.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role extends BaseDTO {

	private Integer roleId;
	private String roleName;

	private Integer roleTypeId;
	private String roleType;

	// list of menu name and menu action names for a role.
	private String permissions;

	private HashMap<Integer, Integer> permissionMap;

	List<Menu> menulist = new ArrayList<Menu>();
	List<MenuAction> menuActionlist = new ArrayList<MenuAction>();	//TODO: Check and delete this

	public Role() {

	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
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

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public HashMap<Integer, Integer> getPermissionMap() {
		return permissionMap;
	}

	public void setPermissionMap(HashMap<Integer, Integer> permissionMap) {
		this.permissionMap = permissionMap;
	}
	
	public List<Menu> getMenulist() {
		return menulist;
	}

	public void setMenulist(List<Menu> menulist) {
		this.menulist = menulist;
	}
	
	public List<MenuAction> getMenuActionlist() {
		return menuActionlist;
	}

	public void setMenuActionlist(List<MenuAction> menuActionlist) {
		this.menuActionlist = menuActionlist;
	}
	
}
