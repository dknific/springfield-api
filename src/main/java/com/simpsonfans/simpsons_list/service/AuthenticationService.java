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
	private static final String AUTH_TOKEN_HEADER_NAME = "x-api-key";
	
	private String apiKeyValue;
	private static String API_KEY;
	
	@Value("${api.key}")
	public void setApiKey(String apiKey) {
		this.apiKeyValue = apiKey;
	}
	
	@PostConstruct
	public void initializeApiKey() {
		API_KEY = this.apiKeyValue;
	}
	
	public static Authentication getAuthentication(HttpServletRequest request) {
		String requestApiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
		System.out.println("Does " + requestApiKey + " match " + API_KEY);
		
		if (requestApiKey == null || !requestApiKey.equals(API_KEY)) {
			throw new BadCredentialsException("Invalid API Key");
		}
		
		return new ApiKeyAuthentication(requestApiKey, AuthorityUtils.NO_AUTHORITIES);
	}
}
