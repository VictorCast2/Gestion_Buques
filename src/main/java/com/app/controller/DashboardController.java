package com.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/buques/dashboard-admin")
public class DashboardController {

    @GetMapping("/")
    public String adminDashboard() {
        return "Dashboard";
    }
}
