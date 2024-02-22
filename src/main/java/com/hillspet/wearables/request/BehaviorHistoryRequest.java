package com.hillspet.wearables.request;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiModelProperty;

public class BehaviorHistoryRequest {

	@QueryParam("fromDate")
	@ApiModelProperty(value = "fromDate", required = true)
	private String fromDate;

	@QueryParam("toDate")
	@ApiModelProperty(value = "toDate", required = true)
	private String toDate;

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

}
