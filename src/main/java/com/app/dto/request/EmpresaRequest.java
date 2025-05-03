package com.app.dto.request;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

@Validated
public record EmpresaRequest(
        MultipartFile imagen,
        String imagenOriginal,
        String nit,
        String nombre,
        String pais,
        String ciudad,
        String direccion,
        String telefono,
        String correo) {
}