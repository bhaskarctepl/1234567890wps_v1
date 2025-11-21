package com.hillspet.wearables.dto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hillspet.wearables.request.PointTrackerActivityRequest;
import com.hillspet.wearables.request.PointTrackerMetricRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Contains all information that needed to create a campaign", value = "PointTrackerRequest")
public class PointTracker extends BaseDTO {

	private String studyName;
	private String trackerName;
	private Integer studyId;
	private LocalDate startDate;
	private LocalDate endDate;
	private Integer status;
	private Integer isDelete;
	private Integer activityId;
	private Integer pointTrackerId;
	private HashMap<Integer, Integer> permissionMap;
	private String permissions;
	private Integer flag;
	private String totalPoints;
	private String activities;
	private String activityIds;
	private List<PointTrackerAssociatedDTO> pointTrackerAssociatedObject;
	private List<PointTrackerMetricAssociatedDTO> pointTrackerMetricAssociatedObject;
	private List<PointTrackerQuestionnaire> pointTrackerQuestionnaire;
	private Integer userId;
	private Boolean isActive;
	private Boolean isPublished;
	private String studyConfigId;
	private String phaseId;
	
	public String getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(String phaseId) {
		this.phaseId = phaseId;
	}

	public String getStudyConfigId() {
		return studyConfigId;
	}

	public void setStudyConfigId(String studyConfigId) {
		this.studyConfigId = studyConfigId;
	}

	@ApiModelProperty(value = "pointTrackerSubscribed", required = true)
	private List<PointTrackerActivityRequest> pointTrackerSubscribed;
	
	@ApiModelProperty(value = "pointTrackerMetricsSubscribed", required = true)
	private List<PointTrackerMetricRequest> pointTrackerMetricsSubscribed;
	
	

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}

	public List<PointTrackerMetricAssociatedDTO> getPointTrackerMetricAssociatedObject() {
		return pointTrackerMetricAssociatedObject;
	}

	public void setPointTrackerMetricAssociatedObject(
			List<PointTrackerMetricAssociatedDTO> pointTrackerMetricAssociatedObject) {
		this.pointTrackerMetricAssociatedObject = pointTrackerMetricAssociatedObject;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<PointTrackerAssociatedDTO> getPointTrackerAssociatedObject() {
		return pointTrackerAssociatedObject;
	}

	public void setPointTrackerAssociatedObject(List<PointTrackerAssociatedDTO> pointTrackerAssociatedObject) {
		this.pointTrackerAssociatedObject = pointTrackerAssociatedObject;
	}

	public String getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(String totalPoints) {
		this.totalPoints = totalPoints;
	}

	public String getActivities() {
		return activities;
	}

	public String getActivityIds() {
		return activityIds;
	}

	public void setActivityIds(String activityIds) {
		this.activityIds = activityIds;
	}

	public List<PointTrackerMetricRequest> getPointTrackerMetricsSubscribed() {
		return pointTrackerMetricsSubscribed;
	}

	public void setPointTrackerMetricsSubscribed(List<PointTrackerMetricRequest> pointTrackerMetricsSubscribed) {
		this.pointTrackerMetricsSubscribed = pointTrackerMetricsSubscribed;
	}

	public List<PointTrackerActivityRequest> getPointTrackerSubscribed() {
		return pointTrackerSubscribed;
	}

	public void setActivities(String activities) {
		this.activities = activities;
	}

	public void setPointTrackerSubscribed(List<PointTrackerActivityRequest> pointTrackerSubscribed) {
		this.pointTrackerSubscribed = pointTrackerSubscribed;
	}
	
	public List<PointTrackerQuestionnaire> getPointTrackerQuestionnaire() {
		return pointTrackerQuestionnaire;
	}

	public void setPointTrackerQuestionnaire(List<PointTrackerQuestionnaire> pointTrackerQuestionnaire) {
		this.pointTrackerQuestionnaire = pointTrackerQuestionnaire;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public HashMap<Integer, Integer> getPermissionMap() {
		return permissionMap;
	}

	public void setPermissionMap(HashMap<Integer, Integer> permissionMap) {
		this.permissionMap = permissionMap;
	}

	public Integer getPointTrackerId() {
		return pointTrackerId;
	}

	public void setPointTrackerId(Integer pointTrackerId) {
		this.pointTrackerId = pointTrackerId;
	}

	public String getTrackerName() {
		return trackerName;
	}

	public void setTrackerName(String trackerName) {
		this.trackerName = trackerName;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}
}
