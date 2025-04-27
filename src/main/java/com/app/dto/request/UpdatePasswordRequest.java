package com.app.dto.request;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;

@JsonPropertyOrder({"currentPassword", "newPassword"})
public record UpdatePasswordRequest(@NotBlank String currentPassword,
                                    @NotBlank String newPassword) {
}