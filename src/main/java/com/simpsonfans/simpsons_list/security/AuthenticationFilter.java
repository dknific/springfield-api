package com.simpsonfans.simpsons_list.security;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.simpsonfans.simpsons_list.service.AuthenticationService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter extends GenericFilterBean {
	
	@Override
	public void doFilter(
			ServletRequest request,
			ServletResponse response,
			FilterChain filterChain
	) throws IOException, ServletException {
		try {
			Authentication auth = AuthenticationService.getAuthentication((HttpServletRequest) request);
			SecurityContextHolder.getContext().setAuthentication(auth);
		} catch (Exception exp) {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
			httpResponse.getWriter().write("{ \"error\": \"Invalid API Key\"}");
			httpResponse.getWriter().flush();
			httpResponse.getWriter().close();
			return;
		}
		
		filterChain.doFilter(request, response);
	}
}
