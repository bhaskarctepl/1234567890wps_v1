package com.hillspet.wearables.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.hillspet.wearables.request.ActivityFactorConfig;
import com.hillspet.wearables.request.ImageScoringAssociated;
import com.hillspet.wearables.request.PlanSubscribed;
import com.hillspet.wearables.request.PreludeAssociated;
import com.hillspet.wearables.request.PushNotificationsAssociated;
import com.hillspet.wearables.request.QuestionnaireAssociated;
import com.hillspet.wearables.response.StudyNotesResponse;

public class StudyDTO {

	private Integer studyId;
	private String studyName;
	private String principalInvestigator;
	private Date startDate;
	private Date endDate;
	private Integer status;
	private List<StudyNotesResponse> notes;
	private List<PlanSubscribed> plansSubscribed;
	private List<QuestionnaireAssociated> questionnairesAssociated;
	private List<PushNotificationsAssociated> pushNotificationsAssociated;
	private List<ImageScoringAssociated> imageScoringSaclesAssociated;
	private List<Integer> mobileAppConfigs;
	private Integer totalPets;
	private Integer totalActivePets;
	private Integer totalInactivePets;
	private Integer isNotificationEnable;
	private Integer isExternal;
	private String description;
	private List<PreludeAssociated> preludeAssociated;
	private List<PreludeMandatory> PreludeMandatory;
	private String preludeUrl;
	private Integer isDataLoadSuccess;
	private String weightUnit;
	private LocalDate entsmScaleStartDate;
	private LocalDate entsmScaleEndDate;
	private ActivityFactorConfig activityFactorConfig;
	private Integer algorithmId;
	private String algorithmName;
	private List<Algorithm> algorithmHistory;

	public Integer getIsDataLoadSuccess() {
		return isDataLoadSuccess;
	}

	public void setIsDataLoadSuccess(Integer isDataLoadSuccess) {
		this.isDataLoadSuccess = isDataLoadSuccess;
	}

	public String getPreludeUrl() {
		return preludeUrl;
	}

	public void setPreludeUrl(String preludeUrl) {
		this.preludeUrl = preludeUrl;
	}

	public List<PreludeMandatory> getPreludeMandatory() {
		return PreludeMandatory;
	}

	public void setPreludeMandatory(List<PreludeMandatory> preludeMandatory) {
		PreludeMandatory = preludeMandatory;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<StudyNotesResponse> getNotes() {
		return notes;
	}

	public void setNotes(List<StudyNotesResponse> notes) {
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

	public List<PushNotificationsAssociated> getPushNotificationsAssociated() {
		return pushNotificationsAssociated;
	}

	public void setPushNotificationsAssociated(List<PushNotificationsAssociated> pushNotificationsAssociated) {
		this.pushNotificationsAssociated = pushNotificationsAssociated;
	}

	public Integer getTotalPets() {
		return totalPets;
	}

	public void setTotalPets(Integer totalPets) {
		this.totalPets = totalPets;
	}

	public Integer getTotalActivePets() {
		return totalActivePets;
	}

	public void setTotalActivePets(Integer totalActivePets) {
		this.totalActivePets = totalActivePets;
	}

	public Integer getTotalInactivePets() {
		return totalInactivePets;
	}

	public void setTotalInactivePets(Integer totalInactivePets) {
		this.totalInactivePets = totalInactivePets;
	}

	public Integer getIsExternal() {
		return isExternal;
	}

	public void setIsExternal(Integer isExternal) {
		this.isExternal = isExternal;
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

	public List<Algorithm> getAlgorithmHistory() {
		return algorithmHistory;
	}

	public void setAlgorithmHistory(List<Algorithm> algorithmHistory) {
		this.algorithmHistory = algorithmHistory;
	}

}
