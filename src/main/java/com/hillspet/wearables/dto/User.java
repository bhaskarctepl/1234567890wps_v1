package com.hillspet.wearables.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer userId;
	private String userName;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	private String firstName;
	private String lastName;
	private String fullName;
	private String email;
	private String secondaryEmail;
	private Integer isdCodeId;
	private String isdCode;
	private String phoneNumber;
	private Integer countryId;
	private Integer stateId;
	private String city;
	private String postalCode;
	private LocalDateTime lastLogin;
	private String photo;
	private Boolean isShowWelcome;
	private Boolean needChangePwd;
	private String preferredUserMethod;
	private String failedCount;
	private Boolean recordFlag;
	private String uniqueId;
	private LocalDateTime failedDate;
	private String roleIds;
	private Integer roleId;
	private String roleName;
	private Integer roleTypeId;
	private String roleTypeName;
	private List<Role> roles;
	private List<Menu> rolePermissions;
	/* private HashMap<Integer, Integer> stydyPermissionMap; */
	private ArrayList<String> stydyPermissionMap;
	private String studyPermissions;
	private Boolean isSuperAdmin;
	private Integer futureStudies;
	private Integer inactiveStudies;
	private String hiddenFieldStatus;
	private String userDetailsMailBody;
	private boolean hasIssueTrackerAccess;

	public String getUserDetailsMailBody() {
		return userDetailsMailBody;
	}

	public void setUserDetailsMailBody(String userDetailsMailBody) {
		this.userDetailsMailBody = userDetailsMailBody;
	}

	public String getHiddenFieldStatus() {
		return hiddenFieldStatus;
	}

	public void setHiddenFieldStatus(String hiddenFieldStatus) {
		this.hiddenFieldStatus = hiddenFieldStatus;
	}

	public Integer getInactiveStudies() {
		return inactiveStudies;
	}

	public void setInactiveStudies(Integer inactiveStudies) {
		this.inactiveStudies = inactiveStudies;
	}

	public Integer getFutureStudies() {
		return futureStudies;
	}

	public void setFutureStudies(Integer futureStudies) {
		this.futureStudies = futureStudies;
	}

	private List<AssociatedStudy> associatedStudies;
	private List<AuditLog> auditLogs;

	public User() {

	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getIsdCodeId() {
		return isdCodeId;
	}

	public void setIsdCodeId(Integer isdCodeId) {
		this.isdCodeId = isdCodeId;
	}

	public String getIsdCode() {
		return isdCode;
	}

	public void setIsdCode(String isdCode) {
		this.isdCode = isdCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Boolean getIsShowWelcome() {
		return isShowWelcome;
	}

	public void setIsShowWelcome(Boolean isShowWelcome) {
		this.isShowWelcome = isShowWelcome;
	}

	public Boolean getNeedChangePwd() {
		return needChangePwd;
	}

	public void setNeedChangePwd(Boolean needChangePwd) {
		this.needChangePwd = needChangePwd;
	}

	public String getPreferredUserMethod() {
		return preferredUserMethod;
	}

	public void setPreferredUserMethod(String preferredUserMethod) {
		this.preferredUserMethod = preferredUserMethod;
	}

	public String getFailedCount() {
		return failedCount;
	}

	public void setFailedCount(String failedCount) {
		this.failedCount = failedCount;
	}

	public Boolean getRecordFlag() {
		return recordFlag;
	}

	public void setRecordFlag(Boolean recordFlag) {
		this.recordFlag = recordFlag;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public LocalDateTime getFailedDate() {
		return failedDate;
	}

	public void setFailedDate(LocalDateTime failedDate) {
		this.failedDate = failedDate;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getRoleTypeId() {
		return roleTypeId;
	}

	public void setRoleTypeId(Integer roleTypeId) {
		this.roleTypeId = roleTypeId;
	}

	public String getRoleTypeName() {
		return roleTypeName;
	}

	public void setRoleTypeName(String roleTypeName) {
		this.roleTypeName = roleTypeName;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Menu> getRolePermissions() {
		return rolePermissions;
	}

	public void setRolePermissions(List<Menu> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}

	/*
	 * public HashMap<Integer, Integer> getStydyPermissionMap() { return
	 * stydyPermissionMap; }
	 * 
	 * public void setStydyPermissionMap(HashMap<Integer, Integer>
	 * stydyPermissionMap) { this.stydyPermissionMap = stydyPermissionMap; }
	 */

	public String getStudyPermissions() {
		return studyPermissions;
	}

	public void setStudyPermissions(String studyPermissions) {
		this.studyPermissions = studyPermissions;
	}

	public Boolean getIsSuperAdmin() {
		return isSuperAdmin;
	}

	public void setIsSuperAdmin(Boolean isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

	public List<AssociatedStudy> getAssociatedStudies() {
		return associatedStudies;
	}

	public void setAssociatedStudies(List<AssociatedStudy> associatedStudies) {
		this.associatedStudies = associatedStudies;
	}

	public List<AuditLog> getAuditLogs() {
		return auditLogs;
	}

	public void setAuditLogs(List<AuditLog> auditLogs) {
		this.auditLogs = auditLogs;
	}

	public ArrayList<String> getStydyPermissionMap() {
		return stydyPermissionMap;
	}

	public void setStydyPermissionMap(ArrayList<String> stydyPermissionMap) {
		this.stydyPermissionMap = stydyPermissionMap;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSecondaryEmail() {
		return secondaryEmail;
	}

	public void setSecondaryEmail(String secondaryEmail) {
		this.secondaryEmail = secondaryEmail;
	}

	public boolean isHasIssueTrackerAccess() {
		return hasIssueTrackerAccess;
	}

	public void setHasIssueTrackerAccess(boolean hasIssueTrackerAccess) {
		this.hasIssueTrackerAccess = hasIssueTrackerAccess;
	}

}
