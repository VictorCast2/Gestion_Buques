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

    @PostMapping("/registrar")
    public String registrarEmpresa(
            @ModelAttribute EmpresaRequest requestEmpresa,  // Esto capturará los datos de la empresa
            @RequestParam("foto") MultipartFile file,      // Captura la foto enviada
            HttpServletRequest request                     // Captura la solicitud para obtener el token
    ) {
        // Extraer el token del JWT desde la solicitud
        String token = "";
        try {
            token = jwtUtils.extractTokenFromRequest(request);
            // Aquí puedes verificar si el token es válido
        } catch (RuntimeException e) {
            e.getMessage();
        }

        // Subir la imagen
        MensajeResponse response = userDetailService.uploadImagenUsuario(file, token);

        // Asignar la empresa al usuario correspondiente
        empresaService.asignarEmpresa(requestEmpresa, token);

        // Redirigir o dar feedback al usuario
        return "redirect:/buques/perfil";  // Puedes cambiarlo si quieres redireccionar a otra página
    }

}