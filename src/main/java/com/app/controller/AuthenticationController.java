package com.app.controller;

import com.app.collections.Usuario.Enum.EIdentificacion;
import com.app.dto.request.AuthCreateUserRequest;
import com.app.dto.request.AuthLoginRequest;
import com.app.dto.response.MensajeResponse;
import com.app.service.UserDetailServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
    public String postLogin(@ModelAttribute @Valid AuthLoginRequest authLoginRequest, HttpServletResponse response, Model model) {

        try {
            String tokenDeAcceso = this.userDetailService.loginUser(authLoginRequest);

            Cookie cookie = new Cookie("access_token", tokenDeAcceso);
            cookie.setHttpOnly(true); // para que no se pueda acceder a la cookie por JS
            cookie.setMaxAge(7200); // tiempo de expiración de la cookie en segundos (2hrs), coincide con la expiración del token
            cookie.setPath("/"); // asi nos aseguramos de que esté en todas las rutas del sitio web
            response.addCookie(cookie); // añadimos la cookie al HttpServletResponse

            model.addAttribute("mensaje", "Usuario Logueado Exitosamente");

            return "redirect:/"; // redirigimos a la página principal
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
        MensajeResponse response = this.userDetailService.createUser(authCreateUserRequest);
        model.addAttribute("tiposIdentificacion", EIdentificacion.values());
        model.addAttribute("mensajeExitoso", response.mensaje());
        return "redirect:/auth/login";
    }

}