package com.hillspet.wearables.dto;

public class QuestionSliderType {
	private Integer floor;
	private String floorDescription;
	private Integer ceil;
	private String ceilDescription;
	private Integer tickStep;
	private Boolean isVerticalScale;
	private Boolean isContinuousScale;

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public Integer getCeil() {
		return ceil;
	}

	public void setCeil(Integer ceil) {
		this.ceil = ceil;
	}

	public Integer getTickStep() {
		return tickStep;
	}

	public void setTickStep(Integer tickStep) {
		this.tickStep = tickStep;
	}

	public Boolean getIsVerticalScale() {
		return isVerticalScale;
	}

	public void setIsVerticalScale(Boolean isVerticalScale) {
		this.isVerticalScale = isVerticalScale;
	}

	public Boolean getIsContinuousScale() {
		return isContinuousScale;
	}

	public void setIsContinuousScale(Boolean isContinuousScale) {
		this.isContinuousScale = isContinuousScale;
	}

	public String getFloorDescription() {
		return floorDescription;
	}

	public void setFloorDescription(String floorDescription) {
		this.floorDescription = floorDescription;
	}

	public String getCeilDescription() {
		return ceilDescription;
	}

	public void setCeilDescription(String ceilDescription) {
		this.ceilDescription = ceilDescription;
	}

}
