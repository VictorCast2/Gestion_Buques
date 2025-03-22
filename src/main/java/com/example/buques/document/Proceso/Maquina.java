package com.example.buques.document.Proceso;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Maquina {

    private String id;
    private String nombre;
    private String estado; // este atributo se puede trabajar mejor como una clase enum
    private int cantidad;
}
