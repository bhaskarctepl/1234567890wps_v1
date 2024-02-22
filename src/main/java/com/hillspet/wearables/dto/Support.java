
/**
 * Created Date: 08-Dec-2020
 * Class Name  : Support.java
 * Description : Description of the package.
 *
 * © Copyright 2020 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>        <Author>        <Date>        <Comments on Change>
 * ID                agujjar          08-Dec-2020        Mentions the comments on change, for the new file it's not required.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

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
 * @author agujjar
 * @version Wearables Portal Relase Version..
 * @since Available Since Wearables Portal Version.
 * @see New line seperated Classes or Interfaces related to this class.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Support extends BaseDTO {

	private Integer ticketID;
	private Integer ticketTypeID;
	private Integer priorityID;
	private Integer petParentID;
	private Integer petID;
	private Integer contactMethodID;
	private Integer studyID;
	private Integer contactReasonID;
	private Integer issueCategoryID;
	private String ticketTitle;
	private String ticketDescription;
	private String shippingStatus;
	private String otherContactReason;
	private String notes;
	private Integer ticketStatusId;

	public Integer getTicketID() {
		return ticketID;
	}

	public void setTicketID(Integer ticketID) {
		this.ticketID = ticketID;
	}

	public Integer getTicketTypeID() {
		return ticketTypeID;
	}

	public void setTicketTypeID(Integer ticketTypeID) {
		this.ticketTypeID = ticketTypeID;
	}

	public Integer getPriorityID() {
		return priorityID;
	}

	public void setPriorityID(Integer priorityID) {
		this.priorityID = priorityID;
	}

	public Integer getPetParentID() {
		return petParentID;
	}

	public void setPetParentID(Integer petParentID) {
		this.petParentID = petParentID;
	}

	public Integer getPetID() {
		return petID;
	}

	public void setPetID(Integer petID) {
		this.petID = petID;
	}

	public Integer getContactMethodID() {
		return contactMethodID;
	}

	public void setContactMethodID(Integer contactMethodID) {
		this.contactMethodID = contactMethodID;
	}

	public Integer getStudyID() {
		return studyID;
	}

	public void setStudyID(Integer studyID) {
		this.studyID = studyID;
	}

	public Integer getContactReasonID() {
		return contactReasonID;
	}

	public void setContactReasonID(Integer contactReasonID) {
		this.contactReasonID = contactReasonID;
	}

	public Integer getIssueCategoryID() {
		return issueCategoryID;
	}

	public void setIssueCategoryID(Integer issueCategoryID) {
		this.issueCategoryID = issueCategoryID;
	}

	public String getTicketTitle() {
		return ticketTitle;
	}

	public void setTicketTitle(String ticketTitle) {
		this.ticketTitle = ticketTitle;
	}

	public String getTicketDescription() {
		return ticketDescription;
	}

	public void setTicketDescription(String ticketDescription) {
		this.ticketDescription = ticketDescription;
	}

	public String getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(String shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	public String getOtherContactReason() {
		return otherContactReason;
	}

	public void setOtherContactReason(String otherContactReason) {
		this.otherContactReason = otherContactReason;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Integer getTicketStatusId() {
		return ticketStatusId;
	}

	public void setTicketStatusId(Integer ticketStatusId) {
		this.ticketStatusId = ticketStatusId;
	}

}
