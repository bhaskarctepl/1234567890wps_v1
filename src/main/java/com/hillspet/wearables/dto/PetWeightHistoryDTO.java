package com.hillspet.wearables.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetWeightHistoryDTO extends BaseDTO {
	private int petId;
	private int petWeightId;
	private double weightLbs;
	private double weightKgs;
	private LocalDateTime addDate;
	private String weightUnit;
	private boolean isActive;

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	public int getPetWeightId() {
		return petWeightId;
	}

	public void setPetWeightId(int petWeightId) {
		this.petWeightId = petWeightId;
	}

	public double getWeightLbs() {
		return weightLbs;
	}

	public void setWeightLbs(double weightLbs) {
		this.weightLbs = weightLbs;
	}

	public LocalDateTime getAddDate() {
		return addDate;
	}

	public void setAddDate(LocalDateTime addDate) {
		this.addDate = addDate;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public double getWeightKgs() {
		return weightKgs;
	}

	public void setWeightKgs(double weightKgs) {
		this.weightKgs = weightKgs;
	}

}
