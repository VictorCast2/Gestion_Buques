package com.example.buques.docs.Buque.pojo;

import com.example.buques.docs.Usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inspeccion {

    private String id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    private String resultado; // este atributo se puede trabajar mejor como una clase enum
    private String observaciones;

    @DBRef
    private Usuario inspector;
}
