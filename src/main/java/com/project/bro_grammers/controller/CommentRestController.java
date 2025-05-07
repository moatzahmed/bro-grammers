package com.project.bro_grammers.controller;


import com.project.bro_grammers.dto.CommentSubmissionRequest;
import com.project.bro_grammers.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentRestController {
    private final CommentService commentService;
    @PostMapping("/comment/{codeId}")
    public ResponseEntity<CommentSubmissionRequest> createComment(@RequestBody CommentSubmissionRequest comment, @PathVariable Long codeId, @RequestHeader("Authorization") String authHeader) {
        return commentService.createComment(comment, codeId, authHeader);
    }

}
