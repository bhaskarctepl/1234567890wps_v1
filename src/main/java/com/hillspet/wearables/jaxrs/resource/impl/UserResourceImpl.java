package com.hillspet.wearables.jaxrs.resource.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.Warning;
import com.hillspet.wearables.common.exceptions.ServiceValidationException;
import com.hillspet.wearables.common.response.SuccessResponse;
import com.hillspet.wearables.common.utils.AESUtil;
import com.hillspet.wearables.common.validation.ValidationResult;
import com.hillspet.wearables.dto.AuditLog;
import com.hillspet.wearables.dto.CustomUserDetails;
import com.hillspet.wearables.dto.User;
import com.hillspet.wearables.dto.filter.UserFilter;
import com.hillspet.wearables.format.validation.EqualsValDataHolder;
import com.hillspet.wearables.format.validation.FormatValidationHelper;
import com.hillspet.wearables.format.validation.FormatValidationService;
import com.hillspet.wearables.format.validation.ValidationAttribute;
import com.hillspet.wearables.jaxrs.resource.UserResource;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.request.UpdatePasswordRequest;
import com.hillspet.wearables.request.UserRequest;
import com.hillspet.wearables.response.UserResponse;
import com.hillspet.wearables.response.UsersResponse;
import com.hillspet.wearables.security.Authentication;
import com.hillspet.wearables.service.auditlog.AuditLogService;
import com.hillspet.wearables.service.user.UserService;

@Service
public class UserResourceImpl implements UserResource {

	private static final Logger LOGGER = LogManager.getLogger(UserResourceImpl.class);

	@Autowired
	private UserService userService;

	@Autowired
	private AuditLogService auditLogService;

	@Autowired
	private Authentication authentication;

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;

	@Autowired
	private FormatValidationService formatValidationService;

	@Autowired
	private TokenStore tokenStore;

