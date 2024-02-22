package com.hillspet.wearables.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.hillspet.wearables.dto.CustomUserDetails;

@Component
public class AuthenticationImpl implements Authentication {

	@Override
	public CustomUserDetails getAuthUserDetails() {
		org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		return (CustomUserDetails) authentication.getPrincipal();
	}
}