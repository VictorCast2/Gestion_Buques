package com.app.controller;

import com.app.service.UserDetailServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Data
@Controller
@RequestMapping("/buques/admin")
public class AdminController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @GetMapping("/")
    public String adminHome() {
        return "Admin";
    }

    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "DashboardAdmin";
    }

    @GetMapping("/registro-muelle")
    public String adminRegistroMuelle() {
        return "RegistroMuelle";
    }

}