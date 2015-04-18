package com.complaints.service;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.complaints.dao.UserDAOImpl;
import com.complaints.dto.FacebookTransfer;
import com.complaints.dto.PasswordChangeTransfer;
import com.complaints.entities.EmailEntity;
import com.complaints.entities.RoleEntity;
import com.complaints.entities.UserEntity;
import com.complaints.response.ChangePasswordServiceResponse;
import com.complaints.response.DatabaseResponse;
import com.complaints.response.ServiceResponse;
import com.complaints.utils.PasswordUtils;
import com.complaints.utils.TokenUtils;

public class UserServiceImpl implements UserService {
	
	private static Logger LOG = Logger.getLogger(UserServiceImpl.class);
	
	private static Validator validator;
	
	@Autowired
	@Qualifier("userDAOImpl")
	private UserDAOImpl userDAO;
	
	private PasswordEncoder passwordEncoder;
	
	public UserServiceImpl(PasswordEncoder passwordEncoder) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	    validator = factory.getValidator();
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		UserEntity user = userDAO.findByName(username);
		
		return user;
	}

	/**
     * @author c.george
     * 
     * Ensure there are no validation exceptions before creating the user.
     */
	@Override
	@Transactional
	public ServiceResponse createUser(UserEntity user) {
		LOG.info("createUser");
		ServiceResponse resp = new ServiceResponse();
		DatabaseResponse dbResp = null;
		
		if(this.userDAO.findUserByEmail(user.getEmail()) != null) {
		    LOG.info("The email "+user.getEmail()+" is already in use.");
            resp.setMessage("This email is already in use, check our forgotten username page.");
            resp.setStatus(500);
		} else {
    		Set<ConstraintViolation<UserEntity>> constraintViolations = validator.validate(user);
    		
    		if(constraintViolations.isEmpty()) {
    			LOG.info("There are no validation violations");
    			giveUserRole(user);
    			user.setFacebookAccount(false);
    			dbResp = this.userDAO.createUser(user);
    		}
    		
    		if(null == dbResp) {
    			LOG.info("The user object contains invalid data : " +constraintViolations.iterator().next().getMessage());
    			resp.setMessage("A validation exception has occured.");
            	resp.setStatus(500);
    		}else if(dbResp.isSuccess()) {
            	resp.setDbResp(dbResp);
            	resp.setStatus(200);
            } else {
            	resp.setMessage("A database exception has occured.");
            	resp.setDbResp(dbResp);
            	resp.setStatus(500);
            }
		}
        return resp;
	}
	
	/**
     * @author c.george
     * 
     * Upgrade all newly created users to have the role USER.
     */
	private void giveUserRole(UserEntity user) {
		Date currentTimestamp = new Date();
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		user.setTimeCreated(currentTimestamp);
		user.setTimeUpdated(currentTimestamp);
		RoleEntity userRole = new RoleEntity();
		userRole.setId(2);
		userRole.setRole("ROLE_USER");
		user.addRole(userRole);
	}

	@Override
	public boolean checkUsernameExists(String username) {
		 return null != userDAO.findByName(username);
	}

    @Override
    public void updateUser(UserEntity userEntity) {
        this.userDAO.updateUser(userEntity);
    }

    /**
     * @author c.george
     * 
     * Generate a new password based on a random array of alphanumeric
     * characters.
     */
    @Override
    public String generateNewPassword(UserEntity userEntity)
    {
        String newPassword = PasswordUtils.generateRandomPassword();
        Date currentTimestamp = new Date();
        userEntity.setTimeUpdated(currentTimestamp);
        String randomEncryptedPassword = passwordEncoder.encode(newPassword);
        userEntity.setPassword(randomEncryptedPassword);

        this.userDAO.updateUser(userEntity);
        return newPassword;
    }
    
    @Override
    public UserEntity findUserByEmail(String email) {
    	return this.userDAO.findUserByEmail(email);
    }

    /**
     * @author c.george
     * 
     * Check if the password entered matches the users password,
     * YES - change the password and generate new token
     * NO - return an error
     */
	@Override
	public ChangePasswordServiceResponse changePassword(PasswordChangeTransfer passwords) {
		UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		LOG.info("Updating password for "+user.getUsername());
		ChangePasswordServiceResponse resp = new ChangePasswordServiceResponse();
		LOG.info(passwordEncoder.matches(passwords.getOldPassword(), user.getPassword()));
		
		if(passwordEncoder.matches(passwords.getOldPassword(),user.getPassword())) {
			user.setPassword(passwordEncoder.encode(passwords.getNewPassword()));
			Date currentTimestamp = new Date();
	        user.setTimeUpdated(currentTimestamp);
			userDAO.updateUser(user);
			resp.setMessage("Password has been successfully changed");
			resp.setStatus(200);
			resp.setToken(TokenUtils.createToken(user));
		} else {
			resp.setMessage("The password entered is incorrect");
			resp.setStatus(500);
		}
		
		return resp;
	}

    @Override
    public ServiceResponse createAndOrLogin(FacebookTransfer userDetails) {
        ServiceResponse resp = new ServiceResponse();
        
        resp = this.createFBUser(userDetails);

        return resp;
    }
    
    private ServiceResponse createFBUser(FacebookTransfer userDetails) {
        LOG.info("Attempting to create Facebook User in DB for - "+userDetails.getEmail());
        ServiceResponse resp = new ServiceResponse();
        UserEntity user = new UserEntity();
        DatabaseResponse dbResp = null;
        if(null == this.userDAO.findUserByEmail(userDetails.getEmail())) {
            user.setEmail(userDetails.getEmail());
            String[] names = userDetails.getName().split("\\s+");
            user.setUsername(userDetails.getEmail().split("@")[0]);
            user.setFirstname(names[0]);
            user.setLastname(names[names.length-1]);
            user.setPassword(userDetails.getId());
            user.setFacebookAccount(true);
            giveUserRole(user);
            
            dbResp = this.userDAO.createUser(user);
            
            if(dbResp.isSuccess()) {
                LOG.info("User created");
                resp.setDbResp(dbResp);
                resp.setStatus(200);
            } else {
                LOG.info("An error has occured at the DB layer");
                resp.setMessage("A database exception has occured.");
                resp.setDbResp(dbResp);
                resp.setStatus(500);
            }
        } else {
            LOG.info("User email address already exists");
            resp.setMessage("Email address already in use.");
            resp.setStatus(201);
        }
        return resp;
    }

    @Override
    public void saveEmail(String remoteAddr, Integer id) {
        LOG.info("Save Details to email table");
        EmailEntity emailDetails = new EmailEntity();
        emailDetails.setEmailSent(new Date());
        emailDetails.setUserId(id);
        emailDetails.setIp(remoteAddr);
        userDAO.saveEmail(emailDetails);
    }
    
    @Override
    public boolean checkUserLimit(Integer id) {
        return userDAO.limitEmailsByUser(id) >= 2;
    }
        
}
