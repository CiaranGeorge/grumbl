package com.complaints.dao;

import java.util.Set;

import com.complaints.entities.ComplaintEntity;
import com.complaints.entities.EmailEntity;
import com.complaints.entities.UserEntity;
import com.complaints.response.DatabaseResponse;

public interface UserDAO {

	DatabaseResponse createUser(UserEntity userEntity);
	UserEntity findByName(String username);
    void updateUser(UserEntity userEntity);
    Set<ComplaintEntity> findComplaintsByUserName(UserEntity userEntity);
    UserEntity findUserByEmail(String email);
    void saveEmail(EmailEntity emailDetails);
    int limitEmailsByUser(int userId);
}
