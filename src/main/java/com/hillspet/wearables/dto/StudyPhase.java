package com.hillspet.wearables.dto;

import io.swagger.annotations.ApiModelProperty;

public class StudyPhase {

	@ApiModelProperty(value = "studyPhaseConfigId", required = false)
	private Integer studyPhaseConfigId;

	@ApiModelProperty(value = "studyPhaseId", required = true)
	private Integer studyPhaseId;

	private String studyPhase;

	@ApiModelProperty(value = "duration", required = true)
	private Integer duration;

	@ApiModelProperty(value = "durationUnitId", required = true)
	private Integer durationUnitId;

	@ApiModelProperty(value = "durationIndays", required = false)
	private Integer durationIndays;

	@ApiModelProperty(value = "isChecked", required = false)
	private Boolean isChecked;

	public Integer getStudyPhaseConfigId() {
		return studyPhaseConfigId;
	}

	public void setStudyPhaseConfigId(Integer studyPhaseConfigId) {
		this.studyPhaseConfigId = studyPhaseConfigId;
	}

	public Integer getStudyPhaseId() {
		return studyPhaseId;
	}

	public void setStudyPhaseId(Integer studyPhaseId) {
		this.studyPhaseId = studyPhaseId;
	}

	public String getStudyPhase() {
		return studyPhase;
	}

	public void setStudyPhase(String studyPhase) {
		this.studyPhase = studyPhase;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getDurationUnitId() {
		return durationUnitId;
	}

	public void setDurationUnitId(Integer durationUnitId) {
		this.durationUnitId = durationUnitId;
	}

	public Integer getDurationIndays() {
		return durationIndays;
	}

	public void setDurationIndays(Integer durationIndays) {
		this.durationIndays = durationIndays;
	}

	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}

}
