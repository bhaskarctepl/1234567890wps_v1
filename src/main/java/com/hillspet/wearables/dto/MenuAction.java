package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuAction extends BaseDTO {

	private Integer menuActionId;
	private String menuActionName;

	public MenuAction() {

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

}
