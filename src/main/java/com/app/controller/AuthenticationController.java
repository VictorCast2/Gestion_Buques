package com.app.controller;

import com.app.collections.Usuario.Enum.EIdentificacion;
import com.app.dto.request.AuthCreateUserRequest;
import com.app.dto.request.AuthLoginRequest;
import com.app.dto.response.AuthResponse;
import com.app.service.UserDetailServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @PostMapping("/login")
    public String postLogin(@ModelAttribute @Valid AuthLoginRequest authLoginRequest, Model model) {

        try {
            AuthResponse response = this.userDetailService.loginUser(authLoginRequest);
            model.addAttribute("mensaje", response.mensaje());
            return "Login";
        } catch (BadCredentialsException | UsernameNotFoundException exception) {
            model.addAttribute("mensaje", exception.getMessage());
            return "Login";
        }
    }

    @GetMapping("/registro")
    public String registro(Model model) {
        model.addAttribute("tiposIdentificacion", EIdentificacion.values());
        return "Registro";
    }

    @PostMapping("/registro")
    public String postRegister(@ModelAttribute @Valid AuthCreateUserRequest authCreateUserRequest, Model model) {
        AuthResponse response = this.userDetailService.createUser(authCreateUserRequest);
        model.addAttribute("tiposIdentificacion", EIdentificacion.values());
        model.addAttribute("mensajeExitoso", response.mensaje());
        return "Registro";
    }

}