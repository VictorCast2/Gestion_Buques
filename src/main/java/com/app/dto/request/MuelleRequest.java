package com.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record MuelleRequest(@NotBlank String nombre,
                            @Positive int capacidadBuques,
                            @Positive int capacidad,
                            String estadoMuelle) {
}
