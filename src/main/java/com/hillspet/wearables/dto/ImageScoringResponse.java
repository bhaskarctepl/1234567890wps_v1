package com.hillspet.wearables.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

/**
 * @author yreddy
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Contains all information that needed to get Pet Image Scale Response Details", value = "ImageScoringResponse")
@JsonInclude(value = Include.NON_NULL)
public class ImageScoringResponse extends BaseDTO {

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
	private String petImagePath;
	private String petImgThumbnailPath;
	private String scaleImagePath;
	private LocalDate scheduleDate;
	private String petName;
	private String imageScoringId;

	private LocalDate fromDate;
	private LocalDate toDate;
	private LocalDate submittedDate;
	private LocalDate dueDate;
	private String status;

	private Boolean isVipPetParent;

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

	public LocalDate getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(LocalDate scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getImageScoringId() {
		return imageScoringId;
	}

	public void setImageScoringId(String imageScoringId) {
		this.imageScoringId = imageScoringId;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public LocalDate getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(LocalDate submittedDate) {
		this.submittedDate = submittedDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getIsVipPetParent() {
		return isVipPetParent;
	}

	public void setIsVipPetParent(Boolean isVipPetParent) {
		this.isVipPetParent = isVipPetParent;
	}
}
