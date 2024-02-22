package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class PetImageScaleFilter extends BaseFilter {

	@QueryParam("petId")
	@ApiParam(name = "petId", type = "integer", value = "Search based on pet id")
	private int petId;

	@QueryParam("imageScaleId")
	@ApiParam(name = "imageScaleId", type = "integer", value = "Search based on image scale drop down")
	private int imageScaleId;

	@QueryParam("scoringTypeId")
	@ApiParam(name = "scoringTypeId", type = "integer", value = "Search based on scoring Type drop down")
	private int scoringTypeId;

	@QueryParam("startDate")
	@ApiParam(name = "startDate", type = "String", value = "Search based on image score time date from")
	private String startDate;

	@QueryParam("endDate")
	@ApiParam(name = "endDate", type = "String", value = "Search based on image score time date to")
	private String endDate;

	public PetImageScaleFilter() {

	}

	public PetImageScaleFilter(int petId, int imageScaleId, int scoringTypeId, String startDate, String endDate) {
		super();
		this.petId = petId;
		this.imageScaleId = imageScaleId;
		this.scoringTypeId = scoringTypeId;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	public int getImageScaleId() {
		return imageScaleId;
	}

	public void setImageScaleId(int imageScaleId) {
		this.imageScaleId = imageScaleId;
	}

	public int getScoringTypeId() {
		return scoringTypeId;
	}

	public void setScoringTypeId(int scoringTypeId) {
		this.scoringTypeId = scoringTypeId;
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
