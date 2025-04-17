package com.app.collections.Usuario;

import com.app.collections.Factura.Factura;
import com.app.collections.Usuario.pojo.Empresa;
import com.app.collections.Usuario.Enum.EIdentificacion;
import com.app.collections.Usuario.Enum.ERol;
import com.app.collections.Usuario.pojo.Inspeccion;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.HashSet;
import java.util.Set;

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
    private String numeroIdentificacion;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String correo;
    private String password;

    private ERol rol;
    private Empresa empresa;
    private Set<Inspeccion> inspecciones = new HashSet<>();

    @DBRef
    private Set<Factura> facturas = new HashSet<>();

    @Field(name = "is_enabled")
    private boolean isEnabled;

    @Field(name = "account_No_Expired")
    private boolean accountNoExpired;

    @Field(name = "account_No_Locked")
    private boolean accountNoLocked;

    @Field(name = "credential_No_Expired")
    private boolean credentialNoExpired;

}