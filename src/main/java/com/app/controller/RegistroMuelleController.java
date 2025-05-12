package com.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.service.UserDetailServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import lombok.Data;

@Data
@Controller
@RequestMapping("/buques/registro-muelle")
public class RegistroMuelleController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @GetMapping("/")
    public String adminRegistroMuelle() {
        return "RegistroMuelle";
    }

}
