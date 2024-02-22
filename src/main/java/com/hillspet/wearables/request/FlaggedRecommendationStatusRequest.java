package com.hillspet.wearables.request;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;
import java.util.StringJoiner;

public class FlaggedRecommendationStatusRequest {
    @QueryParam("setRecommendQuantity")
    @ApiParam(name = "setRecommendQuantity", value = "Automatically set recommended food quantity")
    @ApiModelProperty(value = "setRecommendQuantity")
    private Boolean setRecommendQuantity;

    @QueryParam("manualRecommendation")
    @ApiParam(name = "manualRecommendation", value = "ManualRecommendation to save manual recommendation")
    @ApiModelProperty(value = "manualRecommendation")
    private Float manualRecommendation;

    @QueryParam("comments")
    @ApiParam(name = "comments", type = "String", value = "To save comments")
    private String comments;

    @QueryParam("status")
    @ApiParam(name = "status", type = "Integer", value = "status")
    private Integer status;

    @QueryParam("afId")
    @ApiParam(name = "afId", type = "String", value = "status")
    private String afId;

    @QueryParam("petId")
    @ApiParam(name = "petId", type = "String", value = "status")
    private String petId;

    @QueryParam("studyId")
    @ApiParam(name = "studyId", type = "String", value = "status")
    private String studyId;

    @QueryParam("feedUnits")
    @ApiModelProperty(value = "feedUnits", required = true)
    private String feedUnits;
    private int userId;

    public Boolean getSetRecommendQuantity() {
        return setRecommendQuantity;
    }

    public void setSetRecommendQuantity(Boolean setRecommendQuantity) {
        this.setRecommendQuantity = setRecommendQuantity;
    }

    public Float getManualRecommendation() {
        return manualRecommendation;
    }

    public void setManualRecommendation(Float manualRecommendation) {
        this.manualRecommendation = manualRecommendation;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAfId() {
        return afId;
    }

    public void setAfId(String afId) {
        this.afId = afId;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getStudyId() {
        return studyId;
    }

    public void setStudyId(String studyId) {
        this.studyId = studyId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFeedUnits() {
        return feedUnits;
    }

    public void setFeedUnits(String feedUnits) {
        this.feedUnits = feedUnits;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FlaggedRecommendationStatusRequest.class.getSimpleName() + "[", "]")
                .add("setRecommendQuantity='" + setRecommendQuantity + "'")
                .add("manualRecommendation=" + manualRecommendation)
                .add("comments='" + comments + "'")
                .add("status=" + status)
                .add("afId=" + afId)
                .add("petId=" + petId)
                .add("studyId=" + studyId)
                .add("feedUnits='" + feedUnits + "'")
                .add("userId=" + userId)
                .toString();
    }
}
