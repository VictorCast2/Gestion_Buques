package com.app.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record AtraqueRequest(@NotBlank String paisProcedencia,
                             @NotBlank String ciudadProcedencia,
                             @NotBlank String puertoProcedencia,
                             @NotBlank String paisDestino,
                             @NotBlank String ciudadDestino,
                             @NotBlank String puertoDestino,
                             @FutureOrPresent LocalDate fechaLlegada,
                             @FutureOrPresent LocalDate fechaSalida,
                             @Valid BuqueRequest buque) {
}
