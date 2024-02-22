package com.hillspet.wearables.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Contains all information that needed to extend phase", value = "ExtendPhase")
@JsonInclude(value = Include.NON_NULL)
public class ExtendPhase {
	
	private int petId;
	private int extendDays;
	private String reason;
	private int treatmentGroupId;
	private int petStudyId;
	
	
	public int getPetId() {
		return petId;
	}
	public void setPetId(int petId) {
		this.petId = petId;
	}
	public int getExtendDays() {
		return extendDays;
	}
	public void setExtendDays(int extendDays) {
		this.extendDays = extendDays;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public int getTreatmentGroupId() {
		return treatmentGroupId;
	}
	public void setTreatmentGroupId(int treatmentGroupId) {
		this.treatmentGroupId = treatmentGroupId;
	}
	public int getPetStudyId() {
		return petStudyId;
	}
	public void setPetStudyId(int petStudyId) {
		this.petStudyId = petStudyId;
	}
	
	
	 
}
