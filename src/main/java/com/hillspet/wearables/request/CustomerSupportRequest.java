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
@ApiModel(description = "Contains all information that needed to create customer support ticket", value = "CustomerSupportRequest")
@JsonInclude(value = Include.NON_NULL)
public class CustomerSupportRequest {

	@ApiModelProperty(value = "ticketTitle", required = true)
	private String ticketTitle;

	@ApiModelProperty(value = "assignedTo", required = true, example = "1")
	private Integer assignedTo;

	@ApiModelProperty(value = "priorityId", required = true, example = "1")
	private Integer priorityId;

	@ApiModelProperty(value = "statusId", required = true, example = "1")
	private Integer statusId;

	@ApiModelProperty(value = "petName", required = true, example = "1")
	private Integer petName;

	@ApiModelProperty(value = "petParentName", required = true, example = "1")
	private Integer petParentName;

	@ApiModelProperty(value = "contactMethod", required = true, example = "1")
	private Integer contactMethod;

	@ApiModelProperty(value = "petParentAddress", required = true, example = "1")
	private String petParentAddress;

	@ApiModelProperty(value = "studyId", required = true, example = "1")
	private Integer studyId;

	@ApiModelProperty(value = "issue", required = true, example = "1")
	private Integer issue;

	@ApiModelProperty(value = "rootCause", required = true, example = "1")
	private Integer rootCause;

	@ApiModelProperty(value = "sensor", required = true, example = "1")
	private Integer sensorId;

	@ApiModelProperty(value = "sensorLocation",required = true, example = "1")
	private Integer sensorLocation;

	@ApiModelProperty(value = "sensorOtherLocation",required = true, example = "1")
	private String sensorOtherLocation;

	@ApiModelProperty(value = "inventoryStatus", required = true, example = "1")
	private Integer inventoryStatus;

	@ApiModelProperty(value = "inventoryStudyStatus", required = true, example = "1")
	private Integer inventoryStudyStatus;

	@ApiModelProperty(value = "agentSystemActionInitial", required = true, example = "1")
	private Integer agentSystemActionInitial;

	@ApiModelProperty(value = "agentSystemActionSecondary", required = true, example = "1")
	private Integer agentSystemActionSecondary;

	@ApiModelProperty(value = "agentSystemActionTertiary", required = true, example = "1")
	private Integer agentSystemActionTertiary;

	@ApiModelProperty(value = "defectiveSensorAction", required = true, example = "1")
	private Integer defectiveSensorAction;

	@ApiModelProperty(value = "note", required = false)
	private String notes;

	@ApiModelProperty(value = "uploadedFileNames", required = false)
	private List<Map<String, String>> uploadedFileNames;

	public String getTicketTitle() {
		return ticketTitle;
	}

	public void setTicketTitle(String ticketTitle) {
		this.ticketTitle = ticketTitle;
	}

	public Integer getPetName() {
		return petName;
	}

	public void setPetName(Integer petName) {
		this.petName = petName;
	}

	public Integer getPetParentName() {
		return petParentName;
	}

	public void setPetParentName(Integer petParentName) {
		this.petParentName = petParentName;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public Integer getIssue() {
		return issue;
	}

	public void setIssue(Integer issue) {
		this.issue = issue;
	}

	public Integer getRootCause() {
		return rootCause;
	}

	public void setRootCause(Integer rootCause) {
		this.rootCause = rootCause;
	}

	public Integer getSensorId() {
		return sensorId;
	}

	public void setSensorId(Integer sensorId) {
		this.sensorId = sensorId;
	}

	public Integer getSensorLocation() {
		return sensorLocation;
	}

	public void setSensorLocation(Integer sensorLocation) {
		this.sensorLocation = sensorLocation;
	}

	public Integer getInventoryStatus() {
		return inventoryStatus;
	}

	public void setInventoryStatus(Integer inventoryStatus) {
		this.inventoryStatus = inventoryStatus;
	}

	public Integer getAgentSystemActionInitial() {
		return agentSystemActionInitial;
	}

	public void setAgentSystemActionInitial(Integer agentSystemActionInitial) {
		this.agentSystemActionInitial = agentSystemActionInitial;
	}

	public Integer getAgentSystemActionSecondary() {
		return agentSystemActionSecondary;
	}

	public void setAgentSystemActionSecondary(Integer agentSystemActionSecondary) {
		this.agentSystemActionSecondary = agentSystemActionSecondary;
	}

	public Integer getAgentSystemActionTertiary() {
		return agentSystemActionTertiary;
	}

	public void setAgentSystemActionTertiary(Integer agentSystemActionTertiary) {
		this.agentSystemActionTertiary = agentSystemActionTertiary;
	}

	public Integer getDefectiveSensorAction() {
		return defectiveSensorAction;
	}

	public void setDefectiveSensorAction(Integer defectiveSensorAction) {
		this.defectiveSensorAction = defectiveSensorAction;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Integer getPriorityId() {
		return priorityId;
	}

	public void setPriorityId(Integer priorityId) {
		this.priorityId = priorityId;
	}

	public Integer getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(Integer assignedTo) {
		this.assignedTo = assignedTo;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public Integer getContactMethod() {
		return contactMethod;
	}

	public void setContactMethod(Integer contactMethod) {
		this.contactMethod = contactMethod;
	}

	public String getPetParentAddress() {
		return petParentAddress;
	}

	public void setPetParentAddress(String petParentAddress) {
		this.petParentAddress = petParentAddress;
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

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("CustomerSupportRequest{");
		sb.append("ticketTitle='").append(ticketTitle).append('\'');
		sb.append(", assignedTo=").append(assignedTo);
		sb.append(", priorityId=").append(priorityId);
		sb.append(", statusId=").append(statusId);
		sb.append(", petName=").append(petName);
		sb.append(", petParentName=").append(petParentName);
		sb.append(", contactMethod=").append(contactMethod);
		sb.append(", petParentAddress='").append(petParentAddress).append('\'');
		sb.append(", studyId=").append(studyId);
		sb.append(", issue=").append(issue);
		sb.append(", rootCause=").append(rootCause);
		sb.append(", sensorId=").append(sensorId);
		sb.append(", sensorLocation=").append(sensorLocation);
		sb.append(", sensorOtherLocation='").append(sensorOtherLocation).append('\'');
		sb.append(", inventoryStatus=").append(inventoryStatus);
		sb.append(", inventoryStudyStatus=").append(inventoryStudyStatus);
		sb.append(", agentSystemActionInitial=").append(agentSystemActionInitial);
		sb.append(", agentSystemActionSecondary=").append(agentSystemActionSecondary);
		sb.append(", agentSystemActionTertiary=").append(agentSystemActionTertiary);
		sb.append(", defectiveSensorAction=").append(defectiveSensorAction);
		sb.append(", notes='").append(notes).append('\'');
		sb.append(", uploadedFileNames=").append(uploadedFileNames);
		sb.append('}');
		return sb.toString();
	}
}
