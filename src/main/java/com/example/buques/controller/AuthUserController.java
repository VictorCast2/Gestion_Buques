package com.example.buques.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
@RequestMapping("/auth")
public class AuthUserController {

    @GetMapping("/index")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // esto va a src/main/resources/templates/login.html
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
