package com.project.bro_grammers.service;

import com.project.bro_grammers.dto.LoginRequest;
import com.project.bro_grammers.dto.LoginResponse;
import com.project.bro_grammers.dto.RegisterRequest;
import com.project.bro_grammers.model.User;
import com.project.bro_grammers.repository.UserRepository;
import com.project.bro_grammers.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService{
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    @Override
    public LoginResponse register(RegisterRequest registerRequest) {
        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(registerRequest.getRole())
                .build();
        var savedUser = userRepository.save(user);
        String token = jwtUtil.generateToken(savedUser.getEmail(), savedUser.getRole().name());
        return LoginResponse.builder()
                .accessToken(token)
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return LoginResponse.builder()
                .accessToken(token)
                .build();
    }
}
