package com.hillspet.wearables.dto.filter;

import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;

public class QuestionnaireResponseByStudyFilter extends BaseFilter {
    @ApiParam(name = "questionnaireId")
    @QueryParam("questionnaireId")
    private String questionnaireId;

    @ApiParam(name = "studyId")
    @QueryParam("studyId")
    private String studyId;

    @ApiParam(name = "startDate", value = "Start Date is the first date component value of date range")
    @QueryParam("startDate")
    private String startDate;

    @ApiParam(name = "endDate", value = "End Date is the second date component value of date range")
    @QueryParam("endDate")
    private String endDate;

    public String getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(String questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public String getStudyId() {
        return studyId;
    }

    public void setStudyId(String studyId) {
        this.studyId = studyId;
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
}
