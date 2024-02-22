
/**
 * Created Date: 17-Dec-2020
 * Class Name  : SupportFilter.java
 * Description : Description of the package.
 *
 * © Copyright 2020 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>        <Author>        <Date>        <Comments on Change>
 * ID                agujjar          17-Dec-2020        Mentions the comments on change, for the new file it's not required.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.hillspet.wearables.dto.filter;

import javax.ws.rs.QueryParam;

import io.swagger.annotations.ApiParam;

/**
 * Enter detailed explanation of the class here..
 * <p>
 * This class implementation of the <tt>Interface or class Name</tt> interface
 * or class. In addition to implementing the <tt>Interface Name</tt> interface,
 * this class provides methods to do other operations. (Mention other methods
 * purpose)
 *
 * <p>
 * More Description about the class need to be entered here.
 *
 * @author sgorle
 * @version Wearables Portal Relase Version..
 * @since Available Since Wearables Portal Version.
 * @see New line seperated Classes or Interfaces related to this class.
 */
public class SupportFilter extends BaseFilter {

	@ApiParam(name = "startDate", value = "Start Date is the first date component value of date range")
	@QueryParam("startDate")
	private String startDate;

	@ApiParam(name = "endDate", value = "End Date is the second date component value of date range")
	@QueryParam("endDate")
	private String endDate;

	@QueryParam("priorityId")
	@ApiParam(name = "priorityId", type = "integer", value = "Search based on Priority Id drop down")
	private String priorityId;

	@QueryParam("assignedToId")
	@ApiParam(name = "assignToId", type = "integer", value = "Search based on assign To Id drop down")
	private String assignedToId;

	public SupportFilter() {

	}

	public SupportFilter(String startDate, String endDate, String priorityId, String statusId, String assignToId) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.priorityId = priorityId;
		this.assignedToId = assignToId;
	}

	public String getPriorityId() {
		return priorityId;
	}

	public void setPriorityId(String priorityId) {
		this.priorityId = priorityId;
	}

	public String getAssignedToId() {
		return assignedToId;
	}

	public void setAssignedToId(String assignToId) {
		this.assignedToId = assignToId;
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

}
