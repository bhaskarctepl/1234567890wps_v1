package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.TicketPriority;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketPriorityResponse {

	private List<TicketPriority> ticketPriorities;

	@ApiModelProperty(value = "List of details for Ticket Priorities")
	public List<TicketPriority> getTicketPriorities() {
		return ticketPriorities;
	}

	public void setTicketPriorities(List<TicketPriority> ticketPriorities) {
		this.ticketPriorities = ticketPriorities;
	}

}
