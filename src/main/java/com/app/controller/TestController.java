package com.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "hello Word!";
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
