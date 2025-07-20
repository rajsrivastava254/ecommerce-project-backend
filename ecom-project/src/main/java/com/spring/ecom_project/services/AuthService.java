package com.spring.ecom_project.services;

import com.spring.ecom_project.dto.AuthRequest;
import com.spring.ecom_project.dto.AuthResponse;
import com.spring.ecom_project.dto.RegisterRequest;
import com.spring.ecom_project.model.Role;
import com.spring.ecom_project.model.User;
import com.spring.ecom_project.repository.UserRepository;
import com.spring.ecom_project.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    public AuthResponse register(RegisterRequest request) {
        // Check if user already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            return new AuthResponse(null, null, null, "Username already exists");
        }
        
        if (userRepository.existsByEmail(request.getEmail())) {
            return new AuthResponse(null, null, null, "Email already exists");
        }
        
        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER); // Default role
        user.setEnabled(true);
        
        userRepository.save(user);
        
        // Generate token
        String token = jwtService.generateToken(user);
        
        return new AuthResponse(token, user.getUsername(), user.getRole().name(), "User registered successfully");
    }
    
    public AuthResponse authenticate(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            
            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            String token = jwtService.generateToken(user);
            
            return new AuthResponse(token, user.getUsername(), user.getRole().name(), "Login successful");
        } catch (Exception e) {
            return new AuthResponse(null, null, null, "Invalid username or password");
        }
    }
} 