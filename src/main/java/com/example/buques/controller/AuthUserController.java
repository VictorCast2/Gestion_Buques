package com.example.buques.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthUserController {

    @GetMapping("/hello")
    public String index() {
        return "index";
    }

    @GetMapping("/hello-protegido")
    public String helloProtegido() {
        return "hello Word! - Protegido";
    }

    @GetMapping("/admin")
    public String admin() {
        return "hello Word! - Admin";
    }

    @GetMapping("/inspector")
    public String inspector() {
        return "hello Word! - Inspector";
    }
}
