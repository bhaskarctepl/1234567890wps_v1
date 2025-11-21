package com.hillspet.wearables.request;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class QuestionnaireAssociated {

	@ApiModelProperty(value = "studyQuestionnaireConfigId", required = true)
	private Integer studyQuestionnaireConfigId;

	private Integer studyId;

	private Integer phaseId;

	@ApiModelProperty(value = "questionnaireId", required = true)
	private Integer questionnaireId;

	@ApiModelProperty(value = "questionnaireName", required = false)
	private String questionnaireName;

	@ApiModelProperty(value = "occuranceId", required = true)
	private Integer occuranceId;

	@ApiModelProperty(value = "frequencyId", required = true)
	private Integer frequencyId;

	@ApiModelProperty(value = "startDate", required = true)
	private String startDate;

	@ApiModelProperty(value = "endDate", required = true)
	private String endDate;

	@ApiModelProperty(value = "phaseDays", required = false)
	private List<Integer> phaseDays;

	@ApiModelProperty(value = "occurance", required = true)
	private String occurance;

	@ApiModelProperty(value = "frequency", required = true)
	private String frequency;

	@ApiModelProperty(value = "isActive", required = false)
	private int isActive;

	@ApiModelProperty(value = "notificationStartDay", required = false)
	private Integer notificationStartDay;

	@ApiModelProperty(value = "notificationFrequency", required = false)
	private Integer notificationFrequency;

	@ApiModelProperty(value = "notificationTime", required = false)
	private String notificationTime;

	@ApiModelProperty(value = "isNotificationsReq", required = false)
	private Integer isNotificationsReq;

	private Integer isNotificationEnable;

	@ApiModelProperty(value = "statusId", required = false)
	private Integer statusId;

	@ApiModelProperty(value = "status", required = false)
	private String status;

	public Integer getIsNotificationEnable() {
		return isNotificationEnable;
	}

	public void setIsNotificationEnable(Integer isNotificationEnable) {
		this.isNotificationEnable = isNotificationEnable;
	}

	public Integer getNotificationStartDay() {
		return notificationStartDay;
	}

	public void setNotificationStartDay(Integer notificationStartDay) {
		this.notificationStartDay = notificationStartDay;
	}

	public Integer getNotificationFrequency() {
		return notificationFrequency;
	}

	public void setNotificationFrequency(Integer notificationFrequency) {
		this.notificationFrequency = notificationFrequency;
	}

	public String getNotificationTime() {
		return notificationTime;
	}

	public void setNotificationTime(String notificationTime) {
		this.notificationTime = notificationTime;
	}

	public Integer getIsNotificationsReq() {
		return isNotificationsReq;
	}

	public void setIsNotificationsReq(Integer isNotificationsReq) {
		this.isNotificationsReq = isNotificationsReq;
	}

	public Integer getStudyQuestionnaireConfigId() {
		return studyQuestionnaireConfigId;
	}

	public void setStudyQuestionnaireConfigId(Integer studyQuestionnaireConfigId) {
		this.studyQuestionnaireConfigId = studyQuestionnaireConfigId;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public Integer getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(Integer phaseId) {
		this.phaseId = phaseId;
	}

	public Integer getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Integer questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public String getQuestionnaireName() {
		return questionnaireName;
	}

	public void setQuestionnaireName(String questionnaireName) {
		this.questionnaireName = questionnaireName;
	}

	public Integer getOccuranceId() {
		return occuranceId;
	}

	public void setOccuranceId(Integer occuranceId) {
		this.occuranceId = occuranceId;
	}

	public Integer getFrequencyId() {
		return frequencyId;
	}

	public void setFrequencyId(Integer frequencyId) {
		this.frequencyId = frequencyId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<Integer> getPhaseDays() {
		return phaseDays;
	}

	public void setPhaseDays(List<Integer> phaseDays) {
		this.phaseDays = phaseDays;
	}

	public String getOccurance() {
		return occurance;
	}

	public void setOccurance(String occurance) {
		this.occurance = occurance;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
