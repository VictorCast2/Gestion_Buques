package com.example.buques.docs.SolicitudAtraque;

import com.example.buques.docs.Buque.Buque;
import com.example.buques.docs.Buque.pojo.EResultado;
import com.example.buques.docs.Usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolicitudAtraque {

    @Id
    private String id;

    @Field("fecha_llegada")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaLlegada;

    @Field("fecha_salida")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaSalida;

    @Field("estado_solicitud")
    private EResultado estadoSolicitud;

    @DBRef
    private Usuario administradorPortuario;

    @DBRef
    private Buque buque;
}
