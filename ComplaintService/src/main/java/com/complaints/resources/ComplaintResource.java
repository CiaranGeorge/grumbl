package com.complaints.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.complaints.entities.CommentEntity;
import com.complaints.entities.ComplaintEntity;
import com.complaints.response.ServiceResponse;
import com.complaints.service.ComplaintService;
import com.sun.jersey.api.model.AbstractResource;
import com.sun.jersey.api.model.AbstractSubResourceMethod;
import com.sun.jersey.server.impl.modelapi.annotation.IntrospectionModeller;

@Component
@Path("complaint")
public class ComplaintResource {
	
	private static Logger LOG = Logger.getLogger(ComplaintResource.class);
	
	@Autowired
	private ComplaintService complaintService;
	
	@Path("list")
	@GET
	public Response availableServices() {
		AbstractResource resource = IntrospectionModeller.createResource(this.getClass());
		
		String availablePaths = "";
		String uriPrefix = resource.getPath().getValue();
		for (AbstractSubResourceMethod subResource :resource.getSubResourceMethods())
		{
		    String uri = uriPrefix + "/" + subResource.getPath().getValue();
		    availablePaths += subResource.getHttpMethod() + " at the path " + uri + " return " + subResource.getReturnType().getName() +"<br>";
		}
		return Response.ok().entity(availablePaths).build();
	}

    @Path("createComplaint")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createComplaint(ComplaintEntity complaintEntity)
    {
    	LOG.info("createComplaint");
    	ResponseBuilder respbuilder;
    	ServiceResponse resp = this.complaintService.createComplaint(complaintEntity);
    	if(null != resp.getDbResp() && resp.getDbResp().isSuccess()) {
        	respbuilder = Response.ok().entity(resp);
        } else {
        	respbuilder = Response.status(500).entity(resp);
        }
    	return respbuilder.build();
    }
    
    @Path("createComment")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createComment(CommentEntity commentEntity)
    {
        LOG.info("createComment");
        ResponseBuilder respbuilder;
        ServiceResponse resp = this.complaintService.createComment(commentEntity);
        if(null != resp.getDbResp() && resp.getDbResp().isSuccess()) {
            respbuilder = Response.ok().entity(resp);
        } else {
            respbuilder = Response.status(500).entity(resp);
        }
        return respbuilder.build();
    }
    
    @Path("/getComplaint/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComplaint(@PathParam("id") String id)
    {
    	LOG.info("getComplaint for id : "+id);
    	ResponseBuilder respbuilder;
    	ServiceResponse resp = this.complaintService.getComplaint(id);
    	if(null != resp.getDbResp() && resp.getDbResp().isSuccess()) {
        	respbuilder = Response.ok().entity(resp);
        } else {
        	respbuilder = Response.status(500).entity(resp);
        }
    	return respbuilder.build();
    }

    @Path("createReport")
	@POST
	public Response createReport() {
		return Response.ok().entity("Create Report").build();
	}
	
	@GET
	@Path("getReport")
	public Response getReport() {
		return Response.ok().entity("Get Report").build();
	}
}
