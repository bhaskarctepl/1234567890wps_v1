package com.hillspet.wearables.request;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hillspet.wearables.dto.PreludeMandatory;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author sgorle
 *
 */
@ApiModel(description = "Contains all information that needed to create study", value = "StudyRequest")
@JsonInclude(value = Include.NON_NULL)
public class StudyRequest {

	@ApiModelProperty(value = "studyId", required = false, example = "1")
	private Integer studyId;

	@ApiModelProperty(value = "studyName", required = true, example = "Clinical Study")
	private String studyName;

	@ApiModelProperty(value = "principalInvestigator", required = false, example = "John")
	private String principalInvestigator;

	@ApiModelProperty(value = "startDate", required = true, example = "1")
	private LocalDate startDate;

	@ApiModelProperty(value = "endDate", required = false)
	private LocalDate endDate;

	@ApiModelProperty(value = "status", required = true, example = "1")
	private Integer status;

	@ApiModelProperty(value = "notes", required = false)
	private String notes;

	@ApiModelProperty(value = "plansSubscribed", required = true)
	private List<PlanSubscribed> plansSubscribed;

	@ApiModelProperty(value = "mobileAppConfigs", required = true)
	private List<Integer> mobileAppConfigs;

	@ApiModelProperty(value = "questionnairesAssociated", required = false)
	private List<QuestionnaireAssociated> questionnairesAssociated;

	@ApiModelProperty(value = "pushNotificationsAssociated", required = false)
	private List<PushNotificationAssociated> pushNotificationsAssociated;

	@ApiModelProperty(value = "isNotificationEnable", required = true, example = "1")
	private Integer isNotificationEnable;

	@ApiModelProperty(value = "isExternal", required = true, example = "1")
	private Integer isExternal;

	@ApiModelProperty(value = "description", required = false)
	private String description;

	@ApiModelProperty(value = "preludeAssociated", required = false)
	private List<PreludeAssociated> preludeAssociated;

	@ApiModelProperty(value = "ownerLastName", required = false)
	private String ownerLastName;

	@ApiModelProperty(value = "ownerEmail", required = false)
	private String ownerEmail;

	@ApiModelProperty(value = "patientId", required = false)
	private String patientId;

	@ApiModelProperty(value = "petName", required = false)
	private String petName;

	@ApiModelProperty(value = "preludeAssociated", required = false)
	private List<PreludeMandatory> preludeMandatory;

	@ApiModelProperty(value = "externalLink", required = false)
	private String externalLink;

	@ApiModelProperty(value = "weightUnit", required = false)
	private String weightUnit;

	@ApiModelProperty(value = "entsmScaleStartDate", required = false)
	private LocalDate entsmScaleStartDate;

	@ApiModelProperty(value = "entsmScaleEndDate", required = false)
	private LocalDate entsmScaleEndDate;

	@ApiModelProperty(value = "imageScoringSaclesAssociated", required = false)
	private List<ImageScoringAssociated> imageScoringSaclesAssociated;

	@ApiModelProperty(value = "activityFactorConfig", required = false)
	private ActivityFactorConfig activityFactorConfig;

	@ApiModelProperty(value = "algorithmId", required = false)
	private Integer algorithmId;

	@ApiModelProperty(value = "algorithmName", required = false)
	private String algorithmName;

	public String getExternalLink() {
		return externalLink;
	}

	public void setExternalLink(String externalLink) {
		this.externalLink = externalLink;
	}

	public List<PreludeMandatory> getPreludeMandatory() {
		return preludeMandatory;
	}

	public void setPreludeMandatory(List<PreludeMandatory> preludeMandatory) {
		this.preludeMandatory = preludeMandatory;
	}

	public String getOwnerLastName() {
		return ownerLastName;
	}

	public void setOwnerLastName(String ownerLastName) {
		this.ownerLastName = ownerLastName;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public List<PreludeAssociated> getPreludeAssociated() {
		return preludeAssociated;
	}

	public void setPreludeAssociated(List<PreludeAssociated> preludeAssociated) {
		this.preludeAssociated = preludeAssociated;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIsNotificationEnable() {
		return isNotificationEnable;
	}

	public Integer getIsExternal() {
		return isExternal;
	}

	public void setIsExternal(Integer isExternal) {
		this.isExternal = isExternal;
	}

	public void setIsNotificationEnable(Integer isNotificationEnable) {
		this.isNotificationEnable = isNotificationEnable;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

	public String getPrincipalInvestigator() {
		return principalInvestigator;
	}

	public void setPrincipalInvestigator(String principalInvestigator) {
		this.principalInvestigator = principalInvestigator;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<PlanSubscribed> getPlansSubscribed() {
		return plansSubscribed;
	}

	public void setPlansSubscribed(List<PlanSubscribed> plansSubscribed) {
		this.plansSubscribed = plansSubscribed;
	}

	public List<Integer> getMobileAppConfigs() {
		return mobileAppConfigs;
	}

	public void setMobileAppConfigs(List<Integer> mobileAppConfigs) {
		this.mobileAppConfigs = mobileAppConfigs;
	}

	public List<QuestionnaireAssociated> getQuestionnairesAssociated() {
		return questionnairesAssociated;
	}

	public void setQuestionnairesAssociated(List<QuestionnaireAssociated> questionnairesAssociated) {
		this.questionnairesAssociated = questionnairesAssociated;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public LocalDate getEntsmScaleStartDate() {
		return entsmScaleStartDate;
	}

	public void setEntsmScaleStartDate(LocalDate entsmScaleStartDate) {
		this.entsmScaleStartDate = entsmScaleStartDate;
	}

	public LocalDate getEntsmScaleEndDate() {
		return entsmScaleEndDate;
	}

	public void setEntsmScaleEndDate(LocalDate entsmScaleEndDate) {
		this.entsmScaleEndDate = entsmScaleEndDate;
	}

	public List<PushNotificationAssociated> getPushNotificationsAssociated() {
		return pushNotificationsAssociated;
	}

	public void setPushNotificationsAssociated(List<PushNotificationAssociated> pushNotificationsAssociated) {
		this.pushNotificationsAssociated = pushNotificationsAssociated;
	}

	public List<ImageScoringAssociated> getImageScoringSaclesAssociated() {
		return imageScoringSaclesAssociated;
	}

	public void setImageScoringSaclesAssociated(List<ImageScoringAssociated> imageScoringSaclesAssociated) {
		this.imageScoringSaclesAssociated = imageScoringSaclesAssociated;
	}

	public ActivityFactorConfig getActivityFactorConfig() {
		return activityFactorConfig;
	}

	public void setActivityFactorConfig(ActivityFactorConfig activityFactorConfig) {
		this.activityFactorConfig = activityFactorConfig;
	}

	public Integer getAlgorithmId() {
		return algorithmId;
	}

	public void setAlgorithmId(Integer algorithmId) {
		this.algorithmId = algorithmId;
	}

	public String getAlgorithmName() {
		return algorithmName;
	}

	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}

}
