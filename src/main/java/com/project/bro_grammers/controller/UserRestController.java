package com.project.bro_grammers.controller;


import com.project.bro_grammers.model.User;
import com.project.bro_grammers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserRestController {
    private final UserService userService;


    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id) {
        return userService.find(id);
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        return userService.update(user);
    }

    @PatchMapping("/users/{id}")
    public User updateUser(@PathVariable int id, @RequestBody Map<String, Object> updates) {
        return userService.patch(id, updates);
    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

