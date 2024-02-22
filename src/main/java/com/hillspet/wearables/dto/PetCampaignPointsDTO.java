package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetCampaignPointsDTO {
	
	private Integer totalEarnedPoints;
	private Integer observations;
	private Integer questionnaire;
	private Integer feedback;
	private Integer videos;
	private Integer images;
	private Integer pointsUtilized;
	private Integer redeemablePoints;
	
	
	public Integer getPointsUtilized() {
		return pointsUtilized;
	}
	public void setPointsUtilized(Integer pointsUtilized) {
		this.pointsUtilized = pointsUtilized;
	}
	public Integer getRedeemablePoints() {
		return redeemablePoints;
	}
	public void setRedeemablePoints(Integer redeemablePoints) {
		this.redeemablePoints = redeemablePoints;
	}
	public Integer getTotalEarnedPoints() {
		return totalEarnedPoints;
	}
	public void setTotalEarnedPoints(Integer totalEarnedPoints) {
		this.totalEarnedPoints = totalEarnedPoints;
	}
	public Integer getObservations() {
		return observations;
	}
	public void setObservations(Integer observations) {
		this.observations = observations;
	}
	public Integer getQuestionnaire() {
		return questionnaire;
	}
	public void setQuestionnaire(Integer questionnaire) {
		this.questionnaire = questionnaire;
	}
	public Integer getFeedback() {
		return feedback;
	}
	public void setFeedback(Integer feedback) {
		this.feedback = feedback;
	}
	public Integer getVideos() {
		return videos;
	}
	public void setVideos(Integer videos) {
		this.videos = videos;
	}
	public Integer getImages() {
		return images;
	}
	public void setImages(Integer images) {
		this.images = images;
	}
	
	
}
