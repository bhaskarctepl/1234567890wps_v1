package com.hillspet.wearables.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.BulkUploadDietInfo;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BulkDietUploadResponse extends BaseResultCollection {
    private List<BulkUploadDietInfo> dietInfoList;
    private Integer errorRecordsCount;
    private Integer successRecordsCount;

    @JsonProperty("rows")
    @ApiModelProperty(value = "List of details for all diets info that matches the search criteria")
    public List<BulkUploadDietInfo> getDietInfoList() {
        return dietInfoList;
    }

    public void setDietInfoList(List<BulkUploadDietInfo> dietInfoList) {
        this.dietInfoList = dietInfoList;
    }
    @ApiModelProperty(value = "Uploaded diets error records count")
    public Integer getErrorRecordsCount() {
        return errorRecordsCount;
    }

    public void setErrorRecordsCount(Integer errorRecordsCount) {
        this.errorRecordsCount = errorRecordsCount;
    }
    @ApiModelProperty(value = "Uploaded diets success records count")
    public Integer getSuccessRecordsCount() {
        return successRecordsCount;
    }

    public void setSuccessRecordsCount(Integer successRecordsCount) {
        this.successRecordsCount = successRecordsCount;
    }
}
