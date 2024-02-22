package com.hillspet.wearables.dto.filter;

import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;

public class IssueWidgetFilter {
    @ApiParam(name = "startDate", value = "Start Date is the first date component value of date range")
    @QueryParam("startDate")
    private String startDate;

    @ApiParam(name = "endDate", value = "End Date is the second date component value of date range")
    @QueryParam("endDate")
    private String endDate;

    @ApiParam(name = "studyId", value = "study Id")
    @QueryParam("studyId")
    private Integer studyId;

    @ApiParam(name = "modelType", value = "Assert Type")
    @QueryParam("modelType")
    private String modelType;

    @ApiParam(name = "assertModel", value = "Assert Model")
    @QueryParam("assertModel")
    private String assertModel;

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

    public Integer getStudyId() {
        return studyId;
    }

    public void setStudyId(Integer studyId) {
        this.studyId = studyId;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public String getAssertModel() {
        return assertModel;
    }

    public void setAssertModel(String assertModel) {
        this.assertModel = assertModel;
    }
}
