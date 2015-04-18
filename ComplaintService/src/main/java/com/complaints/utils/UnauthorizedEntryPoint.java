package com.complaints.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.complaints.response.ServiceResponse;

public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {

	private static Logger LOG = Logger.getLogger(UnauthorizedEntryPoint.class);
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		LOG.error("token appears to be missing from header.",authException);
		response.setContentType("application/json");
	    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    response.getOutputStream().println("{ \"message\": \"" + authException.getMessage() + "\" }");
	}
}