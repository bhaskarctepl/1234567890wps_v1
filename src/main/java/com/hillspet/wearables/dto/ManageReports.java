package com.hillspet.wearables.dto;

public class ManageReports {

	private Integer reportId;
	private Integer reportGroupId;
	private String reportName;
	private String reportUrl;
	private Integer status;
	private Integer reportVisibleTo;

//	private Integer userId;
	/*
	 * public Integer getUserId() { return userId; } public void setUserId(Integer
	 * userId) { this.userId = userId; }
	 */
	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public Integer getReportGroupId() {
		return reportGroupId;
	}

	public void setReportGroupId(Integer reportGroupId) {
		this.reportGroupId = reportGroupId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportUrl() {
		return reportUrl;
	}

	public void setReportUrl(String reportUrl) {
		this.reportUrl = reportUrl;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getReportVisibleTo() {
		return reportVisibleTo;
	}

	public void setReportVisibleTo(Integer reportVisibleTo) {
		this.reportVisibleTo = reportVisibleTo;
	}

}
