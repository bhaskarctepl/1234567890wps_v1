package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Menu extends BaseDTO {

	private Integer menuId;
	private String menuName;
	private Integer parentMenuId;
	private String parentMenuName;
	private Integer menuActionId;
	private String menuActionName;

	public Menu() {

	}

	public Menu(Integer menuId, String menuName, Integer parentMenuId, String parentMenuName, Integer menuActionId, String menuActionName) {
		this.menuId = menuId;
		this.menuName = menuName;
		this.parentMenuId = parentMenuId;
		this.parentMenuName = parentMenuName;
		this.menuActionId = menuActionId;
		this.menuActionName = menuActionName;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Integer getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(Integer parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public Integer getMenuActionId() {
		return menuActionId;
	}

	public void setMenuActionId(Integer menuActionId) {
		this.menuActionId = menuActionId;
	}

	public String getMenuActionName() {
		return menuActionName;
	}

	public void setMenuActionName(String menuActionName) {
		this.menuActionName = menuActionName;
	}

	public String getParentMenuName() {
		return parentMenuName;
	}

	public void setParentMenuName(String parentMenuName) {
		this.parentMenuName = parentMenuName;
	}
}
