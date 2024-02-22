package com.hillspet.wearables.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author sgorle
 *
 */
@ApiModel(description = "Contains all information that needed to update customer support ticket", value = "CustomerSupportUpdateRequest")
@JsonInclude(value = Include.NON_NULL)
public class CustomerSupportUpdateRequest {
	@ApiModelProperty(value = "ticketId", required = true)
	private Integer ticketId;

	@ApiModelProperty(value = "ticketTitle", required = true)
	private String ticketTitle;

	@ApiModelProperty(value = "assignedTo", required = true, example = "1")
	private Integer assignedTo;

	@ApiModelProperty(value = "priorityId", required = true, example = "1")
	private Integer priorityId;

	@ApiModelProperty(value = "statusId", required = true, example = "1")
	private Integer statusId;

	@ApiModelProperty(value = "petName", required = true, example = "1")
	private Integer petNameId;

	@ApiModelProperty(value = "petParentNameId", required = true, example = "1")
	private Integer petParentNameId;

	@ApiModelProperty(value = "contactMethodId", required = true, example = "1")
	private Integer contactMethodId;

	@ApiModelProperty(value = "petParentAddress", required = false)
	private String petParentAddress;

	@ApiModelProperty(value = "studyId", required = true, example = "1")
	private Integer studyId;

	@ApiModelProperty(value = "issueId", required = true, example = "1")
	private Integer issueId;

	@ApiModelProperty(value = "rootCauseId", required = true, example = "1")
	private Integer rootCauseId;

	@ApiModelProperty(value = "sensor", required = true, example = "1")
	private Integer sensorId;

	@ApiModelProperty(value = "sensorLocationId",required = true, example = "1")
	private Integer sensorLocationId;

	@ApiModelProperty(value = "sensorOtherLocation",required = true, example = "1")
	private String sensorOtherLocation;

	@ApiModelProperty(value = "inventoryStatusId", required = true, example = "1")
	private Integer inventoryStatusId;

	@ApiModelProperty(value = "inventoryStudyStatus", required = true, example = "1")
	private Integer inventoryStudyStatus;

	@ApiModelProperty(value = "agentSystemActionInitialId", required = true, example = "1")
	private Integer agentSystemActionInitialId;

	@ApiModelProperty(value = "agentSystemActionSecondaryId", required = true, example = "1")
	private Integer agentSystemActionSecondaryId;

	@ApiModelProperty(value = "agentSystemActionTertiaryId", required = true, example = "1")
	private Integer agentSystemActionTertiaryId;

	@ApiModelProperty(value = "defectiveSensorActionId", required = true, example = "1")
	private Integer defectiveSensorActionId;

	@ApiModelProperty(value = "note", required = false)
	private String notes;

	@ApiModelProperty(value = "uploadedFileNames", required = false)
	private List<Map<String, String>> uploadedFileNames;

	public Integer getTicketId() {
		return ticketId;
	}

	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}

	public String getTicketTitle() {
		return ticketTitle;
	}

	public void setTicketTitle(String ticketTitle) {
		this.ticketTitle = ticketTitle;
	}

	public Integer getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(Integer assignedTo) {
		this.assignedTo = assignedTo;
	}

	public Integer getPriorityId() {
		return priorityId;
	}

	public void setPriorityId(Integer priorityId) {
		this.priorityId = priorityId;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public Integer getPetNameId() {
		return petNameId;
	}

	public void setPetNameId(Integer petNameId) {
		this.petNameId = petNameId;
	}

	public Integer getPetParentNameId() {
		return petParentNameId;
	}

	public void setPetParentNameId(Integer petParentNameId) {
		this.petParentNameId = petParentNameId;
	}

	public Integer getContactMethodId() {
		return contactMethodId;
	}

	public void setContactMethodId(Integer contactMethodId) {
		this.contactMethodId = contactMethodId;
	}

	public String getPetParentAddress() {
		return petParentAddress;
	}

	public void setPetParentAddress(String petParentAddress) {
		this.petParentAddress = petParentAddress;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public Integer getIssueId() {
		return issueId;
	}

	public void setIssueId(Integer issueId) {
		this.issueId = issueId;
	}

	public Integer getRootCauseId() {
		return rootCauseId;
	}

	public void setRootCauseId(Integer rootCauseId) {
		this.rootCauseId = rootCauseId;
	}

	public Integer getSensorId() {
		return sensorId;
	}

	public void setSensorId(Integer sensorId) {
		this.sensorId = sensorId;
	}

	public Integer getSensorLocationId() {
		return sensorLocationId;
	}

	public void setSensorLocationId(Integer sensorLocationId) {
		this.sensorLocationId = sensorLocationId;
	}

	public Integer getInventoryStatusId() {
		return inventoryStatusId;
	}

	public void setInventoryStatusId(Integer inventoryStatusId) {
		this.inventoryStatusId = inventoryStatusId;
	}

	public Integer getAgentSystemActionInitialId() {
		return agentSystemActionInitialId;
	}

	public void setAgentSystemActionInitialId(Integer agentSystemActionInitialId) {
		this.agentSystemActionInitialId = agentSystemActionInitialId;
	}

	public Integer getAgentSystemActionSecondaryId() {
		return agentSystemActionSecondaryId;
	}

	public void setAgentSystemActionSecondaryId(Integer agentSystemActionSecondaryId) {
		this.agentSystemActionSecondaryId = agentSystemActionSecondaryId;
	}

	public Integer getAgentSystemActionTertiaryId() {
		return agentSystemActionTertiaryId;
	}

	public void setAgentSystemActionTertiaryId(Integer agentSystemActionTertiaryId) {
		this.agentSystemActionTertiaryId = agentSystemActionTertiaryId;
	}

	public Integer getDefectiveSensorActionId() {
		return defectiveSensorActionId;
	}

	public void setDefectiveSensorActionId(Integer defectiveSensorActionId) {
		this.defectiveSensorActionId = defectiveSensorActionId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getSensorOtherLocation() {
		return sensorOtherLocation;
	}

	public void setSensorOtherLocation(String sensorOtherLocation) {
		this.sensorOtherLocation = sensorOtherLocation;
	}

	public Integer getInventoryStudyStatus() {
		return inventoryStudyStatus;
	}

	public void setInventoryStudyStatus(Integer inventoryStudyStatus) {
		this.inventoryStudyStatus = inventoryStudyStatus;
	}

	public List<Map<String, String>> getUploadedFileNames() {
		return uploadedFileNames;
	}

	public void setUploadedFileNames(List<Map<String, String>> uploadedFileNames) {
		this.uploadedFileNames = uploadedFileNames;
	}
}
