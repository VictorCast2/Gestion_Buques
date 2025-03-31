package com.example.buques.docs.Usuario.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EIdentificacion {
    CC("Cédula de Ciudadanía"),
    CE("Cédula de Extranjería"),
    DE("Documento de Extranjería"),
    NIT("Número de Identificación Tributaria"),
    PP("Pasaporte"),
    RUT("Registro Único Tributario"),
    TE("Tarjeta de Extranjería"),
    TI("Tarjeta de Identidad");

    private final String descripcion;
}
