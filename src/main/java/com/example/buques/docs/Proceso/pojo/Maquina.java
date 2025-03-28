package com.example.buques.docs.Proceso.pojo;

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
    private EEstado estado; // este atributo se puede trabajar mejor como una clase enum
    private int cantidad;
}
