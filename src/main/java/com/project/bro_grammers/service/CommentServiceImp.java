package com.project.bro_grammers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.bro_grammers.dto.CommentSubmissionRequest;
import com.project.bro_grammers.exception.BadRequestException;
import com.project.bro_grammers.exception.ResourceNotFoundException;
import com.project.bro_grammers.model.*;
import com.project.bro_grammers.repository.CommentRepository;
import com.project.bro_grammers.security.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImp implements CommentService {
    private final JwtUtil jwtUtil;
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ObjectMapper objectMapper;


    @Override
    public ResponseEntity<CommentSubmissionRequest> createComment(CommentSubmissionRequest commentDTO, Long codeId, String authHeader) {
        Long commenterId = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            commenterId = jwtUtil.extractId(token);
        }
        Comment comment = Comment.builder()
                .codeId(codeId)
                .content(commentDTO.getContent())
                .commenterId(commenterId)
                .build();
        commentRepository.save(comment);
        System.out.println(commentDTO.getContent());
        return ResponseEntity.ok(commentDTO);
    }

    @Override
    public ResponseEntity<Void> deleteComment(Long commentId, String authHeader) {
        Long deleterId = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            deleterId = jwtUtil.extractId(token);
        }
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Comment comment = optionalComment.orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        User deleter = userService.find(deleterId);
        if (comment.getCommenterId() != deleterId && deleter.getRole() != Role.TEAM_LEAD)
            throw new BadRequestException("Not authorized");

        commentRepository.delete(comment);
        return ResponseEntity.noContent().build();
    }

    @Override
    public List<Comment> myAllComments(String authHeader) {
        Long curUserId = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            curUserId = jwtUtil.extractId(token);
        }
        return commentRepository.findByCommenterId(curUserId);
    }


    @Override
    public Comment findComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("This comment with ID " + commentId + " isn't found :("));
    }

    @Override
    public List<Comment> getAllUserComments(Long userId) {
        return commentRepository.findByCommenterId(userId);
    }

    @Override
    public Comment updateComment(Long commentId, CommentSubmissionRequest newComment, String authHeader) {
        Long updaterId = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            updaterId = jwtUtil.extractId(token);
        }
        Comment comment = findComment(commentId);
        User user = userService.find(updaterId);
        if (comment.getCommenterId() != updaterId && user.getRole() != Role.TEAM_LEAD)
            throw new BadRequestException("Not authorized");

        ObjectNode oldComment = objectMapper.convertValue(comment, ObjectNode.class);
        oldComment.put("content", newComment.getContent());
        comment = objectMapper.convertValue(oldComment, Comment.class);
        return commentRepository.save(comment);

    }
}
