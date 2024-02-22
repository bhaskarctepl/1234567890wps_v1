package com.hillspet.wearables.dto;

import java.sql.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PointTrackerCampaignActivities {

	private Integer trackerPetPointsId;
	private String petName;
	private String studyName;
	private String obseravattion;
	private String activity;
	private String behavior;
	private String media;
	private String status;
	private Date createdDate;
	private Integer petId;
	private Integer studyId;
	private Integer points;
	private Integer activityId;
	private Integer metricId;
	private Integer statusId;
	private String imageURL;
	private String videoURL;
	private Integer questionnaireRespId;
	private Integer behaviorId;
	private Integer speciesId;

	public Integer getQuestionnaireRespId() {
		return questionnaireRespId;
	}

	public void setQuestionnaireRespId(Integer questionnaireRespId) {
		this.questionnaireRespId = questionnaireRespId;
	}

	public Integer getTrackerPetPointsId() {
		return trackerPetPointsId;
	}

	public void setTrackerPetPointsId(Integer trackerPetPointsId) {
		this.trackerPetPointsId = trackerPetPointsId;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getVideoURL() {
		return videoURL;
	}

	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

	public String getObseravattion() {
		return obseravattion;
	}

	public void setObseravattion(String obseravattion) {
		this.obseravattion = obseravattion;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public Integer getMetricId() {
		return metricId;
	}

	public void setMetricId(Integer metricId) {
		this.metricId = metricId;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getBehavior() {
		return behavior;
	}

	public void setBehavior(String behavior) {
		this.behavior = behavior;
	}

	public Integer getBehaviorId() {
		return behaviorId;
	}

	public void setBehaviorId(Integer behaviorId) {
		this.behaviorId = behaviorId;
	}

	public Integer getSpeciesId() {
		return speciesId;
	}

	public void setSpeciesId(Integer speciesId) {
		this.speciesId = speciesId;
	}

}
