package com.complaints.resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.complaints.dao.ComplaintDAOImpl;
import com.complaints.dto.AuthTransfer;
import com.complaints.dto.FacebookTransfer;
import com.complaints.dto.PasswordChangeTransfer;
import com.complaints.dto.TokenTransfer;
import com.complaints.entities.UserEntity;
import com.complaints.response.ChangePasswordServiceResponse;
import com.complaints.response.ServiceResponse;
import com.complaints.service.CommService;
import com.complaints.service.UserService;
import com.complaints.utils.TokenUtils;
import com.sun.jersey.api.model.AbstractResource;
import com.sun.jersey.api.model.AbstractSubResourceMethod;
import com.sun.jersey.server.impl.modelapi.annotation.IntrospectionModeller;

@Component
@Path("/user")
public class UserResource {
	
	private static Logger LOG = Logger.getLogger(ComplaintDAOImpl.class);
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authManager;

	@Autowired
	private CommService commService;

	@GET
	public Response availableServices() {
		LOG.info("view paths");
		AbstractResource resource = IntrospectionModeller.createResource(this
				.getClass());

		String availablePaths = "";
		String uriPrefix = resource.getPath().getValue();
		for (AbstractSubResourceMethod subResource : resource
				.getSubResourceMethods()) {
			String uri = uriPrefix + "/" + subResource.getPath().getValue();
			availablePaths += subResource.getHttpMethod() + " at the path "
					+ uri + " return " + subResource.getReturnType().getName()
					+ "<br>";
		}
		return Response.ok().entity(availablePaths).build();
	}

	@POST
	@Path("createUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(UserEntity user) {
		LOG.info("Creating the user "+user.getUsername());
		ResponseBuilder respbuilder = null;
		ServiceResponse resp = null;
		resp = this.userService.createUser(user);
	    if(null != resp.getDbResp() && resp.getDbResp().isSuccess()) {
	       	respbuilder = Response.ok().entity(resp);
	    } else {
	       	respbuilder = Response.status(500).entity(resp);
	    }
    	return respbuilder.build();
	}

