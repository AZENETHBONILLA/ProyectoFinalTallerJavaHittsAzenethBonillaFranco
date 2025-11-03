package com.hitss.springboot.taskmanager.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hitss.springboot.taskmanager.models.User;

public class AuthController {
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        return ResponseEntity.ok("Este endpoint es manejado por Spring Security.");
    }
}
