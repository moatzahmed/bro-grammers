package com.project.bro_grammers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.bro_grammers.exception.NotAllowedIdException;
import com.project.bro_grammers.exception.ResourceNotFoundException;
import com.project.bro_grammers.model.User;
import com.project.bro_grammers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImp implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UserRepository userRepository, ObjectMapper objectMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User user) {
        if (user.getId() != null) {
            throw new NotAllowedIdException("Can't Add Id for The User manually !");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User find(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This user with ID " + id + " isn't found :("));
    }

    @Override
    public User update(User user) {
        find(user.getId());
        return userRepository.save(user);
    }

    @Override
    public User patch(Long id, Map<String, Object> updates) {
        User user = find(id);
        if (updates.containsKey("id")) throw new NotAllowedIdException("Can't Add Id for The User manually !");
        ObjectNode userNode = objectMapper.convertValue(user, ObjectNode.class);
        ObjectNode patchNode = objectMapper.convertValue(updates, ObjectNode.class);
        userNode.setAll(patchNode);
        return userRepository.save(objectMapper.convertValue(userNode, User.class));

    }

    @Override
    public void delete(Long id) {
        find(id);
        userRepository.deleteById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
