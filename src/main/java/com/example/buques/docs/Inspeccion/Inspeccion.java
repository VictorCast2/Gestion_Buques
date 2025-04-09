package com.example.buques.docs.Inspeccion;

import com.example.buques.docs.Buque.Buque;
import com.example.buques.docs.Buque.pojo.EResultado;
import com.example.buques.docs.Usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonTypeName("inspecciones")
@Document(collection = "inspecciones")
public class Inspeccion {

    @Id
    @BsonProperty("_id")
    private String id;

    @BsonProperty("fecha")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    @BsonProperty("resultado")
    private EResultado resultado;
    @BsonProperty("observaciones")
    private String observaciones;

    @DBRef
    @BsonProperty("inspector")
    private Usuario inspector;

    @DBRef
    @BsonProperty("buque")
    private Buque buque;

}