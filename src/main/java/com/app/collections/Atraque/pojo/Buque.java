package com.app.collections.Atraque.pojo;

import com.app.collections.Atraque.Enum.ETipoBuque;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Buque {

    private String matricula;
    private String nombre;
    @Field("tipo_buque")
    private ETipoBuque tipoBuque;
    private Dimension dimensiones;
}
