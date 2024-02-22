package com.hillspet.wearables.service.user.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadPoolExecutor;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.constants.StringLiterals;
import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.common.exceptions.ServiceValidationException;
import com.hillspet.wearables.common.utils.BeanUtil;
import com.hillspet.wearables.common.utils.WearablesUtils;
import com.hillspet.wearables.concurrent.EmailSenderThread;
import com.hillspet.wearables.concurrent.EmailThreadPoolExecutor;
import com.hillspet.wearables.dao.user.UserDao;
import com.hillspet.wearables.dto.CustomUserDetails;
import com.hillspet.wearables.dto.User;
import com.hillspet.wearables.dto.filter.UserFilter;
import com.hillspet.wearables.request.UpdatePasswordRequest;
import com.hillspet.wearables.response.UsersResponse;
import com.hillspet.wearables.service.user.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		LOGGER.info("loadUserByUsername called");
		Optional<User> usersOptional = userDao.findByUsername(userName);
		usersOptional.orElseThrow(() -> new UsernameNotFoundException("Username not found!"));

		User user = usersOptional.get();
		LOGGER.info("loadUserByUsername completed successfully");
		return new CustomUserDetails(user);
	}

	@Override
	public void addUser(User user) throws ServiceExecutionException {
		LOGGER.debug("addUser called");
		// set password
		String rawPassword = StringUtils.isNotEmpty(user.getPassword()) ? user.getPassword()
				: WearablesUtils.generatePassword();
		// need to change it to set the rawPassword once email functionality is added
		// user.setPassword(passwordEncoder.encode("Admin@123"));

		// This is to resolve circular dependency injection
		PasswordEncoder passwordEncoder = BeanUtil.getBean(PasswordEncoder.class);
		user.setPassword(passwordEncoder.encode(rawPassword));

		user.setStudyPermissions(String.join(",", user.getStydyPermissionMap()));
		userDao.addUser(user);

		// Sending Create Mail
		String subject = Constants.CREATE_USER_MAIL_SUBJECT;
		String messageBody = MessageFormat.format(Constants.CREATE_USER_MAIL_BODY, user.getFullName(),
				user.getUserName(), Constants.WEBSITE_SITE_TITLE, rawPassword, Constants.WEBSITE_SITE_TITLE);

		ThreadPoolExecutor threadPoolExecutor = EmailThreadPoolExecutor.getEmailThreadPoolExecutor();
		EmailSenderThread emailSenderThread = new EmailSenderThread(user.getEmail(), subject, messageBody);
		threadPoolExecutor.submit(emailSenderThread);
		LOGGER.debug("addUser completed successfully");
	}

	@Override
	public void updateUser(User user) throws ServiceExecutionException {
		LOGGER.debug("updateUser called");
		user.setStudyPermissions(String.join(",", user.getStydyPermissionMap()));
		userDao.updateUser(user);
		if (!user.getHiddenFieldStatus().equals("")) {
			String mailBody = getUpdatedUserDetailsMailBody(user.getHiddenFieldStatus(), user);
			String subject = Constants.UPDATED_USER_MAIL_SUBJECT;
			String messageBody = MessageFormat.format(Constants.UPDATED_USER_MAIL_BODY, user.getFullName(),
					user.getUserName(), mailBody, Constants.WEBSITE_SITE_TITLE, Constants.WEBSITE_SITE_TITLE);

			ThreadPoolExecutor threadPoolExecutor = EmailThreadPoolExecutor.getEmailThreadPoolExecutor();
			EmailSenderThread emailSenderThread = new EmailSenderThread(user.getEmail(), subject, messageBody);
			threadPoolExecutor.submit(emailSenderThread);
		}
		LOGGER.debug("updateUser completed successfully");
	}

	public String getUpdatedUserDetailsMailBody(String mailBody, User user) {
		String mailchanges = "";
		StringBuffer mailBodyStringBuffer = new StringBuffer();
		String[] mailList = mailBody.split(",");
		for (int i = 0; i <= mailList.length - 1; i++) {

			if (mailList[i].equals("email")) {
				mailBodyStringBuffer.append("<p>Email has been updated to <b>" + user.getEmail() + "</b>.</p>");
			}
			if (mailList[i].equals("userName")) {
				mailBodyStringBuffer.append("<p>Username has been updated to <b>" + user.getUserName() + "</b>.</p>");
			}

		}

		mailchanges = mailBodyStringBuffer.toString();
		return mailchanges;
	}

	public String getStudyPermissionsByMap(Map<Integer, Integer> studyPermissionMap) {
		StringBuffer permissionsStringBuffer = new StringBuffer();

		if (MapUtils.isNotEmpty(studyPermissionMap)) {
			studyPermissionMap.entrySet().stream().forEach(entry -> {
				permissionsStringBuffer.append(entry.getKey() + StringLiterals.HASH.getCode() + entry.getValue()
						+ StringLiterals.COMMA.getCode());
			});
		}
		String studyPermissions = permissionsStringBuffer.toString();

		if (studyPermissions.endsWith(",")) {
			studyPermissions = studyPermissions.substring(0, studyPermissions.length() - 1);
		}
		return studyPermissions;
	}

	@Override
	public void deleteUser(int userId, int modifiedBy) throws ServiceExecutionException {
		LOGGER.debug("deleteUser called");
		userDao.deleteUser(userId, modifiedBy);
		LOGGER.debug("deleteUser completed successfully");
	}

	@Override
	public User getUserById(int userId) throws ServiceExecutionException {
		LOGGER.debug("getUserById called");
		User user2 = userDao.getUserById(userId);
		LOGGER.debug("getUserById completed successfully");
		return user2;
	}

	@Override
	public User getUserByEmailId(String emailId) throws ServiceExecutionException {
		LOGGER.debug("getUserByEmailId called");
		Optional<User> userOptional = userDao.findByUsername(emailId);

		User user = new User();
		if (userOptional.isPresent()) {
			user = userOptional.get();
		}
		LOGGER.debug("getUserByEmailId completed successfully");
		return user;
	}

	@Override
	public UsersResponse getAllUsers(UserFilter filter) throws ServiceExecutionException {
		LOGGER.debug("getAllUsers called");
		Map<String, Integer> mapper = userDao.getUserCount(filter);
		int total = mapper.get("count");
		int totalCount = mapper.get("totalCount");
		List<User> users = total > NumberUtils.INTEGER_ZERO ? userDao.getAllUsers(filter) : new ArrayList<>();

		UsersResponse response = new UsersResponse();
		response.setUsers(users);
		response.setNoOfElements(users.size());
		response.setTotalRecords(totalCount);
		response.setSearchElments(total);

		LOGGER.debug("getAllUsers users are {}", users);
		LOGGER.debug("getAllUsers completed successfully");
		return response;
	}

	@Override
	public void performForgotPasswordTask(String userName, int modifiedBy, String platform)
			throws ServiceExecutionException {
		LOGGER.debug("performForgotPasswordTask called");
		Optional<User> userOpt = userDao.findByUsername(userName);

		userOpt.orElseThrow(() -> new ServiceValidationException(
				"performForgotPasswordTask service validation failed cannot proceed further",
				Status.NOT_FOUND.getStatusCode(),
				Arrays.asList(new WearablesError(WearablesErrorCode.USERNAME_NOT_FOUND, userName))));

		User user = userOpt.get();

		// set password
		String rawPassword = WearablesUtils.generatePassword();

		// This is to resolve circular dependency injection
		PasswordEncoder passwordEncoder = BeanUtil.getBean(PasswordEncoder.class);
		userDao.updateUserPassword(user.getUserId(), user.getUserName(), passwordEncoder.encode(rawPassword), true,
				modifiedBy > NumberUtils.INTEGER_ZERO ? modifiedBy : user.getUserId(), platform);

		// Sending Forgot Password Mail
		String subject = Constants.FORGOT_PASSWORD_MAIL_SUBJECT;
		String requestFrom = Constants.WEBSITE_SITE_TITLE;

		if (Constants.REQUEST_FROM_ANALYTICAL.equalsIgnoreCase(platform)) {
			subject = Constants.FORGOT_PASSWORD_MAIL_SUBJECT_AP;
			requestFrom = Constants.WEBSITE_SITE_TITLE_AP;
		}

		String messageBody = MessageFormat.format(Constants.FORGOT_PASSWORD_MAIL_BODY, user.getFullName(), requestFrom,
				rawPassword, requestFrom);

		ThreadPoolExecutor threadPoolExecutor = EmailThreadPoolExecutor.getEmailThreadPoolExecutor();
		EmailSenderThread emailSenderThread = new EmailSenderThread(user.getEmail(), subject, messageBody);
		threadPoolExecutor.submit(emailSenderThread);

		LOGGER.debug("performForgotPasswordTask completed successfully");
	}

	@Override
	public void updatePassword(UpdatePasswordRequest updatePasswordRequest, String platform)
			throws ServiceExecutionException {
		LOGGER.debug("updatePassword called");
		User user = userDao.getUserById(updatePasswordRequest.getUserId());
		// This is to resolve circular dependency injection
		PasswordEncoder passwordEncoder = BeanUtil.getBean(PasswordEncoder.class);

		if (passwordEncoder.matches(updatePasswordRequest.getPassword(), user.getPassword())) {

			List<String> passwords = userDao.getPasswordHistoryById(updatePasswordRequest.getUserId());
			passwords.stream().forEach(password -> {
				if (passwordEncoder.matches(updatePasswordRequest.getNewPassword(), password)) {
					throw new ServiceValidationException(
							"updatePassword service failed for history validation cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.INCORRECT_NEW_PASSWORD)));

				}
			});

			userDao.updateUserPassword(user.getUserId(), user.getUserName(),
					passwordEncoder.encode(updatePasswordRequest.getNewPassword()), false, user.getUserId(), platform);

			// Sending update password mail
			String subject = Constants.UPDATE_PASSWORD_MAIL_SUBJECT;
			String requestFrom = Constants.WEBSITE_SITE_TITLE;

			if (Constants.REQUEST_FROM_ANALYTICAL.equalsIgnoreCase(platform)) {
				subject = Constants.FORGOT_PASSWORD_MAIL_SUBJECT_AP;
				requestFrom = Constants.WEBSITE_SITE_TITLE_AP;
			}

			String messageBody = MessageFormat.format(Constants.UPDATE_PASSWORD_MAIL_BODY, user.getFullName(),
					requestFrom, requestFrom);

			ThreadPoolExecutor threadPoolExecutor = EmailThreadPoolExecutor.getEmailThreadPoolExecutor();
			EmailSenderThread emailSenderThread = new EmailSenderThread(user.getEmail(), subject, messageBody);
			threadPoolExecutor.submit(emailSenderThread);

		} else {
			throw new ServiceValidationException("updatePassword service validation failed cannot proceed further",
					Status.BAD_REQUEST.getStatusCode(),
					Arrays.asList(new WearablesError(WearablesErrorCode.INCORRECT_PASSWORD)));
		}
		LOGGER.debug("updatePassword completed successfully");
	}

	@Override
	public void updateUserProfile(User user) throws ServiceExecutionException {
		LOGGER.debug("updateUser called");
		userDao.updateUserProfile(user);
		LOGGER.debug("updateUser completed successfully");
	}
}
