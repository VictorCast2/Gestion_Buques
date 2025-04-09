package com.example.buques.docs.SolicitudAtraque;

import com.example.buques.docs.Buque.Buque;
import com.example.buques.docs.Buque.pojo.EResultado;
import com.example.buques.docs.Usuario.Usuario;
import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonTypeName("solicitudAtraque")
@Document(collection = "solicitudAtraque")
public class SolicitudAtraque {

    @Id
    @BsonProperty("_id")
    private String id;

    @Field("fecha_llegada")
    @BsonProperty("fecha_llegada")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaLlegada;

    @Field("fecha_salida")
    @BsonProperty("fecha_salida")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaSalida;

    @Field("estado_solicitud")
    @BsonProperty("estado_solicitud")
    private EResultado estadoSolicitud;

    @DBRef
    @BsonProperty("administradorPortuario")
    private Usuario administradorPortuario;

    @DBRef
    @BsonProperty("buque")
    private Buque buque;

}