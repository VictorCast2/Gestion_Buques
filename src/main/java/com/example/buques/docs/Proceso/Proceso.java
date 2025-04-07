package com.example.buques.docs.Proceso;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "procesos")
public class Proceso {

    @Id
    private String id;

    @Field("tipo_operacion")
    private String tipoOperacion; // considerar trabajar este atributo como un Enum
    private String tipoCarga; // considerar trabajar este atributo como un Enum
    private String tipoProducto; // considerar trabajar este atributo como un Enum
    private int cantidad;
    private double pesoTotal;
    private String descripcion;

    @Field("_idOperadores")
    private Set<String> operadoresPortuariosId = new HashSet<>();
}
