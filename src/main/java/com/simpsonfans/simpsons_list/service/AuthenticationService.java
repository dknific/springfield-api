package com.simpsonfans.simpsons_list.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

import com.simpsonfans.simpsons_list.security.ApiKeyAuthentication;

import jakarta.servlet.http.HttpServletRequest;

public class AuthenticationService {
	private static final String AUTH_TOKEN_HEADER_NAME = "x-api-key";
	private static final String AUTH_TOKEN = "Baeldung";
	
	public static Authentication getAuthentication(HttpServletRequest request) {
		String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
		if (apiKey == null || !apiKey.equals(AUTH_TOKEN)) {
			throw new BadCredentialsException("Invalid API Key");
		}
		
		return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
	}
}
