package com.hillspet.wearables.request;

import io.swagger.annotations.ApiModelProperty;

public class ImageScoringAssociated {

	@ApiModelProperty(value = "studyImageScoringId", required = false)
	private Integer studyImageScoringConfigId;

	private Integer studyId;

	private Integer phaseId;

	@ApiModelProperty(value = "imageScoringId", required = true)
	private Integer imageScoringId;

	@ApiModelProperty(value = "imageScaleName", required = false)
	private String imageScaleName;

	@ApiModelProperty(value = "frequencyId", required = true)
	private Integer frequencyId;

	@ApiModelProperty(value = "startDate", required = true)
	private String startDate;

	@ApiModelProperty(value = "endDate", required = true)
	private String endDate;

	@ApiModelProperty(value = "frequencyName", required = false)
	private String frequency;

	public Integer getStudyImageScoringConfigId() {
		return studyImageScoringConfigId;
	}

	public void setStudyImageScoringConfigId(Integer studyImageScoringConfigId) {
		this.studyImageScoringConfigId = studyImageScoringConfigId;
	}

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

	public Integer getImageScoringId() {
		return imageScoringId;
	}

	public void setImageScoringId(Integer imageScoringId) {
		this.imageScoringId = imageScoringId;
	}

	public String getImageScaleName() {
		return imageScaleName;
	}

	public void setImageScaleName(String imageScaleName) {
		this.imageScaleName = imageScaleName;
	}

	public Integer getFrequencyId() {
		return frequencyId;
	}

	public void setFrequencyId(Integer frequencyId) {
		this.frequencyId = frequencyId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

}
