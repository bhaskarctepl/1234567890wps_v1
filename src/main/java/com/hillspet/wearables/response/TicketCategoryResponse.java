package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.TicketCategory;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketCategoryResponse {

	private List<TicketCategory> ticketCategories;

	@ApiModelProperty(value = "List of details for Ticket Categories")
	public List<TicketCategory> getTicketCategories() {
		return ticketCategories;
	}

	public void setTicketCategories(List<TicketCategory> ticketCategories) {
		this.ticketCategories = ticketCategories;
	}

}
