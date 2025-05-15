package com.app.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class MasterController {
    @RequestMapping("")
    public String index() {
        return "redirect:/buques/";
    }
}