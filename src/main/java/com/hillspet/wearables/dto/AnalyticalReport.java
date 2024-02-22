package com.hillspet.wearables.dto;

public class AnalyticalReport {

	private Integer reportId;
	private Integer reportGroupId;
	private String reportName;
	private String reportUrl;
	private String reportGroupName;
	private String createdDate;
	private String modifiedDate;
	private String reportVisibleTo;
	private Integer status;

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

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

	public String getReportGroupName() {
		return reportGroupName;
	}

	public void setReportGroupName(String reportGroupName) {
		this.reportGroupName = reportGroupName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getReportVisibleTo() {
		return reportVisibleTo;
	}

	public void setReportVisibleTo(String reportVisibleTo) {
		this.reportVisibleTo = reportVisibleTo;
	}

}
