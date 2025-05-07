package com.project.bro_grammers.service;

import com.project.bro_grammers.dto.CommentSubmissionRequest;
import com.project.bro_grammers.model.Comment;
import com.project.bro_grammers.repository.CommentRepository;
import com.project.bro_grammers.security.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class CommentServiceImp implements CommentService {
    private final JwtUtil jwtUtil;
    private final CommentRepository commentRepository;
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
        return ResponseEntity.ok(commentDTO);
    }
}
