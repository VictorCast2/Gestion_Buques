package com.app.controller;

import com.app.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/buques/admin")
public class AdminController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @GetMapping("/")
    public String adminHome() {
        return "Admin";
    }

}