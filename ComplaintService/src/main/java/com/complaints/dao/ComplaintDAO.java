package com.complaints.dao;

import java.util.List;

import com.complaints.entities.CommentEntity;
import com.complaints.entities.ComplaintEntity;
import com.complaints.response.ComplaintDatabaseResponse;
import com.complaints.response.DatabaseResponse;

public interface ComplaintDAO {
    DatabaseResponse createComplaint(ComplaintEntity complaintEntity);

    ComplaintDatabaseResponse getComplaint(String id);

    DatabaseResponse createComment(CommentEntity commentEntity);

    List<CommentEntity> getComments(String complaintId) throws Exception;
}
