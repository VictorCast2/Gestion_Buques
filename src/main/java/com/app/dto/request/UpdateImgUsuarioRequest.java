package com.app.dto.request;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;

@JsonPropertyOrder({"imagen"})
public record UpdateImgUsuarioRequest(
        @NotBlank String imagen
) {
}