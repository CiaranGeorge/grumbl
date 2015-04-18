package com.complaints.utils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;


public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

	private static Logger LOG = Logger.getLogger(AuthenticationTokenProcessingFilter.class);
	
	private final UserDetailsService userService;

	public AuthenticationTokenProcessingFilter(UserDetailsService userService) {
		LOG.info("AuthenticationTokenProcessingFilter constr");
		this.userService = userService;
	}

	/**
     * @author c.george
     * 
     * Validate the user request ensuring the token matches a user.
     */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		LOG.info("doFilter");
		HttpServletRequest httpRequest = this.getAsHttpRequest(request);

		String authToken = this.extractAuthTokenFromRequest(httpRequest);
		String userName = TokenUtils.getUserNameFromToken(authToken);

		if (userName != null) {
			UserDetails userDetails = this.userService.loadUserByUsername(userName);

			if (TokenUtils.validateToken(authToken, userDetails)) {
				LOG.info("Token is valid");
				UsernamePasswordAuthenticationToken authentication =
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		chain.doFilter(request, response);
	}


	private HttpServletRequest getAsHttpRequest(ServletRequest request) {
		LOG.info("getAsHttpRequest");
		if (!(request instanceof HttpServletRequest)) {
			throw new RuntimeException("Expecting an HTTP request");
		}

		return (HttpServletRequest) request;
	}

	/**
     * @author c.george
     * 
     * Check if a token is present in the request header.
     */
	private String extractAuthTokenFromRequest(HttpServletRequest httpRequest) {
		LOG.info("extractAuthTokenFromRequest");
		/* Get token from header */
		String authToken = httpRequest.getHeader("X-Auth-Token");

		/* If token not found get it from request parameter */
		if (authToken == null) {
			authToken = httpRequest.getParameter("token");
		}

		return authToken;
	}
}
