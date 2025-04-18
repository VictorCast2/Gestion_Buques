package com.app.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"mensaje"})
public record AuthResponse(String mensaje) {
}