package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.TicketStatus;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketStatusResponse {

	private List<TicketStatus> ticketStatus;

	@ApiModelProperty(value = "List of details for Ticket Status")
	public List<TicketStatus> getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(List<TicketStatus> ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

}
