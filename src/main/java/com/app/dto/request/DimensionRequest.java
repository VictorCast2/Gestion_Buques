package com.app.dto.request;

import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

@Validated
public record DimensionRequest(@Positive double peso,
                               @Positive double largo,
                               @Positive double ancho) {
}
