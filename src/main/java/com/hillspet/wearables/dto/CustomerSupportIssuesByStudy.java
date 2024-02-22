package com.hillspet.wearables.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerSupportIssuesByStudy {
    private String studyName;
    private String studyPercentage;
    private Integer studyCount;
    private Integer openCount;
    private Integer inProgressCount;
    private Integer closedCount;

    public String getStudyName() {
        return studyName;
    }

    public void setStudyName(String studyName) {
        this.studyName = studyName;
    }

    public String getStudyPercentage() {
        return studyPercentage;
    }

    public void setStudyPercentage(String studyPercentage) {
        this.studyPercentage = studyPercentage;
    }

    public Integer getStudyCount() {
        return studyCount;
    }

    public void setStudyCount(Integer studyCount) {
        this.studyCount = studyCount;
    }

    public Integer getOpenCount() {
        return openCount;
    }

    public void setOpenCount(Integer openCount) {
        this.openCount = openCount;
    }

    public Integer getInProgressCount() {
        return inProgressCount;
    }

    public void setInProgressCount(Integer inProgressCount) {
        this.inProgressCount = inProgressCount;
    }

    public Integer getClosedCount() {
        return closedCount;
    }

    public void setClosedCount(Integer closedCount) {
        this.closedCount = closedCount;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CustomerSupportIssueByStudyWidgetResponse{");
        sb.append("studyName='").append(studyName).append('\'');
        sb.append(", studyPercentage='").append(studyPercentage).append('\'');
        sb.append(", studyCount=").append(studyCount);
        sb.append(", openCount=").append(openCount);
        sb.append(", inProgressCount=").append(inProgressCount);
        sb.append(", closedCount=").append(closedCount);
        sb.append('}');
        return sb.toString();
    }
}
