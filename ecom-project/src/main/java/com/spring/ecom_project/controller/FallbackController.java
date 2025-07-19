package com.spring.ecom_project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {
    @RequestMapping("/**")
    public ResponseEntity<?> fallback() {
        return ResponseEntity.status(404).body("Endpoint is not valid. Move to another endpoint.");
    }
} 