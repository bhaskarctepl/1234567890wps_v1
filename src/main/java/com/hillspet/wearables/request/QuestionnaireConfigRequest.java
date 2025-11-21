package com.hillspet.wearables.request;

import java.util.List;

import javax.ws.rs.PathParam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author yreddy
 *
 *
 */
@ApiModel(description = "Contains all information that needed to configure study questionnaire", value = "QuestionnaireConfigRequest")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
public class QuestionnaireConfigRequest {

	@ApiModelProperty(value = "studyId", required = true)
	@PathParam("studyId")
	private Integer studyId;

	@ApiModelProperty(value = "phaseId", required = true)
	@PathParam("phaseId")
	private Integer phaseId;

	@ApiModelProperty(value = "questionnairesAssociated", required = true)
	private List<QuestionnaireAssociated> questionnairesAssociated;
	
	private Integer userId;

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

	public List<QuestionnaireAssociated> getQuestionnairesAssociated() {
		return questionnairesAssociated;
	}

	public void setQuestionnairesAssociated(List<QuestionnaireAssociated> questionnairesAssociated) {
		this.questionnairesAssociated = questionnairesAssociated;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
