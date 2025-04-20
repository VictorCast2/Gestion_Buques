package com.app.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"mensaje"})
public record EmpresaResponse(String mensaje) {
}