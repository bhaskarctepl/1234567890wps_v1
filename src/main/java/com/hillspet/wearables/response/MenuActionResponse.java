package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.MenuAction;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MenuActionResponse {

	private List<MenuAction> menuActions;

	@ApiModelProperty(value = "List of details for Menu Actions")
	public List<MenuAction> getMenuActions() {
		return menuActions;
	}

	public void setMenuActions(List<MenuAction> menuActions) {
		this.menuActions = menuActions;
	}

}
