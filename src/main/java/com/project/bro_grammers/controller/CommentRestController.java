package com.project.bro_grammers.controller;


import com.project.bro_grammers.service.CodeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CommentRestController {
    private final CommentService commentService;
}
