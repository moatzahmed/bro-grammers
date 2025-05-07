package com.project.bro_grammers.service;

import com.project.bro_grammers.dto.LoginRequest;
import com.project.bro_grammers.dto.LoginResponse;
import com.project.bro_grammers.dto.RegisterRequest;
import com.project.bro_grammers.model.User;
import com.project.bro_grammers.repository.UserRepository;
import com.project.bro_grammers.security.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService{
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenBlackListService tokenBlacklistService;
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
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", savedUser.getRole().name());
        claims.put("ID", savedUser.getId());
        String token = jwtUtil.generateToken(savedUser.getEmail(), claims);
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
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());
        claims.put("ID", user.getId());
        String token = jwtUtil.generateToken(user.getEmail(), claims);
        return LoginResponse.builder()
                .accessToken(token)
                .build();
    }
    @Override
    public ResponseEntity<?> logout(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Date expiration = jwtUtil.extractClaim(token, Claims::getExpiration);
            tokenBlacklistService.blacklistToken(token, expiration);
        }
        return ResponseEntity.ok("Logged out successfully.");
    }
}
