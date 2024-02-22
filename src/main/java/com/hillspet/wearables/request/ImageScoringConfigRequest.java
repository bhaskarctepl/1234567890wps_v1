package com.hillspet.wearables.request;

import java.util.List;

import javax.ws.rs.PathParam;

import io.swagger.annotations.ApiModelProperty;

public class ImageScoringConfigRequest {

	@ApiModelProperty(value = "studyId", required = true)
	@PathParam("studyId")
	private Integer studyId;

	@ApiModelProperty(value = "phaseId", required = true)
	@PathParam("phaseId")
	private Integer phaseId;

	@ApiModelProperty(value = "imageScoringSaclesAssociated", required = false)
	private List<ImageScoringAssociated> imageScoringSaclesAssociated;

	private Integer userId;

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public Integer getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(Integer phaseId) {
		this.phaseId = phaseId;
	}

	public List<ImageScoringAssociated> getImageScoringSaclesAssociated() {
		return imageScoringSaclesAssociated;
	}

	public void setImageScoringSaclesAssociated(List<ImageScoringAssociated> imageScoringSaclesAssociated) {
		this.imageScoringSaclesAssociated = imageScoringSaclesAssociated;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
