package com.example.buques.docs.Muelle.Enum;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Maquina {

    // considerar si trabajar máquina como un documento
    private String id;
    private String nombre;
    private EEstado estado;
    private int cantidad;
}
