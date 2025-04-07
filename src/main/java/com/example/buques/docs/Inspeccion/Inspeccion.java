package com.example.buques.docs.Inspeccion;

import com.example.buques.docs.Buque.Buque;
import com.example.buques.docs.Buque.pojo.EResultado;
import com.example.buques.docs.Usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "inspecciones")
public class Inspeccion {

    @Id
    private String id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    private EResultado resultado;
    private String observaciones;

    @DBRef
    private Usuario inspector;

    @DBRef
    private Buque buque;
}
