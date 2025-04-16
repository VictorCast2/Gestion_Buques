package com.app.collections.Atraque.pojo;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Buque {

    private String imagen; // considerar remover imagen
    private String matricula;
    private String nombre;
    @Field("tipo_buque")
    private String tipoBuque;
    private Dimension dimensiones;
}
