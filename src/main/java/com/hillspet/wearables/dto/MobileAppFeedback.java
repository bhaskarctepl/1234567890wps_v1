
/**
 * Created Date: Nov 9, 2020
 * Class Name  : MobileAppFeedback.java
 * Description : Description of the package.
 *
 * © Copyright 2008 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>        <Author>        <Date>        <Comments on Change>
 * ID                rmaram          Nov 9, 2020        Mentions the comments on change, for the new file it's not required.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.hillspet.wearables.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *MobileAppFeedback DTO.
 *
 * @author  rmaram
 * @since   w1.0
 * @version	w1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MobileAppFeedback  extends BaseDTO {

	private String studyName;
	private String petOwnerName;	
	private String petName;
	private LocalDateTime feedbackDate;
	private String pageName;
	private String deviceType;
	private String feedback;
	private String Plan;
	
	private int petId;
	private int petParentId;
	private int petStudyId;
	
	

	public int getPetId() {
		return petId;
	}
	public void setPetId(int petId) {
		this.petId = petId;
	}
	public int getPetParentId() {
		return petParentId;
	}
	public void setPetParentId(int petParentId) {
		this.petParentId = petParentId;
	}
	public int getPetStudyId() {
		return petStudyId;
	}
	public void setPetStudyId(int petStudyId) {
		this.petStudyId = petStudyId;
	}
	public String getStudyName() {
		return studyName;
	}
	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}
	public String getPetOwnerName() {
		return petOwnerName;
	}
	public void setPetOwnerName(String petOwnerName) {
		this.petOwnerName = petOwnerName;
	}
	public String getPetName() {
		return petName;
	}
	public void setPetName(String petName) {
		this.petName = petName;
	}
	public LocalDateTime getFeedbackDate() {
		return feedbackDate;
	}
	public void setFeedbackDate(LocalDateTime feedbackDate) {
		this.feedbackDate = feedbackDate;
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	public String getPlan() {
		return Plan;
	}
	public void setPlan(String plan) {
		Plan = plan;
	}
}
