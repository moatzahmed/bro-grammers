package com.project.bro_grammers.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.bro_grammers.exception.BadRequestException;
import com.project.bro_grammers.exception.NotAllowedIdException;
import com.project.bro_grammers.exception.ResourceNotFoundException;
import com.project.bro_grammers.model.User;
import com.project.bro_grammers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImp implements UserService {
    private UserRepository userRepository;
    private ObjectMapper objectMapper;

    @Autowired
    public UserServiceImp(UserRepository userRepository, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public User createUser(User user) {
        if (user.getId() != null) {
            throw new NotAllowedIdException("Can't Add Id for The User manually !");
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User find(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This user with ID " + id + " isn't found :("));
    }

    @Override
    public User update(User user) {
        find(user.getId());
        return userRepository.save(user);
    }

    @Override
    public User patch(int id, Map<String, Object> updates) {
        User user = find(id);
        if (updates.containsKey("id")) throw new NotAllowedIdException("Can't Add Id for The User manually !");
        ObjectNode userNode = objectMapper.convertValue(user, ObjectNode.class);
        ObjectNode patchNode = objectMapper.convertValue(updates, ObjectNode.class);
        userNode.setAll(patchNode);
        return objectMapper.convertValue(userNode, User.class);

    }

    @Override
    public void delete(int id) {
        find(id);
        userRepository.deleteById(id);
    }


}
