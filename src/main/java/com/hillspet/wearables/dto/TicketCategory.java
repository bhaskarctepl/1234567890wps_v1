package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketCategory {

	private Integer ticketCategoryId;
	private String ticketCategoryName;

	public Integer getTicketCategoryId() {
		return ticketCategoryId;
	}

	public void setTicketCategoryId(Integer ticketCategoryId) {
		this.ticketCategoryId = ticketCategoryId;
	}

	public String getTicketCategoryName() {
		return ticketCategoryName;
	}

	public void setTicketCategoryName(String ticketCategoryName) {
		this.ticketCategoryName = ticketCategoryName;
	}

}
