package com.project.bro_grammers.controller;


import com.project.bro_grammers.dto.CommentSubmissionRequest;
import com.project.bro_grammers.model.Comment;
import com.project.bro_grammers.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentRestController {
    private final CommentService commentService;

    @PostMapping("/comments/codes/{codeId}")
    public ResponseEntity<CommentSubmissionRequest> createComment(@RequestBody CommentSubmissionRequest comment, @PathVariable Long codeId, @RequestHeader("Authorization") String authHeader) {
        System.out.println(comment.getContent());
        return commentService.createComment(comment, codeId, authHeader);
    }

    @DeleteMapping("comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, @RequestHeader("Authorization") String authHeader) {
        return commentService.deleteComment(commentId, authHeader);
    }

    @GetMapping("comments/myComments")
    public List<Comment> myComments(@RequestHeader("Authorization") String authHeader) {
        return commentService.myAllComments(authHeader);
    }

    @GetMapping("comments/user/{userId}")
    public List<Comment> getAllUserComments(@PathVariable Long userId) {
        return commentService.getAllUserComments(userId);
    }

    @GetMapping("comments/{commentId}")
    public Comment getComment(@PathVariable Long commentId) {
        return commentService.findComment(commentId);
    }

    @PatchMapping("/comments/{commentId}")
    public Comment updateComment(@PathVariable Long commentId, @RequestBody CommentSubmissionRequest newComment, @RequestHeader("Authorization") String authHeader) {
        return commentService.updateComment(commentId, newComment, authHeader);
    }

}
