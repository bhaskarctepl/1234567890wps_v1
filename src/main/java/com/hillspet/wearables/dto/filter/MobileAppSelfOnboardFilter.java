package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class MobileAppSelfOnboardFilter extends BaseFilter {

	@QueryParam("petName")
	@ApiParam(name = "petName", value = "Search pet by pet name", required = false)
	private String petName;

	@QueryParam("petParentName")
	@ApiParam(name = "petParentName", value = "Search pet by Pet Parent Name", required = false)
	private String petParentName;

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getPetParentName() {
		return petParentName;
	}

	public void setPetParentName(String petParentName) {
		this.petParentName = petParentName;
	}

}
