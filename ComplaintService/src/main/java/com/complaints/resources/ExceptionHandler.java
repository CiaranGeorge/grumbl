package com.complaints.resources;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.complaints.response.ServiceResponse;

@Component
public class ExceptionHandler {
	
	private static Logger LOG = Logger.getLogger(ExceptionHandler.class);
	
	@Provider
	public static class JsonMappingExceptionMapper implements
			ExceptionMapper<Exception> {
		
		/**
		 * Catch all Jackson exceptions and map them to JSON.
		 */
		@Override
		public Response toResponse(Exception exception) {
			LOG.error("A JSONMappingError has occured \n",exception);
			ServiceResponse resp = new ServiceResponse();
			resp.setMessage("An error has occured on the service.");
			resp.setStatus(500);
			return Response.serverError().entity(resp).build();
		}
	}
	
}
