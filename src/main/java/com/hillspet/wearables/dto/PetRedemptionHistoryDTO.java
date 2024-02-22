package com.hillspet.wearables.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetRedemptionHistoryDTO extends BaseDTO {
	private Integer petPointsId;
	private Integer petId;
	private Integer totalPoints;
	private Integer pointsRedeemed;
	private Integer balancePoints;
	private Integer redeemedByUserId;
	private String redeemedByUser;
	
	public Integer getPetPointsId() {
		return petPointsId;
	}
	public void setPetPointsId(Integer petPointsId) {
		this.petPointsId = petPointsId;
	}
	public Integer getPetId() {
		return petId;
	}
	public void setPetId(Integer petId) {
		this.petId = petId;
	}
	public Integer getTotalPoints() {
		return totalPoints;
	}
	public void setTotalPoints(Integer totalPoints) {
		this.totalPoints = totalPoints;
	}
	public Integer getPointsRedeemed() {
		return pointsRedeemed;
	}
	public void setPointsRedeemed(Integer pointsRedeemed) {
		this.pointsRedeemed = pointsRedeemed;
	}
	public Integer getBalancePoints() {
		return balancePoints;
	}
	public void setBalancePoints(Integer balancePoints) {
		this.balancePoints = balancePoints;
	}
	
	public String getRedeemedByUser() {
		return redeemedByUser;
	}
	public void setRedeemedByUser(String redeemedByUser) {
		this.redeemedByUser = redeemedByUser;
	}
	public Integer getRedeemedByUserId() {
		return redeemedByUserId;
	}
	public void setRedeemedByUserId(Integer redeemedByUserId) {
		this.redeemedByUserId = redeemedByUserId;
	}
	
	
}
