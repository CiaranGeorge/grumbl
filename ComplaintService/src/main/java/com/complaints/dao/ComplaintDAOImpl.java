package com.complaints.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.complaints.entities.CommentEntity;
import com.complaints.entities.ComplaintEntity;
import com.complaints.persistence.HibernateUtil;
import com.complaints.response.ComplaintDatabaseResponse;
import com.complaints.response.DatabaseResponse;

public class ComplaintDAOImpl implements ComplaintDAO
{
	private static Logger LOG = Logger.getLogger(ComplaintDAOImpl.class);
	
    @Override
    public DatabaseResponse createComplaint(ComplaintEntity complaintEntity)
    {
    	LOG.info("createComplaint");
    	DatabaseResponse response = new DatabaseResponse();
    	Session session = HibernateUtil.getSessionFactory().openSession();
    	
    	try {
	        session.beginTransaction();
	        session.save(complaintEntity);
	        LOG.info("Complaint has been successfully created");
	        session.getTransaction().commit();
	        session.flush();
    	}catch(Exception e) {
    		LOG.error("Creating a complaint has failed \n", e);
    		response.setStatus("An exception has occured with the database");
    		response.setSuccess(false);
    		return response;
        }
    	finally {
			session.close();
    	}
    	response.setStatus("Complaint has been added");
		response.setSuccess(true);
    	return response;
    }
    
    @Override
    public DatabaseResponse createComment(CommentEntity commentEntity) {
        LOG.info("createComment");
        DatabaseResponse response = new DatabaseResponse();
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try {
            session.beginTransaction();
            session.save(commentEntity);
            LOG.info("Comment has been successfully created");
            session.getTransaction().commit();
            session.flush();
        }catch(Exception e) {
            LOG.error("Creating a comment has failed \n", e);
            response.setStatus("An exception has occured with the database");
            response.setSuccess(false);
            return response;
        }
        finally {
            session.close();
        }
        response.setStatus("Comment has been added");
        response.setSuccess(true);
        return response;
    }
    
    @Override
    public ComplaintDatabaseResponse getComplaint(String id) {
    	LOG.info("getComplaint for id: "+id);
    	ComplaintDatabaseResponse response = new ComplaintDatabaseResponse();
    	Session session = HibernateUtil.getSessionFactory().openSession();
    	try {
    		String queryString = "from ComplaintEntity where id = :id";
            Query query = session.createQuery(queryString);
            query.setString("id", id);
            ComplaintEntity complaint = (ComplaintEntity) query.uniqueResult();
            response.setComplaint(complaint);
	        session.flush();
    	}catch(Exception e) {
    		LOG.error("Getting a complaint has failed \n", e);
    		response.setStatus("An exception has occured with the database");
    		response.setSuccess(false);
    		return response;
        }
    	finally {
			session.close();
    	}
    	if(null == response.getComplaint()) {
    		response.setStatus("Complaint cannot be found for specified ID");
			response.setSuccess(false);
    	} else{
	    	response.setStatus("Complaint has been added");
			response.setSuccess(true);
    	}
    	return response;
    }
    
    @Override
    public List<CommentEntity> getComments(String complaintId) throws Exception {
        LOG.info("Get comments for id: "+complaintId);
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<CommentEntity> comments;
        try {
            
            String queryString = "from CommentEntity where complaintId = :complaintId";
            Query query = session.createQuery(queryString);
            query.setString("complaintId", complaintId);
            comments = query.list();
            session.flush();
        }catch(Exception e) {
               throw new Exception(e);
        }
        finally {
            session.close();
        }
        
        return comments;
    }
    
}
