package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

/**
 * @author sgorle
 *
 */
@ApiModel(description = "Contains all information that needed to create Pet Image Scale Details", value = "PetImageScale")
@JsonInclude(value = Include.NON_NULL)
public class PetImageScale extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer petId;
	private String scoreType;
	private String scaleName;
	private String score;
	private String imageLabel;
	private String petParentName;
	private String submittedOn;
	private String petImagePath;
	private String petImgThumbnailPath;
	private String scaleImagePath;

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}

	public String getScoreType() {
		return scoreType;
	}

	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}

	public String getScaleName() {
		return scaleName;
	}

	public void setScaleName(String scaleName) {
		this.scaleName = scaleName;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getImageLabel() {
		return imageLabel;
	}

	public void setImageLabel(String imageLabel) {
		this.imageLabel = imageLabel;
	}

	public String getPetParentName() {
		return petParentName;
	}

	public void setPetParentName(String petParentName) {
		this.petParentName = petParentName;
	}

	public String getSubmittedOn() {
		return submittedOn;
	}

	public void setSubmittedOn(String submittedOn) {
		this.submittedOn = submittedOn;
	}

	public String getPetImagePath() {
		return petImagePath;
	}

	public void setPetImagePath(String petImagePath) {
		this.petImagePath = petImagePath;
	}

	public String getPetImgThumbnailPath() {
		return petImgThumbnailPath;
	}

	public void setPetImgThumbnailPath(String petImgThumbnailPath) {
		this.petImgThumbnailPath = petImgThumbnailPath;
	}

	public String getScaleImagePath() {
		return scaleImagePath;
	}

	public void setScaleImagePath(String scaleImagePath) {
		this.scaleImagePath = scaleImagePath;
	}
}
