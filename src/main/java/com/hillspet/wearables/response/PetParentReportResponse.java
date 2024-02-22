package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.PetParentReport;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetParentReportResponse extends BaseResultCollection {

	private List<PetParentReport> petParentReport;
	
	private long pendingCount;
	private long completedCount;

	@JsonProperty("rows")
	@ApiModelProperty(value = "Pet Parent Report Details")
	public List<PetParentReport> getPetParentReport() {
		return petParentReport;
	}

	public void setPetParentReport(List<PetParentReport> petParentReport) {
		this.petParentReport = petParentReport;
	}

	public long getPendingCount() {
		return pendingCount;
	}

	public void setPendingCount(long pendingCount) {
		this.pendingCount = pendingCount;
	}

	public long getCompletedCount() {
		return completedCount;
	}

	public void setCompletedCount(long completedCount) {
		this.completedCount = completedCount;
	}

	

	
	
}
