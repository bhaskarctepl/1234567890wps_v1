package com.hillspet.wearables.response;

import java.util.List;

import com.hillspet.wearables.dto.TicketAssignedUser;


public class TicketAssignedUserResponse {
	 List<TicketAssignedUser> ticketAssignedUserList;

	public List<TicketAssignedUser> getTicketAssignedUserList() {
		return ticketAssignedUserList;
	}

	public void setTicketAssignedUserList(List<TicketAssignedUser> ticketAssignedUserList) {
		this.ticketAssignedUserList = ticketAssignedUserList;
	}

}
