package com.hillspet.wearables.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.DevicesbyStudyReport;
import com.hillspet.wearables.dto.ThresholdByStudyReport;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FRThresholdByCountResponse extends BaseResultCollection {
    private List<ThresholdByStudyReport> thresholdByStudyReportList;

    @JsonProperty("rows")
    @ApiModelProperty(value = "List of threshold by action search criteria")
    public List<ThresholdByStudyReport> getThresholdByStudyReportList() {
        return thresholdByStudyReportList;
    }

    public void setThresholdByStudyReportList(List<ThresholdByStudyReport> thresholdByStudyReportList) {
        this.thresholdByStudyReportList = thresholdByStudyReportList;
    }
}
