package com.hillspet.wearables.request;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.StringJoiner;

@ApiModel(description = "Contains all information that needed to update manual recommendation", value = "ManualRecommendationRequest")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ManualRecommendationRequest {
    @ApiModelProperty(value = "activityFactorId", required = true, example = "100")
    private int activityFactorId;

    @ApiModelProperty(value = "petId", required = true, example = "1")
    private int petId;

    @ApiModelProperty(value = "studyId", required = true, example = "1")
    private int studyId;

    @ApiModelProperty(value = "value", required = true, example = "1.0")
    private Float value;

    @ApiModelProperty(value = "unit", required = true, example = "grams/kgs/cups")
    private String unit;

    public int getActivityFactorId() {
        return activityFactorId;
    }

    public void setActivityFactorId(int activityFactorId) {
        this.activityFactorId = activityFactorId;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getStudyId() {
        return studyId;
    }

    public void setStudyId(int studyId) {
        this.studyId = studyId;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ManualRecommendationRequest.class.getSimpleName() + "[", "]")
                .add("activityFactorId=" + activityFactorId)
                .add("petId=" + petId)
                .add("studyId=" + studyId)
                .add("value=" + value)
                .add("unit='" + unit + "'")
                .toString();
    }
}
