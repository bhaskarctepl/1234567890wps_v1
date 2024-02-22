package com.hillspet.wearables.response;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.QuestionnaireResponseByStudyListDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionnaireResponseByStudyListResponse extends BaseResultCollection {

    @JsonProperty("rows")
    @ApiModelProperty(value = "Get the Support tickets based on search criteria")
    private List<QuestionnaireResponseByStudyListDTO> questionnaireResponseByStudyList;

    public List<QuestionnaireResponseByStudyListDTO> getQuestionnaireResponseByStudyList() {
        return questionnaireResponseByStudyList;
    }

    public void setQuestionnaireResponseByStudyList(List<QuestionnaireResponseByStudyListDTO> questionnaireResponseByStudyList) {
        this.questionnaireResponseByStudyList = questionnaireResponseByStudyList;
    }
}
