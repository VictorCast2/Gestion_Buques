package com.example.buques.docs.Usuario;

import com.example.buques.docs.Empresa.Empresa;
import com.example.buques.docs.Usuario.Enum.EIdentificacion;
import com.example.buques.docs.Usuario.Enum.ERol;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "usuarios")
public class Usuario {

    @Id
    private String id;
    private EIdentificacion tipoIdentificacion;
    private int numeroIdentificacion;
    private String nombres;
    private String apellidos;
    private int telefono;
    private String correo;
    private String password;

    private ERol rol;

    @DBRef
    private Empresa empresa;

}