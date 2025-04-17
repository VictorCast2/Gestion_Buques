package com.app.controller;

import com.app.dto.request.AuthCreateUserRequest;
import com.app.dto.request.AuthLoginRequest;
import com.app.dto.response.AuthResponse;
import com.app.service.UserDetailServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUserRequest authCreateUserRequest) {
        return new ResponseEntity<>(userDetailService.createUser(authCreateUserRequest), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest) {
        return new ResponseEntity<>(userDetailService.loginUser(userRequest), HttpStatus.OK);
    }
}