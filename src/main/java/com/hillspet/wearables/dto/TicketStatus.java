package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketStatus {

	private Integer ticketStatusId;
	private String statusName;

	public Integer getTicketStatusId() {
		return ticketStatusId;
	}

	public void setTicketStatusId(Integer ticketStatusId) {
		this.ticketStatusId = ticketStatusId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

}
