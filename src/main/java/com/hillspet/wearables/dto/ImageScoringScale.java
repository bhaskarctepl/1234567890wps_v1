package com.hillspet.wearables.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageScoringScale extends BaseDTO {

	private Integer imageScoringScaleId;
	private String imageScaleName;

	private Integer classificationId;
	private String classification;
	private Integer scoringTypeId;
	private String scoringType;
	private Integer speciesId;
	private String speciesName;
	private Integer statusId;
	private Boolean isPublished;
	private Integer noOfScales;

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

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public Integer getScoringTypeId() {
		return scoringTypeId;
	}

	public void setScoringTypeId(Integer scoringTypeId) {
		this.scoringTypeId = scoringTypeId;
	}

	public String getScoringType() {
		return scoringType;
	}

	public void setScoringType(String scoringType) {
		this.scoringType = scoringType;
	}

	public Integer getSpeciesId() {
		return speciesId;
	}

	public void setSpeciesId(Integer speciesId) {
		this.speciesId = speciesId;
	}

	public String getSpeciesName() {
		return speciesName;
	}

	public void setSpeciesName(String speciesName) {
		this.speciesName = speciesName;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public Boolean getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}

	public Integer getNoOfScales() {
		return noOfScales;
	}

	public void setNoOfScales(Integer noOfScales) {
		this.noOfScales = noOfScales;
	}

	public List<ImageScoringScaleDetails> getImageScoringScaleDetails() {
		return imageScoringScaleDetails;
	}

	public void setImageScoringScaleDetails(List<ImageScoringScaleDetails> imageScoringScaleDetails) {
		this.imageScoringScaleDetails = imageScoringScaleDetails;
	}

}
