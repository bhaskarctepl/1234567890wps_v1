package com.hillspet.wearables.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author sgorle
 *
 */
@ApiModel(description = "Contains all information that needed to create a cross over study", value = "AddCrossOverStudyRequest")
@JsonInclude(value = Include.NON_NULL)
public class AddCrossOverStudyRequest {

	@ApiModelProperty(value = "studyId", required = true, example = "12")
	private Integer studyId;

	@ApiModelProperty(value = "crossOverStudyName", required = true, example = "Clinical Study")
	private String crossOverStudyName;

	@ApiModelProperty(value = "crossOverStudyPhaseFrom", required = true, example = "1")
	private Integer crossOverStudyPhaseFrom;

	@ApiModelProperty(value = "days", required = true, example = "1")
	private Integer days;

	private Integer userId;

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public String getCrossOverStudyName() {
		return crossOverStudyName;
	}

	public void setCrossOverStudyName(String crossOverStudyName) {
		this.crossOverStudyName = crossOverStudyName;
	}

	public Integer getCrossOverStudyPhaseFrom() {
		return crossOverStudyPhaseFrom;
	}

	public void setCrossOverStudyPhaseFrom(Integer crossOverStudyPhaseFrom) {
		this.crossOverStudyPhaseFrom = crossOverStudyPhaseFrom;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
