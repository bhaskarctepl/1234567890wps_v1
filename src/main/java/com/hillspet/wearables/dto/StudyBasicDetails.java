package com.hillspet.wearables.dto;

import java.time.LocalDate;
import java.util.List;

public class StudyBasicDetails {

	private Integer studyId;
	private String studyName;
	private String principalInvestigator;
	private String description;
	private Integer studyLocationId;
	private LocalDate startDate;
	private String externalLink;
	private Boolean isNotificationEnable;
	private Integer status;
	private String studyStatus;
	private List<StudyTreatmentGroup> treatmentGroups;
	private List<StudyPhase> studyPhases;
	private Integer totalActivePets;

	private Integer isExtetnal;
	private String studyLocationName;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStudyLocationId() {
		return studyLocationId;
	}

	public void setStudyLocationId(Integer studyLocationId) {
		this.studyLocationId = studyLocationId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public String getExternalLink() {
		return externalLink;
	}

	public void setExternalLink(String externalLink) {
		this.externalLink = externalLink;
	}

	public Boolean getIsNotificationEnable() {
		return isNotificationEnable;
	}

	public void setIsNotificationEnable(Boolean isNotificationEnable) {
		this.isNotificationEnable = isNotificationEnable;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStudyStatus() {
		return studyStatus;
	}

	public void setStudyStatus(String studyStatus) {
		this.studyStatus = studyStatus;
	}

	public List<StudyTreatmentGroup> getTreatmentGroups() {
		return treatmentGroups;
	}

	public void setTreatmentGroups(List<StudyTreatmentGroup> treatmentGroups) {
		this.treatmentGroups = treatmentGroups;
	}

	public List<StudyPhase> getStudyPhases() {
		return studyPhases;
	}

	public void setStudyPhases(List<StudyPhase> studyPhases) {
		this.studyPhases = studyPhases;
	}

	public Integer getIsExtetnal() {
		return isExtetnal;
	}

	public void setIsExtetnal(Integer isExtetnal) {
		this.isExtetnal = isExtetnal;
	}

	public String getStudyLocationName() {
		return studyLocationName;
	}

	public void setStudyLocationName(String studyLocationName) {
		this.studyLocationName = studyLocationName;
	}

	public Integer getTotalActivePets() {
		return totalActivePets;
	}

	public void setTotalActivePets(Integer totalActivePets) {
		this.totalActivePets = totalActivePets;
	}

}
