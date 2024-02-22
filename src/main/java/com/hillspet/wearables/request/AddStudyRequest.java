package com.hillspet.wearables.request;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hillspet.wearables.dto.StudyPhase;
import com.hillspet.wearables.dto.StudyTreatmentGroup;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author sgorle
 *
 */
@ApiModel(description = "Contains all information that needed to create study", value = "AddStudyRequest")
@JsonInclude(value = Include.NON_NULL)
public class AddStudyRequest {

	@ApiModelProperty(value = "studyId", required = true, example = "12")
	private Integer studyId;

	@ApiModelProperty(value = "studyName", required = true, example = "Clinical Study")
	private String studyName;

	@ApiModelProperty(value = "principalInvestigator", required = false, example = "John")
	private String principalInvestigator;

	@ApiModelProperty(value = "description", required = false)
	private String description;

	@ApiModelProperty(value = "studyLocationId", required = true)
	private Integer studyLocationId;

	@ApiModelProperty(value = "startDate", required = true, example = "1")
	private LocalDate startDate;

	@ApiModelProperty(value = "externalLink", required = true)
	private String externalLink;

	@ApiModelProperty(value = "isNotificationEnable", required = true, example = "1")
	private Boolean isNotificationEnable;

	@ApiModelProperty(value = "status", required = true, example = "1")
	private Integer status;

	@ApiModelProperty(value = "treatmentGroups", required = true)
	private List<StudyTreatmentGroup> treatmentGroups;

	@ApiModelProperty(value = "studyPhases", required = true)
	private List<StudyPhase> studyPhases;

	@ApiModelProperty(value = "isCrossOverStudy", required = false)
	private Boolean isCrossOverStudy;

	@ApiModelProperty(value = "crossOverStudyId", required = false)
	private Integer crossOverStudyId;

	@ApiModelProperty(value = "crossOverStudyPhaseId", required = false)
	private Integer crossOverStudyPhaseId;

	@ApiModelProperty(value = "crossOverStudyGapDays", required = false)
	private Integer crossOverStudyGapDays;

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

	public Boolean getIsCrossOverStudy() {
		return isCrossOverStudy;
	}

	public void setIsCrossOverStudy(Boolean isCrossOverStudy) {
		this.isCrossOverStudy = isCrossOverStudy;
	}

	public Integer getCrossOverStudyId() {
		return crossOverStudyId;
	}

	public void setCrossOverStudyId(Integer crossOverStudyId) {
		this.crossOverStudyId = crossOverStudyId;
	}

	public Integer getCrossOverStudyPhaseId() {
		return crossOverStudyPhaseId;
	}

	public void setCrossOverStudyPhaseId(Integer crossOverStudyPhaseId) {
		this.crossOverStudyPhaseId = crossOverStudyPhaseId;
	}

	public Integer getCrossOverStudyGapDays() {
		return crossOverStudyGapDays;
	}

	public void setCrossOverStudyGapDays(Integer crossOverStudyGapDays) {
		this.crossOverStudyGapDays = crossOverStudyGapDays;
	}

}
