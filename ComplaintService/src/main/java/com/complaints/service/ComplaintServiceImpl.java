package com.complaints.service;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.complaints.dao.ComplaintDAO;
import com.complaints.entities.CommentEntity;
import com.complaints.entities.ComplaintEntity;
import com.complaints.entities.UserEntity;
import com.complaints.response.ComplaintDatabaseResponse;
import com.complaints.response.DatabaseResponse;
import com.complaints.response.ServiceResponse;
import com.complaints.utils.AWSUtils;

public class ComplaintServiceImpl implements ComplaintService
{
	
	private static Logger LOG = Logger.getLogger(ComplaintServiceImpl.class);
	
    @Autowired
    private ComplaintDAO complaintDAO;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Override
    @Transactional
    public ServiceResponse createComplaint(ComplaintEntity complaintEntity)
    {
    	LOG.info("createComplaint");
    	ServiceResponse resp = new ServiceResponse();
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        complaintEntity.setOwnerId(user.getId());
        complaintEntity.setTimeCreated(new Date());
        complaintEntity.setTimeUpdated(new Date());
        DatabaseResponse dbResp = this.complaintDAO.createComplaint(complaintEntity);
        if(dbResp.isSuccess()) {
            if(null != complaintEntity.getImageData()) {
                AWSUtils.uploadImage(user.getUsername(), complaintEntity.getId(), complaintEntity.getImageData());
            }
        	resp.setDbResp(dbResp);
        	resp.setStatus(200);
        } else {
        	resp.setDbResp(dbResp);
        	resp.setStatus(500);
        }
        return resp;
    }
    
    @Override
    @Transactional
    public ServiceResponse createComment(CommentEntity commentEntity) {
        LOG.info("Creating comment.");
        ServiceResponse resp = new ServiceResponse();
        commentEntity.setTimeCreated(new Date());
        commentEntity.setTimeUpdated(new Date());
        DatabaseResponse dbResp = this.complaintDAO.createComment(commentEntity);
        if(dbResp.isSuccess()) {
            resp.setDbResp(dbResp);
            resp.setStatus(200);
        } else {
            resp.setDbResp(dbResp);
            resp.setStatus(500);
        }
        return resp;
    }
    
    @Transactional
    public ServiceResponse getComplaint(String id) {
    	LOG.info("getComplaint");
    	ServiceResponse resp = new ServiceResponse();
    	String username = SecurityContextHolder.getContext().getAuthentication().getName();
    	ComplaintDatabaseResponse dbResp = this.complaintDAO.getComplaint(id);
        if(dbResp.isSuccess()) {
        	try {
        	    dbResp.getComplaint().setCommentEntities(this.complaintDAO.getComments(id));
        		dbResp.getComplaint().setImageData(AWSUtils.getImageFromAWS(username, id));
			} catch (IOException e) {
				LOG.error("Failed to retrieve image from AWS",e);
				resp.setMessage("An exception has occured recieving this complaint.");
	        	resp.setStatus(500);
			} catch (AmazonS3Exception e) {
			    LOG.info("Failed to retrieve image from AWS");
                resp.setMessage("This complaint does not contain an image.");
                resp.setStatus(500);
			} catch (Exception e) {
			    LOG.info("An error has occured on the service - cant get comments.");
                resp.setMessage("This complaint cannot retrieve comments.");
                resp.setStatus(500);
			}
        	resp.setDbResp(dbResp);
        	resp.setStatus(200);
        } else {
        	resp.setDbResp(dbResp);
        	resp.setStatus(500);
        }
        return resp;
    }
}
