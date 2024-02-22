package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

public class AuditFilter extends BaseFilter {

	@ApiParam(name = "actionName", value = "Search based on actionName")
	@QueryParam("actionName")
	private String actionName;

	@ApiParam(name = "moduleId", value = "Search based on module Id drop down")
	@QueryParam("moduleId")
	private String moduleId;

	@ApiParam(name = "startDate", value = "Start Date is the first date component value of date range")
	@QueryParam("startDate")
	private String startDate;

	@ApiParam(name = "endDate", value = "End Date is the second date component value of date range")
	@QueryParam("endDate")
	private String endDate;

	@ApiParam(name = "requestFromId", value = "Search based on request from Id drop down")
	@QueryParam("requestFromId")
	private String requestFromId;

	public AuditFilter() {

	}

	public AuditFilter(String actionName, String moduleId, String startDate, String endDate) {
		super();
		this.actionName = actionName;
		this.moduleId = moduleId;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getRequestFromId() {
		return requestFromId;
	}

	public void setRequestFromId(String requestFromId) {
		this.requestFromId = requestFromId;
	}

}
