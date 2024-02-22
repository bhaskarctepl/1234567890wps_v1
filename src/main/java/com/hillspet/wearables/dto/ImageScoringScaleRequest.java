package com.hillspet.wearables.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Contains all information that needed to create Image Scoring Scale", value = "ImageScoringScaleRequest")
@JsonInclude(value = Include.NON_NULL)
public class ImageScoringScaleRequest {

	private Integer imageScoringScaleId;
	@ApiModelProperty(value = "imageScaleName", required = true, example = "BCS Scoring")
	private String imageScaleName;

	private Integer classificationId;
	private Integer scoringTypeId;
	private Integer speciesId;
	private Integer statusId;
	private Integer createdBy;

	private List<ImageScoringScaleDetails> imageScoringScaleDetails;

	public Integer getImageScoringScaleId() {
		return imageScoringScaleId;
	}

	public void setImageScoringScaleId(Integer imageScoringScaleId) {
		this.imageScoringScaleId = imageScoringScaleId;
	}

	public String getImageScaleName() {
		return imageScaleName;
	}

	public void setImageScaleName(String imageScaleName) {
		this.imageScaleName = imageScaleName;
	}

	public Integer getClassificationId() {
		return classificationId;
	}

	public void setClassificationId(Integer classificationId) {
		this.classificationId = classificationId;
	}

	public Integer getScoringTypeId() {
		return scoringTypeId;
	}

	public void setScoringTypeId(Integer scoringTypeId) {
		this.scoringTypeId = scoringTypeId;
	}

	public Integer getSpeciesId() {
		return speciesId;
	}

	public void setSpeciesId(Integer speciesId) {
		this.speciesId = speciesId;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public List<ImageScoringScaleDetails> getImageScoringScaleDetails() {
		return imageScoringScaleDetails;
	}

	public void setImageScoringScaleDetails(List<ImageScoringScaleDetails> imageScoringScaleDetails) {
		this.imageScoringScaleDetails = imageScoringScaleDetails;
	}

}
