package com.complaints.dao;

import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.complaints.entities.ComplaintEntity;
import com.complaints.entities.EmailEntity;
import com.complaints.entities.UserEntity;
import com.complaints.persistence.HibernateUtil;
import com.complaints.response.DatabaseResponse;

public class UserDAOImpl implements UserDAO {
	
	private static Logger LOG = Logger.getLogger(UserDAOImpl.class);
	
    @Override
    public DatabaseResponse createUser(UserEntity userEntity)
    {
    	LOG.info("createUser");
    	DatabaseResponse response = new DatabaseResponse();
    	Session session = HibernateUtil.getSessionFactory().openSession();
    	
    	try {
	        session.beginTransaction();
	        session.save(userEntity);
	        LOG.info("User has been successfully created");
	        session.getTransaction().commit();
	        session.flush();
    	}catch(Exception e) {
    		LOG.error("Creating a user has failed \n", e );
    		response.setStatus("An exception has occured with the database");
    		response.setSuccess(false);
    		return response;
        }
    	finally {
			session.close();
    	}
    	response.setStatus("User has been added");
		response.setSuccess(true);
    	return response;
    }

	@Override
	public UserEntity findByName(String username) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		 
		String queryString = "from UserEntity where username = :username";
		Query query = session.createQuery(queryString);
		query.setString("username", username);
		UserEntity user = (UserEntity) query.uniqueResult();
		session.flush();
        session.close();
        
		return user;
	}

    @Override
    public void updateUser(UserEntity userEntity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(userEntity);
        session.getTransaction().commit();
        session.flush();
        session.close();
    }
    
    @Override
    public Set<ComplaintEntity> findComplaintsByUserName(UserEntity userEntity)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();

        String queryString = "from UserEntity where username = :username";
        Query query = session.createQuery(queryString);
        query.setString("username", userEntity.getUsername());
        UserEntity user = (UserEntity) query.uniqueResult();
        session.flush();
        session.close();

        return user.getComplaintEntities();
    }

    public UserEntity findUserByEmail(String email) {
		Session session = HibernateUtil.getSessionFactory().openSession();

        String queryString = "from UserEntity where email = :email";
        Query query = session.createQuery(queryString);
        query.setString("email", email);
        UserEntity user = (UserEntity) query.uniqueResult();
        session.flush();
        session.close();

        return user;
	}
    
    @Override
    public void saveEmail(EmailEntity emailDetails) {
        LOG.info("saveEmail");
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(emailDetails);
        LOG.info("Email saved");
        session.getTransaction().commit();
        session.flush();
        session.close();
    }
    
    @Override
    public int limitEmailsByUser(int userId) {
        LOG.info("limitEmail");
        Session session = HibernateUtil.getSessionFactory().openSession();

        String queryString = "SELECT * FROM email WHERE userId = "+userId+
                " and emailSent >= NOW() - INTERVAL 1 HOUR";
        Query query = session.createSQLQuery(queryString);
        int emailNums  = query.list().size();
        LOG.info(emailNums);
        
        session.flush();
        session.close();
        return emailNums;
    }
}
