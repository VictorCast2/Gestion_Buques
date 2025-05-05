package com.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

@Validated
public record ProcesoRequest(@NotBlank String tipoOperacion,
                             @NotBlank String tipoCarga,
                             @NotBlank String tipoProducto,
                             @Positive int cantidad,
                             @NotBlank String descripcion) {
}
