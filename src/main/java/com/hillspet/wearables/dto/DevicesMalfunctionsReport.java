package com.hillspet.wearables.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DevicesMalfunctionsReport extends BaseDTO{

	private String studyName;
//	private int malfunctionedDevices;
	private int ticketsCount;
	private List<AssetIssueCountByStudy> issueNameCount;
	public List<AssetIssueCountByStudy> getIssueNameCount() {
		return issueNameCount;
	}
	public void setIssueNameCount(List<AssetIssueCountByStudy> issueNameCount) {
		this.issueNameCount = issueNameCount;
	}
	public int getTicketsCount() {
		return ticketsCount;
	}
	public void setTicketsCount(int ticketsCount) {
		this.ticketsCount = ticketsCount;
	}
	public String getStudyName() {
		return studyName;
	}
	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}
//	public int getMalfunctionedDevices() {
//		return malfunctionedDevices;
//	}
//	public void setMalfunctionedDevices(int malfunctionedDevices) {
//		this.malfunctionedDevices = malfunctionedDevices;
//	}
}
