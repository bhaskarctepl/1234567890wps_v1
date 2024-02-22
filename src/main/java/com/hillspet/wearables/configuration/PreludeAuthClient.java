package com.hillspet.wearables.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author: sgorle
 * @date: 14-09-2022
 * 
 */
@Component
public class PreludeAuthClient {

	private static final Logger LOGGER = LogManager.getLogger(PreludeAuthClient.class);
	private final RestTemplate restTemplate;
	
	private String preludeUserName = System.getenv("PRELUDE_USER_NAME");
	private String preludePass = System.getenv("PRELUDE_PASSWORD");

	private final String authTokenURL = String.format("/api?username=%s&password=%s&get=auth", preludeUserName, preludePass);
			
			

	public PreludeAuthClient(RestTemplateBuilder restTemplateBuilder) {
		restTemplate = restTemplateBuilder.basicAuthentication(preludeUserName, preludePass).build();
	}

	public String getPreludeAuthToken(String studyUrl) {
		LOGGER.info("studyUrl {}", studyUrl);
		final ResponseEntity<String> responseEntity;
		responseEntity = restTemplate.getForEntity(studyUrl + authTokenURL, String.class);		
		return responseEntity.getBody();
	}
}