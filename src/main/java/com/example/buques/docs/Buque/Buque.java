package com.example.buques.docs.Buque;

import com.example.buques.docs.Buque.pojo.Dimension;
import com.example.buques.docs.Muelle.Muelle;
import com.example.buques.docs.Proceso.Proceso;
import com.example.buques.docs.Usuario.Usuario;
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
@JsonTypeName("buques")
@Document(collection = "buques")
public class Buque {

    @Id
    @BsonProperty("_id")
    private String id;

    @BsonProperty("imagen")
    private String urlImagen; // URL de la imagen
    @BsonProperty("matricula")
    private String matricula;
    @BsonProperty("nombre")
    private String nombre;
    @BsonProperty("tipo_buque")
    private String tipoBuque;
    @BsonProperty("tipo_carga")
    private Dimension dimensiones;

    @DBRef
    @BsonProperty("agente_naviero")
    private Usuario agenteNaviero;

    @DBRef
    @BsonProperty("muelle")
    private Muelle muelle;

    @DBRef
    @BsonProperty("proceso")
    private Proceso proceso;

}