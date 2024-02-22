package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Phase {

	@ApiModelProperty(value = "studyPhaseId", required = true)
	private Integer studyPhaseId;

	@ApiModelProperty(value = "studyPhase", required = true)
	private String studyPhase;

	@ApiModelProperty(value = "duration", required = true)
	private Integer duration;

	@ApiModelProperty(value = "durationUnitId", required = true)
	private Integer durationUnitId;

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

}
