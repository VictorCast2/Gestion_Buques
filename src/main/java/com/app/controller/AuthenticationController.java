package com.app.controller;

import com.app.collections.Usuario.Enum.EIdentificacion;
import com.app.dto.request.AuthCreateUserRequest;
import com.app.dto.request.AuthLoginRequest;
import com.app.dto.response.AuthResponse;
import com.app.service.UserDetailServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @ResponseStatus(value = HttpStatus.CREATED)
    public String postLogin(@ModelAttribute @Valid AuthLoginRequest authLoginRequest, HttpServletResponse response, Model model) {

        try {
            String tokenDeAcceso = this.userDetailService.loginUser(authLoginRequest);

            // Creación de la cookie para JWT
            Cookie cookie = new Cookie("access_token", tokenDeAcceso);
            cookie.setHttpOnly(true); // para que no se pueda acceder a la cookie por JS
            cookie.setMaxAge(7200); // tiempo de expiración de la cookie en segundos (2hrs), coincide con la expiración del token
            cookie.setPath("/"); // asi nos aseguramos de que esté en todas las rutas del sitio web
            response.addCookie(cookie); // añadimos la cookie al HttpServletResponse

            model.addAttribute("mensajeExitoso", "Inicio de sesión exitoso");

            return "Login";
        } catch (BadCredentialsException | UsernameNotFoundException exception) {
            model.addAttribute("mensajeError", exception.getMessage());
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
        model.addAttribute("mensajeExitoso", response.mensaje());
        return "redirect:/auth/login";
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("access_token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

}