package com.complaints.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.complaints.dto.FacebookTransfer;
import com.complaints.dto.PasswordChangeTransfer;
import com.complaints.entities.UserEntity;
import com.complaints.response.ChangePasswordServiceResponse;
import com.complaints.response.ServiceResponse;

public interface UserService extends UserDetailsService {
	
	public ServiceResponse createUser(UserEntity user);
	public boolean checkUsernameExists(String username);
    void updateUser(UserEntity userEntity);
    String generateNewPassword(UserEntity userEntity);
	UserEntity findUserByEmail(String email);
	public ChangePasswordServiceResponse changePassword(PasswordChangeTransfer passwords);
    public ServiceResponse createAndOrLogin(FacebookTransfer fbto);
    public void saveEmail(String remoteAddr, Integer id);
    boolean checkUserLimit(Integer id);
}
