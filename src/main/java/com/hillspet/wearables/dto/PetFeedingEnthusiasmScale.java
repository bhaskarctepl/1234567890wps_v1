package com.hillspet.wearables.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

/**
 * @author sgorle
 *
 */
@ApiModel(description = "Contains all information that needed to create Pet Feeding Enthusiasm Scale Details", value = "PetFeedingEnthusiasmScale")
@JsonInclude(value = Include.NON_NULL)
public class PetFeedingEnthusiasmScale extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer feedingEnthusiasmScaleId;
	private Integer petId;
	private Integer enthusiasmScaleId;
	private Integer feedingTimeId;
	private LocalDateTime feedingDate;
	private Integer petParentId;
	private String petParentName;
	private String enthusiasmScale;
	private String enthusiasmScaleDesc;
	private String feedingTime;

	public Integer getFeedingEnthusiasmScaleId() {
		return feedingEnthusiasmScaleId;
	}

	public void setFeedingEnthusiasmScaleId(Integer feedingEnthusiasmScaleId) {
		this.feedingEnthusiasmScaleId = feedingEnthusiasmScaleId;
	}

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}

	public Integer getEnthusiasmScaleId() {
		return enthusiasmScaleId;
	}

	public void setEnthusiasmScaleId(Integer enthusiasmScaleId) {
		this.enthusiasmScaleId = enthusiasmScaleId;
	}

	public Integer getFeedingTimeId() {
		return feedingTimeId;
	}

	public void setFeedingTimeId(Integer feedingTimeId) {
		this.feedingTimeId = feedingTimeId;
	}

	public LocalDateTime getFeedingDate() {
		return feedingDate;
	}

	public void setFeedingDate(LocalDateTime feedingDate) {
		this.feedingDate = feedingDate;
	}

	public Integer getPetParentId() {
		return petParentId;
	}

	public void setPetParentId(Integer petParentId) {
		this.petParentId = petParentId;
	}

	public String getPetParentName() {
		return petParentName;
	}

	public void setPetParentName(String petParentName) {
		this.petParentName = petParentName;
	}

	public String getEnthusiasmScale() {
		return enthusiasmScale;
	}

	public void setEnthusiasmScale(String enthusiasmScale) {
		this.enthusiasmScale = enthusiasmScale;
	}

	public String getFeedingTime() {
		return feedingTime;
	}

	public void setFeedingTime(String feedingTime) {
		this.feedingTime = feedingTime;
	}

	public String getEnthusiasmScaleDesc() {
		return enthusiasmScaleDesc;
	}

	public void setEnthusiasmScaleDesc(String enthusiasmScaleDesc) {
		this.enthusiasmScaleDesc = enthusiasmScaleDesc;
	}

}
