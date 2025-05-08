package com.project.bro_grammers.service;

import com.project.bro_grammers.dto.CommentSubmissionRequest;
import com.project.bro_grammers.model.Comment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface CommentService {
    ResponseEntity<CommentSubmissionRequest> createComment(CommentSubmissionRequest comment, Long codeId, String authHeader);

    ResponseEntity<Void> deleteComment(Long commentId, String authHeader);

    List<Comment> myAllComments(String authHeader);

    Comment findComment(Long commentId);

    List<Comment> getAllUserComments(Long userId);

    Comment updateComment(Long commentId, CommentSubmissionRequest newComment, String authHeader);

}
