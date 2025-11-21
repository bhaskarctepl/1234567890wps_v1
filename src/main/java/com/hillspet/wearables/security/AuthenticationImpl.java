package com.hillspet.wearables.security;

import java.util.Arrays;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.ServiceValidationException;
import com.hillspet.wearables.dto.CustomUserDetails;
import com.hillspet.wearables.dto.User;
import com.hillspet.wearables.service.user.UserService;


@Component
public class AuthenticationImpl implements Authentication {

	private static final Logger LOGGER = LogManager.getLogger(AuthenticationImpl.class);

	@Autowired
	private UserService userService;

	@Override
	public CustomUserDetails getAuthUserDetails() {

		CustomUserDetails userDetails = null;
		org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		// Check if the user is authenticated
		if (authentication != null && authentication.isAuthenticated()) {
			// Assuming the authentication object is an instance of JwtAuthenticationToken
			if (authentication instanceof JwtAuthenticationToken) {
				JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
				
				LOGGER.info("Token ============== "+ jwtAuthenticationToken.getToken().getTokenValue());

				Map<String, Object> userAttributes = jwtAuthenticationToken.getTokenAttributes();
				LOGGER.info("User Details......................: " + userAttributes);

				String email = (String) userAttributes.get("sub"); // Assuming 'sub' is the user ID claim

				LOGGER.info("User Email ID: " + email);

				User user = userService.getUserByEmailId(email);
				if(user == null || user.getUserId() == null ) {
						throw new ServiceValidationException("User validation check has failed cannot proceed further",
								HttpStatus.FORBIDDEN.value(), Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
				}
				userDetails = new CustomUserDetails(user);
			}
		}
		return userDetails;
	}
}