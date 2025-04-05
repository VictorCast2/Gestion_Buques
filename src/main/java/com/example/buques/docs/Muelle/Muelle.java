package com.example.buques.docs.Muelle;

import com.example.buques.docs.Muelle.pojo.EEstado;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "muelles")
public class Muelle {

    @Id
    private String id;
    private String nombre;
    @Field("capacidad_Buques")
    private int capacidadBuques; //numero de buques que soporta el muelle
    private int capacidad; // peso en toneladas
    private EEstado estado;
}
