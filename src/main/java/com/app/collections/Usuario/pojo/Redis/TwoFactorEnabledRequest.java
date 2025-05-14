package com.app.collections.Usuario.pojo.Redis;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TwoFactorEnabledRequest {

    @NotBlank
    private String correo;

    private boolean twoFactorEnabled;

    @NotBlank
    private String pregunta1;

    @NotBlank
    private String respuesta1;

    @NotBlank
    private String pregunta2;

    @NotBlank
    private String respuesta2;

}