package com.hillspet.wearables.request;

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
public class UpdatePasswordRequest {

	UpdatePasswordRequest() {
	}

	private Integer userId;

	@ApiModelProperty(value = "Email", required = true, example = "john@ctepl.com")
	private String email;

	@ApiModelProperty(value = "Username", required = true, example = "joe2312")
	private String userName;

	@ApiModelProperty(value = "password", required = true)
	private String password;

	@ApiModelProperty(value = "new password")
	private String newPassword;

	@ApiModelProperty(value = "confirm password")
	private String confirmPassword;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
