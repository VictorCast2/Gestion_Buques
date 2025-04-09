package com.example.buques.docs.Proceso;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonTypeName("procesos")
@Document(collection = "procesos")
public class Proceso {

    @Id
    @BsonProperty("_id")
    private String id;

    @BsonProperty("tipoOperacion")
    private String tipoOperacion; // considerar trabajar este atributo como un Enum
    @BsonProperty("tipoCarga")
    private String tipoCarga; // considerar trabajar este atributo como un Enum
    @BsonProperty("tipoProducto")
    private String tipoProducto; // considerar trabajar este atributo como un Enum
    @BsonProperty("cantidad")
    private int cantidad;
    @BsonProperty("pesoTotal")
    private double pesoTotal;
    @BsonProperty("descripcion")
    private String descripcion;

    @BsonProperty("operadoresPortuariosId")
    private Set<String> operadoresPortuariosId = new HashSet<>();

}