package com.hillspet.wearables.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetIBWHistoryDTO extends BaseDTO {

	private int petIBWId;
	private int petId;
	private double ibwLbs;
	private double ibwKgs;
	private String ibwCalSource;
	private double correctedIBWLbs;
	private double correctedIBWKgs;
	private Integer correctedIBWUnitId;
	private String correctedIBWUnit;
	private String comment;
	private LocalDateTime recordedDate;
	private LocalDateTime correctedDate;

	private double graphIBWLbs;
	private double graphIBWKgs;

	public int getPetIBWId() {
		return petIBWId;
	}

	public void setPetIBWId(int petIBWId) {
		this.petIBWId = petIBWId;
	}

	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	public double getIbwLbs() {
		return ibwLbs;
	}

	public void setIbwLbs(double ibwLbs) {
		this.ibwLbs = ibwLbs;
	}

	public double getIbwKgs() {
		return ibwKgs;
	}

	public void setIbwKgs(double ibwKgs) {
		this.ibwKgs = ibwKgs;
	}

	public String getIbwCalSource() {
		return ibwCalSource;
	}

	public void setIbwCalSource(String ibwCalSource) {
		this.ibwCalSource = ibwCalSource;
	}

	public double getCorrectedIBWLbs() {
		return correctedIBWLbs;
	}

	public void setCorrectedIBWLbs(double correctedIBWLbs) {
		this.correctedIBWLbs = correctedIBWLbs;
	}

	public double getCorrectedIBWKgs() {
		return correctedIBWKgs;
	}

	public void setCorrectedIBWKgs(double correctedIBWKgs) {
		this.correctedIBWKgs = correctedIBWKgs;
	}

	public Integer getCorrectedIBWUnitId() {
		return correctedIBWUnitId;
	}

	public void setCorrectedIBWUnitId(Integer correctedIBWUnitId) {
		this.correctedIBWUnitId = correctedIBWUnitId;
	}

	public String getCorrectedIBWUnit() {
		return correctedIBWUnit;
	}

	public void setCorrectedIBWUnit(String correctedIBWUnit) {
		this.correctedIBWUnit = correctedIBWUnit;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDateTime getRecordedDate() {
		return recordedDate;
	}

	public void setRecordedDate(LocalDateTime recordedDate) {
		this.recordedDate = recordedDate;
	}

	public LocalDateTime getCorrectedDate() {
		return correctedDate;
	}

	public void setCorrectedDate(LocalDateTime correctedDate) {
		this.correctedDate = correctedDate;
	}

	public double getGraphIBWLbs() {
		return graphIBWLbs;
	}

	public void setGraphIBWLbs(double graphIBWLbs) {
		this.graphIBWLbs = graphIBWLbs;
	}

	public double getGraphIBWKgs() {
		return graphIBWKgs;
	}

	public void setGraphIBWKgs(double graphIBWKgs) {
		this.graphIBWKgs = graphIBWKgs;
	}

}
