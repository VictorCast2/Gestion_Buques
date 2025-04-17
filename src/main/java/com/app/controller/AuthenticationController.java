package com.app.controller;

import com.app.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @GetMapping("/login")
    public String login() {
        return "Login";
    }

    @GetMapping("/registro")
    public String registro() {
        return "Registro";
    }

}