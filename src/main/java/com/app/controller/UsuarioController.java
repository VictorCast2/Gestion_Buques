package com.app.controller;

import com.app.dto.response.MensajeResponse;
import com.app.service.UserDetailServiceImpl;
import com.app.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Data
@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/actualizar-imagen")
    public ResponseEntity<MensajeResponse> actualizarImagen(@RequestParam("foto") MultipartFile file, HttpServletRequest request) {
        String token = jwtUtils.extractTokenFromRequest(request);
        MensajeResponse response = userDetailService.uploadImagenUsuario(file, token);
        return ResponseEntity.ok(response);
    }

}