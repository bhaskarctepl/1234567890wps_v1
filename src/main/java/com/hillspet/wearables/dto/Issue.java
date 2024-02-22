package com.hillspet.wearables.dto;

public class Issue {
    private int issueId;
    private String issueName;
    /*private int rootCauseId;
    private String rootCauseName;*/

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public String getIssueName() {
        return issueName;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

  /*  public int getRootCauseId() {
        return rootCauseId;
    }

    public void setRootCauseId(int rootCauseId) {
        this.rootCauseId = rootCauseId;
    }

    public String getRootCauseName() {
        return rootCauseName;
    }

    public void setRootCauseName(String rootCauseName) {
        this.rootCauseName = rootCauseName;
    }*/

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Issue{");
        sb.append("issueId=").append(issueId);
        sb.append(", issueName='").append(issueName).append('\'');
       /* sb.append(", rootCauseId=").append(rootCauseId);
        sb.append(", rootCauseName='").append(rootCauseName).append('\'');*/
        sb.append('}');
        return sb.toString();
    }
}
