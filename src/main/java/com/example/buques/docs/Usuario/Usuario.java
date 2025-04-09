package com.example.buques.docs.Usuario;

import com.example.buques.docs.Empresa.Empresa;
import com.example.buques.docs.Usuario.Enum.EIdentificacion;
import com.example.buques.docs.Usuario.Enum.ERol;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonTypeName("usuarios")
@Document(collection = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @BsonProperty("_id")
    private String id;

    @BsonProperty("tipo_identificacion")
    @Field("tipo_identificacion")
    private EIdentificacion tipoIdentificacion;
    @BsonProperty("numero_identificacion")
    @Field("numero_identificacion")
    private int numeroIdentificacion;
    @BsonProperty("nombres")
    private String nombres;
    @BsonProperty("apellidos")
    private String apellidos;
    @BsonProperty("telefono")
    private String telefono;
    @BsonProperty("correo")
    private String correo;
    @BsonProperty("password")
    private String password;

    @BsonProperty("rol")
    private ERol rol;

    @DBRef
    @BsonProperty("empresa")
    private Empresa empresa;

    @BsonProperty("is_enabled")
    @Field(name = "is_enabled")
    private boolean isEnabled;

    @BsonProperty("account_No_Expired")
    @Field(name = "account_No_Expired")
    private boolean accountNoExpired;

    @BsonProperty("account_No_Locked")
    @Field(name = "account_No_Locked")
    private boolean accountNoLocked;

    @BsonProperty("credential_No_Expired")
    @Field(name = "credential_No_Expired")
    private boolean credentialNoExpired;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.correo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNoExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNoLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialNoExpired;
    }

}