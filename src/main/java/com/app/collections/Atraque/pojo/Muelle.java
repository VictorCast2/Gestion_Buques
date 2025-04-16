package com.app.collections.Atraque.pojo;

import com.app.collections.Atraque.Enum.EEstado;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Muelle {

    private String nombre;
    @Field("capacidad_Buques")
    private int capacidadBuques; // numero de buques que soporta el muelle
    private int capacidad; // peso en toneladas
    private EEstado estado;
}