	@Path("getUser")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response getUser() {	
		LOG.info("Get details from token");
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof String
				&& ((String) principal).equals("anonymousUser")) { 
			return Response.status(401).entity("Session has expired or User is incorrect.").build();
		}
		UserEntity userDetails = (UserEntity) principal;
		userDetails.setPassword(null);
		return Response.ok().entity(userDetails).build();
	}

	@Path("authenticate")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticate(AuthTransfer authDetails) {
		LOG.info("authenticate");
		UserDetails userDetails;
		try {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					authDetails.getUsername(), authDetails.getPassword());
			Authentication authentication = this.authManager
					.authenticate(authenticationToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			/*
			 * Reload user as password of authentication principal will be null
			 * after authorization and password is needed for token generation
			 */
			userDetails = this.userService
					.loadUserByUsername(authDetails.getUsername());
		} catch(BadCredentialsException|AuthenticationServiceException e) {
			LOG.error("An authentication exception has occured \n",e);
			return Response.status(Response.Status.UNAUTHORIZED)
					.entity(new TokenTransfer("User details are incorrect.")).build();
		}
		return Response.ok().entity(new TokenTransfer(TokenUtils.createToken(userDetails),"Successful Auth")).build();
	}
	
	@Path("facebookAuth")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response facebookAuth(FacebookTransfer fbto) {
	    System.out.println(fbto.getEmail());
	    LOG.info("authenticate via facebook");
	    ResponseBuilder respbuilder = null;
        ServiceResponse resp = null;
        UserDetails userDetails = null;
	    
        resp = this.userService.createAndOrLogin(fbto);
        if(resp.getStatus() == 201 || resp.getStatus() == 200) {
            LOG.info("Authenticating user");
            try {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        fbto.getEmail().split("@")[0], fbto.getId());
                Authentication authentication = this.authManager
                        .authenticate(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                userDetails = this.userService
                        .loadUserByUsername(fbto.getEmail().split("@")[0]);
            } catch(BadCredentialsException|AuthenticationServiceException e) {
                LOG.error("An authentication exception has occured \n",e);
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(new TokenTransfer("User details are incorrect.")).build();
            }
            return Response.ok().entity(new TokenTransfer(TokenUtils.createToken(userDetails),"success")).build();
        } else {
            return Response.status(Response.Status.CONFLICT).entity(resp).build();
        }
	}

	@Path("checkUsername")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	public Response doesUserExist(UserEntity user) {
		LOG.info("Checking if username exists for : " +user.getUsername());
		return Response.ok()
				.entity(userService.checkUsernameExists(user.getUsername()))
				.build();
	}
	
	@Path("changePassword")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	public Response changePassword(PasswordChangeTransfer passwords) {
		ChangePasswordServiceResponse resp = null;
		if(null == passwords || null == passwords.getNewPassword() || 
				null ==passwords.getOldPassword()) {
			resp = new ChangePasswordServiceResponse();
			resp.setStatus(500);
			resp.setMessage("A field is missing");
		} else if(passwords.getNewPassword().equals(passwords.getOldPassword())) {
			resp = new ChangePasswordServiceResponse();
			resp.setStatus(500);
			resp.setMessage("Passwords cannot be the same.");
		} else{
			LOG.info("Changing user password");
			resp = this.userService.changePassword(passwords);
		}
		return Response.status(resp.getStatus()).entity(resp).build();
	}

	@Path("resetPassword/{username}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendPasswordResetEmail(@PathParam("username") String username, @Context HttpServletRequest request) {
	    LOG.info("Forgotten password for : "+username);
	    ServiceResponse resp = new ServiceResponse();
		UserEntity userEntity = (UserEntity) userService
                .loadUserByUsername(username);
		if (null == userEntity) {
            resp.setMessage("Username is not valid.");
            resp.setStatus(403);
        } else if(userEntity.isFacebookAccount()){
		    resp.setMessage("Facebook users cannot change password.");
            resp.setStatus(403);
		} else if (this.userService.checkUserLimit(userEntity.getId())) {
            resp.setMessage("Email limit reached.");
            resp.setStatus(500);
        } else {
		    resp.setMessage("Successfully sent password reset email.");
            resp.setStatus(200);
            
			String newPassword = this.userService.generateNewPassword(userEntity);
            this.commService.sendPasswordResetNotification(userEntity, newPassword);
            this.userService.saveEmail(request.getRemoteAddr(),userEntity.getId());
			return Response.ok().entity(resp).build();
		}
		    
		
        return Response.status(Response.Status.FORBIDDEN)
                .entity(resp).build();
	}

	@Path("forgottenUsername/{email}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response forgottenUsername(@PathParam("email") String email, @Context HttpServletRequest request) {
		LOG.info("Forgotten Username for : "+email+", with an ip address of: "+request.getRemoteAddr());
		ServiceResponse resp = new ServiceResponse();
		UserEntity userDetails = (UserEntity) this.userService
				.findUserByEmail(email);
		
		if (null == userDetails) {
            resp.setMessage("Username is not valid.");
            resp.setStatus(403);
        } else if(userDetails.isFacebookAccount()){
            resp.setMessage("Facebook users cannot retrieve account.");
            resp.setStatus(403);
        } else if (this.userService.checkUserLimit(userDetails.getId())) {
            resp.setMessage("Email limit reached.");
            resp.setStatus(500);
        }else {
		    resp.setMessage("Successfully obtained username.");
		    resp.setStatus(200);
			this.commService.sendUsernameReminderNotification(userDetails);
			this.userService.saveEmail(request.getRemoteAddr(),userDetails.getId());
			return Response.ok().entity(resp).build();
		}

		return Response.status(Response.Status.FORBIDDEN)
				.entity(resp).build();
	}
}
