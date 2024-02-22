package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class PetEnthusiasmFilter extends BaseFilter {

	@QueryParam("petId")
	@ApiParam(name = "petId", type = "integer", value = "Search based on pet id")
	private int petId;

	@QueryParam("enthusiasmScaleId")
	@ApiParam(name = "enthusiasmScaleId", type = "integer", value = "Search based on enthusiasm scale drop down")
	private int enthusiasmScaleId;

	@QueryParam("feedingTimeId")
	@ApiParam(name = "feedingTimeId", type = "integer", value = "Search based on feeding time drop down")
	private int feedingTimeId;

	@QueryParam("startDate")
	@ApiParam(name = "startDate", type = "String", value = "Search based on feeding time date from")
	private String startDate;

	@QueryParam("endDate")
	@ApiParam(name = "endDate", type = "String", value = "Search based on feeding time date to")
	private String endDate;

	public PetEnthusiasmFilter() {

	}

	public PetEnthusiasmFilter(int petId, int enthusiasmScaleId, int feedingTimeId, String startDate, String endDate) {
		super();
		this.petId = petId;
		this.enthusiasmScaleId = enthusiasmScaleId;
		this.feedingTimeId = feedingTimeId;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	public int getEnthusiasmScaleId() {
		return enthusiasmScaleId;
	}

	public void setEnthusiasmScaleId(int enthusiasmScaleId) {
		this.enthusiasmScaleId = enthusiasmScaleId;
	}

	public int getFeedingTimeId() {
		return feedingTimeId;
	}

	public void setFeedingTimeId(int feedingTimeId) {
		this.feedingTimeId = feedingTimeId;
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
