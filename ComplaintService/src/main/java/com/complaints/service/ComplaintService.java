package com.complaints.service;

import com.complaints.entities.CommentEntity;
import com.complaints.entities.ComplaintEntity;
import com.complaints.response.ServiceResponse;

public interface ComplaintService {
    ServiceResponse createComplaint(ComplaintEntity complaintEntity);

	ServiceResponse getComplaint(String id);

    ServiceResponse createComment(CommentEntity commentEntity);
}
