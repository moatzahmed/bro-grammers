package com.project.bro_grammers.service;

import com.project.bro_grammers.dto.CommentSubmissionRequest;
import com.project.bro_grammers.model.Comment;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<CommentSubmissionRequest> createComment(CommentSubmissionRequest comment, Long codeId, String authHeader);
}
