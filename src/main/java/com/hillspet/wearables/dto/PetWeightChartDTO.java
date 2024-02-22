package com.hillspet.wearables.dto;

import java.time.LocalDate;

public class PetWeightChartDTO {

	private LocalDate date;
	private Double weightInKgs;
	private Double weightInLbs;
	private Double ibwInKgs;
	private Double ibwInLbs;

	public PetWeightChartDTO(LocalDate date, Double weightInKgs, Double weightInLbs, Double ibwInKgs, Double ibwInLbs) {
		super();
		this.date = date;
		this.weightInKgs = weightInKgs;
		this.weightInLbs = weightInLbs;
		this.ibwInKgs = ibwInKgs;
		this.ibwInLbs = ibwInLbs;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Double getWeightInKgs() {
		return weightInKgs;
	}

	public void setWeightInKgs(Double weightInKgs) {
		this.weightInKgs = weightInKgs;
	}

	public Double getWeightInLbs() {
		return weightInLbs;
	}

	public void setWeightInLbs(Double weightInLbs) {
		this.weightInLbs = weightInLbs;
	}

	public Double getIbwInKgs() {
		return ibwInKgs;
	}

	public void setIbwInKgs(Double ibwInKgs) {
		this.ibwInKgs = ibwInKgs;
	}

	public Double getIbwInLbs() {
		return ibwInLbs;
	}

	public void setIbwInLbs(Double ibwInLbs) {
		this.ibwInLbs = ibwInLbs;
	}

}
