package com.app.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public record BuqueRequest(@NotBlank String matricula,
                           @NotBlank String nombre,
                           @NotBlank String tipoBuque,
                           @Valid DimensionRequest dimensiones) {
}
