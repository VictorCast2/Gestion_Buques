package com.example.buques.docs.Usuario;

import com.example.buques.docs.Empresa.Empresa;
import com.example.buques.docs.Usuario.Enum.EIdentificacion;
import com.example.buques.docs.Usuario.Enum.ERol;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
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
    @Field("tipo_identificacion")
    private EIdentificacion tipoIdentificacion;
    @Field("numero_identificacion")
    private int numeroIdentificacion;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String correo;
    private String password;

    private ERol rol;

    @DBRef
    private Empresa empresa;

    @Field(name = "is_enabled")
    private boolean isEnabled;

    @Field(name = "account_No_Expired")
    private boolean accountNoExpired;

    @Field(name = "account_No_Locked")
    private boolean accountNoLocked;

    @Field(name = "credential_No_Expired")
    private boolean credentialNoExpired;

}