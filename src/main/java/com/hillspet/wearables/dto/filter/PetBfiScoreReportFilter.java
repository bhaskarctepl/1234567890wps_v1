package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class PetBfiScoreReportFilter extends BaseFilter {
	@ApiParam(name = "petId", value = "Pet Id key holds Pet Id.")
	@QueryParam("petId")
	private String petId;

	@ApiParam(name = "petParentId", value = "petParentId key holds Pet Parent Id Id.")
	@QueryParam("petParentId")
	private String petParentId;

	@ApiParam(name = "scorerId", value = "scorerId key holds Pet Parent Id Id.")
	@QueryParam("scorerId")
	private String scorerId;

	@ApiParam(name = "startDate", value = "Start Date is the first date component value of date range")
	@QueryParam("startDate")
	private String startDate;

	@ApiParam(name = "endDate", value = "End Date is the second date component value of date range")
	@QueryParam("endDate")
	private String endDate;

	public PetBfiScoreReportFilter() {

	}

	public PetBfiScoreReportFilter(String petId, String petParentId, String scorerId, String startDate,
			String endDate) {
		super();
		this.petId = petId;
		this.petParentId = petParentId;
		this.scorerId = scorerId;
		this.startDate = startDate;
		this.endDate = endDate;
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

	public String getScorerId() {
		return scorerId;
	}

	public void setScorerId(String scorerId) {
		this.scorerId = scorerId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
