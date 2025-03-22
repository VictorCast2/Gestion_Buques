package com.example.buques.document.Proceso;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Muelle {

    private String id;
    private String nombre;
    private int cantidad;
    private int capacidad;
    private String estado; // este atributo se puede trabajar mejor como una clase enum
}
