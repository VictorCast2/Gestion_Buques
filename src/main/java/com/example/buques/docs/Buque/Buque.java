package com.example.buques.docs.Buque;

import com.example.buques.docs.Buque.pojo.Dimension;
import com.example.buques.docs.Muelle.Muelle;
import com.example.buques.docs.Proceso.Proceso;
import com.example.buques.docs.Usuario.Usuario;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "buques")
public class Buque {

    @Id
    private String id;
    private String imagen; // considerar remover imagen
    private String matricula;
    private String nombre;
    private String tipoBuque;
    private Dimension dimensiones;

    @DBRef
    @Field("agente_naviero")
    private Usuario agenteNaviero;

    @DBRef
    private Muelle muelle;

    @DBRef
    private Proceso proceso;
}
