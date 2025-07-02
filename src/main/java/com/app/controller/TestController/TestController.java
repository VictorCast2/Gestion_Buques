package com.app.controller.TestController;

import org.springframework.web.bind.annotation.*;

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
