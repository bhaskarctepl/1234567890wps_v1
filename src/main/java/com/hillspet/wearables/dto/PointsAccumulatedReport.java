package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PointsAccumulatedReport extends BaseDTO {

	private String petName;
	private String petParentName;
	private Integer pointsAccumulated;
	private Integer pointsRedeemed;
	private Integer pointsAvailable;
	
	private int petId;
	
	

	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

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

	public Integer getPointsAccumulated() {
		return pointsAccumulated;
	}

	public void setPointsAccumulated(Integer pointsAccumulated) {
		this.pointsAccumulated = pointsAccumulated;
	}

	public Integer getPointsRedeemed() {
		return pointsRedeemed;
	}

	public void setPointsRedeemed(Integer pointsRedeemed) {
		this.pointsRedeemed = pointsRedeemed;
	}

	public Integer getPointsAvailable() {
		return pointsAvailable;
	}

	public void setPointsAvailable(Integer pointsAvailable) {
		this.pointsAvailable = pointsAvailable;
	}

}
