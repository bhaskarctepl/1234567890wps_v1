package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class PointsAccumulatedReportFilter extends BaseFilter {
	@ApiParam(name = "petId", value = "Pet Id key holds Pet Id.")
	@QueryParam("petId")
	private String petId;

	@ApiParam(name = "petParentId", value = "petParentId key holds Pet Parent Id Id.")
	@QueryParam("petParentId")
	private String petParentId;

	public PointsAccumulatedReportFilter() {

	}

	public PointsAccumulatedReportFilter(String petId, String petParentId) {
		super();
		this.petId = petId;
		this.petParentId = petParentId;
	}

	public String getPetId() {
		return petId;
	}

	public void setPetId(String petId) {
		this.petId = petId;
	}

	public String getPetParentId() {
		return petParentId;
	}

	public void setPetParentId(String petParentId) {
		this.petParentId = petParentId;
	}

}
