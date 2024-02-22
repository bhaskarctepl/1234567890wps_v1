package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerSupportByCategory {
	private String issueName;
	private Float issuePercent;
	private int totalCount;
	private int issueCount;

	public int getIssueCount() {
		return issueCount;
	}

	public void setIssueCount(int issueCount) {
		this.issueCount = issueCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getIssueName() {
		return issueName;
	}

	public void setIssueName(String issueName) {
		this.issueName = issueName;
	}

	public Float getIssuePercent() {
		return issuePercent;
	}

	public void setIssuePercent(Float issuePercent) {
		this.issuePercent = issuePercent;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("CustomerSupportIssueWidgetResponse{");
		sb.append("issueName='").append(issueName).append('\'');
		sb.append(", issuePercent='").append(issuePercent).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
