package com.app.controller;

import com.app.collections.Usuario.Enum.EIdentificacion;
import com.app.collections.Usuario.Usuario;
import com.app.dto.request.UpdatePasswordRequest;
import com.app.dto.request.UpdateUsuarioRequest;
import com.app.dto.response.AuthResponse;
import com.app.security.CustomUserDetails;
import com.app.service.UserDetailServiceImpl;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@Data
@Controller
@RequestMapping("/buques/admin")
public class AdminController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @GetMapping("/")
    public String adminHome() {
        return "Admin";
    }

    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "DashboardAdmin";
    }

    @GetMapping("/registro-muelle")
    public String adminRegistroMuelle() {
        return "RegistroMuelle";
    }

}