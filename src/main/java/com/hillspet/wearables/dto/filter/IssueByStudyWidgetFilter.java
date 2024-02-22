package com.hillspet.wearables.dto.filter;

import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;

public class IssueByStudyWidgetFilter {

    @ApiParam(name = "startDate", value = "Start Date is the first date component value of date range")
    @QueryParam("startDate")
    private String startDate;

    @ApiParam(name = "endDate", value = "End Date is the second date component value of date range")
    @QueryParam("endDate")
    private String endDate;
    
    @ApiParam(name = "agent", value = "agent")
    @QueryParam("agent")
    private int agent;

    public int getAgent() {
		return agent;
	}

	public void setAgent(int agent) {
		this.agent = agent;
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


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("IssueByStudyWidgetFilter{");
        sb.append("startDate='").append(startDate).append('\'');
        sb.append(", endDate='").append(endDate).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
