package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.StudyPhaseQuestionnaireScheduleDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudyPhaseQuestionnaireScheduleList extends BaseResultCollection {

	private List<StudyPhaseQuestionnaireScheduleDTO> studyPhaseQuestionnaireScheduleDTO;

	@JsonProperty("rows")
	@ApiModelProperty(value = "Get the Study Phase Questionnaire Schedule based on search criteria")
	public List<StudyPhaseQuestionnaireScheduleDTO> getStudyPhaseQuestionnaireScheduleDTO() {
		return studyPhaseQuestionnaireScheduleDTO;
	}

	public void setStudyPhaseQuestionnaireScheduleDTO(
			List<StudyPhaseQuestionnaireScheduleDTO> studyPhaseQuestionnaireScheduleDTO) {
		this.studyPhaseQuestionnaireScheduleDTO = studyPhaseQuestionnaireScheduleDTO;
	}

}
