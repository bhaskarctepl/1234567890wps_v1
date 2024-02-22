package com.hillspet.wearables.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contains all information that needed to update pet ideal body weight", value = "UpdatePetIBWRequest")
@JsonInclude(value = Include.NON_NULL)
public class UpdatePetIBWRequest {

	@ApiModelProperty(value = "correctedIBW", required = true, example = "12.0")
	private double correctedIBW;

	@ApiModelProperty(value = "correctedIBWUnit", required = true, example = "1")
	private int correctedIBWUnit;

	@ApiModelProperty(value = "comment", required = true, example = "Wrong Calculation")
	private String comment;

	@ApiModelProperty(value = "date", required = true, example = "02-08-2024")
	private String date;

	@ApiModelProperty(value = "isLatest", required = true, example = "true")
	private boolean isLatest;

	public double getCorrectedIBW() {
		return correctedIBW;
	}

	public void setCorrectedIBW(double correctedIBW) {
		this.correctedIBW = correctedIBW;
	}

	public int getCorrectedIBWUnit() {
		return correctedIBWUnit;
	}

	public void setCorrectedIBWUnit(int correctedIBWUnit) {
		this.correctedIBWUnit = correctedIBWUnit;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public boolean isLatest() {
		return isLatest;
	}

	public void setLatest(boolean latest) {
		isLatest = latest;
	}
}
