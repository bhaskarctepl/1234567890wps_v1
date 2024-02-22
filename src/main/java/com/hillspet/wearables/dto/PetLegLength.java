package com.hillspet.wearables.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Contains all information pet leg length history", value = "PetLegLength")
@JsonInclude(value = Include.NON_NULL)
public class PetLegLength {

	private int legLengthId;
	private double legLength;
	private int legLengthUnitId;
	private String legLengthUnit;
	private LocalDateTime capturedOn;

	public int getLegLengthId() {
		return legLengthId;
	}

	public void setLegLengthId(int legLengthId) {
		this.legLengthId = legLengthId;
	}

	public double getLegLength() {
		return legLength;
	}

	public void setLegLength(double legLength) {
		this.legLength = legLength;
	}

	public int getLegLengthUnitId() {
		return legLengthUnitId;
	}

	public void setLegLengthUnitId(int legLengthUnitId) {
		this.legLengthUnitId = legLengthUnitId;
	}

	public String getLegLengthUnit() {
		return legLengthUnit;
	}

	public void setLegLengthUnit(String legLengthUnit) {
		this.legLengthUnit = legLengthUnit;
	}

	public LocalDateTime getCapturedOn() {
		return capturedOn;
	}

	public void setCapturedOn(LocalDateTime capturedOn) {
		this.capturedOn = capturedOn;
	}

}
