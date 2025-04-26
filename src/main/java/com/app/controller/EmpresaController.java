package com.app.controller;

import com.app.dto.request.EmpresaRequest;
import com.app.dto.response.MensajeResponse;
import com.app.service.EmpresaService;
import com.app.service.UserDetailServiceImpl;
import com.app.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Controller
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/registrar")
    public String registro(Model model) {
        return "Empresa";
    }

    @PostMapping("/registrar")
    public String registrarEmpresa(
            @ModelAttribute EmpresaRequest requestEmpresa,
            @RequestParam("correoUsuario") String correoUsuario,
            @RequestParam("foto") MultipartFile file,
            HttpServletRequest request
    ) {
        String token = jwtUtils.extractTokenFromRequest(request);
        MensajeResponse response = userDetailService.uploadImagenUsuario(file, token);
        empresaService.asignarEmpresa(requestEmpresa, correoUsuario);
        return "Empresa"; // Puedes cambiarlo si quieres redireccionar o dar feedback
    }

}