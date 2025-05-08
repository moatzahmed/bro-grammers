package com.project.bro_grammers.service;

import com.project.bro_grammers.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User createUser(User user);

    List<User> findAll();

    User find(Long id);

    User update(User user);

    User patch(Long id, Map<String, Object> updates);

    void delete(Long id);


}
