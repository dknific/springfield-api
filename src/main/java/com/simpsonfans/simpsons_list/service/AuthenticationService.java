package com.simpsonfans.simpsons_list.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import com.simpsonfans.simpsons_list.security.ApiKeyAuthentication;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthenticationService {
	private static final String REQUEST_HEADER_NAME = "x-api-key";
	private String SYSTEM_API_KEY;
	
	// Inject api.key value from application.properties:
	@Value("${api.key}")
	public void setApiKey(String apiKey) {
		this.SYSTEM_API_KEY = apiKey;
	}
	
	private static String STATIC_SYS_API_KEY;
	
	@PostConstruct
	public void makeApiKeyStatic() {
		STATIC_SYS_API_KEY = this.SYSTEM_API_KEY;
	}

	public static Authentication getAuthentication(HttpServletRequest request) {
		String requestApiKey = request.getHeader(REQUEST_HEADER_NAME);
		
		if (requestApiKey == null || !requestApiKey.equals(STATIC_SYS_API_KEY)) {
			throw new BadCredentialsException("Invalid API Key");
		}
		
		return new ApiKeyAuthentication(requestApiKey, AuthorityUtils.NO_AUTHORITIES);
	}
}