	@Override
	public Response user(SecurityContext securityContext) {
		SuccessResponse<Principal> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(securityContext.getUserPrincipal());
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response addUser(UserRequest userRequest) {
		List<Warning> warnings = new ArrayList<>();
		// Step 1: prevalidate
		ValidationResult validationResult = validateUserRequest(userRequest, warnings);
		// TODO: check validationResult is null or not. Since
		// apiOperation.preValidate
		// may return optional of null value.
		if (CollectionUtils.isNotEmpty(validationResult.getErrorList())) {
			throw new ServiceValidationException("addUser PreValidation check has failed cannot proceed further",
					validationResult.getStatusOnError(), validationResult.getErrorList());
		}
		warnings.addAll(validationResult.getWarningList());

		// Step 2: process
		User user = new User();
		BeanUtils.copyProperties(userRequest, user);
		setFullName(user);
		user.setCreatedBy(authentication.getAuthUserDetails().getUserId());
		userService.addUser(user);

		// Step 5: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	private String setFullName(User user) {
		String fullName = "";
		if (StringUtils.isNotEmpty(user.getFirstName()) && StringUtils.isNotEmpty(user.getLastName())) {
			fullName += user.getFirstName() + " " + user.getLastName();
		} else {
			fullName = user.getLastName();
		}
		user.setFullName(fullName);
		return fullName;
	}

	private ValidationResult validateUserRequest(UserRequest userRequest, List<Warning> warnings) {
		ValidationResult validationResult = new ValidationResult();
		FormatValidationHelper formatValidationHelper = formatValidationService.getValidationHelper();

		ValidationAttribute emailAttr = new ValidationAttribute("email");
		emailAttr.addRequired(userRequest.getEmail(), WearablesErrorCode.EMAIL_REQUIRED)
				.addPatternMatch(userRequest.getEmail(), Constants.REGEX_EMAIL, WearablesErrorCode.INVALID_EMAIL);

		ValidationAttribute userNameAttr = new ValidationAttribute("userName");
		userNameAttr.addRequired(userRequest.getUserName(), WearablesErrorCode.USER_NAME_REQUIRED);

		formatValidationHelper.add(emailAttr).add(userNameAttr);

		formatValidationHelper.setProceedOnError(Boolean.TRUE);
		formatValidationHelper.execute(validationResult);
		return validationResult;
	}

	@Override
	public Response updateUser(UserRequest userRequest) {
		// TODO Auto-generated method stub
		List<Warning> warnings = new ArrayList<>();
		// Step 1: prevalidate
		ValidationResult validationResult = validateUserRequest(userRequest, warnings);
		// TODO: check validationResult is null or not. Since
		// apiOperation.preValidate
		// may return optional of null value.
		if (CollectionUtils.isNotEmpty(validationResult.getErrorList())) {
			throw new ServiceValidationException("updateUser PreValidation check has failed cannot proceed further",
					validationResult.getStatusOnError(), validationResult.getErrorList());
		}
		warnings.addAll(validationResult.getWarningList());

		// Step 2: process
		User user = new User();
		BeanUtils.copyProperties(userRequest, user);
		setFullName(user);
		user.setModifiedBy(authentication.getAuthUserDetails().getUserId());
		userService.updateUser(user);

		// Step 5: build a successful response
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response deleteUser(int userId) {
		int modifiedBy = authentication.getAuthUserDetails().getUserId();
		userService.deleteUser(userId, modifiedBy);

		// Step 5: build a successful response
		CommonResponse response = new CommonResponse();
		response.setMessage("User has been deleted successfully");
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getUserById(int userId) {
		User user = userService.getUserById(userId);
		UserResponse response = new UserResponse();
		response.setUser(user);

		SuccessResponse<UserResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getUserByEmailId(String emailId) {
		User user = userService.getUserByEmailId(emailId);
		UserResponse response = new UserResponse();
		response.setUser(user);

		SuccessResponse<UserResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getAllUsers(UserFilter filter) {
		UsersResponse response = userService.getAllUsers(filter);
		SuccessResponse<UsersResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	public Response logoutUser(String authorization, String userId, String platform) {
		try {
			if (authorization != null && authorization.contains("Bearer")) {
				String tokenValue = authorization.replace("Bearer", "").trim();
				OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
				try {
					if (accessToken != null && !accessToken.isExpired() && StringUtils.isNotEmpty(userId)
							&& !userId.equalsIgnoreCase("null")) {
						int uId = Integer.parseInt(userId);
						AuditLog auditLog = new AuditLog();
						auditLog.setActionName("Logged-Out");
						auditLog.setModuleName("User");
						auditLog.setMenuId(24);
						auditLog.setTableName("B_USER");
						auditLog.setEntityId(uId);
						auditLog.setAuditMessage("Logged out");
						auditLog.setUserId(uId);
						auditLog.setCreatedBy(uId);
						auditLogService.addAuditLog(auditLog, platform);
					}
				} catch (Exception e) {
					LOGGER.error("Exception, while user logout,  insert audit log", e);
				}
				if (accessToken != null) {
					tokenStore.removeAccessToken(accessToken);

					OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
					tokenStore.removeRefreshToken(refreshToken);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Invalid access token, while user logout", e);
		}
		return responseBuilder.buildResponse(new SuccessResponse<>());
	}

	@Override
	public Response forgotPassword(String userName, String modifiedBy, String platform) {
		userService.performForgotPasswordTask(userName,
				StringUtils.isNoneEmpty(modifiedBy) ? Integer.valueOf(modifiedBy) : NumberUtils.INTEGER_ZERO, platform);

		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		CommonResponse forgotPwdResponse = new CommonResponse();
		forgotPwdResponse.setMessage(Constants.FORGOT_PASSWORD_MESSAGE);
		successResponse.setServiceResponse(forgotPwdResponse);
		return responseBuilder.buildResponse(successResponse);
	};

	public ValidationResult validateEmail(String emailId) {
		// Step 1: prevalidate
		ValidationResult validationResult = new ValidationResult();
		FormatValidationHelper formatValidationHelper = formatValidationService.getValidationHelper();

		ValidationAttribute emailAttr = new ValidationAttribute("email");
		emailAttr.addRequired(emailId, WearablesErrorCode.EMAIL_REQUIRED).patternMatch(emailId, Constants.REGEX_EMAIL,
				WearablesErrorCode.INVALID_EMAIL);

		formatValidationHelper.add(emailAttr);
		formatValidationHelper.setProceedOnError(Boolean.TRUE);
		formatValidationHelper.execute(validationResult);
		return validationResult;
	}

	@Override
	public Response updatePassword(UpdatePasswordRequest updatePasswordRequest, String platform) {
		// Creating warking list
		List<Warning> warnings = new ArrayList<>();
		// Step 1: prevalidate
		ValidationResult validationResult = validateUpdatePasswordRequest(updatePasswordRequest, warnings);
		// Checking validationResult is null or not. Since
		// apiOperation.preValidate
		// may return optional of null value.
		if (CollectionUtils.isNotEmpty(validationResult.getErrorList())) {
			throw new ServiceValidationException("updatePassword PreValidation check has failed cannot proceed further",
					validationResult.getStatusOnError(), validationResult.getErrorList());
		}
		warnings.addAll(validationResult.getWarningList());

		userService.updatePassword(updatePasswordRequest, platform);

		CommonResponse response = new CommonResponse();
		response.setMessage(Constants.UPDATE_PASSWORD_MESSAGE);
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	public ValidationResult validateUpdatePasswordRequest(UpdatePasswordRequest updatePasswordRequest,
			List<Warning> warnings) {
		ValidationResult validationResult = new ValidationResult();
		FormatValidationHelper formatValidationHelper = formatValidationService.getValidationHelper();

		ValidationAttribute userIdAttr = new ValidationAttribute("userId");
		userIdAttr.addRequired(updatePasswordRequest.getUserId(), WearablesErrorCode.USER_ID_REQUIRED);

		ValidationAttribute passwordAttr = new ValidationAttribute("password");
		passwordAttr.addRequired(updatePasswordRequest.getPassword(), WearablesErrorCode.PASSWORD_REQUIRED);

		ValidationAttribute newPassword = new ValidationAttribute("newPassword");
		newPassword.addRequired(updatePasswordRequest.getNewPassword(), WearablesErrorCode.NEW_PASSWORD_REQUIRED)
				.addPatternMatch(updatePasswordRequest.getNewPassword(), Constants.REGEX_PASSWORD,
						WearablesErrorCode.INVALID_NEW_PASSWORD)
				.addLength(updatePasswordRequest.getNewPassword(), Constants.PASSWORD_MIN_LENGTH,
						Constants.PASSWORD_MAX_LENGTH, WearablesErrorCode.INVALID_NEW_PASSWORD_LENGTH);

		ValidationAttribute confirmPassword = new ValidationAttribute("confirmPassword");
		confirmPassword
				.addRequired(updatePasswordRequest.getConfirmPassword(), WearablesErrorCode.CONFIRM_PASSWORD_REQUIRED)
				.addPatternMatch(updatePasswordRequest.getConfirmPassword(), Constants.REGEX_PASSWORD,
						WearablesErrorCode.INVALID_CONFIRM_PASSWORD)
				.addLength(updatePasswordRequest.getConfirmPassword(), Constants.PASSWORD_MIN_LENGTH,
						Constants.PASSWORD_MAX_LENGTH, WearablesErrorCode.INVALID_CONFIRM_PASSWORD_LENGTH)
				.addCondition(
						new EqualsValDataHolder<Boolean>(
								StringUtils.equals(updatePasswordRequest.getPassword(),
										updatePasswordRequest.getNewPassword()),
								Boolean.FALSE, WearablesErrorCode.INVALID_PASSWORD_NEW_PASSWORD),
						new EqualsValDataHolder<Boolean>(
								StringUtils.equals(updatePasswordRequest.getNewPassword(),
										updatePasswordRequest.getConfirmPassword()),
								Boolean.TRUE, WearablesErrorCode.INVALID_NEW_CONFIRM_PASSWORD));

		formatValidationHelper.add(userIdAttr).add(passwordAttr).add(newPassword).add(confirmPassword);

		formatValidationHelper.setProceedOnError(Boolean.TRUE);
		formatValidationHelper.execute(validationResult);
		return validationResult;
	}

	@Override
	public Response updateUserProfile(@Valid UserRequest request) {
		User user = new User();
		BeanUtils.copyProperties(request, user);
		user.setModifiedBy(authentication.getAuthUserDetails().getUserId());
		userService.updateUserProfile(user);

		// Step 5: build a successful response
		CommonResponse response = new CommonResponse();
		response.setMessage("Full Name updated successfully.");
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getUserDetails(int userId) {
		User response = userService.getUserById(userId);
		SuccessResponse<User> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response loginSuccess(String platform) {
		int userId = authentication.getAuthUserDetails().getUserId();
		AuditLog auditLog = new AuditLog();
		auditLog.setActionName("Logged-In");
		auditLog.setModuleName("User");
		auditLog.setMenuId(24);
		auditLog.setTableName("B_USER");
		auditLog.setEntityId(userId);
		auditLog.setAuditMessage("Logged In");
		auditLog.setUserId(userId);
		auditLog.setCreatedBy(userId);

		auditLogService.addAuditLog(auditLog, platform);

		// Step 5: build a successful response
		CommonResponse response = new CommonResponse();
		response.setMessage("User login successfully.");
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response validateUser() {
		CustomUserDetails userDtls = authentication.getAuthUserDetails();
		userDtls.setRolePermissions(null);
		SuccessResponse<CustomUserDetails> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(userDtls);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response generateIssueTrackerUrl(String authorization) {
		// http://<serverip>:<port>/issuetracker/remoteUserLogin?userId={0}&source={1}&token={2}
		CustomUserDetails userDtls = authentication.getAuthUserDetails();
		String tokenValue = authorization.replace("Bearer", "").trim();
		String issueTrackerUrl = "";
		try {
			String urlString = System.getenv("ISSUE_TRACKER_URL");
			String source = System.getenv("ISSUE_TRACKER_REQUEST_SOURCE");
			issueTrackerUrl = MessageFormat.format(urlString,
					URLEncoder.encode(AESUtil.encrypt(userDtls.getEmail()), "UTF-8"),
					URLEncoder.encode(AESUtil.encrypt(source), "UTF-8"),
					URLEncoder.encode(AESUtil.encrypt(tokenValue), "UTF-8"));
		} catch (UnsupportedEncodingException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException
				| InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException e) {
			LOGGER.error("Error while constructing the issue tracker url {}", e);
		}
		SuccessResponse<String> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(issueTrackerUrl);
		return responseBuilder.buildResponse(successResponse);
	}
}
