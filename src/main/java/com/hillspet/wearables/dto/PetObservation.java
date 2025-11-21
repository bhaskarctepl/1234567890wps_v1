package com.hillspet.wearables.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetObservation extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer petId;
	private Integer activityTypeId;
	private String activityType;

	private Integer behaviorTypeId;
	private String behaviorType;
	private Integer behaviorId;
	private String behavior;
	private LocalDateTime modifiedDateTime;

	private String observationText;
	private LocalDateTime observationTime;
	private String ImagePath;
	private List<String> imageList;
	private String videoURL;
	private List<String> videoList;
	private String videoThumbnailURL;
	private List<String> videoThumbnailList;
	private String studyNames;
	private List<ObservationVideo> observationVideos;

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}

	public Integer getActivityTypeId() {
		return activityTypeId;
	}

	public void setActivityTypeId(Integer activityTypeId) {
		this.activityTypeId = activityTypeId;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getObservationText() {
		return observationText;
	}

	public void setObservationText(String observationText) {
		this.observationText = observationText;
	}

	public LocalDateTime getObservationTime() {
		return observationTime;
	}

	public void setObservationTime(LocalDateTime observationTime) {
		this.observationTime = observationTime;
	}

	public String getImagePath() {
		return ImagePath;
	}

	public void setImagePath(String imagePath) {
		ImagePath = imagePath;
	}

	public List<String> getImageList() {
		return imageList;
	}

	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}

	public String getVideoURL() {
		return videoURL;
	}

	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	}

	public List<String> getVideoList() {
		return videoList;
	}

	public void setVideoList(List<String> videoList) {
		this.videoList = videoList;
	}

	public String getVideoThumbnailURL() {
		return videoThumbnailURL;
	}

	public void setVideoThumbnailURL(String videoThumbnailURL) {
		this.videoThumbnailURL = videoThumbnailURL;
	}

	public List<String> getVideoThumbnailList() {
		return videoThumbnailList;
	}

	public void setVideoThumbnailList(List<String> videoThumbnailList) {
		this.videoThumbnailList = videoThumbnailList;
	}

	public String getStudyNames() {
		return studyNames;
	}

	public void setStudyNames(String studyNames) {
		this.studyNames = studyNames;
	}

	public List<ObservationVideo> getObservationVideos() {
		return observationVideos;
	}

	public void setObservationVideos(List<ObservationVideo> observationVideos) {
		this.observationVideos = observationVideos;
	}

	public Integer getBehaviorTypeId() {
		return behaviorTypeId;
	}

	public void setBehaviorTypeId(Integer behaviorTypeId) {
		this.behaviorTypeId = behaviorTypeId;
	}

	public String getBehaviorType() {
		return behaviorType;
	}

	public void setBehaviorType(String behaviorType) {
		this.behaviorType = behaviorType;
	}

	public Integer getBehaviorId() {
		return behaviorId;
	}

	public void setBehaviorId(Integer behaviorId) {
		this.behaviorId = behaviorId;
	}

	public String getBehavior() {
		return behavior;
	}

	public void setBehavior(String behavior) {
		this.behavior = behavior;
	}

	public LocalDateTime getModifiedDateTime() {
		return modifiedDateTime;
	}

	public void setModifiedDateTime(LocalDateTime modifiedDateTime) {
		this.modifiedDateTime = modifiedDateTime;
	}

}
