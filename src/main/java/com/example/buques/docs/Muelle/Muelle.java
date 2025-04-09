package com.example.buques.docs.Muelle;

import com.example.buques.docs.Muelle.Enum.EEstado;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonTypeName("muelles")
@Document(collection = "muelles")
public class Muelle {

    @Id
    @BsonProperty("_id")
    private String id;

    @BsonProperty("nombre")
    private String nombre;
    @BsonProperty("capacidad_buques")
    private int capacidadBuques; //numero de buques que soporta el muelle
    @BsonProperty("capacidad")
    private int capacidad; // peso en toneladas
    @BsonProperty("estado")
    private EEstado estado;

}