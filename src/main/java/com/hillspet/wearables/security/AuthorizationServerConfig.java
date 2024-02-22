package com.hillspet.wearables.security;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.hillspet.wearables.dto.CustomUserDetails;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	private static final String CLIEN_ID = System.getenv("CLIENT_ID");
	private static final String CLIENT_SECRET = System.getenv("CLIENT_SECRET");
	private static final String GRANT_TYPE = System.getenv("GRANT_TYPE");

	private static final String AUTHORIZATION_CODE = "authorization_code";
	private static final String REFRESH_TOKEN = "refresh_token";
	private static final String IMPLICIT = "implicit";
	private static final String SCOPE_READ = "read";
	private static final String SCOPE_WRITE = "write";
	private static final String TRUST = "trust";
	private static final String USER_INFO = "user_info";
	private static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1 * 30 * 60;
	private static final int REFRESH_TOKEN_VALIDITY_SECONDS = 6 * 60 * 60;

	private static final Logger LOGGER = LogManager.getLogger(AuthorizationServerConfig.class);

	@Lazy
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	TokenStore tokenStore;

	@Autowired
	private UserApprovalHandler userApprovalHandler;

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	private static String REALM = "MY_OAUTH_REALM";

	private static String redirectURLs = "http://localhost:8082/wearables/login/oauth2/code/";

	@Override
	@CrossOrigin
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
		configurer.inMemory().withClient(CLIEN_ID).secret(passwordEncoder.encode(CLIENT_SECRET))
				.authorizedGrantTypes(GRANT_TYPE, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT)
				.scopes(SCOPE_READ, SCOPE_WRITE, TRUST, USER_INFO).autoApprove(true).redirectUris(redirectURLs)
				.accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
				.refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()").realm(REALM + "/client");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager).userApprovalHandler(userApprovalHandler)
				.tokenEnhancer(tokenEnhancer());
		endpoints.tokenStore(tokenStore);
	}

	@EventListener
	public void authSuccessEventListener(AuthenticationSuccessEvent authorizedEvent) {
		LOGGER.debug("User Autherizaton is Success");
		LOGGER.debug(authorizedEvent.getAuthentication().getPrincipal());
	}

	@EventListener
	public void authFailedEventListener(AbstractAuthenticationFailureEvent oAuth2AuthenticationFailureEvent) {
		LOGGER.info("User Autherizaton is Failed");
		LOGGER.info(oAuth2AuthenticationFailureEvent.getAuthentication().getPrincipal());
	}

	@Bean
	public TokenEnhancer tokenEnhancer() {
		return (accessToken, authentication) -> {
			CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
			Map<String, Object> info = new LinkedHashMap<>(accessToken.getAdditionalInformation());
			if (user.getUserId() != null)
				info.put("userId", user.getUserId());
			if (user.getUserName() != null)
				info.put("userName", user.getUserName());
			if (user.getFullName() != null)
				info.put("fullName", user.getFullName());
			if (user.getEmail() != null)
				info.put("email", user.getEmail());
			if (user.getIsSuperAdmin() != null)
				info.put("isSuperAdmin", user.getIsSuperAdmin());
			if (user.getIsActive() != null)
				info.put("isActive", user.getIsActive());
			if (user.getNeedChangePwd() != null)
				info.put("needChangePwd", user.getNeedChangePwd());
			if (user.getRoleIds() != null)
				info.put("roleIds", user.getRoleIds());
			if (user.getRoleName() != null)
				info.put("roleName", user.getRoleName());
			if (user.getRoleTypeId() != null)
				info.put("roleTypeId", user.getRoleTypeId());
			if (user.getRoleTypeName() != null)
				info.put("roleTypeName", user.getRoleTypeName());
			if (user.getRolePermissions() != null)
				info.put("rolePermissions", user.getRolePermissions());

			DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
			customAccessToken.setAdditionalInformation(info);

			LOGGER.info(user.getUserId());
			LOGGER.info(user.getUsername());

			return customAccessToken;
		};
	}

}
