package com.example.buques.document.Usuario;

import com.example.buques.document.Empresa.Empresa;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "usuarios")
public class Usuario {

    @Id
    private String id;
    private Integer cedula;
    private String nombres;
    private String apellidos;
    private int telefono;
    private String correo;
    private String password;
    private ERol rol;

    @DBRef
    private Empresa empresa;

}
