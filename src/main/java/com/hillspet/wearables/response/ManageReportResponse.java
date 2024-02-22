package com.hillspet.wearables.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.ManageReports;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ManageReportResponse extends BaseResultCollection {

//	 private List<ManageReports> ManageReportList;
	 
	 private ManageReports manageReports;
	 

	public ManageReports getManageReports() {
		return manageReports;
	}

	public void setManageReports(ManageReports manageReports) {
		this.manageReports = manageReports;
	}

	/*public List<ManageReports> getManageReportList() {
		return ManageReportList;
	}

	public void setManageReportList(List<ManageReports> manageReportList) {
		ManageReportList = manageReportList;
	}*/
	 
}
