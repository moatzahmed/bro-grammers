package com.project.bro_grammers.service;

import com.project.bro_grammers.dto.LoginRequest;
import com.project.bro_grammers.dto.LoginResponse;
import com.project.bro_grammers.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    LoginResponse register(RegisterRequest registerRequest );
    LoginResponse login(LoginRequest loginRequest);
    ResponseEntity<?> logout(String authHeader);
}
