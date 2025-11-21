package com.hillspet.wearables.request;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author vvodyaram
 *
 */
@ApiModel(description = "Contains all information that needed to create customer", value = "UserRequest")
@JsonInclude(value = Include.NON_NULL)
public class UserRequest {

	UserRequest() {
	}

	private Integer userId;

	@ApiModelProperty(value = "Email", required = true, example = "john@ctepl.com")
	private String email;

	@ApiModelProperty(value = "First Name", required = true, example = "john")
	private String firstName;

	@ApiModelProperty(value = "Last Name", required = true, example = "joe")
	private String lastName;

	@ApiModelProperty(value = "SecondaryEmail", required = true, example = "john@ctepl.com")
	private String secondaryEmail;

	@ApiModelProperty(value = "isdCodeId", required = true, example = "1")
	private Integer isdCodeId;

	@ApiModelProperty(value = "phoneNumber", required = true, example = "(846) 546-5468")
	private String phoneNumber;

	private String fullName;

	private String userName;

	@ApiModelProperty(value = "password", required = true)
	private String password;

	private String roleIds;

	private String roleName;

	private Integer futureStudies;

	private Boolean isActive;
	private Integer inactiveStudies;

	private String hiddenFieldStatus;
	private String userDetailsMailBody;

	public String getHiddenFieldStatus() {
		return hiddenFieldStatus;
	}

	public void setHiddenFieldStatus(String hiddenFieldStatus) {
		this.hiddenFieldStatus = hiddenFieldStatus;
	}

	public String getUserDetailsMailBody() {
		return userDetailsMailBody;
	}

	public void setUserDetailsMailBody(String userDetailsMailBody) {
		this.userDetailsMailBody = userDetailsMailBody;
	}

	public Integer getInactiveStudies() {
		return inactiveStudies;
	}

	public void setInactiveStudies(Integer inactiveStudies) {
		this.inactiveStudies = inactiveStudies;
	}

	@ApiModelProperty(value = "studyPermissions", required = false)
	private ArrayList<String> stydyPermissionMap;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getFutureStudies() {
		return futureStudies;
	}

	public void setFutureStudies(Integer futureStudies) {
		this.futureStudies = futureStudies;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getIsdCodeId() {
		return isdCodeId;
	}

	public void setIsdCodeId(Integer isdCodeId) {
		this.isdCodeId = isdCodeId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleId(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

}
